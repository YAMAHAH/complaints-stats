package com.axon.complaintsstats.controller;

import com.axon.axondemo.commandside.event.ComplaintFiledEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@ProcessingGroup("statistics")
@RestController
public class StatisticsAPI {

    private final ConcurrentMap<String, AtomicLong> statistics = new ConcurrentHashMap<>();

    @EventHandler
    public void on(ComplaintFiledEvent event) {
        statistics.computeIfAbsent(event.getCompany(), k -> new AtomicLong()).incrementAndGet();
    }

    @GetMapping
    public ConcurrentMap<String, AtomicLong> showStatistics() {
        return statistics;
    }
}
