package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import net.jacobpeterson.basementdashboard.data.BackgroundVideoData;
import net.jacobpeterson.basementdashboard.view.DashboardView;

import static javafx.util.Duration.ZERO;
import static net.jacobpeterson.basementdashboard.util.exception.ExceptionUtil.showException;

/**
 * {@link BackgroundVideo} is the background video component.
 */
public class BackgroundVideo {

    private final DashboardView dashboardView;
    private final BackgroundVideoData backgroundVideoData;
    private final MediaView mediaView;
    private int backgroundVideoFilesIndex;

    /**
     * Instantiates a new {@link BackgroundVideo}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public BackgroundVideo(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        backgroundVideoData = dashboardView.getBasementDashboard().getBackgroundVideoData();

        mediaView = new MediaView();
        mediaView.setSmooth(false);
        mediaView.setPreserveRatio(true);

        backgroundVideoFilesIndex = 0;
    }

    /**
     * Plays the next background video.
     */
    public synchronized void next() {
        final MediaPlayer mediaPlayer = new MediaPlayer(new Media(backgroundVideoData.getBackgroundVideos()
                .get(backgroundVideoFilesIndex++).toURI().toString()));
        if (backgroundVideoFilesIndex >= backgroundVideoData.getBackgroundVideos().size()) {
            backgroundVideoFilesIndex = 0;
        }
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setOnEndOfMedia(() -> {
            mediaPlayer.seek(ZERO);
            mediaPlayer.play();
        });
        mediaPlayer.setMute(true);
        mediaPlayer.setOnError(() -> showException(mediaPlayer.getError()));
        mediaView.setMediaPlayer(mediaPlayer);
    }

    public MediaView getMediaView() {
        return mediaView;
    }
}
