package net.jacobpeterson.basementdashboard;

import java.io.File;

import static net.jacobpeterson.basementdashboard.util.file.FileUtil.getRelativeToHome;

/**
 * {@link BasementDashboardProperties} store various properties.
 */
public class BasementDashboardProperties {

    /**
     * The "Background Videos" directory.
     */
    public static final File BACKGROUND_VIDEOS_DIRECTORY =
            getRelativeToHome("Desktop/Basement Dashboard/Background Videos/");
}
