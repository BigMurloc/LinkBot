package pl.bigmurloc.linkbot.command.commands;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import pl.bigmurloc.linkbot.command.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.bigmurloc.linkbot.entity.Message;
import pl.bigmurloc.linkbot.service.MessageService;
import pl.bigmurloc.linkbot.command.annotations.CommandName;

@Component(value = "!add")
@CommandName(value = "!add")
public class AddCommand implements Command {

    private final MessageService messageService;

    public AddCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        if (channel.hasLatestMessage()) {
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        channel.sendMessage(args[1]).queue((response -> {
            Message message = new Message();
            message.setMessageValue(response.getContentRaw());
            message.setDiscordMessageId(response.getIdLong());
            message.setAuthor(response.getAuthor().getIdLong());
            messageService.add(message);
        }));
    }
}
