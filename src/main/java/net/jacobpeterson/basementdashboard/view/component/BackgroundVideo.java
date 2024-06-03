package net.jacobpeterson.basementdashboard.view.component;

import javafx.scene.image.ImageView;
import net.jacobpeterson.basementdashboard.data.BackgroundVideoData;
import net.jacobpeterson.basementdashboard.view.DashboardView;
import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.javafx.videosurface.ImageViewVideoSurface;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

/**
 * {@link BackgroundVideo} is the background video component.
 */
public class BackgroundVideo {

    private final DashboardView dashboardView;
    private final BackgroundVideoData backgroundVideoData;
    private final MediaPlayerFactory mediaPlayerFactory;
    private final EmbeddedMediaPlayer embeddedMediaPlayer;
    private final ImageView imageView;
    private int backgroundVideoFilesIndex;

    /**
     * Instantiates a new {@link BackgroundVideo}.
     *
     * @param dashboardView the {@link DashboardView}
     */
    public BackgroundVideo(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        backgroundVideoData = dashboardView.getBasementDashboard().getBackgroundVideoData();

        imageView = new ImageView();
        mediaPlayerFactory = new MediaPlayerFactory();
        embeddedMediaPlayer = mediaPlayerFactory.mediaPlayers().newEmbeddedMediaPlayer();
        embeddedMediaPlayer.videoSurface().set(new ImageViewVideoSurface(imageView));
        embeddedMediaPlayer.audio().setMute(true);
        embeddedMediaPlayer.controls().setRepeat(true);

        backgroundVideoFilesIndex = 0;

        dashboardView.getBasementDashboard().getDateTimeData().addOnSnoozeUpdate(snooze -> {
            if (snooze && embeddedMediaPlayer.status().isPlaying()) {
                embeddedMediaPlayer.controls().pause();
            }
            if (!snooze && !embeddedMediaPlayer.status().isPlaying()) {
                embeddedMediaPlayer.controls().play();
            }
        });
    }

    /**
     * Stops this {@link BackgroundVideo}.
     */
    public void stop() {
        embeddedMediaPlayer.release();
        mediaPlayerFactory.release();
    }

    /**
     * Plays the next background video.
     */
    public synchronized void next() {
        embeddedMediaPlayer.media().play(backgroundVideoData.getBackgroundVideos()
                .get(backgroundVideoFilesIndex++).getAbsolutePath());
        if (backgroundVideoFilesIndex >= backgroundVideoData.getBackgroundVideos().size()) {
            backgroundVideoFilesIndex = 0;
        }
    }

    public ImageView getImageView() {
        return imageView;
    }
}
