package com.whatsapp.whatsapp_banking.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessagingService {

    @org.springframework.beans.factory.annotation.Value("${webhook.verify.token}")
    private String webhookVerifyToken;

    @org.springframework.beans.factory.annotation.Value("${graph.api.token}")
    private String graphApiToken;

    @org.springframework.beans.factory.annotation.Value("${server.port}")
    private int port;

    public String getWebhookVerifyToken() {
        return webhookVerifyToken;
    }

    public void processTheMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        switch (messageBody.toLowerCase()) {
            case "hi":
            case "hello":
                sendWelcomeMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            case "account services":
                sendAccountServicesMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            case "account balance":
                sendAccountBalanceMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            case "mini statement":
                sendMiniStatementMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            case "loan services":
                sendLoanServicesMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            case "banking services":
                sendBankingServicesMessage(businessPhoneNumberId, from, messageBody, messageId);
                break;
            default:
                break;
        }
    }

    private void sendWelcomeMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        System.out.println("welcome message");
        String welcomeMessage = "welcome message";
        sendReplyMessage(businessPhoneNumberId, from, welcomeMessage, messageId);
    }

    private void sendAccountServicesMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        String accountServicesMessage = "account service menu";
        sendReplyMessage(businessPhoneNumberId, from, accountServicesMessage, messageId);
    }

    private void sendAccountBalanceMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        int balance = 1000; // TODO: Fetch it from database
        String accountBalanceMessage = "acc. balance 1000";
        sendReplyMessage(businessPhoneNumberId, from, accountBalanceMessage, messageId);
    }

    private void sendMiniStatementMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        //TODO: Fetch mini statement from database
        String miniStatementMessage = "mini statement";
        sendReplyMessage(businessPhoneNumberId, from, miniStatementMessage, messageId);
    }

    private void sendLoanServicesMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        String loanServicesMessage = "loan service menu";
        sendReplyMessage(businessPhoneNumberId, from, loanServicesMessage, messageId);
    }

    private void sendBankingServicesMessage(String businessPhoneNumberId, String from, String messageBody, String messageId) {
        String bankingServicesMessage = "banking menu";
        sendReplyMessage(businessPhoneNumberId, from, bankingServicesMessage, messageId);
    }


    private void sendReplyMessage(String businessPhoneNumberId, String to, String replyText, String contextMessageId) {
        String url = "https://graph.facebook.com/v20.0/" + businessPhoneNumberId + "/messages";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + graphApiToken);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format(
                "{" +
                        "\"messaging_product\": \"whatsapp\"," +
                        "\"to\": \"%s\"," +
                        "\"text\": { \"body\": \"%s\" }," +
                        "\"context\": { \"message_id\": \"%s\" }" +
                        "}",
                to, replyText, contextMessageId
        );

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, request, String.class);
    }

    public void markMessageAsRead(String businessPhoneNumberId, String messageId) {
        String url = "https://graph.facebook.com/v18.0/" + businessPhoneNumberId + "/messages";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + graphApiToken);
        headers.set("Content-Type", "application/json");

        String requestBody = String.format(
                "{" +
                        "\"messaging_product\": \"whatsapp\"," +
                        "\"status\": \"read\"," +
                        "\"message_id\": \"%s\"" +
                        "}",
                messageId
        );

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url, request, String.class);
    }
}
