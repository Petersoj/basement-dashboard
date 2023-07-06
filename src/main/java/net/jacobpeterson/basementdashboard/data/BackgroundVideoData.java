package net.jacobpeterson.basementdashboard.data;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static net.jacobpeterson.basementdashboard.BasementDashboardProperties.BACKGROUND_VIDEOS_DIRECTORY;

/**
 * {@link BackgroundVideoData} controls background video data.
 */
public class BackgroundVideoData {

    private List<File> backgroundVideos;

    /**
     * Starts {@link BackgroundVideoData}.
     */
    public void start() {
        backgroundVideos = indexVideos();
    }

    /**
     * Stops {@link BackgroundVideoData}.
     */
    public void stop() {}

    /**
     * Indexes the background videos.
     *
     * @return the background video {@link File} {@link List}
     *
     * @throws RuntimeException thrown for {@link RuntimeException}s
     */
    public List<File> indexVideos() {
        final File[] files = BACKGROUND_VIDEOS_DIRECTORY.listFiles();
        if (files == null || files.length == 0) {
            throw new RuntimeException(BACKGROUND_VIDEOS_DIRECTORY.getAbsolutePath() + " is empty!");
        }
        final List<File> backgroundVideoFiles = Arrays.stream(files)
                .filter(file -> file.getName().contains(".mp4"))
                .collect(Collectors.toList());
        if (backgroundVideoFiles.size() == 0) {
            throw new RuntimeException(BACKGROUND_VIDEOS_DIRECTORY.getAbsolutePath() + " is empty!");
        }
        return backgroundVideoFiles;
    }

    public List<File> getBackgroundVideos() {
        return backgroundVideos;
    }
}
