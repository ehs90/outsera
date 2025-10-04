package com.ehs.outsera.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies", indexes = {
        @Index(name = "idx_movie_winner", columnList = "winner"),
        @Index(name = "idx_movie_year", columnList = "movie_year")
})
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_year")
    private Integer year;

    private String title;
    private String studios;
    private Boolean winner;

    @ManyToMany
    @JoinTable(
            name = "movie_producers",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private Set<Producer> producers = new HashSet<>();

    public Movie() {
    }

    public Movie(Long id, Integer year, String title, String studios, Set<Producer> producers, Boolean winner) {
        this.id = id;
        this.year = year;
        this.title = title;
        this.studios = studios;
        this.producers = producers;
        this.winner = winner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudios() {
        return studios;
    }

    public void setStudios(String studios) {
        this.studios = studios;
    }

    public Set<Producer> getProducers() {
        return producers;
    }

    public void setProducers(Set<Producer> producers) {
        this.producers = producers;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    @Override
    public String toString() {
        return """
                Movie{id=%s, year=%s, title='%s', studios='%s', producers=%s, winner=%s}
                """.formatted(id, year, title, studios, producers, winner);
    }

}
