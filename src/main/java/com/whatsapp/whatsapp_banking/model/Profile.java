package com.whatsapp.whatsapp_banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;

// Profile class
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Profile {
    private String name;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}