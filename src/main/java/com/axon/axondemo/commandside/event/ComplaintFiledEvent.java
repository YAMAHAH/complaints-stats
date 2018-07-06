package com.axon.axondemo.commandside.event;

public class ComplaintFiledEvent {
    private String id = "";
    private String company = "";
    private String description = "";

    public ComplaintFiledEvent() {

    }

    public ComplaintFiledEvent(String id, String company, String description) {
        this.id = id;
        this.company = company;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }
}
