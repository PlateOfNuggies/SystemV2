package com.example.funcs.Pipeline;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public interface COMMAND {

    public void execute();

    public String getName();

    public COMMAND setContext(Message e, String[] parameters, MessageChannel channel);
}
