package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class TestCommand implements Command {


    private static TestCommand instance;

    private TestCommand(){}
    public static TestCommand getInstance(){
        if(instance == null){
            instance = new TestCommand();
        }
        return instance;
    }

    @Override
    public void handle(MessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        channel.sendMessage("Dzialam!").queue();
    }
}
