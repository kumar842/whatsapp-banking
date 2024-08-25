package com.whatsapp.whatsapp_banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

// Root class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebhookPayload {
    private String object;
    private List<Entry> entry;

    // Getters and Setters
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public List<Entry> getEntry() {
        return entry;
    }

    public void setEntry(List<Entry> entry) {
        this.entry = entry;
    }
}
