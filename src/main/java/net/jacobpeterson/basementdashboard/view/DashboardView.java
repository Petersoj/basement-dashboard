package net.jacobpeterson.basementdashboard.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import net.jacobpeterson.basementdashboard.BasementDashboard;
import net.jacobpeterson.basementdashboard.view.component.BackgroundVideo;
import net.jacobpeterson.basementdashboard.view.component.Clock;

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
        componentPane.getChildren().add(backgroundVideo.getMediaView());
    }

    private void setupClock() {
        clock = new Clock(this);
        componentPane.getChildren().add(clock.getContainer());
    }

    private void setupStage() {
        stage.setMinWidth(300);
        stage.setMinHeight(200);
        stage.setFullScreen(true);
        stage.setTitle("Basement Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stops {@link DashboardView}.
     */
    public void stop() {}

    public BasementDashboard getBasementDashboard() {
        return basementDashboard;
    }
}
