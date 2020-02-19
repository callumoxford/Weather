package com.develogical;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CacheTest {
    @Test
    public void simpleRetrieval() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        TemperatureRetriever cache = new TemperatureCache(delegate);
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        assertThat(cache.getTemperature("LONDON", "MONDAY"), equalTo(2.0));
    }

    @Test
    public void cacheHit() throws Exception {
        TemperatureRetriever delegate = mock(TemperatureRetriever.class);
        TemperatureRetriever cache = new TemperatureCache(delegate);
        when(delegate.getTemperature(any(String.class), any(String.class))).thenReturn(2.0);
        cache.getTemperature("LONDON", "MONDAY");
        cache.getTemperature("LONDON", "MONDAY");
        verify(delegate, times(1)).getTemperature(any(String.class), any(String.class));
    }
}
