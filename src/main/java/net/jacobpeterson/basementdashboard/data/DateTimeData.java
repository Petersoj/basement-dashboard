package net.jacobpeterson.basementdashboard.data;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.time.Duration.between;
import static java.time.Duration.ofDays;
import static java.time.Duration.ofMinutes;
import static java.time.LocalDateTime.now;
import static java.time.format.TextStyle.FULL;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_WEEK;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.time.temporal.ChronoField.MONTH_OF_YEAR;
import static java.util.Collections.synchronizedList;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * {@link DateTimeData} controls date and time data.
 */
public class DateTimeData {

    private static final DateTimeFormatter HOUR_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(CLOCK_HOUR_OF_AMPM)
            .toFormatter();
    private static final DateTimeFormatter MINUTE_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();
    private static final DateTimeFormatter DAY_OF_WEEK_FORMATTER = new DateTimeFormatterBuilder()
            .appendText(DAY_OF_WEEK, FULL)
            .toFormatter();
    private static final DateTimeFormatter MONTH_OF_YEAR_FORMATTER = new DateTimeFormatterBuilder()
            .appendText(MONTH_OF_YEAR, FULL)
            .toFormatter();
    private static final DateTimeFormatter DAY_OF_MONTH_FORMATTER = new DateTimeFormatterBuilder()
            .appendText(DAY_OF_MONTH)
            .toFormatter();

    private static final Supplier<Boolean> SNOOZE_ON_CHECK = () -> {
        final LocalTime now = LocalTime.now();
        return now.getHour() == 1 && now.getMinute() == 0;
    };
    private static final Supplier<Boolean> SNOOZE_OFF_CHECK = () -> {
        final LocalTime now = LocalTime.now();
        return now.getHour() == 6 && now.getMinute() == 0;
    };

    private final List<BiConsumer<String, String>> onTimeMinuteUpdates;
    private final List<Consumer<String>> onDayOfWeekUpdates;
    private final List<BiConsumer<String, String>> onDayOfMonthUpdates;
    private final List<Consumer<Boolean>> onSnoozeUpdates;
    private ScheduledExecutorService scheduledExecutorService;

    /**
     * Instantiates a new {@link DateTimeData}.
     */
    public DateTimeData() {
        onTimeMinuteUpdates = synchronizedList(new ArrayList<>());
        onDayOfWeekUpdates = synchronizedList(new ArrayList<>());
        onDayOfMonthUpdates = synchronizedList(new ArrayList<>());
        onSnoozeUpdates = synchronizedList(new ArrayList<>());
    }

    /**
     * Starts {@link DateTimeData}.
     */
    public void start() {
        scheduledExecutorService = newSingleThreadScheduledExecutor(runnable -> new Thread(runnable, "Date Time Data"));
        final LocalDateTime now = now();
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    synchronized (onTimeMinuteUpdates) {
                        for (BiConsumer<String, String> onTimeMinuteUpdate : onTimeMinuteUpdates) {
                            onTimeMinuteUpdate(onTimeMinuteUpdate);
                        }
                    }
                    final boolean snoozeOnCheck = SNOOZE_ON_CHECK.get();
                    final boolean snoozeOffCheck = SNOOZE_OFF_CHECK.get();
                    if (snoozeOnCheck || snoozeOffCheck) {
                        synchronized (onSnoozeUpdates) {
                            for (Consumer<Boolean> onSnoozeUpdate : onSnoozeUpdates) {
                                onSnoozeUpdate(onSnoozeUpdate);
                            }
                        }
                    }
                }, between(now, now.plusMinutes(1).withSecond(0).withNano(0)).toMillis(),
                ofMinutes(1).toMillis(), MILLISECONDS);
        scheduledExecutorService.scheduleAtFixedRate(() -> {
                    synchronized (onDayOfWeekUpdates) {
                        for (Consumer<String> onDayOfWeekUpdate : onDayOfWeekUpdates) {
                            onDayOfWeekUpdate(onDayOfWeekUpdate);
                        }
                    }
                    synchronized (onDayOfMonthUpdates) {
                        for (BiConsumer<String, String> onDayOfMonthUpdate : onDayOfMonthUpdates) {
                            onDayOfMonthUpdate(onDayOfMonthUpdate);
                        }
                    }
                },
                between(now, now.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0)).toMillis(),
                ofDays(1).toMillis(), MILLISECONDS);
    }

    /**
     * Stops {@link DateTimeData}.
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

    private void onTimeMinuteUpdate(BiConsumer<String, String> onTimeMinuteUpdate) {
        final LocalDateTime now = now();
        onTimeMinuteUpdate.accept(HOUR_FORMATTER.format(now), MINUTE_FORMATTER.format(now));
    }

    private void onSnoozeUpdate(Consumer<Boolean> onSnoozeUpdate) {
        onSnoozeUpdate.accept(SNOOZE_ON_CHECK.get());
    }

    private void onDayOfWeekUpdate(Consumer<String> onDayOfWeekUpdate) {
        onDayOfWeekUpdate.accept(DAY_OF_WEEK_FORMATTER.format(now()));
    }

    private void onDayOfMonthUpdate(BiConsumer<String, String> onDayOfMonthUpdate) {
        final LocalDateTime now = now();
        onDayOfMonthUpdate.accept(MONTH_OF_YEAR_FORMATTER.format(now), DAY_OF_MONTH_FORMATTER.format(now));
    }

    public void addOnTimeMinuteUpdate(BiConsumer<String, String> onTimeMinuteUpdate) {
        onTimeMinuteUpdates.add(onTimeMinuteUpdate);
        onTimeMinuteUpdate(onTimeMinuteUpdate);
    }

    public void addOnSnoozeUpdate(Consumer<Boolean> onSnoozeUpdate) {
        onSnoozeUpdates.add(onSnoozeUpdate);
        onSnoozeUpdate(onSnoozeUpdate);
    }

    public void addOnDayOfWeekUpdate(Consumer<String> onDayOfWeekUpdate) {
        onDayOfWeekUpdates.add(onDayOfWeekUpdate);
        onDayOfWeekUpdate(onDayOfWeekUpdate);
    }

    public void addOnDayOfMonthUpdate(BiConsumer<String, String> onDayOfMonthUpdate) {
        onDayOfMonthUpdates.add(onDayOfMonthUpdate);
        onDayOfMonthUpdate(onDayOfMonthUpdate);
    }
}
