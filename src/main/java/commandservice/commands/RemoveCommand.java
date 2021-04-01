package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class RemoveCommand implements Command {

    private static RemoveCommand instance;
    private RemoveCommand(){}

    public static RemoveCommand getInstance(){
        if(instance == null){
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
        if(channel.hasLatestMessage()){
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
            String[] row = scanner.nextLine().split(" ");
            String link = row[0];
            String linkId = row[1];
            if(linkMessage.equals(link)){
                channel.purgeMessagesById(linkId);
            }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}
