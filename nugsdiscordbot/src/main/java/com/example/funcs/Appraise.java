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

    /**
     * Function to appraise a character. This function will read the character's JSON file and output their stats and skills in a formatted message.
     * If the character does not exist, it will state that the character could not be found.
     * 
     * Parameters:
     * - parameters[1]: The name of the character to appraise.
     */
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
                            + " HP: " + charaSheetJSON.getJSONObject("stats").getInt("HPcurr") + "/" + charaSheetJSON.getJSONObject("stats").getInt("HPmax")
                            + " MP: " + charaSheetJSON.getJSONObject("stats").getInt("MPcurr") + "/" + charaSheetJSON.getJSONObject("stats").getInt("MPmax")
                            + " SPY: " + charaSheetJSON.getJSONObject("stats").getInt("SPYcurr") + "/" + charaSheetJSON.getJSONObject("stats").getInt("SPYmax")
                            + " SPR: " + charaSheetJSON.getJSONObject("stats").getInt("SPRcurr") + "/" + charaSheetJSON.getJSONObject("stats").getInt("SPRmax")
                            + "\n"
                            + " OFF: " + charaSheetJSON.getJSONObject("stats").getInt("OFFcurr")
                            + " DEF: " + charaSheetJSON.getJSONObject("stats").getInt("DEFcurr")
                            + " MAG: " + charaSheetJSON.getJSONObject("stats").getInt("OFFcurr")
                            + " RES: " + charaSheetJSON.getJSONObject("stats").getInt("REScurr")
                            + " SPD: " + charaSheetJSON.getJSONObject("stats").getInt("SPDcurr")
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
