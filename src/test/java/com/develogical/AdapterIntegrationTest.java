package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AdapterIntegrationTest {
    @Test
    public void usingRealForecaster() {
        Forecaster forecaster = new Forecaster();
        ForecastAdapter adapter = new ForecastAdapter(forecaster);
        adapter.getTemperature("LONDON", "MONDAY");
    }
}
