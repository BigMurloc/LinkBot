package pl.bigmurloc.linkbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bigmurloc.linkbot.entity.Message;


@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    boolean existsByMessageValue(String message);
    Message findMessageByDiscordMessageId(Long discordMessageId);
    Message findMessageByMessageValue(String messageValue);
}
