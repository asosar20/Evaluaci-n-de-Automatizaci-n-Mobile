package com.store.utils;

import net.serenitybdd.model.environment.ConfiguredEnvironment;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.util.EnvironmentVariables;


/**
 * Configuration helper using Serenity's native configuration system.
 *
 * OPTIONAL: Only use if you need convenience methods for reading config.
 * Otherwise, use Serenity's ConfiguredEnvironment directly.
 *
 * All values come from:
 * - serenity.conf
 * - System properties (-Dproperty=value)
 * - Environment variables
 */
public class ConfigHelper {

    private static final EnvironmentVariables ENV = ConfiguredEnvironment.getEnvironmentVariables();

    /**
     * Get property value (generic)
     */
    public static String getProperty(String key) {
        return ENV.getProperty(key);
    }

    /**
     * Get property with default value
     */
    public static String getProperty(String key, String defaultValue) {
        String value = ENV.getProperty(key);
        return (value != null && !value.isBlank()) ? value : defaultValue;
    }

    /**
     * Get environment-specific property
     * Example: If environment=android, looks for android.property.key first,
     * then falls back to property.key
     */
    public static String getEnvironmentProperty(String key) {
        return EnvironmentSpecificConfiguration.from(ENV).getProperty(key);
    }

    /**
     * Get current environment (android/ios)
     */
    public static String getEnvironment() {
        return getProperty("environment", "android");
    }

    /**
     * Get current platform (falls back to environment)
     */
    public static String getPlatform() {
        return getProperty("platform", getEnvironment());
    }

    /**
     * Check if running on Android
     */
    public static boolean isAndroid() {
        return "android".equalsIgnoreCase(getPlatform());
    }

    /**
     * Check if running on iOS
     */
    public static boolean isIOS() {
        return "ios".equalsIgnoreCase(getPlatform());
    }

    /**
     * Get Appium hub URL
     */
    public static String getAppiumHub() {
        return getEnvironmentProperty("appium.hub");
    }

    /**
     * Get app path for current platform
     */
    public static String getAppPath() {
        return getEnvironmentProperty("appium.app");
    }

    /**
     * Get app package (Android only)
     */
    public static String getAppPackage() {
        return getEnvironmentProperty("appium.appPackage");
    }

    /**
     * Get bundle ID (iOS only)
     */
    public static String getBundleId() {
        return getEnvironmentProperty("appium.bundleId");
    }

    /**
     * Get device name
     */
    public static String getDeviceName() {
        return getEnvironmentProperty("appium.deviceName");
    }

    /**
     * Get platform version
     */
    public static String getPlatformVersion() {
        return getEnvironmentProperty("appium.platformVersion");
    }

    /**
     * Check if property exists
     */
    public static boolean hasProperty(String key) {
        String value = getProperty(key);
        return value != null && !value.isBlank();
    }

    /**
     * Get integer property
     */
    public static int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Get boolean property
     */
    public static boolean getBooleanProperty(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value);
    }
}