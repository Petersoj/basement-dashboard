package net.jacobpeterson.basementdashboard.util.exception;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

import static javafx.application.Platform.runLater;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.ButtonType.CLOSE;

/**
 * {@link ExceptionUtil} is a utility class for exceptions.
 */
public class ExceptionUtil {

    /**
     * The primary {@link Stage} to be internally set.
     */
    public static Stage PRIMARY_STAGE;

    /**
     * Formats a stacktrace with less clutter and only relevant elements.
     *
     * @param throwable the {@link Throwable}
     *
     * @return a {@link String}
     */
    public static String formatStacktrace(Throwable throwable) {
        try {
            final StringBuilder formattedString = new StringBuilder();
            formattedString.append(throwable.toString());
            formattedString.append(System.lineSeparator());
            for (StackTraceElement stackTraceElement : throwable.getStackTrace()) {
                formattedString.append("    ");
                formattedString.append(stackTraceElement);
                formattedString.append(System.lineSeparator());
            }
            return formattedString.toString();
        } catch (Exception ignored) {
            return throwable.toString();
        }
    }

    /**
     * Shows an error.
     *
     * @param exceptionString the exception string
     */
    public static void showException(String exceptionString) {
        runLater(() -> {
            final Alert alert = new Alert(ERROR, exceptionString, CLOSE);
            if (PRIMARY_STAGE.isShowing()) {
                alert.initOwner(PRIMARY_STAGE);
            }
            alert.show();
        });
    }

    /**
     * Shows an {@link Exception} stack trace.
     *
     * @param exception the {@link Exception}
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public static void showException(Exception exception) {
        exception.printStackTrace();
        showException(formatStacktrace(exception));
    }
}
