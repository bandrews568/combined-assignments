package com.cooksys.ftd.assignments.concurrency;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;


public class Client implements Runnable {
	
	ClientConfig config;

    public Client(ClientConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        
    	int port = config.getPort();
    	String host = config.getHost();
    	
    	int numberOfInstanceClientsToSpawn = config.getInstances().size();
    	
    	// TODO test this and make sure it spawns the instances
    	
    	for (int i = 0; i < numberOfInstanceClientsToSpawn; i++) {
    		ClientInstanceConfig clientInstanceConfigFile = config.getInstances().get(i);
    		
    		ClientInstance clientInstance = new ClientInstance(clientInstanceConfigFile, port, host);
    		
    		Thread clientThread = new Thread(clientInstance);
    		clientThread.start();   		
    	}
    	

    }
}
