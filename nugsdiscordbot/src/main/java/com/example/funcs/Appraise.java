package com.example.funcs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONObject;

import com.example.funcs.Pipeline.COMMAND;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class Appraise implements COMMAND {
    JDA api;
    String name = "appraise";
    String[] parameters;
    MessageChannel channel;
    Message event;

    String charaPath = "nugsdiscordbot\\src\\main\\resources\\Characters";
    String userPath = "nugsdiscordbot\\src\\main\\resources\\Users";

    public Appraise(JDA api) {
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

    @Override
    public void execute() {

        if (channel == null) {
            channel = event.getChannel();
        }

        if (parameters.length < 2) {
            channel.sendMessage(
                    name + " called without enough parameters. Use with the name of the character you would like to appraise. Usage:\n !appraise \"name\"")
                    .queue();
            return;
        } else {

            System.out.println("Called Appraise on " + parameters[1]);
            File f = new File(charaPath + "\\" + parameters[1] + ".txt");
            if (f.isFile()) {
                channel.sendMessage("Appraising " + parameters[1]).queue();

                //

                FileReader fis;
                String output = "";
                try {

                    fis = new FileReader(f);
                    int i;
                    while ((i = fis.read()) != -1) {
                        output = output + ((char) i);
                    }

                    JSONObject charaSheetJSON = new JSONObject(output);

                    String message = "```\n"
                            + "Name: " + charaSheetJSON.getString("name")
                            + " | Species: " + charaSheetJSON.getString("species")
                            + "\nStats:\n"
                            + " HP: " + charaSheetJSON.getJSONObject("stats").getInt("HP")
                            + " MP: " + charaSheetJSON.getJSONObject("stats").getInt("MP")
                            + " SPY: " + charaSheetJSON.getJSONObject("stats").getInt("SPY")
                            + " SPR: " + charaSheetJSON.getJSONObject("stats").getInt("SPR")
                            + "\n"
                            + " OFF: " + charaSheetJSON.getJSONObject("stats").getInt("OFF")
                            + " DEF: " + charaSheetJSON.getJSONObject("stats").getInt("DEF")
                            + " MAG: " + charaSheetJSON.getJSONObject("stats").getInt("OFF")
                            + " RES: " + charaSheetJSON.getJSONObject("stats").getInt("RES")
                            + " SPD: " + charaSheetJSON.getJSONObject("stats").getInt("SPD")
                            + "\nSkills:\n " + charaSheetJSON.getString("skills")
                            + "\n```";

                    channel.sendMessage(message).queue();
                    //

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //
            } else {
                System.out.println("File does not exist.");
                channel.sendMessage("Couldn't find character.").queue();
            }
        }
    }

}
