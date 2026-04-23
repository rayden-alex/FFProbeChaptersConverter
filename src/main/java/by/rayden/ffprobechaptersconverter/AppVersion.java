package by.rayden.ffprobechaptersconverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppVersion {
    private static final Logger log = LoggerFactory.getLogger(AppVersion.class);
    private static final Properties properties = new Properties();

    static {
        try (InputStream is = AppVersion.class.getClassLoader().getResourceAsStream("version.properties")) {
            properties.load(is);
        } catch (IOException e) {
            log.error("Eroor on reading version.properties", e);
        }
    }

    public static String getName() {
        return properties.getProperty("app.name", "unknown");
    }

    public static String getVersion() {
        return properties.getProperty("app.version", "unknown");
    }

    public static String getBuildDate() {
        return properties.getProperty("app.buildDate", "unknown");
    }
}
