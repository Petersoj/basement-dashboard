package net.jacobpeterson.basementdashboard.http.server;

import io.javalin.Javalin;
import net.jacobpeterson.basementdashboard.BasementDashboard;

import static io.javalin.http.HttpCode.OK;
import static io.javalin.http.HttpCode.UNAUTHORIZED;

/**
 * {@link HTTPServer} is a small HTTP server.
 */
public class HTTPServer {

    private static final String KEY = "z1WhA4d7DCGcB6w1VirC2R1R7P";

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

        javalin.get("/background-video/next", context -> {
            if (!KEY.equals(context.queryParam("key"))) {
                context.result(UNAUTHORIZED.getMessage());
                context.status(UNAUTHORIZED);
                return;
            }
            basementDashboard.getDashboardView().getBackgroundVideo().next();
            context.result("Next video playing...");
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
