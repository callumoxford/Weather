package com.develogical;

public interface TemperatureRetriever {
    /**
     * Gets the temperature in Celsius
     */
    double getTemperature(String region, String day);
}
