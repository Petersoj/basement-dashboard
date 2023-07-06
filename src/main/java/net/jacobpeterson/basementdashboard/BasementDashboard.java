package net.jacobpeterson.basementdashboard;

import javafx.application.Application;
import javafx.stage.Stage;
import net.jacobpeterson.basementdashboard.data.BackgroundVideoData;
import net.jacobpeterson.basementdashboard.data.ClockData;
import net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil.showException;

/**
 * {@link BasementDashboard} is the main class for this application.
 */
public class BasementDashboard extends Application {

    private BackgroundVideoData backgroundVideoData;
    private ClockData clockData;
    private DashboardView dashboardView;

    @Override
    public void init() {
        backgroundVideoData = new BackgroundVideoData();
        clockData = new ClockData();
        dashboardView = new DashboardView(this);
    }

    @Override
    public void start(Stage primaryStage) {
        ExceptionUtil.PRIMARY_STAGE = primaryStage;

        // Load data
        try {
            backgroundVideoData.start();
        } catch (Exception exception) {
            showException(exception.getMessage());
            stop();
            return;
        }
        try {
            clockData.start();
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

        // Stop data in reverse order
        try {
            clockData.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        try {
            backgroundVideoData.stop();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public BackgroundVideoData getBackgroundVideoData() {
        return backgroundVideoData;
    }

    public ClockData getClockData() {
        return clockData;
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
