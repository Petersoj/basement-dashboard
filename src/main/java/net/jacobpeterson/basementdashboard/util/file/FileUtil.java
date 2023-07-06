package net.jacobpeterson.basementdashboard.util.file;

import java.io.File;

/**
 * {@link FileUtil} is a utility class for files.
 */
public class FileUtil {

    /**
     * Gets a {@link File} relative to a user's home directory.
     *
     * @param pathname the pathname
     *
     * @return a {@link File}
     */
    public static File getRelativeToHome(String pathname) {
        return new File(System.getProperty("user.home"), pathname);
    }
}
