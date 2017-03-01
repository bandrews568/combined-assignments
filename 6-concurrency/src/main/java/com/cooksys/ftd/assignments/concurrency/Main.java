package com.cooksys.ftd.assignments.concurrency;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.cooksys.ftd.assignments.concurrency.model.config.Config;

import ch.qos.logback.classic.net.SyslogAppender;


public class Main {
	
	private static final Path CONFIG_FILE_PATH = Paths.get("config/config.xml");

    /**
     * First, load a {@link com.cooksys.ftd.assignments.concurrency.model.config.Config} object from
     * the <project-root>/config/config.xml file.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig} object
     * is not disabled, create a {@link Server} object with the server config and spin off a thread to run it.
     *
     * If the embedded {@link com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig} object
     * is not disabled, create a {@link Client} object with the client config ans spin off a thread to run it.
     */
    public static void main(String[] args) {
       Config configFile = Config.load(CONFIG_FILE_PATH);
       
       boolean isServerDisabled = configFile.getServer().isDisabled();
       boolean isClientDisabled = configFile.getClient().isDisabled();
       
       // TODO expand on this later
       if (isServerDisabled || isClientDisabled) {
    	   if (isServerDisabled) {
    		   System.out.println("Server is disabled");
    	   }
    	   
    	   if (isClientDisabled) {
    		   System.out.println("Client is disabled");
    	   }
    	   
    	   if (isClientDisabled && isServerDisabled) {
    		   System.exit(0);
    	   }
       }

              
       if (!isServerDisabled) {
    	   Server server = new Server(configFile.getServer());
    	   Thread serverThread = new Thread(server);
    	   serverThread.start();
       }
       
       if (!isClientDisabled) {
    	   Client client = new Client(configFile.getClient());
    	   Thread clientThread = new Thread(client);
    	   clientThread.start();
       }
    }
}
