package pl.bigmurloc.linkbot.entity;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "message")
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "link")
    private String messageValue;

    @Column(name = "message_id")
    private Long discordMessageId;

    @Column(name = "author_id")
    private Long author;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(id, message.id) && Objects.equals(message, message.messageValue) && Objects.equals(discordMessageId, message.discordMessageId) && Objects.equals(author, message.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageValue, discordMessageId, author);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessageValue() {
        return messageValue;
    }

    public void setMessageValue(String link) {
        this.messageValue = link;
    }

    public Long getDiscordMessageId() {
        return discordMessageId;
    }

    public void setDiscordMessageId(Long discordMessageId) {
        this.discordMessageId = discordMessageId;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }
}
