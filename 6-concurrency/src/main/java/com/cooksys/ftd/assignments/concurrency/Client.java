package com.cooksys.ftd.assignments.concurrency;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;


public class Client implements Runnable {
	
	ClientConfig config;
	Map<String, ClientInstance> clientInstanceMap = new HashMap<>();

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
				do {
					if (!currentlyHasActiveClientSpawned) {
						ClientInstanceConfig clientInstanceConfigFile = config.getInstances().get(getCurrentInstanceNumber());
						ClientInstance clientInstance = new ClientInstance(clientInstanceConfigFile, config.getHost(), config.getPort());

						String clientName = generateInstanceName();
						clientInstance.setClientName(clientName);


						mapInstanceNameToInstance(clientName, clientInstance);

						Thread clientThread = new Thread(clientInstance);
						clientThread.start();

						incrementCurrentInstanceNumber();

						clientInstance.setSequentitalSpawnStrategy(true);
						setCurrentlyHasActiveClientSpawned(true);
					}
				} while (currentlyHasActiveClientSpawned);
				break;   	
    	}
    }

    private String generateInstanceName() {
		String instanceName = "ClientInstance-" + currentInstanceNumber;
		System.out.println("Generated: " + instanceName);

		currentInstanceNumber++;

		return instanceName;
	}

	private void mapInstanceNameToInstance(String clientName, ClientInstance clientInstance) {
		clientInstanceMap.put(clientName, clientInstance);
	}

	public ClientConfig getConfig() {
		return config;
	}

	public void setConfig(ClientConfig config) {
		this.config = config;
	}

	public Map<String, ClientInstance> getClientInstanceMap() {
		return clientInstanceMap;
	}

	public void setClientInstanceMap(Map<String, ClientInstance> clientInstanceMap) {
		this.clientInstanceMap = clientInstanceMap;
	}

	public int getCurrentInstanceNumber() {
		return currentInstanceNumber;
	}

	public void incrementCurrentInstanceNumber() {
		this.currentInstanceNumber = currentInstanceNumber++;
	}

	public static boolean isCurrentlyHasActiveClientSpawned() {
		return currentlyHasActiveClientSpawned;
	}

	public static void setCurrentlyHasActiveClientSpawned(boolean currentlyHasActiveClientSpawned) {
		Client.currentlyHasActiveClientSpawned = currentlyHasActiveClientSpawned;
	}
}
