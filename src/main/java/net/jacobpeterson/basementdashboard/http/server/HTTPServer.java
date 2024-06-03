package net.jacobpeterson.basementdashboard.http.server;

import io.javalin.Javalin;
import net.jacobpeterson.basementdashboard.BasementDashboard;

import static io.javalin.http.HttpStatus.OK;
import static io.javalin.http.HttpStatus.UNAUTHORIZED;
import static java.lang.String.format;

/**
 * {@link HTTPServer} is a small HTTP server.
 */
public class HTTPServer {

    private static final String KEY = "z1WhA4d7DCGcB6w1VirC2R1R7P";
    private static final String NEXT_VIDEO_URI = "/background-video/next";
    private static final String DEFAULT_HTML_PAGE = format("""
            <html>
            <body>
            <a href="%s?key=%s">Next Video</a>
            </body>
            </html>
            """, NEXT_VIDEO_URI, KEY);

    private final BasementDashboard basementDashboard;
    private Javalin javalin;

    /**
     * Instantiates a new {@link HTTPServer}.
     *
     * @param basementDashboard the {@link BasementDashboard}
     */
    public HTTPServer(BasementDashboard basementDashboard) {
        this.basementDashboard = basementDashboard;
    }

    /**
     * Starts {@link HTTPServer}.
     */
    public void start() {
        javalin = Javalin.create();
        javalin.start(30593);

        javalin.get("/admin-dashboard", context -> {
            context.html(DEFAULT_HTML_PAGE);
            context.status(OK);
        });
        javalin.get(NEXT_VIDEO_URI, context -> {
            if (!KEY.equals(context.queryParam("key"))) {
                context.result(UNAUTHORIZED.getMessage());
                context.status(UNAUTHORIZED);
                return;
            }
            basementDashboard.getDashboardView().getBackgroundVideo().next();
            context.html(DEFAULT_HTML_PAGE);
            context.status(OK);
        });
    }

    /**
     * Stops {@link HTTPServer}.
     */
    public void stop() {
        if (javalin != null) {
            javalin.stop();
        }
    }
}
