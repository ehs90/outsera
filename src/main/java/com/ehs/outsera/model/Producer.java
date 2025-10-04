package com.ehs.outsera.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producer", indexes = {
        @Index(name = "idx_producer_name", columnList = "name")
})
public class Producer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Producer() {
    }

    public Producer(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return """
                Producer{id=%s, name='%s'}
                """.formatted(id, name);
    }

    @Override
    public boolean equals(Object p) {
        if (this == p) {
            return true;
        }
        if (!(p instanceof Producer producer)) {
            return false;
        }
        return name != null && name.equals(producer.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

}
