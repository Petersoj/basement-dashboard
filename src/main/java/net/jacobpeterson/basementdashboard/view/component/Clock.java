package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.WHITE;
import static javafx.scene.text.Font.font;

/**
 * {@link Clock} is the clock component.
 */
public class Clock {

    private final DashboardView dashboardView;
    private final Label clockLabel;
    private final HBox container;

    /**
     * Instantiates a new {@link Clock}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Clock(DashboardView dashboardView) {
        this.dashboardView = dashboardView;

        clockLabel = new Label();
        clockLabel.setFont(font(300));
        clockLabel.setAlignment(CENTER);
        clockLabel.setTextFill(WHITE);
        dashboardView.getBasementDashboard().getClockData()
                .setOnClockStringUpdate(string -> runLater(() -> this.setClockLabelText(string)));

        container = new HBox(clockLabel);
        container.setAlignment(CENTER);
        container.setFillHeight(true);
    }

    private void setClockLabelText(String text) {
        clockLabel.setText(text);
    }

    public HBox getContainer() {
        return container;
    }
}
