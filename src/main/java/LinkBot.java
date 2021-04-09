import commandservice.Command;
import commandservice.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import org.reflections.Reflections;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LinkBot {
    public static void main(String[] args) throws LoginException, FileNotFoundException {
        Scanner scanner = new Scanner(new File("src/main/resources/token"));
        String token = scanner.nextLine();
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(CommandHandler.getInstance());
    }
}
