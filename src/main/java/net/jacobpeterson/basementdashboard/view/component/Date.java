package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.application.Platform.runLater;
import static javafx.geometry.Pos.CENTER;
import static javafx.scene.paint.Color.WHITE;
import static net.jacobpeterson.basementdashboard.util.view.FontUtil.interFont;

/**
 * {@link Date} is the date component.
 */
public class Date {

    private final HBox container;

    /**
     * Instantiates a new {@link Date}.
     */
    public Date(DashboardView dashboardView) {
        final Label dayOfWeekLabel = newLabel();
        dashboardView.getBasementDashboard().getDateTimeData()
                .addOnDayOfWeekUpdate(day -> runLater(() -> dayOfWeekLabel.setText(day)));

        final Label commaSpaceLabel = newLabel();
        commaSpaceLabel.setText(", ");

        final Label monthLabel = newLabel();
        final Label spaceLabel = newLabel();
        spaceLabel.setText(" ");
        final Label dayOfMonthLabel = newLabel();
        dashboardView.getBasementDashboard().getDateTimeData().addOnDayOfMonthUpdate((month, day) -> runLater(() -> {
            monthLabel.setText(month);
            dayOfMonthLabel.setText(day);
        }));

        container = new HBox(dayOfWeekLabel, commaSpaceLabel, monthLabel, spaceLabel, dayOfMonthLabel);
        container.setAlignment(CENTER);
        container.setFillHeight(true);

        dashboardView.getBasementDashboard().getDateTimeData()
                .addOnDayOfMonthUpdate((month, day) -> runLater(() -> dashboardView.getBackgroundVideo().next()));
    }

    private Label newLabel() {
        final Label label = new Label();
        label.setFont(interFont(60));
        label.setAlignment(CENTER);
        label.setTextFill(WHITE);
        return label;
    }

    public HBox getContainer() {
        return container;
    }
}
