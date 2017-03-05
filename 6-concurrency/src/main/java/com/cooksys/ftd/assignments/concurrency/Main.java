package com.cooksys.ftd.assignments.concurrency;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.Config;

import ch.qos.logback.classic.net.SyslogAppender;


public class Main {
	
	private static final Path CONFIG_FILE_PATH = Paths.get("6-concurrency/config/config.xml");

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

       List<ClientInstanceConfig> numberOfClientsToSpawn = configFile.getClient().getInstances();

       boolean serverDisabled = configFile.getServer().isDisabled();
       boolean clientDisabled = configFile.getClient().isDisabled();
       
       // TODO expand on this later
       if (serverDisabled || clientDisabled) {
    	   if (serverDisabled) {
    		   System.out.println("Server is disabled");
    	   }
    	   
    	   if (clientDisabled) {
    		   System.out.println("Client is disabled");
    	   }
    	   
    	   if (clientDisabled && serverDisabled) {
    		   System.exit(0);
    	   }
       }

       Thread serverThread;
              
       if (!serverDisabled) {
		   System.out.println("Starting server");
		   Server server = new Server(configFile.getServer(), numberOfClientsToSpawn);
    	   serverThread = new Thread(server);
    	   serverThread.start();
       }

       try {
		   Thread.sleep(3000);
	   } catch (InterruptedException e) {
		   e.printStackTrace();
	   }

       if (!clientDisabled) {
		   System.out.println("Starting client");
		   Client client = new Client(configFile.getClient());
    	   Thread clientThread = new Thread(client);
    	   clientThread.start();
       }
    }
}
