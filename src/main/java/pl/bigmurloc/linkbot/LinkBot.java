package pl.bigmurloc.linkbot;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import pl.bigmurloc.linkbot.command.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginException;

@SpringBootApplication
public class LinkBot {
    public static void main(String[] args) throws LoginException {
        ApplicationContext context = (SpringApplication.run(LinkBot.class, args));
        Dotenv dotenv = Dotenv.configure().directory(".env").load();
        String token = dotenv.get("TOKEN");
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new CommandHandler(context));
    }
}
