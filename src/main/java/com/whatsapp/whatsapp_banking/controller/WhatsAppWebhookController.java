package com.whatsapp.whatsapp_banking.controller; /**
 * Copyright (c) Meta Platforms, Inc. and affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

import com.whatsapp.whatsapp_banking.model.Message;
import com.whatsapp.whatsapp_banking.model.Value;
import com.whatsapp.whatsapp_banking.model.WebhookPayload;
//import org.springframework.beans.factory.annotation.Value;
import com.whatsapp.whatsapp_banking.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/webhook")
public class WhatsAppWebhookController {

    @Autowired
    private MessagingService messagingService;

//    @PostMapping
//    public ResponseEntity<String> handleIncomingMessage(@RequestBody Map<String, Object> payload) {
//        // Log incoming messages
//        System.out.println("Incoming webhook message: " + payload);
//
//        // Extract message details
//        Map<String, Object> entry = (Map<String, Object>) ((Map<String, Object>) payload.get("entry")).get(0);
//        Map<String, Object> change = (Map<String, Object>) ((Map<String, Object>) entry.get("changes")).get(0);
//        Map<String, Object> value = (Map<String, Object>) change.get("value");
//        Map<String, Object> message = (Map<String, Object>) ((Map<String, Object>) value.get("messages")).get(0);
//
//        if (message != null && "text".equals(message.get("type"))) {
//            // Extract the business phone number ID
//            String businessPhoneNumberId = (String) ((Map<String, Object>) value.get("metadata")).get("phone_number_id");
//            String from = (String) message.get("from");
//            String messageBody = (String) ((Map<String, Object>) message.get("text")).get("body");
//            String messageId = (String) message.get("id");
//
//            try {
//                // Send a reply message
//                sendReplyMessage(businessPhoneNumberId, from, "Echo: " + messageBody, messageId);
//
//                // Mark the incoming message as read
//                markMessageAsRead(businessPhoneNumberId, messageId);
//            } catch (Exception e) {
//                e.printStackTrace();
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing message");
//            }
//        }
//
//        return ResponseEntity.ok("Message processed successfully");
//    }

    @PostMapping
    public ResponseEntity<String> handleIncomingMessage(@RequestBody WebhookPayload payload) {
        // Log incoming messages
        System.out.println("Incoming webhook message: " + payload);

        // Extract message details
        Value value = payload.getEntry().get(0).getChanges().get(0).getValue();
        Message message = null;
        String businessPhoneNumberId = null;
        String from = null;
        String messageBody = null;
        String messageId = null;

        if (value != null && value.getMessages() != null && !value.getMessages().isEmpty()) {
            message = value.getMessages().get(0); // Assuming we only process the first message
            if ("text".equals(message.getType())) {
                // Extract the business phone number ID
                businessPhoneNumberId = value.getMetadata().getPhoneNumberId();
                from = message.getFrom();
                messageBody = message.getText().getBody();
                messageId = message.getId();

                try {
                    messagingService.processTheMessage(businessPhoneNumberId, from, messageBody, messageId);
                    // Send a reply message
//                    sendReplyMessage(businessPhoneNumberId, from, "Echo: " + messageBody, messageId);

                    // Mark the incoming message as read
                    messagingService.markMessageAsRead(businessPhoneNumberId, messageId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing message");
                }
            }
        }

        return ResponseEntity.ok("Message processed successfully");
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam(name = "hub.mode") String mode,
                                                @RequestParam(name = "hub.verify_token") String token,
                                                @RequestParam(name = "hub.challenge") String challenge) {
        // Check the mode and token
        if ("subscribe".equals(mode) && messagingService.getWebhookVerifyToken().equals(token)) {
            // Respond with 200 OK and challenge token
            System.out.println("Webhook verified successfully!");
            return ResponseEntity.ok(challenge);
        } else {
            // Respond with 403 Forbidden if verify tokens do not match
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Forbidden");
        }
    }

    @GetMapping("/")
    public String defaultResponse() {
        return "<pre>Nothing to see here.\nCheckout README.md to start.</pre>";
    }

    @GetMapping("/test")
    public String test(@RequestParam(name = "option") String option) {
//        messagingService.processTheMessage("420781681112710", "919703447700", option, "ABGGFlA5Fpa");
        messagingService.processTheMessage("430282886826234", "919703447700", option, "ABGGFlA5Fpa");
        return "Okay";
    }

}
