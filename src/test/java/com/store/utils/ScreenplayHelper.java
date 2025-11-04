package com.store.utils;

import io.appium.java_client.AppiumDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

/**
 * Helper class for Screenplay pattern operations.
 * Uses Serenity's native BrowseTheWeb ability.
 *
 * OPTIONAL: Only use if you need extra helper methods beyond Serenity's defaults.
 */
public class ScreenplayHelper {

    /**
     * Get AppiumDriver from actor (using Serenity's native BrowseTheWeb)
     */
    public static AppiumDriver getDriver(Actor actor) {
        return (AppiumDriver) BrowseTheWeb.as(actor).getDriver();
    }

    /**
     * Check if actor can browse the web
     */
    public static boolean canBrowseTheWeb(Actor actor) {
        return actor.abilityTo(BrowseTheWeb.class) != null;
    }

    /**
     * Get current platform from driver
     */
    public static String getCurrentPlatform(Actor actor) {
        AppiumDriver driver = getDriver(actor);
        return driver.getCapabilities().getPlatformName().toString().toLowerCase();
    }

    /**
     * Check if current platform is Android
     */
    public static boolean isAndroid(Actor actor) {
        return "android".equalsIgnoreCase(getCurrentPlatform(actor));
    }

    /**
     * Check if current platform is iOS
     */
    public static boolean isIOS(Actor actor) {
        return "ios".equalsIgnoreCase(getCurrentPlatform(actor));
    }
}