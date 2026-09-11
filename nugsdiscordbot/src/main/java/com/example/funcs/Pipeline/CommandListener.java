package com.example.funcs.Pipeline;

import java.util.Arrays;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {
    CommandHandler controller;
    static JDA api;
    private static String[] approvedGuilds = { "857792332610797579", "1365387716363157574" };

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;

        if (event.isFromGuild()) {
            if (!guildIsApproved(event)) {
                System.out.println("Illegal access from:" + event.getGuild().getId());
                return;
            }
        } else
            return;

        Message message = event.getMessage();

        controller.handle(message);

    }

    public CommandListener(CommandHandler c) {
        controller = c;
    }

    public void register(JDA api) {
        api.addEventListener(this);
    }

    public static boolean guildIsApproved(MessageReceivedEvent eventt) {
        if (Arrays.stream(approvedGuilds).anyMatch(eventt.getGuild().getId()::equals)) {
            return true;
        } else
            return false;
    }
}
