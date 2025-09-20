package com.ehs.outsera.controller;

import com.ehs.outsera.model.ProducerInterval;
import com.ehs.outsera.service.IntervalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/intervals")
public class IntervalController {

    private final IntervalService service;

    public IntervalController(IntervalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, List<ProducerInterval>>> getIntervals() {
        return ResponseEntity.ok(service.calculateMinMaxIntervals());
    }

}
