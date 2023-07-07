module BasementDashboard {
    requires annotations;
    requires org.slf4j;
    requires ch.qos.logback.classic;
    requires ch.qos.logback.core;

    requires com.google.common;
    requires com.google.gson;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    opens net.jacobpeterson.basementdashboard to javafx.graphics;

    requires okhttp3;
    requires kotlin.stdlib;
    requires io.javalin;

    opens font;
}
