package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.util.Scanner;

public class AddCommand implements Command {

    private static AddCommand instance;

    private AddCommand(){};

    public static AddCommand getInstance(){
        if(instance==null){
            instance=new AddCommand();
        }
        return instance;
    }

    //optimistic
    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        String path = "src/main/resources/links";
        File file = new File(path);
        MessageChannel channel = event.getChannel();
        if(channel.hasLatestMessage()){
            channel.purgeMessagesById(channel.getLatestMessageId());
        }
        if(isUnique(args[1], file)) {
            channel.sendMessage(args[1]).queue();
            writeToFile(args[1], file, channel.getLatestMessageId());
        }
    }

    private boolean isUnique(String link, File file){
        try {
            Scanner scanner = new Scanner(file);
            while(scanner.hasNext()){
                String[] row = scanner.nextLine().split(" ");
                String fileLink = row[0];
                if(link.equals(fileLink)){
                    return false;
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        return true;
    }

    private void writeToFile(String link, File file, String id){
        try {
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(link+" "+ id);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
