package net.jacobpeterson.basementdashboard.http.client;

import okhttp3.OkHttpClient;

/**
 * {@link OkHTTPClient} handles <code>OkHttp</code>.
 */
public class OkHTTPClient {

    private OkHttpClient okHttpClient;

    /**
     * Starts {@link OkHTTPClient}.
     */
    public void start() {
        okHttpClient = new OkHttpClient();
    }

    /**
     * Stops {@link OkHTTPClient}.
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
