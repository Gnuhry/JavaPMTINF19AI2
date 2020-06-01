package org.dhbw.classes;

import java.sql.*;

public class Database {

    //----------------------------------------General----------------------------


    private static Connection connection;


    /**
     * Create a connection to the database
     *
     * @return the open connection
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://85.214.247.101:3306/dhbw", "mlg_dhbw", "Reisebus1!");
        }
        return connection;

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
            return o;
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    /**
     * To send a insert command to database
     *
     * @param command the sql insert, update, delete command to set the values with ? in the value brackets for example: INSERT INTO table (x,y) VALUES (?, ?)
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
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    //--------------------------------SetObjectToDatabase-----------------------

    /**
     * To send all students to the database
     *
     * @param students the students
     */
    public static void setStudentsToDatabase(DualStudent[] students) {
        if (students == null) return;
        for (DualStudent student : students) {
            Object[][] help = getFromDatabase("SELECT * FROM student WHERE student.student_id = " + student.getStudentNumber());
            if (help != null && help.length > 0) return;
            setToDatabase("INSERT INTO STUDENT (student_id, matriculation_number, person_id, java_knowledge, course_id, company_id) VALUES (?, ?, ?, ?, ?, ?)", new Object[]{student.getStudentNumber(), student.getMatriculationNumber(), setPersonToDatabase(student), student.getJavaKnowledge(), setCourseToDatabase(student.getCourse()), setCompanyToDatabase(student.getCompany())}, new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER});
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
            Object[][] help = getFromDatabase("SELECT * FROM docent WHERE docent.docent_id = " + docent.getDocentNumber());
            if (help != null && help.length > 0) return;
            setToDatabase("INSERT INTO docent (docent_id, person_id) VALUES (?, ?)", new Object[]{docent.getDocentNumber(), setPersonToDatabase(docent)}, new int[]{Types.INTEGER, Types.INTEGER});
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

    //----------------------------------LoadObjectFromDatabase---------------

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
        Object[][] help = getFromDatabase("SELECT * FROM docent LEFT JOIN person ON docent.person_id = person.person_id");
        if (help == null) return null;
        Docent[] erg = new Docent[help.length];
        for (int f = 0; f < erg.length; f++)
            erg[f] = new Docent((String) help[f][4], (String) help[f][3], new java.util.Date(((Timestamp) help[f][5]).getTime()), getAddressByID((int) help[f][7]), (String) help[f][6], (int) help[f][0]);
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
                if (docents[g].getDocentNumber() == (int) help[f][5])
                    director = docents[g];
            erg[f] = new Course((String) help[f][2], StudyCourse.AInformatik.getDeclaringClass().getEnumConstants()[(int) help[f][4]], director, (int) help[f][6], new java.util.Date(((Timestamp) help[f][3]).getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[(int) help[f][1]]);
        }
        return erg;
    }

    //-----------------------------UpdateObjectInDatabase-----------------

    /**
     * To update the values of the student in the database
     *
     * @param student    new student values
     * @param student_id student identification
     */
    public static void updateStudent(DualStudent student, int student_id) {
        setToDatabase("UPDATE student SET java_knowledge = ?, person_id = ?, course_id = ?, company_id = ? WHERE student.student_id = " + student_id, new Object[]{student.getJavaKnowledge(), updatePerson(student), updateCourse(student.getCourse()), updateCompany(student.getCompany())}, new int[]{Types.INTEGER, Types.INTEGER, Types.INTEGER, Types.INTEGER});
    }

    /**
     * To update the values of the docent in the database
     *
     * @param docent    new docent values
     * @param docent_id docent identification
     */
    public static void updateDocent(Docent docent, int docent_id) {
        setToDatabase("UPDATE docent SET person_id = ? WHERE docent_id = " + docent_id, new Object[]{updatePerson(docent)}, new int[]{Types.INTEGER});
    }

    /**
     * To update the values of the course in the database
     *
     * @param course new course values
     * @return course identification
     */
    public static int updateCourse(Course course) {
        setToDatabase("UPDATE course SET room = ?, name = ?, registry_date = ?, course_type = ?, study_director_id = ?, representative_student_id = ? WHERE course.course_id = " + getCourseID(course), new Object[]{getRoomID(course.getRoom()), course.getName(), (new Timestamp(course.getRegistrationDate().getTime())), getCourseTypeID(course.getStudyCourse()), course.getStudyDirector().getDocentNumber(), course.getCourseSpeakerID()}, new int[]{Types.INTEGER, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER, Types.INTEGER});
        return getCourseID(course);
    }

    //---------------------------DeleteObjectInDatabase---------------------

    /**
     * To delete a student in the database
     *
     * @param student   studetn to delete
     * @param studentID student identification to delete
     */
    public static void deleteStudent(DualStudent student, int studentID) {
        setToDatabase("DELETE FROM student WHERE student.student_id = " + studentID, null, null);

        int person_id = getPersonID(student);
        Object[][] help = getFromDatabase("SELECT * FROM student WHERE student.person_id = " + person_id + " UNION SELECT * FROM docent WHERE docent.person_id " + person_id);
        if (help != null && help.length == 1)
            deletePerson(student);

        deleteCourse(student.getCourse());

        deleteCompany(student.getCompany());
    }

    /**
     * To delete a docent in the database
     *
     * @param docent   docent to delete
     * @param docentID docent identification to delete
     */
    public static void deleteDocent(Docent docent, int docentID) {
        setToDatabase("DELETE FROM docent WHERE docent.docent_id = " + docentID, null, null);

        int person_id = getPersonID(docent);
        Object[][] help = getFromDatabase("SELECT * FROM student WHERE student.person_id = " + person_id + " UNION SELECT * FROM docent WHERE docent.person_id " + person_id);
        if (help != null && help.length == 1)
            deletePerson(docent);
    }

    /**
     * To delete a course in the database
     *
     * @param course course to delete
     */
    public static void deleteCourse(Course course) {
        setToDatabase("DELETE FROM course WHERE course.course_id = " + getCourseID(course), null, null);
    }

    //------------------------------GetObjectID-----------------------------------------------

    /**
     * Get the ID of the course
     *
     * @param course the course you need the id
     * @return id or Integer.MIN_VALUE if not found
     */
    public static int getCourseID(Course course) {
        Object[][] erg = getFromDatabase("SELECT * FROM course WHERE room = " + getRoomID(course.getRoom()) + " AND name = " + course.getName() + " AND registry_date = " + (new Timestamp(course.getRegistrationDate().getTime())) + " AND course_type = " + getCourseTypeID(course.getStudyCourse()) + " AND study_director_id = " + course.getStudyDirector().getDocentNumber() + " AND representative_student_id = " + course.getCourseSpeakerID());
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    /**
     * Get the ID of the docent
     *
     * @param docent the docent you need the id
     * @return id or Integer.MIN_VALUE if not found
     */
    public static int getDocentID(Docent docent) {
        Object[][] erg = getFromDatabase("SELECT * FROM docent WHERE docent.docent_id = " + docent.getDocentNumber());
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    /**
     * Get the ID of the student
     *
     * @param student the student you need the id
     * @return id or Integer.MIN_VALUE if not found
     */
    public static int getStudentID(DualStudent student) {
        Object[][] erg = getFromDatabase("SELECT * FROM student WHERE student.student_id = " + student.getStudentNumber());
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
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

    private static int getPersonID(Person person) {
        Object[][] erg = getFromDatabase("SELECT * FROM person WHERE person.last_name = " + person.getName() + " AND person.first_name = " + person.getForeName() + " AND person.birthdate = " + (new Timestamp(person.getBirthday().getTime())) + " AND person.email = " + person.getEmail() + " AND person.address_id = " + getAddressID(person.getAddress()));
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static int getAddressID(Address address) {
        Object[][] erg = getFromDatabase("SELECT * FROM address WHERE street = " + address.getStreet() + " AND number = " + address.getNumber() + " AND postal_code = " + address.getPostcode() + " AND city = " + address.getCity() + " AND country = " + address.getCountry());
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }

    private static int getCompanyID(Company company) {
        Object[][] erg = getFromDatabase("SELECT * FROM company WHERE name = " + company.getName() + " AND address_id = " + getAddressID(company.getAddress()) + " AND contact_person_id = " + getPersonID(company.getContactPerson()));
        if (erg != null)
            return (int) erg[erg.length - 1][0];
        return Integer.MIN_VALUE;
    }


    private static int setPersonToDatabase(Person person) {
        int personID = getPersonID(person);
        if (personID == Integer.MIN_VALUE) {
            setToDatabase("INSERT INTO person (first_name, last_name, birthdate, email, address_id) VALUES (?, ?, ?, ?, ?)", new Object[]{person.getForeName(), person.getName(), person.getBirthday(), person.getEmail(), setAddressToDatabase(person.getAddress())}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER});
            sleep100();
        }
        return personID == Integer.MIN_VALUE ? getPersonID(person) : personID;
    }

    private static int setAddressToDatabase(Address address) {
        int addressID = getAddressID(address);
        if (addressID == Integer.MIN_VALUE) {
            setToDatabase("INSERT INTO address (street, number, postal_code, city, country) VALUES (?, ?, ?, ?, ?)", new Object[]{address.getStreet(), address.getNumber(), address.getPostcode(), address.getCity(), address.getCity()}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
            sleep100();
        }
        return addressID == Integer.MIN_VALUE ? getAddressID(address) : addressID;
    }

    private static int setCourseToDatabase(Course course) {
        int courseID = getCourseID(course);
        if (courseID == Integer.MIN_VALUE) {
            setToDatabase("INSERT INTO course (room, name, registry_date, course_type, study_director_id, representative_student_id) VALUES (?, ?, ?, ?, ?, ?)", new Object[]{getRoomID(course.getRoom()), course.getName(), (new Timestamp(course.getRegistrationDate().getTime())), getCourseTypeID(course.getStudyCourse()), course.getStudyDirector().getDocentNumber(), course.getCourseSpeakerID()}, new int[]{Types.INTEGER, Types.VARCHAR, Types.DATE, Types.INTEGER, Types.INTEGER, Types.INTEGER});
            sleep100();
        }
        return courseID == Integer.MIN_VALUE ? getCourseID(course) : courseID;
    }

    private static int setCompanyToDatabase(Company company) {
        int companyID = getCompanyID(company);
        if (companyID == Integer.MIN_VALUE) {
            setToDatabase("INSERT INTO company (name, address_id, contact_person_id) VALUES (?, ?, ?)", new Object[]{company.getName(), setAddressToDatabase(company.getAddress()), setPersonToDatabase(company.getContactPerson())}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
            sleep100();
        }
        return companyID == Integer.MIN_VALUE ? getCompanyID(company) : companyID;
    }

    private static Docent getDocentByID(int id) {
        Object[][] docent = getFromDatabase("SELECT * FROM student LEFT JOIN person ON student.person_id = person.person_id WHERE docent.docent_id = " + id);
        if (docent == null || docent.length < 1) return null;
        return new Docent((String) docent[0][4], (String) docent[0][3], new Date(((Timestamp) docent[0][5]).getTime()), getAddressByID((int) docent[0][7]), (String) docent[0][6], (int) docent[0][0]);
    }

    private static Address getAddressByID(int id) {
        Object[][] address = getFromDatabase("SELECT * FROM address WHERE address_id = " + id);
        if (address == null || address.length < 1) return null;
        return new Address((String) address[0][1], (String) address[0][2], (String) address[0][3], (String) address[0][4], (String) address[0][5]);
    }

    private static Course getCourseByID(int id) {
        Object[][] courses = getFromDatabase("SELECT * FROM course LEFT JOIN student on course.representative_student_id = student.student_id LEFT JOIN person ON student.person_id = person.person_id  WHERE course.course_id = " + id);
        if (courses == null || courses.length < 1) return null;
        return new Course((String) courses[0][2], StudyCourse.BWL.getDeclaringClass().getEnumConstants()[(int) courses[0][4]], getDocentByID((int) courses[0][5]), (int) courses[0][7], new Date(((Timestamp) courses[0][3]).getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[(int) courses[0][1]]);
    }

    private static Company getCompanyByID(int id) {
        Object[][] company = getFromDatabase("SELECT * FROM company LEFT JOIN person ON company.contact_person_id = person.person_id WHERE company.company_id = " + id);
        if (company == null || company.length < 1) return null;
        return new Company((String) company[0][1], getAddressByID((int) company[0][2]), new Person((String) company[0][6], (String) company[0][5], new Date(((Timestamp) company[0][7]).getTime()), getAddressByID((int) company[0][9]), (String) company[0][8]));
    }

    private static int updatePerson(Person person) {
        setToDatabase("UPDATE person SET first_name = ?, last_name = ?, birthdate = ?, email = ?, address_id = ? WHERE person.person_id = " + getPersonID(person), new Object[]{person.getForeName(), person.getName(), person.getBirthday(), person.getEmail(), updateAddress(person.getAddress())}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.DATE, Types.VARCHAR, Types.INTEGER});
        return getPersonID(person);
    }

    private static int updateAddress(Address address) {
        int address_id = getAddressID(address);
        Object[][] help = getFromDatabase("SELECT * FROM person WHERE person.address_id = " + address_id + " UNION SELECT * FROM company WHERE company.address_id = " + address_id);
        if (help != null && help.length > 0)
            return setAddressToDatabase(address);
        setToDatabase("UPDATE address SET street = ?, number = ?, postal_code = ?, city = ?, country = ? WHERE address.address_id = " + address_id, new Object[]{address.getStreet(), address.getNumber(), address.getPostcode(), address.getCity(), address.getCity()}, new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
        return getAddressID(address);
    }

    private static int updateCompany(Company company) {
        setToDatabase("UPDATE company SET name = ?. address_id = ?, contact_person_id = ? WHERE company.company_id = " + getCompanyID(company), new Object[]{company.getName(), setAddressToDatabase(company.getAddress()), setPersonToDatabase(company.getContactPerson())}, new int[]{Types.VARCHAR, Types.INTEGER, Types.INTEGER});
        return getCompanyID(company);
    }


    private static void deletePerson(Person person) {
        setToDatabase("DELETE FROM person WHERE person.person_id = " + getPersonID(person), null, null);
        int address_id = getAddressID(person.getAddress());
        Object[][] help = getFromDatabase("SELECT * FROM person WHERE person.address_id = " + address_id + " UNION SELECT * FROM company WHERE company.address_id = " + address_id);
        if (help == null) return;
        if (help.length == 1)
            deleteAddress(address_id);
    }

    private static void deleteAddress(int addressID) {
        setToDatabase("DELETE FROM address WHERE address.address_id = " + addressID, null, null);
    }

    private static void deleteCompany(Company company) {
        setToDatabase("DELETE FROM company WHERE company.company_id = " + getCompanyID(company), null, null);

        int address_id = getAddressID(company.getAddress());
        Object[][] help = getFromDatabase("SELECT * FROM person WHERE person.address_id = " + address_id + " UNION SELECT * FROM company WHERE company.address_id = " + address_id);
        if (help != null && help.length == 1)
            deleteAddress(address_id);

        int person_id = getPersonID(company.getContactPerson());
        help = getFromDatabase("SELECT * FROM student WHERE student.person_id = " + person_id + " UNION SELECT * FROM docent WHERE docent.person_id " + person_id);
        if (help == null || help.length == 0)
            deletePerson(company.getContactPerson());
    }


    private static void sleep100() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
