package com.axon.complaintsstats.queryside.handler;

import com.axon.axondemo.commandside.event.account.BalanceUpdatedEvent;
import com.axon.complaintsstats.queryside.entity.AccountBalance;
import com.axon.complaintsstats.queryside.repository.ObjectStore;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@ProcessingGroup("statistics")
@RestController
public class AccountBalanceEventHandler {

    public AccountBalanceEventHandler() {
    }

    @EventHandler
    public void on(BalanceUpdatedEvent event) {
        System.out.println("Received Event -> BalanceUpdatedEvent");
        ObjectStore.save(event.getAccountId(),new AccountBalance(event.getAccountId(), event.getBalance()));
    }

    @GetMapping("/balance/{id}")
    public Optional<Object> getBalance(@PathVariable String id) {
        return Optional.ofNullable(ObjectStore.getValue(id));
    }
}