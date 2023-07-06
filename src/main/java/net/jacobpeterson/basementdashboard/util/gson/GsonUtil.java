package net.jacobpeterson.basementdashboard.util.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * {@link GsonUtil} contains utility methods relating to {@link Gson}.
 */
public final class GsonUtil {

    /**
     * The {@link Gson} instance.
     */
    public static final Gson GSON = new GsonBuilder().create();

}
