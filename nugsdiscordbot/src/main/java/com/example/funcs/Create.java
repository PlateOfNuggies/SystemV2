package com.example.funcs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONObject;

import com.example.funcs.Pipeline.COMMAND;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Create implements COMMAND {
    JDA api;
    String name = "create";
    String[] parameters;
    MessageChannel channel;
    Message event;

    public Create(JDA api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    @Override
    public COMMAND setContext(Message e, String[] parameters, MessageChannel channel) {
        this.event = e;
        this.parameters = parameters;
        this.channel = channel;
        return this;
    }

    @Override
    public void execute() {

        if (channel == null) {
            channel = event.getChannel();
        }

        if (parameters.length < 2) {
            channel.sendMessage(
                    name + " called without enough parameters. Use with the name of the character you would like to create. Usage:\n !create \"name\"")
                    .queue();
            return;
        } else {
            try {
                File Obj = new File("nugsdiscordbot\\src\\main\\resources\\Characters\\" + parameters[1] + ".txt");

                // Creating File
                if (Obj.createNewFile()) {
                    channel.sendMessage("Character file created: " + parameters[1]).queue();
                } else {
                    channel.sendMessage("Character file already exists.").queue();
                    return;
                }
                {
                    FileOutputStream out = new FileOutputStream(Obj);

                    out.write(writeTemplate(Obj, parameters[1]).toString().getBytes());
                    out.close();
                }

            } catch (IOException e) {
                System.out.println("An error has occurred.");
                channel.sendMessage("Invalid name. Please use something that can actually be used as a filename.")
                        .queue();
                e.printStackTrace();
                return;
            }
        }

    };

    public JSONObject writeTemplate(File file, String charName) {

        //
        JSONObject templateJSON = new JSONObject();
        JSONObject stats = new JSONObject();
        templateJSON.put("name", charName);
        templateJSON.put("species", "");

        stats.put("HP", "0");
        stats.put("MP", "0");
        stats.put("SPY", "0");
        stats.put("SPR", "0");

        stats.put("OFF", "0");
        stats.put("DEF", "0");
        stats.put("MAG", "0");
        stats.put("RES", "0");
        stats.put("SPD", "0");

        templateJSON.put("stats", stats);

        templateJSON.put("skills", "none");

        return templateJSON;
    };

}
