package pl.bigmurloc.linkbot.command.commands;

import net.dv8tion.jda.api.entities.ISnowflake;
import org.springframework.stereotype.Component;
import pl.bigmurloc.linkbot.command.Command;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import pl.bigmurloc.linkbot.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component(value = "!erase")
public class EraseCommand implements Command {

    private final MessageService messageService;

    public EraseCommand(MessageService messageService) {
        this.messageService = messageService;
    }

    public final void handle(MessageReceivedEvent event, String[] args) {
        MessageChannel channel = event.getChannel();
        List<Long> ids = null;
        if (channel.hasLatestMessage()) {
            CompletableFuture<List<Message>> completableFuture = channel.getIterableHistory()
                    .takeAsync(100)
                    .thenApply(ArrayList::new);
            try {
                List<Message> messages = completableFuture.get();
                ids = messages.stream().map(ISnowflake::getIdLong).collect(Collectors.toList());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (ids != null) {
            ids.stream().forEach(it -> {
                channel.purgeMessagesById(it);
                pl.bigmurloc.linkbot.entity.Message message = messageService.findByMessageId(it);
                if(message != null) {
                    messageService.remove(message);
                }
            });
        }
    }

}
