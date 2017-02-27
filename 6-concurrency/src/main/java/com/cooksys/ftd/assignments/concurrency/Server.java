package com.cooksys.ftd.assignments.concurrency;

import java.net.ServerSocket;
import java.net.Socket;

import org.junit.experimental.max.MaxCore;

import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

public class Server implements Runnable {
	
	private ServerConfig config;
	private SpawnStrategy spawnStrategy;

    public Server(ServerConfig config) {
        this.config = config;
    }
    
    public void checkSpawnStrategy(int numberOfClients) {
    	
    	if (numberOfClients < 0 ) {
    		spawnStrategy = SpawnStrategy.PARALLEL;
    	} else if (numberOfClients == 0) {
    		spawnStrategy = SpawnStrategy.NONE;
    	} else {
    		spawnStrategy = SpawnStrategy.SEQUENTIAL;
    	}
    }

    
    @Override
    public void run() {
        int portNumber = config.getPort();
    	int maxClients = config.getMaxClients();
    	       
    	try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
    		
    		checkSpawnStrategy(maxClients);
    		
    		
    		while (true) {
    			Socket socket = serverSocket.accept();
    			socket.close();
    		}

    	} catch (Exception e){
    		e.getMessage();
    		e.printStackTrace();
    	}
    }
}
