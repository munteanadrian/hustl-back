package tech.adrianmuntean.hustl.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tech.adrianmuntean.hustl.model.Message;
import tech.adrianmuntean.hustl.security.services.UserDetailsImpl;
import tech.adrianmuntean.hustl.service.MessageService;
import tech.adrianmuntean.hustl.utils.APIResponse;

@CrossOrigin(origins = "*", maxAge = 4800)
@RestController
@RequestMapping("/api/message")
public class MessageController {
//    if community is deleted delete all messages too
//    if user is deleted keep the messages and sort them

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // Send message
    @PostMapping("/{groupId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> sendMessage(@PathVariable Long groupId, @RequestBody String message, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        boolean sent = messageService.sendMessage(message, userDetails.getId(), groupId);

        if (sent) {
            return ResponseEntity.status(HttpStatus.OK).body(new APIResponse(HttpStatus.OK.value(), "Message sent successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(HttpStatus.CONFLICT.value(), "Message not sent."));
        }
    }

    // Get all messages in a group
    @GetMapping("/{groupId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Object> getMessages(@PathVariable Long groupId) {
        return ResponseEntity.status(HttpStatus.OK).body(messageService.getMessagesInGroup(groupId));
    }

    // websockets
//    use simplemessagingtemplate for dynamic topics with path variables
    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public Message send(Message message) {
        messageService.saveMessage(message.getContent(), message.getSender(), message.getRecipient());
        return new Message(message.getContent(), message.getSender(), message.getRecipient());
    }
}
