package com.cooksys.ftd.assignments.concurrency;

import java.io.OutputStream;
import java.net.Socket;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
import com.cooksys.ftd.assignments.concurrency.model.message.RequestType;

import java.io.IOException;
import java.util.List;

public class ClientInstance implements Runnable {
	
    private final ClientInstanceConfig config;
    private final int port;
    private final String host;
    private String clientName;

    private int delay;
    private List<Request> requestList;

    public ClientInstance(ClientInstanceConfig config, String host, int port) {

        this.config = config;
        this.port = port;
        this.host = host;

        delay = config.getDelay();
        requestList = config.getRequests();
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
            		long currentSystemTime = System.currentTimeMillis();
            		outputStream.write((int) currentSystemTime);
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

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
