package net.jacobpeterson.basementdashboard.http.client;

import okhttp3.OkHttpClient;

/**
 * {@link HTTPClient} handles <code>OkHttp</code>.
 */
public class HTTPClient {

    private OkHttpClient okHttpClient;

    /**
     * Starts {@link HTTPClient}.
     */
    public void start() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * Stops {@link HTTPClient}.
     */
    public void stop() {
        if (okHttpClient != null) {
            okHttpClient.dispatcher().executorService().shutdown();
            okHttpClient.connectionPool().evictAll();
        }
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}
