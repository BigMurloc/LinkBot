package commandservice;

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
        command = getCommandImpl(commandRaw);
        if(command != null) {
            command.handle(event);
        }
    }

    private Command getCommandImpl(String command){
        if(command.equals("!erase")){
            return EraseCommand.getInstance();
        }
        return null;
    }

}
