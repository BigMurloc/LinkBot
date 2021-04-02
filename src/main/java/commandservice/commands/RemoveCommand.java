package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;

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
                deleteFromFile(link+" "+linkId, file);
            }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private void deleteFromFile(String line, File file){
        try {
            File copy = new File(file.toPath().toString()+"Copy");
            Files.copy(file.toPath(), copy.toPath());

            BufferedReader bufferedReader = new BufferedReader(new FileReader(copy));
            PrintWriter printWriter = new PrintWriter(file);

            String line1 = bufferedReader.readLine();
            while(line1 != null){
                if(!line.equals(line1)){
                    printWriter.println(line1);
                }
                line1 = bufferedReader.readLine();
            }
            bufferedReader.close();
            printWriter.flush();
            printWriter.close();
            Files.delete(copy.toPath());
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
