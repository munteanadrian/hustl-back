package tech.adrianmuntean.hustl.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import tech.adrianmuntean.hustl.dto.MessageDTO;
import tech.adrianmuntean.hustl.dto.ReturnMessageDTO;
import tech.adrianmuntean.hustl.model.Message;
import tech.adrianmuntean.hustl.service.MessageService;

@CrossOrigin(origins = "*", maxAge = 4800)
@Controller
public class MessageController {
    private final MessageService messageService;

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message/{groupId}")
    @SendTo("/topic/{groupId}")
    public ResponseEntity<Object> sendMessage(@Payload MessageDTO messageDTO) {
        try {
            Message message = messageService.sendMessage(messageDTO.getMessage(), messageDTO.getUserId(), messageDTO.getGroupId());

            if (message != null) {
                ReturnMessageDTO returnMessage = new ReturnMessageDTO(message.getTimestamp(), message.getContent(), message.getSender().getName(), message.getSender().getUserId());
                messagingTemplate.convertAndSend("/topic/" + messageDTO.getGroupId(), message);
                return ResponseEntity.ok().body(returnMessage);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
