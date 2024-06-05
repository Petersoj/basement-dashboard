package net.jacobpeterson.basementdashboard.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.jacobpeterson.basementdashboard.BasementDashboard;
import net.jacobpeterson.basementdashboard.view.component.BackgroundVideo;
import net.jacobpeterson.basementdashboard.view.component.Clock;
import net.jacobpeterson.basementdashboard.view.component.Date;
import net.jacobpeterson.basementdashboard.view.component.Environment;

import static java.lang.Thread.sleep;
import static javafx.application.Platform.runLater;
import static javafx.scene.Cursor.NONE;
import static javafx.scene.paint.Color.BLACK;

/**
 * {@link DashboardView} is the main view of the dashboard.
 */
public class DashboardView {

    private final BasementDashboard basementDashboard;

    private StackPane componentPane;
    private Scene scene;
    private Stage stage;

    private BackgroundVideo backgroundVideo;
    private Clock clock;
    private Date date;
    private Environment environment;

    /**
     * Instantiates a new {@link DashboardView}.
     *
     * @param basementDashboard the {@link BasementDashboard}
     */
    public DashboardView(BasementDashboard basementDashboard) {
        this.basementDashboard = basementDashboard;
    }

    /**
     * Starts {@link DashboardView}.
     *
     * @param primaryStage the primary {@link Stage}
     */
    public void start(Stage primaryStage) {
        stage = primaryStage;

        setupScene();
        setupBackgroundVideo();
        setupClock();
        setupDate();
        setupEnvironment();
        setupStage();
    }

    private void setupScene() {
        componentPane = new StackPane();
        scene = new Scene(componentPane);
        scene.setFill(BLACK);
        scene.setCursor(NONE);
    }

    private void setupBackgroundVideo() {
        backgroundVideo = new BackgroundVideo(this);
        componentPane.getChildren().add(backgroundVideo.getImageView());
    }

    private void setupClock() {
        clock = new Clock(this);
        componentPane.getChildren().add(clock.getContainer());
    }

    private void setupDate() {
        date = new Date(this);
        date.getContainer().setTranslateY(-260);
        componentPane.getChildren().add(date.getContainer());
    }

    private void setupEnvironment() {
        environment = new Environment(this);
        environment.getContainer().setTranslateY(260);
        componentPane.getChildren().add(environment.getContainer());
    }

    private void setupStage() {
        stage.setTitle("Basement Dashboard");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        new Thread(() -> {
            try {
                sleep(2000);
            } catch (InterruptedException ignored) {}
            runLater(() -> stage.setFullScreen(true));
        }).start();
    }

    /**
     * Stops {@link DashboardView}.
     */
    public void stop() {
        backgroundVideo.stop();
    }

    public BasementDashboard getBasementDashboard() {
        return basementDashboard;
    }

    public BackgroundVideo getBackgroundVideo() {
        return backgroundVideo;
    }
}
