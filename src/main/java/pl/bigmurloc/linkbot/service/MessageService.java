package pl.bigmurloc.linkbot.service;

import org.springframework.stereotype.Service;
import pl.bigmurloc.linkbot.entity.Message;

@Service
public interface MessageService {

    boolean add(Message message);
    void remove(Message message);
    boolean update(Message message);
    Message findByMessageValue(String messageValue);
    Message findByMessageId(Long messageId);

}
