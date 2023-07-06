module BasementDashboard {
    requires com.google.common;
    requires com.google.gson;

    requires okhttp3;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;

    opens net.jacobpeterson.basementdashboard to javafx.graphics;
}
