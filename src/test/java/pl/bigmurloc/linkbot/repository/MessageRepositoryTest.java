package pl.bigmurloc.linkbot.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pl.bigmurloc.linkbot.entity.Message;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository underTest;

    @Test
    void itShouldCheck_IfMessageExistsByValue_ThenTrue() {
        // given
        String value = "test";
        Message message = new Message();
        message.setMessageValue(value);
        message.setDiscordMessageId(123L);
        message.setAuthor(132L);
        underTest.save(message);
        // when
        boolean expected = underTest.existsByMessageValue(value);
        // then
        assertThat(expected).isTrue();
    }

    @Test
    void itShouldCheck_IfMessageExistsByValue_ThenFalse() {
        // given
        String value = "test";
        // when
        boolean expected = underTest.existsByMessageValue(value);
        // then
        assertThat(expected).isFalse();
    }

    @Test
    void itShouldReturnMessage_WhenFindMessageByDiscordId_AndMessageExists() {
        // given
        Long discordId = 100L;
        Message message = new Message();
        message.setMessageValue("test");
        message.setAuthor(123L);
        message.setDiscordMessageId(100L);
        underTest.save(message);
        // when
        Message expected = underTest.findMessageByDiscordMessageId(discordId);
        // then
        assertThat(expected).isEqualTo(message);
    }

    @Test
    void itShouldReturnNull_WhenFindMessageByDiscordId_AndMessageDoesNotExists(){
        // given
        Long discordId = 100L;
        // when
        Message expected = underTest.findMessageByDiscordMessageId(discordId);
        // then
        assertThat(expected).isNull();
    }

    @Test
    void itShouldReturnMessage_WhenFindMessageByValue_AndMessageExists() {
        // given
        String value = "test";
        Message message = new Message();
        message.setDiscordMessageId(100L);
        message.setAuthor(123L);
        message.setMessageValue(value);
        underTest.save(message);
        // when
        Message expected = underTest.findMessageByMessageValue(value);
        // then
        assertThat(expected).isEqualTo(message);
    }

    @Test
    void itShouldReturnNull_WhenFindMessageByValue_AndMessageDoesNotExists() {
        // given
        String value = "test";
        // when
        Message expected = underTest.findMessageByMessageValue(value);
        // then
        assertThat(expected).isNull();
    }
}