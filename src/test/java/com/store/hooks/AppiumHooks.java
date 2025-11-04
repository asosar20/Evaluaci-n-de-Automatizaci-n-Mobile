package com.store.hooks;

import com.store.utils.CapabilitiesLoader;
import io.appium.java_client.AppiumDriver;
import io.cucumber.java.Before;
import io.cucumber.java.After;
import net.serenitybdd.core.Serenity;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.MutableCapabilities;

import java.lang.reflect.Constructor;
import java.net.URL;
import java.time.Duration;

public class AppiumHooks {

    private static AppiumDriver driver;
    private static String currentPlatform;

    @Before(order = 0)
    public void setupSerenityAndAppium() throws Exception {
        System.out.println("\n===== INICIALIZANDO APPIUM =====");

        OnStage.setTheStage(new OnlineCast());

        // Leer plataforma desde system property o usar default
        currentPlatform = System.getProperty("environment", "android").toLowerCase();
        System.out.println("Plataforma seleccionada: " + currentPlatform);

        // URL de Appium
        URL appiumServerUrl = new URL("http://127.0.0.1:4723");
        System.out.println("Conectando a: " + appiumServerUrl);

        // Cargar capabilities desde JSON
        MutableCapabilities capabilities = CapabilitiesLoader.loadCapabilities(currentPlatform);
        CapabilitiesLoader.printCapabilities(capabilities);

        // Crear driver según plataforma
        if (currentPlatform.equals("android")) {
            driver = createAndroidDriver(appiumServerUrl, capabilities);
        } else if (currentPlatform.equals("ios")) {
            driver = createIOSDriver(appiumServerUrl, capabilities);
        } else {
            throw new IllegalArgumentException("Plataforma no soportada: " + currentPlatform);
        }

        // Configurar timeout implícito
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        System.out.println("Driver creado exitosamente");
        System.out.println("Session ID: " + driver.getSessionId());

        // Registrar en Serenity
        ThucydidesWebDriverSupport.initialize();
        Serenity.getWebdriverManager().setCurrentDriver(driver);
    }

    private AppiumDriver createAndroidDriver(URL serverUrl, MutableCapabilities capabilities) throws Exception {
        System.out.println("Creando AndroidDriver usando reflexión...");

        Class<?> androidDriverClass = Class.forName("io.appium.java_client.android.AndroidDriver");
        Constructor<?> constructor = androidDriverClass.getConstructor(URL.class, org.openqa.selenium.Capabilities.class);
        return (AppiumDriver) constructor.newInstance(serverUrl, capabilities);
    }

    private AppiumDriver createIOSDriver(URL serverUrl, MutableCapabilities capabilities) throws Exception {
        System.out.println("Creando IOSDriver usando reflexión...");

        Class<?> iosDriverClass = Class.forName("io.appium.java_client.ios.IOSDriver");
        Constructor<?> constructor = iosDriverClass.getConstructor(URL.class, org.openqa.selenium.Capabilities.class);
        return (AppiumDriver) constructor.newInstance(serverUrl, capabilities);
    }

    @After(order = 0)
    public void tearDownAppium() {
        System.out.println("\n🔌 ===== CERRANDO APPIUM =====");

        if (driver != null) {
            try {
                driver.quit();
                System.out.println("Driver cerrado");
            } catch (Exception e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                driver = null;
            }
        }

        ThucydidesWebDriverSupport.closeCurrentDrivers();
        System.out.println("===== APPIUM CERRADO =====\n");
    }

    public static AppiumDriver getDriver() {
        return driver;
    }

    public static String getCurrentPlatform() {
        return currentPlatform;
    }

    public static String getCurrentScreen() {
        if (driver == null) return "N/A";

        try {
            if (currentPlatform.equals("android")) {
                return (String) driver.getClass().getMethod("currentActivity").invoke(driver);
            } else if (currentPlatform.equals("ios")) {
                return driver.getTitle();
            }
            return "N/A";
        } catch (Exception e) {
            return "N/A";
        }
    }
}