package org.dhbw.classes;

import org.junit.jupiter.api.*;

import java.util.Date;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTest {

    Address demoAddress = new Address("Coblitzallee", "1-9", "68163", "Mannheim", "Deutschland");
    Person demoPerson = new Person("Stoll", "Axel", "axel.stoll@web.de");
    Docent demoDocent = new Docent("Stroetmann", "Karl", new Date(), demoAddress, "karl.stroetmann@mannheim.dhbw.de", 1337);
    CourseRoom demoRoom = new CourseRoom("161C", Campus.Coblitzalle, "C", "1", 40, true, true, false);
    Course demoCourse = new Course("Algorithmen", StudyCourse.AInfo, demoDocent, new Date(), demoRoom);
    Company demoCompany = new Company("BRD GmbH", demoAddress, demoPerson);
    DualStudent demoStudent = new DualStudent(420420, 696969, "Schmommartz", "Robby", new Date(), demoAddress, "robbybubbles@gmail.com", demoCourse, 3,demoCompany );

    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        Database.initialize();
    }

    @AfterAll
    public static void tearDown() {
        Database.closeConnection();
    }


    @Test
    public  void addStudent() {
        Database.addStudent(demoStudent);
    }

    @Test
    public void addDocent() {
        Database.addDocent(demoDocent);
    }

    @Test
    public  void addCourse() {
        Database.addCourse(demoCourse);
    }

    @Test
    public  void addPerson() {
        Database.addPerson(demoPerson);
    }

    @Test
    public  void addCompany() {
        Database.addCompany(demoCompany);
    }

    @Test
    public  void addAddress() {
        Database.addAddress(demoAddress);
    }

    @Test
    public  void addRoom() {
        Database.addRoom(demoRoom);
    }

    @Test
    public  void getCourses() {
        //assertTrue(Database.getCourses().contains(demoCourse));
        assertNotEquals(Database.getCourses(), 0);
    }

    @Test
    public  void getDocents() {
        assertNotEquals(Database.getDocents(), 0);
    }

    @Test
    public  void getAddresses() {
        assertNotEquals(Database.getAddresses(), 0);
    }

    @Test
    public  void getCompanies() {
        assertNotEquals(Database.getCompanies(), 0);
    }

    @Test
    public  void getPersons() {
        assertNotEquals(Database.getPersons(), 0);
    }

    @Test
    public  void getStudents() {
        assertNotEquals(Database.getStudents(), 0);
    }

    @Test
    public  void getRooms() {
        assertNotEquals(Database.getRooms(), 0);
    }

    @Test
    public  void getStudentIDs() {
        assertNotEquals(Database.getStudentIDs().size(), 0);
    }

    @Test
    public  void getMatriculationNumbers() {
    }

    @Test
    public  void getDocentIDs() {
    }

    @Test
    public  void getAllEmailsFromCourse() {
    }


    @Test
    public  void updateStudent() {
    }

    @Test
    public  void updateDocent() {
    }

    @Test
    public  void updateCourse() {
    }

    @Test
    public  void updatePerson() {
    }

    @Test
    public  void updateCompany() {
    }

    @Test
    public  void updateAddress() {
    }

    @Test
    public  void updateRoom() {
    }

    @Test
    public  void deleteStudent() {
        Database.deleteStudent(demoStudent);
    }

    @Test
    public  void deleteDocent() {
        Database.deleteDocent(demoDocent);
    }

    @Test
    public  void deleteCourse() {
        Database.deleteCourse(demoCourse);
    }

    @Test
    public  void deleteCompany() {
        Database.deleteCompany(demoCompany);
    }

    @Test
    public  void deletePerson() {
        Database.deletePerson(demoPerson);
    }

    @Test
    public  void deleteAddress() {
        Database.deleteAddress(demoAddress);
    }

    @Test
    public  void deleteRoom() {
        Database.deleteRoom(demoRoom);
    }
}