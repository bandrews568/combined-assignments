package com.cooksys.ftd.assignments.concurrency;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.Socket;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class ClientInstance implements Runnable {
	
    private final ClientInstanceConfig config;
    private final int port;
    private final String host;

    private int delay;
    private List<Request> requestList;

	private String clientName;


    public ClientInstance(ClientInstanceConfig config, String host, int port, String clientName) {

        this.config = config;
        this.port = port;
        this.host = host;
        this.clientName = clientName;

        delay = config.getDelay();
        requestList = config.getRequests();
    }

    public ClientInstance(ClientInstanceConfig config, int port, String host) {
        this.config = config;
        this.port = port;
        this.host = host;
    }

    @Override
    public void run() {

        for (int i = 0; i < requestList.size(); i++) {       	

            try (Socket socket = new Socket(host, port)) {
            	
            	// We have a delay, but it only applies to sending out the second item
            	if (i > 0 && delay > 0) {
            		Thread.sleep(500);
            	}
            	            	
            	Request requestType = requestList.get(i);
            	OutputStream outputStream = socket.getOutputStream();
            	
            	if (requestType.equals(RequestType.TIME)) {
            		long currrentSystemTime = System.currentTimeMillis();
            		outputStream.write((int) currrentSystemTime);
            	} else if (requestType.equals(RequestType.IDENTITY)) {
            		outputStream.write(clientName.getBytes());
            	} else if (requestType.equals(RequestType.DONE)) {
            		String doneMessage = "DONE";
            		outputStream.write(doneMessage.getBytes());
            		socket.close();
            	} else {
            		String errorMessageBadRequest = "Bad request. Request must be: TIME, IDENTITY, DONE\nClosing Connection";
            		outputStream.write(errorMessageBadRequest.getBytes());
            		socket.close();
            	}
                
            } catch (IOException | InterruptedException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
}
