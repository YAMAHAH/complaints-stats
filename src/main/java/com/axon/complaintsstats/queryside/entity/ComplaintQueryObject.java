package com.axon.complaintsstats.queryside.entity;


public class ComplaintQueryObject {

    private String complaintId;
    private String company;
    private String description;

    public ComplaintQueryObject(String complaintId, String company, String description) {
        this.complaintId = complaintId;
        this.company = company;
        this.description = description;
    }

    public ComplaintQueryObject() {
    }

    public String getComplaintId() {
        return complaintId;
    }

    public String getCompany() {
        return company;
    }

    public String getDescription() {
        return description;
    }
}
