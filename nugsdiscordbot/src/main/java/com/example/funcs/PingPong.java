package com.example.funcs;

import com.example.funcs.Pipeline.COMMAND;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class PingPong implements COMMAND {
    JDA api;
    String name = "ping";
    MessageChannel channel;
    Message event;

    public PingPong(JDA api) {
        this.api = api;
    }

    public String getName() {
        return name;
    }

    @Override
    public COMMAND setContext(Message e, String[] parameters, MessageChannel channel) {
        this.event = e;
        this.channel = channel;
        return this;
    }

    @Override
    public void execute() {

        if (channel == null) {
            channel = event.getChannel();
        }

        channel.sendMessage("Pong!").queue();
    }

}
