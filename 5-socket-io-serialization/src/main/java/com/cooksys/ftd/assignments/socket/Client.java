package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.Socket;

import static com.cooksys.ftd.assignments.socket.Utils.CONFIG_FILE_PATH;

public class Client {

    /**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
        JAXBContext jaxbContext = Utils.createJAXBContext();
        Config config = Utils.loadConfig(CONFIG_FILE_PATH, jaxbContext);
        int portNumber = config.getRemote().getPort();
        String host = config.getRemote().getHost();

        try {
            Socket socket = new Socket(host, portNumber);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line+"\n");
            }

            JAXBContext context = JAXBContext.newInstance(Student.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            StringReader stringReader = new StringReader(stringBuffer.toString());
            Student student = (Student) unmarshaller.unmarshal(stringReader);
            System.out.println(student.toString());
            bufferedReader.close();
            stringReader.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
