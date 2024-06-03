package net.jacobpeterson.basementdashboard.data;

import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import net.jacobpeterson.basementdashboard.BasementDashboard;
import okhttp3.Request;
import okhttp3.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import static java.time.Duration.between;
import static java.time.Duration.ofMinutes;
import static java.time.LocalDateTime.now;
import static java.util.Collections.synchronizedList;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil.showException;
import static net.jacobpeterson.basementdashboard.util.gson.GsonUtil.GSON;

/**
 * {@link WeatherData} controls weather data.
 */
public class WeatherData {

    private final BasementDashboard basementDashboard;
    private final List<Consumer<String>> onTemperatureUpdates;
    private final List<Consumer<String>> onShortForecastUpdates;
    private ScheduledExecutorService scheduledExecutorService;
    private String temperature;
    private String shortForecast;

    /**
     * Instantiates a new {@link WeatherData}.
     *
     * @param basementDashboard the {@link BasementDashboard}
     */
    public WeatherData(BasementDashboard basementDashboard) {
        this.basementDashboard = basementDashboard;
        onTemperatureUpdates = synchronizedList(new ArrayList<>());
        onShortForecastUpdates = synchronizedList(new ArrayList<>());
    }

    /**
     * Starts {@link WeatherData}.
     */
    public void start() {
        scheduledExecutorService = newSingleThreadScheduledExecutor(runnable -> new Thread(runnable, "Weather Data"));
        final LocalDateTime now = now();
        scheduledExecutorService.scheduleAtFixedRate(this::pollWeather,
                between(now, now.plusHours(1).withMinute(1).withSecond(0).withNano(0)).toMillis(),
                ofMinutes(15).toMillis(), MILLISECONDS);
        scheduledExecutorService.execute(this::pollWeather);
    }

    /**
     * Stops {@link WeatherData}.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void stop() {
        if (scheduledExecutorService != null) {
            scheduledExecutorService.shutdown();
            try {
                scheduledExecutorService.awaitTermination(10, SECONDS);
            } catch (InterruptedException ignored) {}
        }
    }

    private void pollWeather() {
        // Request data
        final Request request = new Request.Builder()
                .url("https://api.weather.gov/gridpoints/VEF/116,99/forecast/hourly")
                .get()
                .build();
        try (Response response = basementDashboard.getHTTPClient().getOkHttpClient().newCall(request).execute()) {
            // Parse data
            final JsonObject root = GSON.fromJson(new JsonReader(response.body().charStream()), JsonObject.class);
            final JsonObject temperatureObject = root.getAsJsonObject("properties").getAsJsonArray("periods")
                    .get(0).getAsJsonObject();
            temperature = temperatureObject.getAsJsonPrimitive("temperature").getAsString() + "℉";
            shortForecast = temperatureObject.getAsJsonPrimitive("shortForecast").getAsString();
        } catch (Exception exception) {
            temperature = "";
            shortForecast = "*Weather Data Error*";
            showException(exception);
        }

        // Call listeners
        synchronized (onTemperatureUpdates) {
            for (Consumer<String> onTemperatureUpdate : onTemperatureUpdates) {
                onTemperatureUpdate(onTemperatureUpdate);
            }
        }
        synchronized (onShortForecastUpdates) {
            for (Consumer<String> onShortForecastUpdate : onShortForecastUpdates) {
                onShortForecastUpdate(onShortForecastUpdate);
            }
        }
    }

    private void onTemperatureUpdate(Consumer<String> onTemperatureUpdate) {
        if (temperature != null) {
            onTemperatureUpdate.accept(temperature);
        }
    }

    private void onShortForecastUpdate(Consumer<String> onShortForecastUpdate) {
        if (shortForecast != null) {
            onShortForecastUpdate.accept(shortForecast);
        }
    }

    public void addOnTemperatureUpdate(Consumer<String> onTemperatureUpdate) {
        onTemperatureUpdates.add(onTemperatureUpdate);
        onTemperatureUpdate(onTemperatureUpdate);
    }

    public void addOnShortForecastUpdate(Consumer<String> onShortForecastUpdate) {
        onShortForecastUpdates.add(onShortForecastUpdate);
        onShortForecastUpdate(onShortForecastUpdate);
    }
}
