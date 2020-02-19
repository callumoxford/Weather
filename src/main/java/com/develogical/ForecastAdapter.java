package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;

public class ForecastAdapter implements TemperatureRetriever{
    private Forecaster forecaster;

    public ForecastAdapter(Forecaster forecaster) {
        this.forecaster = forecaster;
    }

    @Override
    public double getTemperature(String region, String day) {
        Forecast forecast = forecaster.forecastFor(Region.valueOf(region), Day.valueOf(day));
        return forecast.temperature();
    }
}
