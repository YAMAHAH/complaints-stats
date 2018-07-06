package com.axon.complaintsstats.handler;

import com.axon.axondemo.commandside.event.ComplaintFiledEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class TestHandler {
    @EventHandler
    public void on(ComplaintFiledEvent event) {
        //statistics.computeIfAbsent(event.getCompany(), k -> new AtomicLong()).incrementAndGet();
        System.out.println("正常接收信息");
    }
}
