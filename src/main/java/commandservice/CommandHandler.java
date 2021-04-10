package commandservice;

import commandservice.annotations.CommandName;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;


public class CommandHandler extends ListenerAdapter {

    private static CommandHandler instance;

    private final HashMap<String, Command> commands = new HashMap<>();

    public static CommandHandler getInstance() {
        if (instance == null) {
            instance = new CommandHandler();
        }
        return instance;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        String commandRaw = event.getMessage().getContentRaw();
        String[] args = commandRaw.split(" ");
        Command command = commands.get(args[0]);
        if (command != null) {
            command.handle(event, args);
        }
    }
//

    private CommandHandler() {
        registerCommands();
    }

    private void registerCommands() {
        Reflections reflections = new Reflections(Command.class.getPackageName() + ".commands");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for(Class<? extends Command> clazz : classes){
            if(clazz.isAnnotationPresent(CommandName.class)) {
                try {
                    CommandName annotation = clazz.getAnnotation(CommandName.class);
                    String commandName = annotation.value();
                    Method method = clazz.getMethod("getInstance");
                    if(method.invoke(null) instanceof Command) {
                        Command command = (Command) method.invoke(null);
                        commands.put(commandName, command);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
