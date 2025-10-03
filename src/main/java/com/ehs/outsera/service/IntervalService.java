package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.model.Producer;
import com.ehs.outsera.model.ProducerInterval;
import com.ehs.outsera.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.util.Collections.emptyList;

@Service
public class IntervalService {

    private static final Logger log = LoggerFactory.getLogger(IntervalService.class);

    private final MovieRepository repository;

    public IntervalService(MovieRepository repository) {
        this.repository = repository;
    }

    public Map<String, List<ProducerInterval>> calculateMinMaxIntervals() {
        List<Movie> winners = repository.findByWinnerTrue();

        if (winners.isEmpty()) {
            log.warn("No winners found in the database.");
            return Map.of("min", emptyList(), "max", emptyList());
        }

        Map<String, TreeSet<Integer>> producerWins = getProducerWinningYears(winners);

        List<ProducerInterval> intervals = createProducerIntervals(producerWins);

        if (intervals.isEmpty()) {
            return Map.of("min", emptyList(), "max", emptyList());
        }

        return getMinMaxIntervals(intervals);
    }

    private static Map<String, TreeSet<Integer>> getProducerWinningYears(List<Movie> winners) {
        Map<String, TreeSet<Integer>> producerWinningYears = new HashMap<>();

        for (Movie movie : winners) {
            for (Producer producer : movie.getProducers()) {
                producerWinningYears.computeIfAbsent(producer.getName(), k -> new TreeSet<>()).add(movie.getYear());
            }
        }
        return producerWinningYears;
    }

    private static List<ProducerInterval> createProducerIntervals(Map<String, TreeSet<Integer>> producerWins) {
        List<ProducerInterval> intervals = new ArrayList<>();

        for (var entry : producerWins.entrySet()) {
            NavigableSet<Integer> years = entry.getValue();

            // ignore one-year winners
            if (years.size() < 2) continue;

            Integer previous = null;
            for (Integer current : years) {
                if (previous != null) {
                    int interval = current - previous;
                    intervals.add(new ProducerInterval(entry.getKey(), interval, previous, current));
                }
                previous = current;
            }
        }

        return intervals;
    }

    private static Map<String, List<ProducerInterval>> getMinMaxIntervals(List<ProducerInterval> intervals) {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        List<ProducerInterval> minList = new ArrayList<>();
        List<ProducerInterval> maxList = new ArrayList<>();

        for (ProducerInterval pi : intervals) {
            int interval = pi.getInterval();
            if (interval < min) {
                min = interval;
                minList.clear();
            }

            if (interval == min) {
                minList.add(pi);
            }

            if (interval > max) {
                max = interval;
                maxList.clear();
            }

            if (interval == max) {
                maxList.add(pi);
            }
        }

        return Map.of("min", minList, "max", maxList);
    }

}
