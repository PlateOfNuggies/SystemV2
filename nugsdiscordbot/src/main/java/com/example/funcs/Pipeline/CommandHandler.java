package com.example.funcs.Pipeline;

import java.util.HashMap;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class CommandHandler {
    // Initialize with Hashlist (CommandPrefix : CommandName)
    HashMap<String, COMMAND> commands;

    public CommandHandler(HashMap<String, COMMAND> commands) {
        this.commands = commands;
    }

    public void handle(Message message) {

        String content = message.getContentRaw();

        if (content.startsWith("!")) {
            content = content.substring(1);

            String[][] commandQueue = commaSeparate(content);

            if (commands.containsKey(commandQueue[0][0])) {

                if (commandQueue.length > 1) {

                    MessageChannel messageChannel = message.getChannel();
                    for (int i = 0; i < commandQueue.length; i++) {

                        if (commands.containsKey(commandQueue[i][0])) {

                            commands.get(commandQueue[i][0]).setContext(message, commandQueue[i], messageChannel)
                                    .execute();

                        } else {

                            messageChannel.sendMessage("Invalid command in listed commands at index " + (i + 1))
                                    .queue();
                        }
                    }

                } else {

                    commands.get(commandQueue[0][0]).setContext(message, commandQueue[0], null).execute();
                }
            } else {

                message.getChannel().sendMessage("What do you mean? Check capitalization maybe.").queue();
            }
        }
    }

    public String[][] commaSeparate(String input) {
        // cuts up a given string into a list of lists
        // first index of each sub-list is the command to run

        String[] fullCommands = input.split(",(?=(?:[^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
        String[][] parameterizedCommands = new String[fullCommands.length][];

        for (int i = 0; i < fullCommands.length; i++) {
            fullCommands[i] = fullCommands[i].trim();
            parameterizedCommands[i] = fullCommands[i].split(" (?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            for (int a = 0; a < (parameterizedCommands[i].length); a++) {
                parameterizedCommands[i][a] = parameterizedCommands[i][a].replaceAll("\"", "");
            }
        }

        return parameterizedCommands;
    }

}

/*
 * 
 * Hashmap knownCommands, Message message
 * 
 * !create tender bingus, create alice, ping, marco
 * 
 * remove !
 * seperate by comma into String[] commandQueue
 * trim each instance
 * seperate by space into String[][] commandQueue
 * trim each instance
 * 
 * String[][] commandQueue
 * [{create, tender, bingus},
 * {create, alice},
 * {ping},
 * {marco}]
 * 
 * loop through commandQueue[].length for calling functions
 * call individual commands with commandQueue[i][0]
 * each command gets a String[] which is given by commandQueue[i]
 * 
 */