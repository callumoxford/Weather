package com.develogical;

import org.junit.Test;
import java.time.Clock;
import java.util.concurrent.TimeUnit;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CacheTest {
    @Test
    public void simpleRetrieval() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        TemperatureRetriever cache = new TemperatureCache(delegate, 3, Clock.systemUTC());
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        assertThat(cache.getTemperature("LONDON", "MONDAY"), equalTo(2.0));
    }

    @Test
    public void cacheHit() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        TemperatureRetriever cache = new TemperatureCache(delegate, 3, Clock.systemUTC());
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        cache.getTemperature("LONDON", "MONDAY");
        cache.getTemperature("LONDON", "MONDAY");
        verify(delegate, times(1)).getTemperature(any(String.class), any(String.class));
    }

    @Test
    public void cacheLimitExceeded() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        TemperatureRetriever cache = new TemperatureCache(delegate, 1, Clock.systemUTC());
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        cache.getTemperature("LONDON", "MONDAY");
        cache.getTemperature("LONDON", "TUESDAY");
        cache.getTemperature("LONDON", "MONDAY");
        verify(delegate, times(3)).getTemperature(any(String.class), any(String.class));
    }
    @Test
    public void cacheExpirationTest() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        Clock clock = mock(Clock.class);
        TemperatureRetriever cache = new TemperatureCache(delegate, 1, clock);
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        when(clock.millis()).thenReturn(0L);
        cache.getTemperature("LONDON", "MONDAY");
        when(clock.millis()).thenReturn(TimeUnit.DAYS.toMillis(1));
        cache.getTemperature("LONDON", "MONDAY");
        verify(delegate, times(2)).getTemperature(any(String.class), any(String.class));
    }

}
