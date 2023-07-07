package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.WHITE;
import static net.jacobpeterson.basementdashboard.util.view.FontUtil.interFont;

/**
 * {@link Clock} is the clock component.
 */
public class Clock {

    private final HBox container;

    /**
     * Instantiates a new {@link Clock}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Clock(DashboardView dashboardView) {
        final Label hourLabel = newLabel();
        final Label minuteLabel = newLabel();
        dashboardView.getBasementDashboard().getDateTimeData().addOnTimeMinuteUpdate((hour, minute) -> runLater(() -> {
            hourLabel.setText(hour);
            minuteLabel.setText(minute);
        }));
        final Label colonLabel = newLabel();
        colonLabel.setText(":");

        container = new HBox(hourLabel, colonLabel, minuteLabel);
        container.setAlignment(CENTER);
        container.setFillHeight(true);

        colonLabel.translateYProperty().bind(container.heightProperty().multiply(-0.03));
    }

    private Label newLabel() {
        final Label label = new Label();
        label.setFont(interFont(400));
        label.setAlignment(CENTER);
        label.setTextFill(WHITE);
        return label;
    }

    public HBox getContainer() {
        return container;
    }
}
