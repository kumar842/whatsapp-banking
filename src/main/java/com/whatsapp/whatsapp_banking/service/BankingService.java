package com.whatsapp.whatsapp_banking.service;

import com.whatsapp.whatsapp_banking.model.WhatsAppMessage;
import org.springframework.stereotype.Service;

@Service
public class BankingService {

    public String processMessage(WhatsAppMessage message) {
        // Parse the message content
        String userInput = message.getContent();

        // Example logic to check balance
        if (userInput.equalsIgnoreCase("Check Balance")) {
            return "Your account balance is $1,500";
        }

        // Handle other banking operations

        return "Sorry, I didn’t understand that. Please try again.";
    }
}
