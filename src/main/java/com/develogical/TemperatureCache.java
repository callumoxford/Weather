package com.develogical;

import javafx.util.Pair;

import java.util.LinkedHashMap;
import java.util.Map;

public class TemperatureCache implements TemperatureRetriever {
    private TemperatureRetriever source;

    private Map<Pair<String, String>, Double> temperatureCacheMap = new LinkedHashMap<>();

    public TemperatureCache(TemperatureRetriever source) {
        this.source = source;
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
        return sourceTemperature;
    }
}
