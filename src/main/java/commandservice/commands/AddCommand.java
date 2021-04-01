package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class AddCommand implements Command {

    private static AddCommand instance;

    private AddCommand(){};

    public static AddCommand getInstance(){
        if(instance==null){
            instance=new AddCommand();
        }
        return instance;
    }

    @Override
    public void handle(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        for(String value: args){
            System.out.println(value);
        }
    }
}
