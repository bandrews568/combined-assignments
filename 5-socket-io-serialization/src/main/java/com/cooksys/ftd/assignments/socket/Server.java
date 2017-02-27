package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Utils {

    /**
     * Reads a {@link Student} object from the given file path
     *
     * @param studentFilePath the file path from which to read the student config file
     * @param jaxb the JAXB context to use during unmarshalling
     * @return a {@link Student} object unmarshalled from the given file path
     */
    public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {

        Student student = null;
        try {
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            File filePath = new File(studentFilePath);
            student = (Student) unmarshaller.unmarshal(filePath);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return student;
    }

    /**
     * The server should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" property of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object to create a server socket that
     * listens for connections on the configured port.
     *
     * Upon receiving a connection, the server should unmarshal a {@link Student} object from a file location
     * specified by the config's "studentFilePath" property. It should then re-marshal the object to xml over the
     * socket's output stream, sending the object to the client.
     *
     * Following this transaction, the server may shut down or listen for more connections.
     */
    public static void main(String[] args) {
        JAXBContext jaxbContext = createJAXBContext();
        Config config = loadConfig(CONFIG_FILE_PATH, jaxbContext);
        int portNumber = config.getLocal().getPort();

        String studentFilePath = config.getStudentFilePath();
        File studentFile = new File(studentFilePath);

        try (FileInputStream fileInputStream = new FileInputStream(studentFile);
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)){
            ServerSocket serverSocket = new ServerSocket(portNumber);

            while (true) {
                Socket socket = serverSocket.accept();
                byte[] byteArray = new byte[(int) studentFile.length()];
                bufferedInputStream.read(byteArray, 0, byteArray.length);

                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(byteArray, 0, byteArray.length);
                outputStream.flush();
                socket.close();
                outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
