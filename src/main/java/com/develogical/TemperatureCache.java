package com.develogical;

import javafx.util.Pair;

import java.time.Clock;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TemperatureCache implements TemperatureRetriever {
    private TemperatureRetriever source;
    private int cacheSize;
    private Clock clock;

    private Map<Pair<String, String>, Entry> temperatureCacheMap = new LinkedHashMap<>();

    public TemperatureCache(TemperatureRetriever source, int cacheSize, Clock clock) {
        this.source = source;
        this.cacheSize = cacheSize;
        this.clock = clock;
    }

    @Override
    public double getTemperature(String region, String day) {
        Pair<String, String> key = new Pair<>(region, day);
        Entry entry = temperatureCacheMap.get(key);
        if (entry != null) {
            if (clock.millis() - entry.timestamp < TimeUnit.DAYS.toMillis(1)) {
                return entry.temperature;
            }
            temperatureCacheMap.remove(key);
        }
        double sourceTemperature = source.getTemperature(region, day);
        entry = new Entry();
        entry.temperature = sourceTemperature;
        entry.timestamp = clock.millis();
        temperatureCacheMap.put(key, entry);
        enforceCacheSize();
        return sourceTemperature;
    }

    private void enforceCacheSize() {
        while (temperatureCacheMap.size() > cacheSize) {
            Pair<String, String> oldestKey = temperatureCacheMap.entrySet().iterator().next().getKey();
            temperatureCacheMap.remove(oldestKey);
        }
    }
    private class Entry{
        long timestamp;
        double temperature;
    }
}
