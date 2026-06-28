package com.sdet.mobile.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Reads config.properties so no hard-coded values exist in test code.
 * Interviewers love seeing this pattern — it signals framework maturity.
 */
public class ConfigReader {

    private static final Properties properties = new Properties();
    private static final String CONFIG_PATH = "src/test/resources/config.properties";

    static {
        try (FileInputStream fis = new FileInputStream(CONFIG_PATH)) {
            properties.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config.properties from: " + CONFIG_PATH, e);
        }
    }

    public static String get(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
