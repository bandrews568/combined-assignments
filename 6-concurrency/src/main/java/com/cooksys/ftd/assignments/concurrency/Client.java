package com.cooksys.ftd.assignments.concurrency;


import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;


public class Client implements Runnable {
	
	ClientConfig config;
	List<ClientInstance> clientInstanceList;

    public Client(ClientConfig config) {
        this.config = config;
    }

    @Override
    public void run() {
        
    	int port = config.getPort();
    	String host = config.getHost();
    	
    	SpawnStrategy spawnStrategy = config.getSpawnStrategy();
    	
    	int numberOfInstanceClientsToSpawn = config.getInstances().size();
    	
    	// TODO test this and make sure it spawns the instances
    	// TODO generate custom client names: example. "ClientInstance1"
    	switch (spawnStrategy) {
			case NONE:
				break;
			case PARALLEL:
				for (int i = 0; i < numberOfInstanceClientsToSpawn; i++) {
		    		ClientInstanceConfig clientInstanceConfigFile = config.getInstances().get(i);
		    		
		    		ClientInstance clientInstance = new ClientInstance(clientInstanceConfigFile, host, port);
		    		
		    		clientInstanceList.add(clientInstance);
		    		
		    		Thread clientThread = new Thread(clientInstance);
		    		clientThread.start();   		
		    	}
				break;
			case SEQUENTIAL:
				break;   	
    	}
    	
    	
    	

    }
}
