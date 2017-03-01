package com.cooksys.ftd.assignments.concurrency;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class ClientHandler implements Runnable {

    @Override
    public void run() {
        throw new NotImplementedException();
        // TODO if message from client comes back as 'DONE', close the server 
    }
}
