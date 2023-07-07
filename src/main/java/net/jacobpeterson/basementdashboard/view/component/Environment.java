package net.jacobpeterson.basementdashboard.view.component;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.WHITE;
import static net.jacobpeterson.basementdashboard.util.view.FontUtil.interFont;

/**
 * {@link Environment} is the environment component.
 */
public class Environment {

    private final HBox container;

    /**
     * Instantiates a new {@link Environment}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Environment(DashboardView dashboardView) {
        final Label locationLabel = newLabel();
        locationLabel.setText("Draper, Utah");

        final Label temperatureLabel = newLabel();
        dashboardView.getBasementDashboard().getWeatherData().addOnTemperatureUpdate(temperature ->
                Platform.runLater(() -> temperatureLabel.setText(temperature)));

        final Label shortForecastLabel = newLabel();
        dashboardView.getBasementDashboard().getWeatherData().addOnShortForecastUpdate(shortForecast ->
                Platform.runLater(() -> shortForecastLabel.setText(shortForecast)));

        final HBox forecastHBox = new HBox(35, temperatureLabel, shortForecastLabel);
        forecastHBox.setAlignment(CENTER);
        forecastHBox.setFillHeight(true);
        container = new HBox(150, locationLabel, forecastHBox);
        container.setAlignment(CENTER);
        container.setFillHeight(true);
    }

    private Label newLabel() {
        final Label label = new Label();
        label.setFont(interFont(40));
        label.setAlignment(CENTER);
        label.setTextFill(WHITE);
        return label;
    }

    public HBox getContainer() {
        return container;
    }
}
