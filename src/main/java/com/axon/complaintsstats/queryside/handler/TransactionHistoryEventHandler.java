package com.axon.complaintsstats.queryside.handler;

import com.axon.axondemo.commandside.event.account.MoneyDepositedEvent;
import com.axon.axondemo.commandside.event.account.MoneyWithdrawnEvent;
import com.axon.complaintsstats.queryside.entity.TransactionHistory;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Example;

import java.util.List;

@RestController
public class TransactionHistoryEventHandler {

    @EventHandler
    public void on(MoneyDepositedEvent event) {
      // new TransactionHistory(event.getAccountId(), event.getTransactionId(), event.getAmount());
        System.out.println("Received Event -> MoneyDepositedEvent");
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
      // new TransactionHistory(event.getAccountId(), event.getTransactionId(), -event.getAmount());
        System.out.println("Received Event -> MoneyWithdrawnEvent");
    }

    @GetMapping("/history/{accountId}")
    public List<TransactionHistory> findTransactions(@PathVariable String accountId) {
        //Example.of(new TransactionHistory(accountId));
        return null;
    }
}

