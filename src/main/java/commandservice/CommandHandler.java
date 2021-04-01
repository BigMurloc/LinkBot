package commandservice;

import commandservice.commands.AddCommand;
import commandservice.commands.EraseCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;


public class CommandHandler extends ListenerAdapter {

    private Command command;

    private CommandHandler() {}
    private static CommandHandler instance;
    public static CommandHandler getInstance(){
        if (instance == null) { instance = new CommandHandler(); }
        return instance;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String commandRaw = event.getMessage().getContentRaw();
        String[] args = commandRaw.split(" ");
        command = getCommandImpl(args[0]);
        if(command != null) {
            command.handle(event, args);
        }
    }

    private Command getCommandImpl(String command){
        if(command.equals("!erase")){
            return EraseCommand.getInstance();
        }
        if(command.equals("!add")){
            return AddCommand.getInstance();
        }
        return null;
    }

}
