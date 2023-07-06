package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.BLACK;
import static javafx.scene.paint.Color.WHITE;
import static net.jacobpeterson.basementdashboard.util.view.FontUtil.interFont;

/**
 * {@link Clock} is the clock component.
 */
public class Clock {

    private final DashboardView dashboardView;
    private final StackPane container;

    /**
     * Instantiates a new {@link Clock}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Clock(DashboardView dashboardView) {
        this.dashboardView = dashboardView;

        final HBox foreground = newClockHBox(WHITE);
        final HBox background = newClockHBox(BLACK);
        background.setEffect(new GaussianBlur(150));
        background.setOpacity(0.7);
        container = new StackPane(background, foreground);
    }

    private HBox newClockHBox(Color fill) {
        final Label hourLabel = newLabel(fill);
        final Label minuteLabel = newLabel(fill);
        dashboardView.getBasementDashboard().getDateTimeData().addOnTimeMinuteUpdate((hour, minute) -> runLater(() -> {
            hourLabel.setText(hour);
            minuteLabel.setText(minute);
        }));
        final Label colonLabel = newLabel(fill);
        colonLabel.setText(":");

        final HBox clockHBox = new HBox(hourLabel, colonLabel, minuteLabel);
        clockHBox.setAlignment(CENTER);
        clockHBox.setFillHeight(true);
        colonLabel.translateYProperty().bind(clockHBox.heightProperty().multiply(-0.03));
        return clockHBox;
    }

    private Label newLabel(Color fill) {
        final Label label = new Label();
        label.setFont(interFont(400));
        label.setAlignment(CENTER);
        label.setTextFill(fill);
        return label;
    }

    public StackPane getContainer() {
        return container;
    }
}
