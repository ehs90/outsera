package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.ehs.outsera.model.ProducerInterval;
import com.ehs.outsera.repository.MovieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

        Map<String, List<Integer>> producerWins = getProducerWinningYears(winners);

        List<ProducerInterval> intervals = createProducerIntervals(producerWins);

        if (intervals.isEmpty()) {
            return Map.of("min", emptyList(), "max", emptyList());
        }

        // find min and max intervals
        int min = intervals.stream().mapToInt(ProducerInterval::getInterval).min().orElse(0);
        int max = intervals.stream().mapToInt(ProducerInterval::getInterval).max().orElse(0);

        // filter producers with min and max intervals
        List<ProducerInterval> minList = intervals.stream().filter(i -> i.getInterval() == min).toList();
        List<ProducerInterval> maxList = intervals.stream().filter(i -> i.getInterval() == max).toList();

        return Map.of("min", minList, "max", maxList);
    }

    private Map<String, List<Integer>> getProducerWinningYears(List<Movie> winners) {
        Map<String, List<Integer>> producerWinnings = new HashMap<>();

        for (Movie movie : winners) {
            List<String> producers = splitProducers(movie.getProducers());
            for (String producer : producers) {
                producerWinnings.computeIfAbsent(producer, k -> new ArrayList<>()).add(movie.getYear());
            }
        }
        return producerWinnings;
    }

    private List<String> splitProducers(String raw) {
        if (raw == null) return emptyList();

        String normalized = raw
                .replaceAll("\u00A0", " ")
                .replaceAll(" and ", ",");

        String[] producers = normalized.split(",");

        return Arrays.stream(producers)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    private static List<ProducerInterval> createProducerIntervals(Map<String, List<Integer>> producerWins) {
        List<ProducerInterval> intervals = new ArrayList<>();
        for (var entry : producerWins.entrySet()) {
            var years = entry.getValue();

            // ignore one-year winners
            if (years.size() < 2) continue;

            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {
                int current = years.get(i);
                int previous = years.get(i - 1);
                int interval = current - previous;
                intervals.add(new ProducerInterval(entry.getKey(), interval, previous, current));
            }
        }
        return intervals;
    }
}
