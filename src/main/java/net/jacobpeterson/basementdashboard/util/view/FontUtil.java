package net.jacobpeterson.basementdashboard.util.view;

import javafx.scene.text.Font;

import java.io.IOException;
import java.io.InputStream;

import static javafx.scene.text.Font.font;
import static javafx.scene.text.Font.loadFont;
import static javafx.scene.text.FontPosture.REGULAR;

/**
 * {@link FontUtil} is a utility class for fonts.
 */
public class FontUtil {

    private static final String FONT_RESOURCE_DIRECTORY = "/font/";
    private static final Font FONT_INTER_REGULAR;

    static {
        try (final InputStream stream = FontUtil.class
                .getResourceAsStream(FONT_RESOURCE_DIRECTORY + "Inter-SemiBold.ttf")) {
            FONT_INTER_REGULAR = loadFont(stream, 12);
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Gets the <code>Inter</code> {@link Font}.
     *
     * @param size the size
     *
     * @return the {@link Font}
     */
    public static Font interFont(int size) {
        return font(FONT_INTER_REGULAR.getFamily(), REGULAR, size);
    }
}
