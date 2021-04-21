package pl.bigmurloc.linkbot.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Set;

@Component
public class CommandHandler extends ListenerAdapter {

    private final HashMap<String, Command> commands = new HashMap<>();

    private final ApplicationContext context;

    public CommandHandler(ApplicationContext context) {
        this.context = context;
        registerCommands();
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

    public void registerCommands() {
        Reflections reflections = new Reflections(Command.class.getPackageName() + ".commands");
        Set<Class<? extends Command>> classes = reflections.getSubTypesOf(Command.class);
        for (Class<? extends Command> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class)) {
                try {
                    Component annotation = clazz.getAnnotation(Component.class);
                    String commandName = annotation.value();
                    Command command = (Command) context.getBean(commandName);
                    commands.put(commandName, command);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
