package com.ehs.outsera.service;

import com.ehs.outsera.model.Movie;
import com.univocity.parsers.common.record.Record;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class MovieLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MovieLoader.class);

    private final MovieService movieService;

    public MovieLoader(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public void run(String... args) {
        try {
            var res = new ClassPathResource("Movielist.csv");
            if (res.exists()) {
                try (InputStream in = res.getInputStream()) {
                    parseAndSave(in);
                }
            }
        } catch (Exception e) {
            log.error("File Movielist.csv could not be loaded.", e);
        }
    }

    protected void parseAndSave(InputStream in) {
        List<Record> records = parse(in);

        for (Record record : records) {
            Movie movie = convert(record);
            if (movie == null) continue;
            movieService.createMovie(movie);
        }
    }

    private static List<Record> parse(InputStream in) {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.setLineSeparatorDetectionEnabled(true);
        settings.setDelimiterDetectionEnabled(true, ';', '\t', '|');

        CsvParser parser = new CsvParser(settings);
        return parser.parseAllRecords(in, StandardCharsets.UTF_8);
    }

    private static Movie convert(Record record) {
        Movie movie = new Movie();
        try {
            movie.setYear(Integer.parseInt(record.getString("year")));
        } catch (Exception e) {
            log.warn("Could not parse movie year for record: {}", record, e);
            return null;
        }
        movie.setTitle(record.getString("title"));
        movie.setStudios(record.getString("studios"));
        movie.setProducers(record.getString("producers"));
        movie.setWinner(isWinnerRecord(record));
        return movie;
    }

    private static boolean isWinnerRecord(Record record) {
        String winner = record.getString("winner");
        if (winner != null) {
            String normalized = winner.trim().toLowerCase();
            return normalized.equals("yes");
        }
        return false;
    }

}