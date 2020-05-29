package org.dhbw.classes;

import java.sql.*;

public class Database {

    /**
     * Create a connection to the database
     *
     * @return the open connection
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://85.214.247.101:3306/dhbw", "mlg_dhbw", "Reisebus1!");
    }

    /**
     * To send a select command to database
     *
     * @param command the sql select command to get the values
     * @return all the values from the commands. [row][column]. if no value return null
     */
    public static Object[][] getFromDatabase(String command) {
        try {
            Connection con = getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            int count = resultSet.getMetaData().getColumnCount();
            int rowCount = 0;
            if (resultSet.last()) {
                rowCount = resultSet.getRow();
                resultSet.beforeFirst();
            }
            Object[][] o = new Object[rowCount][count];

            while (resultSet.next())
                for (int i = 1; i <= count; i++)
                    o[resultSet.getRow() - 1][i - 1] = resultSet.getObject(i);
            resultSet.close();
            statement.close();
            con.close();
            return o;
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * To send a insert command to database
     *
     * @param command the sql insert command to set the values with ? in the value brackets for example: INSERT INTO table (x,y) VALUES (?, ?)
     * @param objects the values
     * @param set     the sql types of the values
     */
    public static void setToDatabase(String command, Object[] objects, int[] set) {
        if (objects == null) objects = new Object[0];
        if (set == null) set = new int[0];
        try {
            Connection con = getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(command);
            for (int f = 0; f < objects.length && f < set.length; f++)
                preparedStatement.setObject(f + 1, objects[f], set[f]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    /**
     * To send all students to the database
     *
     * @param students the students
     */
    public static void setStudentsToDatabase(DualStudent[] students) {
        if (students == null) return;
        for (DualStudent student : students) {
            Object[][] help = getFromDatabase("SELECT * FROM student WHERE student.student_id = '" + student.getStudentNumber() + "'");
            if (help != null && help.length > 0) return;
            setToDatabase("INSERT INTO STUDENT (student_id, matriculation_number, person_id, java_knolage, course_id, company_id) VALUES (?, ?, ?, ?, ?, ?)", new Object[]{student.getStudentNumber(), student.getMatriculationNumber(), setPersonToDatabase(student), student.getJavaKnowledge(), setCourseToDatabase(student.getCourse()), setCompanyToDatabase(student.getCompany())}, new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        }
    }

    /**
     * To send all docents to the database
     *
     * @param docents the docents
     */
    public static void setDocentsToDatabase(Docent[] docents) {
        if (docents == null) return;
        for (Docent docent : docents) {
            Object[][] help = getFromDatabase("SELECT * FROM docent WHERE docent.docent_id = '" + docent.getDocentNumber() + "'");
            if (help != null && help.length > 0) return;
            setToDatabase("INSERT INTO docent (docent_id, perosn_id) VALUES (?, ?)", new Object[]{docent.getDocentNumber(), setPersonToDatabase(docent)}, new int[]{Types.INTEGER, Types.INTEGER});
        }
    }

    /**
     * To send all courses to the database
     *
     * @param courses the courses
     */
    public static void setCoursesToDatabase(Course[] courses) {
        if (courses == null) return;
        for (Course course : courses)
            setCourseToDatabase(course);
    }

    /**
     * To get all students from the database
     *
     * @return the students
     */
    public static DualStudent[] loadAllStudents() {
        //        Object[][] help = getFromDatabank("SELECT * FROM student LEFT JOIN person ON student.person_id = person.person_id LEFT JOIN adress ON person.adress_id=adress.adress_id LEFT JOIN course ON student.course_id = course.course_id LEFT JOIN company ON person.company_id = company.company_id LEFT JOIN docent ON course.study_director_id");
        //            erg[f] = new DualStudent((int) help[f][1], (int) help[f][0], (String) help[f][8], (String) help[f][7], new Date(((Timestamp) help[f][9]).getTime()), new Address((String) help[f][13], (String) help[f][14], (String) help[f][15], (String) help[f][16], (String) help[f][17]), (String) help[f][10], new Course((String) help[f][20], null, null, null, new Date(((Timestamp) help[f][21]).getTime()), null), (int) help[f][3], new Company((String) help[f][26], null, null));
        //student id - marticelnr - personid - java - courseid - companyid - personid - firstname - secondname - birthday - email *-* adressid - adressid - street - number - postralcode - city - country - courseid - room - name *-* registrdate - coursetyp - studydirector - represanticeStudentID - Companyid - name - adressid - contactpersonid - docent_id - personid *-*
        Object[][] help = getFromDatabase("SELECT * FROM student LEFT JOIN person ON student.person_id = person.person_id");
        if (help == null) return null;
        DualStudent[] erg = new DualStudent[help.length];
        for (int f = 0; f < erg.length; f++)
            erg[f] = new DualStudent((int) help[f][1], (int) help[f][0], (String) help[f][8], (String) help[f][7], new Date(((Timestamp) help[f][9]).getTime()), getAddressByID((int) help[f][11]), (String) help[f][10], getCourseByID((int) help[f][4]), (int) help[f][3], getCompanyByID((int) help[f][5]));
        return erg;
    }

    /**
     * To get all docents from the database
     *
     * @return the docents
     */
    public static Docent[] loadAllDocents() {
        Object[][] help = getFromDatabase("SELECT * FROM docent LEFT JOIN person ON docent.perosn_id = person.person_id");
        if (help == null) return null;
        Docent[] erg = new Docent[help.length];
        for (int f = 0; f < erg.length; f++)
            erg[f] = new Docent((String) help[f][4], (String) help[f][3], new java.util.Date(((Timestamp) help[f][5]).getTime()), getAddressByID((int) help[f][7]), (String) help[f][6], (String) help[f][0]);
        return erg;
    }

    /**
     * To get all courses from the database
     *
     * @return the courses
     */
    public static Course[] loadAllCourses(Docent[] docents) {
        Object[][] help = getFromDatabase("SELECT * FROM course");
        if (help == null) return null;
        Course[] erg = new Course[help.length];
        Docent director;
        for (int f = 0; f < erg.length; f++) {
            director = null;
            for (int g = 0; g < docents.length && director == null; g++)
                if (docents[g].getDocentNumber() == help[f][5])
                    director = docents[g];
            erg[f] = new Course((String) help[f][2], StudyCourse.AInformatik.getDeclaringClass().getEnumConstants()[(int) help[f][4]], director, (int) help[f][6], new java.util.Date(((Timestamp) help[f][3]).getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[(int) help[f][1]]);
        }
        return erg;
    }

    //----------------------private-------------------------------------------

    private static int getRoomID(CourseRoom c) {
        CourseRoom[] help = c.getDeclaringClass().getEnumConstants();
        for (int f = 0; f < help.length; f++)
            if (help[f] == c)
                return f;
        return Integer.MIN_VALUE;
    }

    private static int getCourseTypeID(StudyCourse c) {
        StudyCourse[] help = c.getDeclaringClass().getEnumConstants();
        for (int f = 0; f < help.length; f++)
            if (help[f] == c)
                return f;
        return Integer.MIN_VALUE;
    }

    private static int setCompanyToDatabase(Company company) {
        int address_id = setAddressToDatabase(company.getAddress());
        int contact_id = setPersonToDatabase(company.getContactPerson());
        Object[][] help = getFromDatabase("SELECT * FROM company WHERE name = '" + company.getName() + "' AND adress_id = '" + address_id + "' AND contact_person_id = '" + contact_id + "'");
        if (help != null && help.length > 0) return Integer.MIN_VALUE;
        setToDatabase("INSERT INTO company (name, adress_id, contact_person_id) VALUES (?, ?, ?)", new Object[]{company.getName(), address_id, contact_id}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        Object[][] erg = getFromDatabase("SELECT * FROM company WHERE company.name = '" + company.getName() + "'");
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static int setPersonToDatabase(Person person) {
        int address_id = setAddressToDatabase(person.getAddress());
        Object[][] help = getFromDatabase("SELECT * FROM person WHERE person.last_name = '" + person.getName() + "' AND person.firts_name = '" + person.getForeName() + "' AND person.birthdate = '" + (new Timestamp(person.getBirthday().getTime())) + "' AND person.email = '" + person.getEmail() + "' AND person.adress_id = '" + address_id + "'");
        if (help != null && help.length > 0) return (int) help[0][0];
        setToDatabase("INSERT INTO person (first_name, last_name, birthdate, email, adress_id) VALUES (?, ?, ?, ?, ?)", new Object[]{person.getForeName(), person.getName(), person.getBirthday(), person.getEmail(), address_id}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER});
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        Object[][] erg = getFromDatabase("SELECT * FROM person WHERE person.last_name = '" + person.getName() + "'");
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static int setAddressToDatabase(Address address) {
        Object[][] help = getFromDatabase("SELECT * FROM adress WHERE street = '" + address.getStreet() + "' AND number = '" + address.getNumber() + "' AND postal_code = '" + address.getPostcode() + "' AND city = '" + address.getCity() + "' AND country = '" + address.getCountry() + "'");
        if (help != null && help.length > 0) return (int) help[0][0];
        setToDatabase("INSERT INTO adress (street, number, postal_code, city, country) VALUES (?, ?, ?, ?, ?)", new Object[]{address.getStreet(), address.getNumber(), address.getPostcode(), address.getCity(), address.getCity()}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        Object[][] erg = getFromDatabase("SELECT * FROM adress WHERE adress.street = '" + address.getStreet() + "'");
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static int setCourseToDatabase(Course course) {
        int roomID = getRoomID(course.getRoom());
        int courseTypeID = getCourseTypeID(course.getStudyCourse());
        Timestamp registry = new Timestamp(course.getRegistrationDate().getTime());
        Object[][] help = getFromDatabase("SELECT * FROM course WHERE room = '" + roomID + "' AND name = '" + course.getName() + "' AND registry_date = '" + registry + "' AND course_type = '" + courseTypeID + "' AND study_director_id = '" + course.getStudyDirector().getDocentNumber() + "' AND representative_student_id = '" + course.getCourseSpeaker() + "'");
        if (help != null && help.length > 0) return (int) help[0][0];
        setToDatabase("INSERT INTO course (room, name, registry_date, course_type, study_director_id, representative_student_id) VALUES (?, ?, ?, ?, ?, ?)", new Object[]{roomID, course.getName(), registry, courseTypeID, course.getStudyDirector().getDocentNumber(), course.getCourseSpeaker()}, new int[]{Types.INTEGER, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        Object[][] erg = getFromDatabase("SELECT * FROM adress WHERE course.name = '" + course.getName() + "'");
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static Company getCompanyByID(int id) {
        Object[][] company = getFromDatabase("SELECT * FROM company WHERE company_id = '" + id + "' LEFT JOIN person ON company.contact_person_id = person.person_id");
        if (company == null) return null;
        return new Company((String) company[0][1], getAddressByID((int) company[0][2]), new Person((String) company[0][6], (String) company[0][5], new Date(((Timestamp) company[0][7]).getTime()), getAddressByID((int) company[0][9]), (String) company[0][8]));
    }

    private static Docent getDocentByID(int id) {
        Object[][] docent = getFromDatabase("SELECT * FROM docent WHERE docent_id = '" + id + "' LEFT JOIN person ON docent.person_id = person.person_id");
        if (docent == null) return null;
        return new Docent((String) docent[0][4], (String) docent[0][3], new Date(((Timestamp) docent[0][5]).getTime()), getAddressByID((int) docent[0][7]), (String) docent[0][6], (String) docent[0][0]);
    }

    private static Address getAddressByID(int id) {
        Object[][] address = getFromDatabase("SELECT * FROM adress WHERE address_id = '" + id + "'");
        if (address == null) return null;
        return new Address((String) address[0][1], (String) address[0][2], (String) address[0][3], (String) address[0][4], (String) address[0][5]);
    }

    private static Course getCourseByID(int id) {
        Object[][] courses = getFromDatabase("SELECT * FROM course WHERE course_id = '" + id + "' LEFT JOIN student on course.representative_student_id = student.student_id LEFT JOIN person ON student.person_id = person.person_id");
        if (courses == null) return null;
        return new Course((String) courses[0][2], StudyCourse.BWL.getDeclaringClass().getEnumConstants()[(int) courses[0][4]], getDocentByID((int) courses[0][5]), (int) courses[0][7], new Date(((Timestamp) courses[0][3]).getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[(int) courses[0][1]]);
    }
}
