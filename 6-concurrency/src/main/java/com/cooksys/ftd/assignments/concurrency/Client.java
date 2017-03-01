package com.cooksys.ftd.assignments.concurrency;


import java.util.List;
import java.util.Map;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;


public class Client implements Runnable {
	
	ClientConfig config;
	Map<String, ClientInstance> clientInstanceMap;

	private int currentInstanceNumber = 1;

	// Used for SEQUENTIAL spawn strategy, only one instance is allowed to be spawned at a time
	// Fix this where ClientInstance will send back a notification back to the client so we can change this
	private static boolean currentlyHasActiveClientSpawned = false;

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
    	switch (spawnStrategy) {
			case NONE:
				String message = "Spawn strategy was NONE. Not spawning instance";
				System.out.println(message);
				break;
			case PARALLEL:
				for (int i = 0; i < numberOfInstanceClientsToSpawn; i++) {
		    		ClientInstanceConfig clientInstanceConfigFile = config.getInstances().get(i);
		    		
		    		ClientInstance clientInstance = new ClientInstance(clientInstanceConfigFile, host, port);

		    		String clientName = generateInstanceName();
		    		clientInstance.setClientName(clientName);

					mapInstanceNameToInstance(clientName, clientInstance);

		    		Thread clientThread = new Thread(clientInstance);
		    		clientThread.start();   		
		    	}
				break;
			case SEQUENTIAL:
				if (!currentlyHasActiveClientSpawned) {
					// TODO spawn a new Thread here
				}
				break;   	
    	}
    }

    private String generateInstanceName() {
		String instanceName = "ClientInstance-" + currentInstanceNumber;
		System.out.println("Generated: " + instanceName); // Remove after testing!

		currentInstanceNumber++;

		return instanceName;
	}

	private void mapInstanceNameToInstance(String clientName, ClientInstance clientInstance) {
		clientInstanceMap.put(clientName, clientInstance);
	}
}
