package studio.aroundhub.square_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class SquareBotApplication {

    @Autowired
    private Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(SquareBotApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(value = "discord-api")
    public DiscordApi discordApi() {
        String TOKEN = environment.getProperty("TOKEN");
        DiscordApi api = new DiscordApiBuilder().setToken(TOKEN)
            .setAllNonPrivilegedIntents()
            .login()
            .join();

        api.addMessageCreateListener(event -> {
            if(event.getMessageContent().equals(".ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        return api;
    }

}
