package net.jacobpeterson.basementdashboard.util.view;

import javafx.scene.layout.Region;

import static javafx.scene.CacheHint.SPEED;

/**
 * {@link CacheUtil} is a utility class for view caching.
 */
public class CacheUtil {

    /**
     * Enables caching for the given {@link Region}.
     *
     * @param region the {@link Region}
     */
    public static void enableCaching(Region region) {
        region.setCache(true);
        region.setCacheShape(true);
        region.setCacheHint(SPEED);
    }
}
