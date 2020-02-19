package com.develogical;

import com.weather.Day;
import com.weather.Forecast;
import com.weather.Forecaster;
import com.weather.Region;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class AdapterTest {
    @Test
    public void simpleAdapterTest() {
        Forecaster forecaster = mock(Forecaster.class);
        ForecastAdapter adapter = new ForecastAdapter(forecaster);
        when(forecaster.forecastFor(Region.LONDON, Day.MONDAY)).thenReturn(new Forecast("bla", 22));
        assertThat( adapter.getTemperature("LONDON", "MONDAY"), equalTo(22.0));
        verify(forecaster, times(1)).forecastFor(Region.LONDON, Day.MONDAY);
    }
}
