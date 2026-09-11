package com.example.funcs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.example.funcs.Pipeline.COMMAND;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Add implements COMMAND {
    JDA api;
    String name = "add";
    String[] parameters;
    MessageChannel channel;
    Message event;

    String charaPath = "nugsdiscordbot\\src\\main\\resources\\Characters";
    String userPath = "nugsdiscordbot\\src\\main\\resources\\Users";

    public Add(JDA api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    @Override
    public COMMAND setContext(Message e, String[] parameters, MessageChannel channel) {
        this.event = e;
        this.channel = channel;
        this.parameters = parameters;
        return this;
    }

    /**
     * Function to add a skill to a character, or skill XP if the skill already exists.
     * If the character or skill doesn't exist, it will return an error message.
     */
    @Override
    public void execute() {

        if (channel == null) {
            channel = event.getChannel();
        }

        if (parameters.length < 3) {
            channel.sendMessage(
                    name + " called without enough parameters. Use with a name and something to add! Usage: \n!add \"Character name\" \"skill name\" \"skill name\" ...")
                    .queue();
            return;
        } else {

            String characterToSaveInto = parameters[1];

            String[] dir = new File(charaPath).list();

            boolean foundCharacter = false;
            for (String fileToCheck : dir) {
                if (fileToCheck.equals(characterToSaveInto + ".txt")) {
                    foundCharacter = true;
                    break; // Found, so finish early.
                }
            }

            if (foundCharacter == false) {
                System.out.println("Couldn't find the character " + characterToSaveInto + ". Responding.");

                channel.sendMessage(
                        "Couldn't find the character.")
                        .queue();
                return; // No such character, don't even try.
            }

            try {

                FileOutputStream out = new FileOutputStream(charaPath + "\\" + characterToSaveInto + ".txt");

                out.write(parameters[2].getBytes());
                out.close();

            } catch (FileNotFoundException e) {
                System.out.println("Couldn't find file at " + charaPath + "\\" + characterToSaveInto + ".txt");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("File found but couldn't write to Tender.");
                e.printStackTrace();
            }

            channel.sendMessage(
                    "Added. :ok_hand:")
                    .queue();
        }
    };

}
