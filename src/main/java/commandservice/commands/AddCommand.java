package commandservice.commands;

import commandservice.Command;
import commandservice.annotations.CommandName;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.Utils;

import java.io.*;

@CommandName(value = "!add")
public class AddCommand implements Command {

    private static AddCommand instance;

    private AddCommand() {}
    

    public static AddCommand getInstance() {
        if (instance == null) {
            instance = new AddCommand();
        }
        return instance;
    }

    //optimistic
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
