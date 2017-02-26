package com.cooksys.ftd.assignments.socket;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.RemoteConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;

/**
 * Shared static methods to be used by both the {@link Client} and {@link Server} classes.
 */
public class Utils {

    public static final String CONFIG_FILE_PATH = "5-socket-io-serialization/config/config.xml";

    /**
     * @return a {@link JAXBContext} initialized with the classes in the
     * com.cooksys.socket.assignment.model package
     */
    public static JAXBContext createJAXBContext() {

        Class[] classArray = {Config.class, LocalConfig.class, RemoteConfig.class, Student.class};
        JAXBContext jaxbContext = null;

        try {
            jaxbContext = JAXBContext.newInstance(classArray);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return jaxbContext;
    }

    /**
     * Reads a {@link Config} object from the given file path.
     *
     * @param configFilePath the file path to the config.xml file
     * @param jaxb the JAXBContext to use
     * @return a {@link Config} object that was read from the config.xml file
     */
    public static Config loadConfig(String configFilePath, JAXBContext jaxb) {

        Config config = null;
        try {
            Unmarshaller unmarshaller = jaxb.createUnmarshaller();
            config = (Config) unmarshaller.unmarshal(new File(configFilePath));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return config;
    }
}
