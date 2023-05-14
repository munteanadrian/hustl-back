package tech.adrianmuntean.hustl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.adrianmuntean.hustl.model.Message;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRecipient_CommunityId(Long communityId);
}
