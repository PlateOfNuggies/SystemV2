package com.example;

import java.util.HashMap;

import com.example.funcs.*;
import com.example.funcs.Pipeline.*;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;

public class App {

    private HashMap<String, COMMAND> commandsHM;
    private JDA jdaApi;
    private CommandListener listener;
    private CommandHandler controller;

    public void startup() {

        initJDA();
        initCommmands();
        initHandler(commandsHM);
        initListener(jdaApi, controller);
        listCommands();
        greeting();

    }

    public void initJDA() {

        Token Api_Key = new Token();
        jdaApi = JDABuilder.createDefault(Api_Key.token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
    }

    public void initCommmands() {

        commandsHM = new HashMap<>();

        register(new PingPong(jdaApi));
        register(new MarcoPolo(jdaApi));
        register(new ReactRoles(jdaApi));
        register(new Create(jdaApi));
        register(new Add(jdaApi));
        register(new Appraise(jdaApi));

    }

    public void listCommands() {
        for (COMMAND i : commandsHM.values()) {
            System.out.println(i.getName());
        }
    }

    public void initHandler(HashMap<String, COMMAND> list) {
        controller = new CommandHandler(list);
    }

    public void initListener(JDA apiReg, CommandHandler controller) {
        listener = new CommandListener(controller);
        listener.register(apiReg);
    }

    public void register(COMMAND command) {
        commandsHM.put(command.getName(), command);
    }

    public static void greeting() {
        System.out.println(
                "\n---------------------------------\n \nGoooooood Morning! \n \n---------------------------------\n");
    }

}