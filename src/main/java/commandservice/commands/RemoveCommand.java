package commandservice.commands;

import commandservice.Command;
import commandservice.annotations.CommandName;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import utils.Utils;

import java.io.*;
import java.util.Scanner;

@CommandName(value = "!remove")
public class RemoveCommand implements Command {

    private static RemoveCommand instance;

    private RemoveCommand() {}

    public static RemoveCommand getInstance() {
        if (instance == null) {
            instance = new RemoveCommand();
        }
        return instance;
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        String path = "src/main/resources/links";
        File file = new File(path);
        MessageChannel channel = event.getChannel();
        String linkMessage = args[1];
        if (channel.hasLatestMessage()) {
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String[] row = scanner.nextLine().split(" ");
                String link = row[0];
                String linkId = row[1];
                if (linkMessage.equals(link)) {
                    channel.purgeMessagesById(linkId);
                    Utils.deleteFromFile(link + " " + linkId, file);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
