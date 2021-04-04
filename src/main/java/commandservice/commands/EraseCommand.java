package commandservice.commands;

import commandservice.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class EraseCommand implements Command {

    private static EraseCommand instance;

    private EraseCommand() {
    }

    public static EraseCommand getInstance() {
        if (instance == null) {
            instance = new EraseCommand();
        }
        return instance;
    }

    public final void handle(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        List<String> ids = null;
        if (channel.hasLatestMessage()) {
            CompletableFuture<List<Message>> completableFuture = channel.getIterableHistory()
                    .takeAsync(100)
                    .thenApply(list -> list.stream().collect(Collectors.toList()));
            try {
                List<Message> messages = completableFuture.get();
                ids = messages.stream().map(it -> it.getId()).collect(Collectors.toList());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (ids != null) {
            ids.stream().forEach(id -> channel.purgeMessagesById(id));
        }
    }

}
