package com.store.exceptions;

/**
 * Custom exception for mobile automation errors.
 * Provides better error context and debugging information.
 */
public class MobileException extends RuntimeException {

    private final String errorCode;
    private final String platform;

    /**
     * Basic constructor with message
     */
    public MobileException(String message) {
        super(message);
        this.errorCode = null;
        this.platform = null;
    }

    /**
     * Constructor with message and cause
     */
    public MobileException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.platform = null;
    }

    /**
     * Constructor with error code for better tracking
     */
    public MobileException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
        this.platform = null;
    }

    /**
     * Full constructor with platform information
     */
    public MobileException(String message, String errorCode, String platform, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.platform = platform;
    }

    /**
     * Get the error code for this exception
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * Get the platform where the error occurred
     */
    public String getPlatform() {
        return platform;
    }

    /**
     * Get detailed error message with all context
     */
    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder(super.getMessage());

        if (errorCode != null) {
            message.append(" [Error Code: ").append(errorCode).append("]");
        }

        if (platform != null) {
            message.append(" [Platform: ").append(platform).append("]");
        }

        return message.toString();
    }

    // Factory methods for common errors

    /**
     * Create exception for element not found
     */
    public static MobileException elementNotFound(String elementName) {
        return new MobileException(
                "Element not found: " + elementName,
                "ELEMENT_NOT_FOUND"
        );
    }

    /**
     * Create exception for element not found on specific platform
     */
    public static MobileException elementNotFound(String elementName, String platform) {
        return new MobileException(
                "Element not found: " + elementName,
                "ELEMENT_NOT_FOUND",
                platform,
                null
        );
    }

    /**
     * Create exception for timeout
     */
    public static MobileException timeout(String action, int seconds) {
        return new MobileException(
                String.format("Timeout waiting for: %s (waited %d seconds)", action, seconds),
                "TIMEOUT"
        );
    }

    /**
     * Create exception for invalid configuration
     */
    public static MobileException invalidConfiguration(String configKey) {
        return new MobileException(
                "Invalid or missing configuration: " + configKey,
                "INVALID_CONFIG"
        );
    }

    /**
     * Create exception for app installation failure
     */
    public static MobileException appInstallationFailed(String appPath, Throwable cause) {
        return new MobileException(
                "Failed to install app: " + appPath,
                "APP_INSTALL_FAILED",
                null,
                cause
        );
    }

    /**
     * Create exception for driver creation failure
     */
    public static MobileException driverCreationFailed(String platform, Throwable cause) {
        return new MobileException(
                "Failed to create driver for platform: " + platform,
                "DRIVER_CREATION_FAILED",
                platform,
                cause
        );
    }
}