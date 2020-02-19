package com.develogical;

import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemperatureCache implements TemperatureRetriever {
    private TemperatureRetriever source;
    private int cacheSize;

    private Map<Pair<String, String>, Double> temperatureCacheMap = new LinkedHashMap<>();

    public TemperatureCache(TemperatureRetriever source, int cacheSize) {
        this.source = source;
        this.cacheSize = cacheSize;
    }

    @Override
    public double getTemperature(String region, String day) {
        Pair<String, String> key = new Pair<>(region, day);
        Double cachedTemperature = temperatureCacheMap.get(key);
        if (cachedTemperature != null) {
            return cachedTemperature;
        }
        double sourceTemperature = source.getTemperature(region, day);
        temperatureCacheMap.put(key, sourceTemperature);
        enforceCacheSize();
        return sourceTemperature;
    }

    private void enforceCacheSize() {
        while (temperatureCacheMap.size() > cacheSize) {
            Pair<String, String> oldestKey = temperatureCacheMap.entrySet().iterator().next().getKey();
            temperatureCacheMap.remove(oldestKey);
        }
    }
}
