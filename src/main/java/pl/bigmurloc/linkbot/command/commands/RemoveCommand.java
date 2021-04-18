package pl.bigmurloc.linkbot.command.commands;

import org.springframework.stereotype.Component;
import pl.bigmurloc.linkbot.command.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.bigmurloc.linkbot.entity.Message;
import pl.bigmurloc.linkbot.service.MessageService;
import pl.bigmurloc.linkbot.utils.Utils;
import pl.bigmurloc.linkbot.command.annotations.CommandName;

import java.io.*;
import java.util.Scanner;

@Component(value = "!remove")
@CommandName(value = "!remove")
public class RemoveCommand implements Command {

    private final MessageService messageService;

    public RemoveCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        String messageValue = args[1];
        if (channel.hasLatestMessage()) {
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        Message message = messageService.findByMessageValue(messageValue);
        if(message != null) {
            messageService.remove(message);
            channel.purgeMessagesById(message.getDiscordMessageId());
        }
    }

}
