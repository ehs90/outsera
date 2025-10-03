package com.ehs.outsera.service;

import com.ehs.outsera.model.Producer;
import com.ehs.outsera.repository.ProducerRepository;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final ProducerRepository producerRepository;

    public ProducerService(ProducerRepository producerRepository) {
        this.producerRepository = producerRepository;
    }

    public Producer findOrCreate(String name) {
        return producerRepository.findByName(name).orElseGet(() -> producerRepository.save(new Producer(name)));
    }

}
