package net.jacobpeterson.basementdashboard;

import javafx.application.Application;
import javafx.stage.Stage;
import net.jacobpeterson.basementdashboard.data.BackgroundVideoData;
import net.jacobpeterson.basementdashboard.data.DateTimeData;
import net.jacobpeterson.basementdashboard.data.WeatherData;
import net.jacobpeterson.basementdashboard.http.client.HTTPClient;
import net.jacobpeterson.basementdashboard.http.server.HTTPServer;
import net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil.showException;

/**
 * {@link BasementDashboard} is the main class for this application.
 */
public class BasementDashboard extends Application {

    private HTTPClient httpClient;
    private BackgroundVideoData backgroundVideoData;
    private DateTimeData dateTimeData;
    private WeatherData weatherData;
    private HTTPServer httpServer;
    private DashboardView dashboardView;

    @Override
    public void init() {
        httpClient = new HTTPClient();
        backgroundVideoData = new BackgroundVideoData();
        dateTimeData = new DateTimeData();
        weatherData = new WeatherData(this);
        httpServer = new HTTPServer(this);
        dashboardView = new DashboardView(this);
    }

    @Override
    public void start(Stage primaryStage) {
        ExceptionUtil.PRIMARY_STAGE = primaryStage;

        try {
            httpClient.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }
        try {
            backgroundVideoData.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }
        try {
            dateTimeData.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }
        try {
            weatherData.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }
        try {
            httpServer.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }

        dashboardView.start(primaryStage);
    }

    @Override
    public void stop() {
        dashboardView.stop();

        // Stop in reverse order
        try {
            httpServer.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            weatherData.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            dateTimeData.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            backgroundVideoData.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            httpClient.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public HTTPClient getHTTPClient() {
        return httpClient;
    }

    public BackgroundVideoData getBackgroundVideoData() {
        return backgroundVideoData;
    }

    public DateTimeData getDateTimeData() {
        return dateTimeData;
    }

    public WeatherData getWeatherData() {
        return weatherData;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
