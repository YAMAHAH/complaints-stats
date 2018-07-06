package com.axon.complaintsstats.queryside.handler;

import com.axon.axondemo.commandside.event.ComplaintFiledEvent;
import com.axon.complaintsstats.queryside.entity.ComplaintQueryObject;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ComplaintQueryObjectUpdater {


    @EventHandler
    public void on(ComplaintFiledEvent event) {
        //new ComplaintQueryObject(event.getId(), event.getCompany(), event.getDescription());
        System.out.println("Received Event -> ComplaintFiledEvent");
    }
}