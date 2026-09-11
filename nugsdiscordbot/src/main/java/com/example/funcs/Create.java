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

    /**
     * Function to write a template JSON object to a file. This is used when creating a new character file.
     * 
     * @param file The file to write the template to.
     * @param charName The name of the character to be created.
     * @return A JSONObject representing the template for the character.
     */
    public JSONObject writeTemplate(File file, String charName) {

        // Adding everything to JSON object to be written to file
        JSONObject templateJSON = new JSONObject();
        JSONObject stats = new JSONObject();
        templateJSON.put("name", charName);
        templateJSON.put("species", "");

        stats.put("HPmax", "0");
        stats.put("MPmax", "0");
        stats.put("SPYmax", "0");
        stats.put("SPRmax", "0");
        
        stats.put("HPcurr", "0");
        stats.put("MPcurr", "0");
        stats.put("SPYcurr", "0");
        stats.put("SPRcurr", "0");

        stats.put("OFFmax", "0");
        stats.put("DEFmax", "0");
        stats.put("MAGmax", "0");
        stats.put("RESmax", "0");
        stats.put("SPDmax", "0");
        stats.put("OFFcurr", "0");
        stats.put("DEFcurr", "0");
        stats.put("MAGcurr", "0");
        stats.put("REScurr", "0");
        stats.put("SPDcurr", "0");

        templateJSON.put("stats", stats);

        templateJSON.put("skills", "none");

        return templateJSON;
    };

}
