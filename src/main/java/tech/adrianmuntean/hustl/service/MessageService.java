package tech.adrianmuntean.hustl.service;

import org.springframework.stereotype.Service;
import tech.adrianmuntean.hustl.model.Community;
import tech.adrianmuntean.hustl.model.Message;
import tech.adrianmuntean.hustl.model.User;
import tech.adrianmuntean.hustl.repository.CommunityRepository;
import tech.adrianmuntean.hustl.repository.MessageRepository;
import tech.adrianmuntean.hustl.repository.UserRepository;

import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;

    public MessageService(MessageRepository messageRepository, UserRepository userRepository, CommunityRepository communityRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.communityRepository = communityRepository;
    }

    public Message sendMessage(String message, Long userId, Long communityId) {
        User sender = userRepository.findById(userId).orElse(null);
        Community recipient = communityRepository.findById(communityId).orElse(null);
        Message newMessage = new Message(message, sender, recipient);

        messageRepository.save(newMessage);

        if (sender != null) {
            sender.getMessages().add(newMessage);
            userRepository.save(sender);
        }

        if (recipient != null) {
            recipient.getMessages().add(newMessage);
            communityRepository.save(recipient);
        }

        return newMessage;
    }

    public List<Message> getMessagesInGroup(Long groupId) {
        return messageRepository.findAllByRecipient_CommunityId(groupId);
    }

    public void saveMessage(String content, User sender, Community recipient) {
        messageRepository.save(new Message(content, sender, recipient));
    }
}
