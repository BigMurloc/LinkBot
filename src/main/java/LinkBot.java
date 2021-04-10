import commandservice.CommandHandler;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import javax.security.auth.login.LoginException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Scanner;

//todo fix paths
public class LinkBot {
    public static void main(String[] args) throws LoginException, FileNotFoundException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream is = classLoader.getResourceAsStream("token");
        assert is != null;
        Scanner scanner = new Scanner(is);
        String token = scanner.nextLine();
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(CommandHandler.getInstance());
        System.out.println(LinkBot.class.getPackageName());
    }
}
