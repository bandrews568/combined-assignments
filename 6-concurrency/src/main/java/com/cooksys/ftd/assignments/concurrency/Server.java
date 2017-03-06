package com.cooksys.ftd.assignments.concurrency;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.ServerConfig;
import com.cooksys.ftd.assignments.concurrency.model.config.SpawnStrategy;

public class Server implements Runnable {
	
	private ServerConfig config;
	private SpawnStrategy spawnStrategy;
	private List<ClientInstanceConfig> numberOfInstancesToCreate;


	public Server(ServerConfig config, List<ClientInstanceConfig> numberOfInstancesToCreate) {

		this.config = config;
		this.numberOfInstancesToCreate = numberOfInstancesToCreate;
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

    	for (int i = 0; i < numberOfInstancesToCreate.size(); i++) {
			try (ServerSocket serverSocket = new ServerSocket(portNumber)) {

				checkSpawnStrategy(maxClients);

				while (true) {
					Socket socket = serverSocket.accept();
					System.out.println("Created client: " + socket );

					ClientHandler clientHandler = new ClientHandler(socket);
					Thread clientHandlerThread = new Thread(clientHandler);

					clientHandlerThread.start();
				}

			} catch (Exception e){
				e.getMessage();
				e.printStackTrace();
			}
		}


    }
}
