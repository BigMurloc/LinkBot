package pl.bigmurloc.linkbot.service.impl;


import org.springframework.stereotype.Service;
import pl.bigmurloc.linkbot.service.MessageService;
import pl.bigmurloc.linkbot.repository.MessageRepository;
import pl.bigmurloc.linkbot.entity.Message;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public boolean add(Message message) {
        if(!doesExist(message)) {
            messageRepository.save(message);
            return true;
        }
        return false;
    }

    @Override
    public void remove(Message message) {
        if(doesExist(message)){
            messageRepository.delete(message);
        }
    }

    @Override
    public boolean update(Message message) {
        if(doesExist(message)) {
            messageRepository.save(message);
            return true;
        }
        return false;
    }

    @Override
    public Message findByMessageValue(String messageValue) {
        return messageRepository.findMessageByMessageValue(messageValue);
    }

    @Override
    public Message findByMessageId(Long messageId) {
        return messageRepository.findMessageByDiscordMessageId(messageId);
    }


    public boolean doesExist(Message message){
        return messageRepository.existsByMessageValue(message.getMessageValue());
    }
}
