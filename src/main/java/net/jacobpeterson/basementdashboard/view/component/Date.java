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
 * {@link Date} is the date component.
 */
public class Date {

    private final DashboardView dashboardView;
    private final StackPane container;

    /**
     * Instantiates a new {@link Date}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public Date(DashboardView dashboardView) {
        this.dashboardView = dashboardView;

        final HBox foreground = newDateHBox(WHITE);
        final HBox background = newDateHBox(BLACK);
        background.setEffect(new GaussianBlur(19));
        background.setOpacity(0.7);

        dashboardView.getBasementDashboard().getDateTimeData()
                .addOnDayOfMonthUpdate((month, day) -> runLater(() -> dashboardView.getBackgroundVideo().next()));

        container = new StackPane(background, foreground);
    }

    private HBox newDateHBox(Color fill) {
        final Label dayOfWeekLabel = newLabel(fill);
        dashboardView.getBasementDashboard().getDateTimeData()
                .addOnDayOfWeekUpdate(day -> runLater(() -> dayOfWeekLabel.setText(day)));

        final Label commaSpaceLabel = newLabel(fill);
        commaSpaceLabel.setText(", ");

        final Label monthLabel = newLabel(fill);
        final Label spaceLabel = newLabel(fill);
        spaceLabel.setText(" ");
        final Label dayOfMonthLabel = newLabel(fill);
        dashboardView.getBasementDashboard().getDateTimeData().addOnDayOfMonthUpdate((month, day) -> runLater(() -> {
            monthLabel.setText(month);
            dayOfMonthLabel.setText(day);
        }));

        final HBox dateHBox = new HBox(dayOfWeekLabel, commaSpaceLabel, monthLabel, spaceLabel, dayOfMonthLabel);
        dateHBox.setAlignment(CENTER);
        dateHBox.setFillHeight(true);
        return dateHBox;
    }

    private Label newLabel(Color fill) {
        final Label label = new Label();
        label.setFont(interFont(50));
        label.setAlignment(CENTER);
        label.setTextFill(fill);
        return label;
    }

    public StackPane getContainer() {
        return container;
    }
}
