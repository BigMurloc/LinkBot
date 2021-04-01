package commandservice;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@FunctionalInterface
public interface Command {
    void handle(MessageReceivedEvent event);
}
