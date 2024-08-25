package com.whatsapp.whatsapp_banking.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhatsAppMessage {
    private String from; // Sender's phone number
    private String to;   // Receiver's phone number (your business number)
    private String content; // The message text content
    private String messageId; // Unique ID of the message
    private String timestamp; // Message timestamp
    private String messageType; // Message type (e.g., text, image, etc.)

    // Constructors
    public WhatsAppMessage() {
    }

    public WhatsAppMessage(String from, String to, String content, String messageId, String timestamp, String messageType) {
        this.from = from;
        this.to = to;
        this.content = content;
        this.messageId = messageId;
        this.timestamp = timestamp;
        this.messageType = messageType;
    }

    // Getters and Setters
    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "WhatsAppMessage{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", content='" + content + '\'' +
                ", messageId='" + messageId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", messageType='" + messageType + '\'' +
                '}';
    }
}
