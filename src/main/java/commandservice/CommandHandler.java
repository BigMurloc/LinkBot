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
        if(commandRaw.equals("erase")){
            command.handle(event);
        }
    }
}
