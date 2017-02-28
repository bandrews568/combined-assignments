package com.cooksys.ftd.assignments.concurrency;

import com.cooksys.ftd.assignments.concurrency.model.config.ClientInstanceConfig;
import com.cooksys.ftd.assignments.concurrency.model.message.Request;
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

    private boolean isDelayBetweenRequests;


    public ClientInstance(ClientInstanceConfig config, String host, int port) {

        this.config = config;
        this.port = port;
        this.host = host;

        delay = config.getDelay();
        requestList = config.getRequests();

        isDelayBetweenRequests = delay >= 0 ? true : false;
    }

    @Override
    public void run() {

        for (int i = 0; i < requestList.size(); i++) {
            // TODO send requests with a delay in between
            try (Socket socket = new Socket(host, port)) {
                long currrentSystemTime = System.currentTimeMillis();

            } catch (IOException e) {
                e.getMessage();
                e.printStackTrace();
            }
        }
    }
}
