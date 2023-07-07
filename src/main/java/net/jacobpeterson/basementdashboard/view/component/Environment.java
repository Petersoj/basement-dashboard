package net.jacobpeterson.basementdashboard.view.component;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static net.jacobpeterson.basementdashboard.util.view.CacheUtil.enableCaching;
import static net.jacobpeterson.basementdashboard.util.view.FontUtil.interFont;

/**
 * {@link Environment} is the environment component.
 */
public class Environment {

    private final DashboardView dashboardView;
    private final StackPane container;

    /**
     * Instantiates a new {@link Environment}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Environment(DashboardView dashboardView) {
        this.dashboardView = dashboardView;

        final HBox foreground = newEnvironmentHBox(WHITE);
        final HBox background = newEnvironmentHBox(BLACK);
        background.setEffect(new GaussianBlur(15));
        background.setOpacity(0.5);

        container = new StackPane(background, foreground);
        enableCaching(container);
    }

    private HBox newEnvironmentHBox(Color fill) {
        final Label locationLabel = newLabel(fill);
        locationLabel.setText("Draper, Utah");

        final Label temperatureLabel = newLabel(fill);
        dashboardView.getBasementDashboard().getWeatherData().addOnTemperatureUpdate(temperature ->
                Platform.runLater(() -> temperatureLabel.setText(temperature)));

        final Label shortForecastLabel = newLabel(fill);
        dashboardView.getBasementDashboard().getWeatherData().addOnShortForecastUpdate(shortForecast ->
                Platform.runLater(() -> shortForecastLabel.setText(shortForecast)));

        final HBox forecastHBox = new HBox(35, temperatureLabel, shortForecastLabel);
        forecastHBox.setAlignment(CENTER);
        forecastHBox.setFillHeight(true);
        final HBox environmentHBox = new HBox(150, locationLabel, forecastHBox);
        environmentHBox.setAlignment(CENTER);
        environmentHBox.setFillHeight(true);
        return environmentHBox;
    }

    private Label newLabel(Color fill) {
        final Label label = new Label();
        label.setFont(interFont(40));
        label.setAlignment(CENTER);
        label.setTextFill(fill);
        return label;
    }

    public StackPane getContainer() {
        return container;
    }
}
