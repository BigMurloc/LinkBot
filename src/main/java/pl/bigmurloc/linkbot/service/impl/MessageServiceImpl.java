package pl.bigmurloc.linkbot.service.impl;


import org.springframework.stereotype.Service;
import pl.bigmurloc.linkbot.exception.MessageAlreadyExistsException;
import pl.bigmurloc.linkbot.exception.MessageDoesNotExistException;
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
    public void add(Message message) {
        boolean doesExist = messageRepository
                .existsByMessageValue(message.getMessageValue());
        if(doesExist) {
            throw new MessageAlreadyExistsException();
        }
        messageRepository.save(message);
    }

    @Override
    public void remove(Message message) {
        boolean doesExist = messageRepository
                .existsByMessageValue(message.getMessageValue());
        if(!doesExist){
            throw new MessageDoesNotExistException();
        }
        messageRepository.delete(message);
    }

    @Override
    public void update(Message message) {
        boolean doesExist = messageRepository
                .existsByMessageValue(message.getMessageValue());
        if(!doesExist) {
            throw new MessageDoesNotExistException();
        }
        messageRepository.save(message);
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
