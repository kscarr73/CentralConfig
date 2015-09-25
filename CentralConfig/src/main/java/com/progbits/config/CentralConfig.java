package com.progbits.config;

/**
 *
 * @author scarr
 */
public interface CentralConfig {
    /**
     * Returns a specific key from the Configuration
     * 
     * @param key The key to return
     * 
     * @return The value found, or null if it does not exist
     */
    public String getProperty(String key);
    
    /**
     * Returns a specific key from the Configuration, or default value if it doesn't exist.
     * 
     * @param key The key to return
     * @param defaultValue The default value to return if it does not exist.
     * 
     * @return The value found, or defaultValue if it does not exist.
     */
    public String getProperty(String key, String defaultValue);
}
