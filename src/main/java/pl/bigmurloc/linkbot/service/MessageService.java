package pl.bigmurloc.linkbot.service;

import org.springframework.stereotype.Service;
import pl.bigmurloc.linkbot.entity.Message;

@Service
public interface MessageService {

    void add(Message message);
    void remove(Message message);
    void update(Message message);
    boolean doesExist(Message message);
    Message findByMessageValue(String messageValue);
    Message findByMessageId(Long messageId);

}
