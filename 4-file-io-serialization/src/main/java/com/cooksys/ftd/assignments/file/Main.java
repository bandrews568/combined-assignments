package com.cooksys.ftd.assignments.file;

import com.cooksys.ftd.assignments.file.model.Contact;
import com.cooksys.ftd.assignments.file.model.Instructor;
import com.cooksys.ftd.assignments.file.model.Session;
import com.cooksys.ftd.assignments.file.model.Student;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
	
	private static final String STUDENT_DIRECTORY_PATH = 
			"/Users/brandon/Desktop/combined-assignments/4-file-io-serialization/input/memphis/08-08-2016/students";
	private static final String INSTRUCTOR_FILE_PATH = 
			"/Users/brandon/Desktop/combined-assignments/4-file-io-serialization/input/memphis/08-08-2016/instructor.xml";
	private static final String ROOT_DIRECTORY =
            "/Users/brandon/Desktop/combined-assignments/4-file-io-serialization/input";
	private static final String OUTPUT_DIRECTORY =
            "/Users/brandon/Desktop/combined-assignments/4-file-io-serialization/output/session.xml";

	private static JAXBContext studentContext;
	private static JAXBContext instructorContext;
	private static JAXBContext sessionContext;
		
	private static File[] walkDirectory(String folderPath) {
		File directory = new File(folderPath);
		File[] folderContents = directory.listFiles();
		return folderContents;
	}
	
		
    /**
     * Creates a {@link Student} object using the given studentContactFile.
     * The studentContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param studentContactFile the XML file to use
     * @param jaxbContext the JAXB context to use
     * @return a {@link Student} object built using the {@link Contact} data in the given file
     */
    public static Student readStudent(File studentContactFile, JAXBContext jaxbContext) {
        
    	Contact studentContact = null;
    	Student student = null;
        try {        	
        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        	studentContact = (Contact) unmarshaller.unmarshal(studentContactFile);
        	student = new Student();
        	student.setContact(studentContact);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
        return student;
        
    }

    /**
     * Creates a list of {@link Student} objects using the given directory of student contact files.
     *
     * @param studentDirectory the directory of student contact files to use
     * @param jaxb the JAXB context to use
     * @return a list of {@link Student} objects built using the contact files in the given directory
     */
    public static List<Student> readStudents(File[] studentDirectory, JAXBContext jaxb) {

        List<Student> studentList = new ArrayList<>();
    	File[] allStudentFiles = walkDirectory(STUDENT_DIRECTORY_PATH);
        
        for (File file : allStudentFiles) {
        	JAXBContext context;
			try {
				context = JAXBContext.newInstance(Contact.class);
				Student student = readStudent(file, context);
				studentList.add(student);
			} catch (JAXBException e) {
				e.printStackTrace();
			}		
        }
        return studentList;   	
    }

    /**
     * Creates an {@link Instructor} object using the given instructorContactFile.
     * The instructorContactFile should be an XML file containing the marshaled form of a
     * {@link Contact} object.
     *
     * @param instructorContactFile the XML file to use
     * @param jaxbContext the JAXB context to use
     * @return an {@link Instructor} object built using the {@link Contact} data in the given file
     */
    public static Instructor readInstructor(File instructorContactFile, JAXBContext jaxbContext) {
    	
    	Contact instructorContact = null;
    	Instructor instructor = null;
        try {        	
        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        	instructorContact = (Contact) unmarshaller.unmarshal(instructorContactFile);
        	instructor = new Instructor();
        	instructor.setContact(instructorContact);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
        return instructor;
    }

    /**
     * Creates a {@link Session} object using the given rootDirectory. A {@link Session}
     * root directory is named after the location of the {@link Session}, and contains a directory named
     * after the start date of the {@link Session}. The start date directory in turn contains a directory named
     * `students`, which contains contact files for the students in the session. The start date directory
     * also contains an instructor contact file named `instructor.xml`.
     *
     * @param rootDirectory the root directory of the session data, named after the session location
     * @param jaxb the JAXB context to use
     * @return a {@link Session} object built from the data in the given directory
     */
<<<<<<< Updated upstream
    public static Session readSession(File rootDirectory, JAXBContext jaxb) {

        File[] studentFileList = walkDirectory(STUDENT_DIRECTORY_PATH);
        File instructorFilePath = new File(INSTRUCTOR_FILE_PATH);
        List<Student> studentList = readStudents(studentFileList, studentContext);
        Instructor instructor = readInstructor(instructorFilePath, instructorContext);

        Session session = new Session();
        session.setLocation("Memphis");
        session.setStartDate("08-08-2016");
        session.setStudents(studentList);
        session.setInstructor(instructor);
=======
    public static Session readSession(File rootDirectory, JAXBContext jaxbContext) {
    	
    	Session session = null;

        try {        	
        	Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        	session = (Session) unmarshaller.unmarshal(rootDirectory);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
>>>>>>> Stashed changes
        return session;
    }

    /**
     * Writes a given session to a given XML file
     *
     * @param session the session to write to the given file
     * @param sessionFile the file to which the session is to be written
     * @param jaxb the JAXB context to use
     */
<<<<<<< Updated upstream
    public static void writeSession(Session session, File sessionFile, JAXBContext jaxb) {

    	try {
			Marshaller jaxbMarshaller = jaxb.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(session, sessionFile);
		}  catch (JAXBException e) {
    		e.printStackTrace();
		}
=======
    public static void writeSession(Session session, File sessionFile, JAXBContext jaxbContext) {
        
    	try {
			Marshaller marshaller = jaxbContext.createMarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
>>>>>>> Stashed changes
    }

    /**
     * Main Method Execution Steps:
     * 1. Configure JAXB for the classes in the com.cooksys.serialization.assignment.model package
     * 2. Read a session object from the <project-root>/input/memphis/ directory using the methods defined above
     * 3. Write the session object to the <project-root>/output/session.xml file.
     *
     * JAXB Annotations and Configuration:
     * You will have to add JAXB annotations to the classes in the com.cooksys.serialization.assignment.model package
     *
     * Check the XML files in the <project-root>/input/ directory to determine how to configure the {@link Contact}
     *  JAXB annotations
     *
     * The {@link Session} object should marshal to look like the following:
     *      <session location="..." start-date="...">
     *           <instructor>
     *               <contact>...</contact>
     *           </instructor>
     *           <students>
     *               ...
     *               <student>
     *                   <contact>...</contact>
     *               </student>
     *               ...
     *           </students>
     *      </session>
     */
    public static void main(String[] args) {
<<<<<<< Updated upstream

        try {
            studentContext = JAXBContext.newInstance(Student.class);
            instructorContext = JAXBContext.newInstance(Instructor.class);
            sessionContext = JAXBContext.newInstance(Session.class);
            Session sessionData = readSession(new File(ROOT_DIRECTORY), sessionContext);
            writeSession(sessionData, new File(OUTPUT_DIRECTORY), sessionContext);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
=======
    	
     	
    	
>>>>>>> Stashed changes
    }
}
