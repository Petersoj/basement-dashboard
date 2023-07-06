package net.jacobpeterson.basementdashboard.http.server;

import io.javalin.Javalin;
import net.jacobpeterson.basementdashboard.BasementDashboard;

import static io.javalin.http.HttpCode.OK;
import static io.javalin.http.HttpCode.UNAUTHORIZED;

/**
 * {@link JavalinServer} is a small HTTP server.
 */
public class JavalinServer {

    private static final String KEY = "z1WhA4d7DCGcB6w1VirC2R1R7P";

    private final BasementDashboard basementDashboard;
    private Javalin javalin;

    /**
     * Instantiates a new {@link JavalinServer}.
     *
     * @param basementDashboard the {@link BasementDashboard}
     */
    public JavalinServer(BasementDashboard basementDashboard) {
        this.basementDashboard = basementDashboard;
    }

    /**
     * Starts {@link JavalinServer}.
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
     * Stops {@link JavalinServer}.
     */
    public void stop() {
        if (javalin != null) {
            javalin.stop();
        }
    }
}
