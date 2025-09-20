package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.model.ProducerInterval;
import com.ehs.outsera.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntervalServiceTest {

    @InjectMocks
    IntervalService service;
    @Mock
    MovieRepository movieRepository;

    @Test
    void testCalculateMinMaxIntervals() {
        Movie m1 = new Movie(1L, 2000, "Movie1", "Studio1", "Producer A, Producer C", true);
        Movie m2 = new Movie(2L, 2002, "Movie2", "Studio1", "Producer A", true);
        Movie m3 = new Movie(3L, 2002, "Movie3", "Studio2", "Producer C", true);
        Movie m4 = new Movie(4L, 2005, "Movie4", "Studio2", "Producer B", true);
        Movie m5 = new Movie(5L, 2010, "Movie5", "Studio2", "Producer B", true);

        when(movieRepository.findByWinnerTrue()).thenReturn(List.of(m1, m2, m3, m4, m5));

        Map<String, List<ProducerInterval>> result = service.calculateMinMaxIntervals();

        assertNotNull(result);
        assertTrue(result.containsKey("min"));
        assertTrue(result.containsKey("max"));

        List<ProducerInterval> minList = result.get("min");
        List<ProducerInterval> maxList = result.get("max");

        assertEquals(2, minList.size());
        assertEquals(1, maxList.size());

        assertEquals(2, minList.getFirst().interval());
        assertEquals(5, maxList.getFirst().interval());
    }
}
