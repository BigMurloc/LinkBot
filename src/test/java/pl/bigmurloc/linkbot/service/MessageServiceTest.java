package pl.bigmurloc.linkbot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bigmurloc.linkbot.entity.Message;
import pl.bigmurloc.linkbot.exception.MessageAlreadyExistsException;
import pl.bigmurloc.linkbot.exception.MessageDoesNotExistException;
import pl.bigmurloc.linkbot.repository.MessageRepository;
import pl.bigmurloc.linkbot.service.impl.MessageServiceImpl;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    private MessageRepository messageRepository;
    private MessageService underTest;

    @BeforeEach
    void setUp() {
        underTest = new MessageServiceImpl(messageRepository);
    }

    @Test
    void canAddMessage() {
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        // when
        underTest.add(message);
        // then
        ArgumentCaptor<Message> messageArgumentCaptor
                = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(messageArgumentCaptor.capture());
        Message capturedMessage = messageArgumentCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(message);
    }

    @Test
    void willThrowWhenMessageAlreadyExists(){
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        given(messageRepository.existsByMessageValue(message.getMessageValue()))
                .willReturn(true);
        // when
        // then
        assertThatThrownBy(() -> underTest.add(message))
                .isInstanceOf(MessageAlreadyExistsException.class);

    }

    @Test
    void canRemoveMessage() {
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        given(messageRepository.existsByMessageValue(message.getMessageValue()))
                .willReturn(true);
        // when
        underTest.remove(message);
        // then
        ArgumentCaptor<Message> messageArgumentCaptor
                = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).delete(messageArgumentCaptor.capture());
        Message capturedMessage = messageArgumentCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(message);
    }

    @Test
    void willThrowMessageDoesNotExistsWhenRemove() {
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        given(messageRepository.existsByMessageValue(message.getMessageValue()))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.remove(message))
                .isInstanceOf(MessageDoesNotExistException.class);
    }

    @Test
    void canUpdateMessage()  {
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        given(messageRepository.existsByMessageValue(message.getMessageValue()))
                .willReturn(true);
        // when
        underTest.update(message);
        // then
        ArgumentCaptor<Message> messageArgumentCaptor
                = ArgumentCaptor.forClass(Message.class);
        verify(messageRepository).save(messageArgumentCaptor.capture());
        Message capturedMessage = messageArgumentCaptor.getValue();
        assertThat(capturedMessage).isEqualTo(message);
    }

    @Test
    void willThrowMessageDoesNotExistsWhenUpdate() {
        // given
        Message message = new Message();
        message.setMessageValue("test");
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        given(messageRepository.existsByMessageValue(message.getMessageValue()))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> underTest.update(message))
                .isInstanceOf(MessageDoesNotExistException.class);
    }

    @Test
    void findByMessageValue() {
        // given
        String value = "test";
        // when
        underTest.findByMessageValue(value);
        // then
        verify(messageRepository).findMessageByMessageValue(value);
    }

    @Test
    void findByMessageId() {
        // given
        Long discordId = 123L;
        // when
        underTest.findByMessageId(discordId);
        // then
        verify(messageRepository).findMessageByDiscordMessageId(discordId);
    }
}