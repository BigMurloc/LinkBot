package pl.bigmurloc.linkbot.command.commands;

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
        String messageValue = args[1];
        MessageChannel channel = event.getChannel();
        Message message = new Message();
        message.setMessageValue(messageValue);
        if (channel.hasLatestMessage()) {
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        if(messageService.doesExist(message)) {
            channel.sendMessage(messageValue).queue((response -> {
                message.setDiscordMessageId(response.getIdLong());
                message.setAuthor(response.getAuthor().getIdLong());
                messageService.update(message);
            }));
        }
    }
}
