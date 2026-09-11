package com.example.funcs;

import com.example.funcs.Pipeline.COMMAND;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class ReactRoles implements COMMAND {
    JDA api;
    String name = "rr";
    String[] params;
    Message event;
    Message prev;

    public ReactRoles(JDA api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    @Override
    public COMMAND setContext(Message e, String[] parameters, MessageChannel channel) {
        this.event = e;
        this.params = parameters;
        return this;
    }

    @Override
    public void execute() {
        MessageChannel channel = event.getChannel();

        // execute the function.
        // switch case: setup, add, repost, remove, deletefull.
        // setup: save parameters[1] into UMessage string, parse parameters[2] for what
        // channel its referring to. send UMessage to channel. save message id as Prev.

        switch (params[0]) {
            case "setup":
                rrSetup();
                break;
            case "add":
                help(channel);
                break;
            case "repost":
                help(channel);
                break;
            case "remove":
                help(channel);
                break;
            case "deletefull":
                help(channel);
                break;
            default:
                help(channel);
                break;
        }

    };

    public int parseChannel(String source) {

        return 0;
    }

    public void rrSetup() {

        parseChannel(params[1]);
        // save user message
        // send message to channel

    };

    public void help(MessageChannel c) {

        c.sendMessage(
                "React roles usage: \n !rr setup <\"Example Message\"> <#channel-link> \n"
                        + "* !rr add <message link> <role to add> <emoji to react> \n"
                        + "Adds a react role to the given list. Sending without a link defaults to the last React Role setup. \n"
                        + "To get the link, right click the message and press Copy Link, then paste it into the command. \n"
                        + "* !rr remove <message link> <role or emoji> \n"
                        + "Removes a react role from the given list. Sending without a link defaults to the last React Role setup. \n"
                        + "* !rr deletefull <message link> \n"
                        + "Deletes the given list and all the react roles on it. CANNOT BE UNDONE. DELETES THE MESSAGE. \n")
                .queue();

    };

}
