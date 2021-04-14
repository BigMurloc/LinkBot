package pl.bigmurloc.linkbot.command.commands;

import org.springframework.stereotype.Component;
import pl.bigmurloc.linkbot.command.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.bigmurloc.linkbot.utils.Utils;
import pl.bigmurloc.linkbot.command.annotations.CommandName;
import java.io.*;

@Component
@CommandName(value = "!add")
public class AddCommand implements Command {

    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        String path = "src/main/resources/links";
        File file = new File(path);
        MessageChannel channel = event.getChannel();
        if (channel.hasLatestMessage()) {
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        if (Utils.isUnique(args[1], file)) {
            channel.sendMessage(args[1]).queue((message -> {
                String messageId = message.getId();
                Utils.writeToFile(args[1], file, messageId);
            }));
        }
    }

}
