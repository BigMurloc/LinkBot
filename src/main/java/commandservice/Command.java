package commandservice;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.FileNotFoundException;

@FunctionalInterface
public interface Command {
    void handle(MessageReceivedEvent event, String[] args);
}
