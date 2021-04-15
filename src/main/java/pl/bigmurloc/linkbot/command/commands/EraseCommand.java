package pl.bigmurloc.linkbot.command.commands;

import org.springframework.stereotype.Component;
import pl.bigmurloc.linkbot.command.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.bigmurloc.linkbot.command.annotations.CommandName;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component(value = "!erase")
@CommandName(value = "!erase")
public class EraseCommand implements Command {

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
