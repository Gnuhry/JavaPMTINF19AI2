package org.dhbw.classes;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseTest {

    Date demoDate = new Date(1577833200000L);
    Address demoAddress = new Address("Testallee", "1-9", "68163", "Mannheim", "Deutschland");
    Person demoPerson = new Person("Stoll", "Axel", "axel.stoll@web.de");
    Docent demoDocent = new Docent("Stroetmeister", "Karl", demoDate, demoAddress, "karl.stroetmann@mannheim.dhbw.de", 1337);
    CourseRoom demoRoom = new CourseRoom("Testraum", Campus.Neuostheim, "C", "1", 40, true, true, false);
    Course demoCourse = new Course("Testkurs", StudyCourse.Info, demoDocent, demoDate, demoRoom);
    Company demoCompany = new Company("BRD GmbH", demoAddress, demoPerson);
    DualStudent demoStudent = new DualStudent(420420, 696969, "Schmommartz", "Robby", demoDate, demoAddress, "robbybubbles@gmail.com", demoCourse, 3, demoCompany);

    DualStudent newStudent = new DualStudent(420420, 696969, "Martzschomm", "Byrob", demoDate, demoAddress, "bobbyrubbles@gmail.com", demoCourse, 100, demoCompany);
    Docent newDocent = new Docent("Kruse", "Ecki", demoDate, demoAddress, "mail@eckhardkruse.net", 1337);
    Course newCourse = new Course("Kurs für Ehrenmänner", StudyCourse.Info, demoDocent, demoDate, demoRoom);
    Person newPerson = new Person("Stoll", "Axel", "axel.stoll@government.ru");
    Address newAddress = new Address("Die neue Allee", "1-9", "53121", "Bonn", "Deutschland");
    Company newCompany = new Company("Neue Weltwordnung e.V.", newAddress, newPerson);
    CourseRoom newRoom = new CourseRoom("Testraum", Campus.Neuostheim, "C", "1", 60, false, false, true);


    @BeforeAll
    public static void setUp() throws SQLException, ClassNotFoundException {
        Database.initialize();
    }

    @AfterAll
    public static void tearDown() {
        Database.closeConnection();
    }

    @Test @Order(0)
    public void addressTests() {
        Database.addAddress(demoAddress);
        assertTrue(Database.getAddresses().contains(demoAddress));

        Database.updateAddress(newAddress, demoAddress);
        assertTrue(Database.getAddresses().contains(newAddress));

        Database.deleteAddress(newAddress);
        assertFalse(Database.getAddresses().contains(newAddress));
    }

    @Test @Order(1)
    public void personTests() {
        Database.addPerson(demoPerson);
        assertTrue(Database.getPersons().contains(demoPerson));

        Database.updatePerson(newPerson, demoPerson);
        assertTrue(Database.getPersons().contains(newPerson));

        Database.deletePerson(newPerson);
        assertFalse(Database.getPersons().contains(newPerson));
    }

    @Test @Order(2)
    public void docentTests() {
        Database.addDocent(demoDocent);
        assertTrue(Database.getDocents().contains(demoDocent));
        assertTrue(Database.getDocentIDs().contains(demoDocent.getDocentNumber()));

        Database.updateDocent(newDocent, demoDocent);
        assertTrue(Database.getDocents().contains(newDocent));

        Database.deleteDocent(newDocent);
        assertFalse(Database.getDocents().contains(newDocent));
        assertFalse(Database.getDocentIDs().contains(newDocent.getDocentNumber()));
    }

    @Test @Order(3)
    public void roomTests() {
        Database.addRoom(demoRoom);
        assertTrue(Database.getRooms().contains(demoRoom));

        Database.updateRoom(newRoom, demoRoom);
        assertTrue(Database.getRooms().contains(newRoom));

        Database.deleteRoom(newRoom);
        assertFalse(Database.getRooms().contains(newRoom));
    }

    @Test @Order(4)
    public void courseTests() {
        Database.addCourse(demoCourse);
        assertTrue(Database.getCourses().contains(demoCourse));

        Database.updateCourse(newCourse, demoCourse);
        assertTrue(Database.getCourses().contains(newCourse));

        Database.deleteCourse(newCourse);
        assertFalse(Database.getCourses().contains(newCourse));

        Database.deleteDocent(demoDocent);
        Database.deleteRoom(demoRoom);
    }

    @Test @Order(5)
    public void companyTests() {
        Database.addCompany(demoCompany);
        assertTrue(Database.getCompanies().contains(demoCompany));

        Database.updateCompany(newCompany, demoCompany);
        assertTrue(Database.getCompanies().contains(newCompany));

        Database.deleteCompany(newCompany);
        assertFalse(Database.getCompanies().contains(newCompany));
    }

    @Test @Order(6)
    public void studentTests() {
        Database.addStudent(demoStudent);
        assertTrue(Database.getStudents().contains(demoStudent));
        assertTrue(Database.getStudentIDs().contains(demoStudent.getStudentNumber()));
        assertTrue(Database.getMatriculationNumbers().contains(demoStudent.getMatriculationNumber()));

        Database.updateStudent(newStudent, demoStudent);
        assertTrue(Database.getStudents().contains(newStudent));

        Database.deleteStudent(newStudent);
        assertFalse(Database.getStudents().contains(newStudent));
        assertFalse(Database.getStudentIDs().contains(newStudent.getStudentNumber()));
        assertFalse(Database.getMatriculationNumbers().contains(newStudent.getMatriculationNumber()));

        Database.deleteCourse(demoCourse);
        Database.deleteCompany(demoCompany);
        Database.deleteDocent(demoDocent);
        Database.deleteRoom(demoRoom);
    }
}