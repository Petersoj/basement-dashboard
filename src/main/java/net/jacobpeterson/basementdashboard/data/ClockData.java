package net.jacobpeterson.basementdashboard.data;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoField.CLOCK_HOUR_OF_AMPM;
import static java.time.temporal.ChronoField.MINUTE_OF_HOUR;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * {@link ClockData} controls clock data.
 */
public class ClockData {

    private static final DateTimeFormatter STANDARD_TIME_FORMATTER = new DateTimeFormatterBuilder()
            .appendValue(CLOCK_HOUR_OF_AMPM)
            .appendLiteral(':')
            .appendValue(MINUTE_OF_HOUR, 2)
            .toFormatter();

    private ScheduledExecutorService scheduledExecutorService;
    private Consumer<String> onClockStringUpdate;

    /**
     * Starts {@link ClockData}.
     */
    public void start() {
        scheduledExecutorService = newSingleThreadScheduledExecutor(runnable -> new Thread(runnable, "Clock"));
        scheduledExecutorService.scheduleAtFixedRate(this::updateClock, now().getSecond(), 60, SECONDS);
        updateClock();
    }

    /**
     * Stops {@link ClockData}.
     */
    public void stop() {
        scheduledExecutorService.shutdown();
    }

    private void updateClock() {
        if (onClockStringUpdate != null) {
            onClockStringUpdate.accept(STANDARD_TIME_FORMATTER.format(now()));
        }
    }

    public void setOnClockStringUpdate(Consumer<String> onClockStringUpdate) {
        this.onClockStringUpdate = onClockStringUpdate;
        updateClock();
    }
}
