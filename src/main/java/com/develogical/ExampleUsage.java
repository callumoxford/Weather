package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

public class ExampleUsage {
    public static void main(String[] args) {
        // This is just an example of using the 3rd party API - delete this class before submission.
        Forecaster forecaster = new Forecaster();
        ForecastAdapter adapter = new ForecastAdapter(forecaster);
        TemperatureCache cache = new TemperatureCache(adapter,10, Clock.systemUTC());
        System.out.println("*****Taking Sample With Cache*****");
        for(int i=0;i<5;i++){
            takeSample(cache);
        }
        System.out.println("*****Taking Sample Without Cache*****");
        for(int i=0;i<5;i++){
            takeSample(adapter);
        }
    }

    private static void takeSample(TemperatureRetriever retriever) {
        Instant start = Instant.now();
        System.out.println("London temperature: " + retriever.getTemperature("LONDON","MONDAY"));
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("Execution time in seconds: " + interval.getSeconds());
    }

}
