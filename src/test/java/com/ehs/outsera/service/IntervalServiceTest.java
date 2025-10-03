package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.model.Producer;
import com.ehs.outsera.model.ProducerInterval;
import com.ehs.outsera.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IntervalServiceTest {

    @InjectMocks
    IntervalService service;
    @Mock
    MovieRepository movieRepository;

    @Test
    void shouldCalculateMinMaxIntervals() {
        Producer producerA = new Producer("Producer A");
        Producer producerB = new Producer("Producer B");
        Producer producerC = new Producer("Producer C");

        Movie m1 = new Movie(1L, 2000, "Movie1", "Studio1", Set.of(producerA, producerC), true);
        Movie m2 = new Movie(2L, 2002, "Movie2", "Studio1", Set.of(producerA), true);
        Movie m3 = new Movie(3L, 2002, "Movie3", "Studio2", Set.of(producerC), true);
        Movie m4 = new Movie(4L, 2005, "Movie4", "Studio2", Set.of(producerB), true);
        Movie m5 = new Movie(5L, 2010, "Movie5", "Studio2", Set.of(producerB), true);

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
