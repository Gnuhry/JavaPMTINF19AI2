package org.dhbw.classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Database {
    private static Connection connection;
    private static Statement statement;
    private static SimpleDateFormat dateFormat;

    //----------------------------------------General----------------------------

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
        if(dateFormat == null)
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return connection;

    }

    /**
     * To send a select command to database
     *
     * @param command the sql select command to get the values
     * @return all the values from the commands. [row][column]. if no value return null
     */
    public static ResultSet getFromDatabase(String command) throws SQLException, ClassNotFoundException {
        getConnection();
        statement = connection.createStatement();
        return statement.executeQuery(command);
    }

    /**
     * To send a insert command to database
     *
     * @param preparedStatement
     */
    public static void setToDatabase(PreparedStatement preparedStatement) {
        try {
            preparedStatement.executeUpdate();
            preparedStatement.close();
//            statement.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    //--------------------------------hasObject-----------------------
    public static boolean hasStudent(DualStudent student) throws SQLException, ClassNotFoundException {
        return getFromDatabase("SELECT student_id FROM student WHERE student_id = " + student.getStudentNumber()).next();
    }

    public static boolean hasDocent(Docent docent) throws SQLException, ClassNotFoundException {
        return getFromDatabase("SELECT docent_id FROM docent WHERE docent_id = " + docent.getDocentNumber()).next();
    }

    public static boolean hasCourse(Course course) throws SQLException, ClassNotFoundException {
        return getCourseID(course) != Integer.MIN_VALUE;
    }

    public static boolean hasCompany(Company company) throws SQLException, ClassNotFoundException {
        return getCompanyID(company) != Integer.MIN_VALUE;
    }

    private static boolean hasAddress(Address address) throws SQLException, ClassNotFoundException {
        return getAddressID(address) != Integer.MIN_VALUE;
    }

    private static boolean hasPerson(Person person) throws SQLException, ClassNotFoundException {
        return getPersonID(person) != Integer.MIN_VALUE;
    }


    //--------------------------------getObjectID-----------------------
    public static int getCourseID(Course course) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = getFromDatabase("SELECT course_id, registry_date FROM course WHERE room = " + getRoomID(course.getRoom()) + " AND name = '" + course.getName() + "' AND registry_date = '" + dateFormat.format(course.getRegistrationDate()) + "'AND course_type = " + getCourseTypeID(course.getStudyCourse()) + " AND study_director_id = " + course.getStudyDirector().getDocentNumber() + " AND representative_student_id = " + course.getCourseSpeakerID());
        return resultSet != null && resultSet.next() ? resultSet.getInt(1) : Integer.MIN_VALUE;
    }

    public static int getCompanyID(Company company) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = getFromDatabase("SELECT company_id FROM company WHERE name = '" + company.getName() + "' AND address_id = " + getAddressID(company.getAddress()) + " AND contact_person_id = " + getPersonID(company.getContactPerson()));
        return resultSet != null && resultSet.next() ? resultSet.getInt(1) : Integer.MIN_VALUE;
    }

    private static int getAddressID(Address address) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = getFromDatabase("SELECT address_id FROM address WHERE street = '" + address.getStreet() + "' AND number = '" + address.getNumber() + "' AND postal_code = '" + address.getPostcode() + "' AND city = '" + address.getCity() + "' AND country = '" + address.getCountry() + "'");
        return resultSet != null && resultSet.next() ? resultSet.getInt(1) : Integer.MIN_VALUE;
    }

    private static int getPersonID(Person person) throws SQLException, ClassNotFoundException {
        if (person.getBirthday() == null) return Integer.MIN_VALUE;
        ResultSet resultSet = getFromDatabase("SELECT person_id, birthdate FROM person WHERE person.last_name = '" + person.getName() + "' AND person.first_name = '" + person.getForeName() + "' AND person.birthdate = '" + dateFormat.format(person.getBirthday()) + "' AND person.email = '" + person.getEmail() + "' AND person.address_id = " + getAddressID(person.getAddress()));
        return resultSet != null && resultSet.next() ? resultSet.getInt(1) : Integer.MIN_VALUE;
    }

    //--------------------------------setObject-----------------------
    public static void setStudent(DualStudent student) throws SQLException, ClassNotFoundException {
        if (!hasStudent(student)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO student (student_id, matriculation_number, person_id, java_knowlage, course_id, company_id) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, student.getStudentNumber());
            preparedStatement.setInt(2, student.getMatriculationNumber());
            preparedStatement.setInt(3, setPerson(student));
            preparedStatement.setInt(4, student.getJavaKnowledge());
            preparedStatement.setInt(5, setCourse(student.getCourse()));
            preparedStatement.setInt(6, setCompany(student.getCompany()));
            setToDatabase(preparedStatement);
        }

    }

    public static void setDocent(Docent docent) throws SQLException, ClassNotFoundException {
        if (!hasDocent(docent)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO docent (docent_id, person_id) VALUES (?, ?)");
            preparedStatement.setInt(1, docent.getDocentNumber());
            preparedStatement.setInt(2, setPerson(docent));
            setToDatabase(preparedStatement);
        }
    }

    public static int setCourse(Course course) throws SQLException, ClassNotFoundException {
        if (!hasCourse(course)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO course (room, name, registry_date, course_type, study_director_id, representative_student_id) VALUES (?, ?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, getRoomID(course.getRoom()));
            preparedStatement.setString(2, course.getName());
            preparedStatement.setDate(3, new Date(course.getRegistrationDate().getTime()));
            preparedStatement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            preparedStatement.setInt(5, course.getStudyDirector().getDocentNumber());
            preparedStatement.setInt(6, course.getCourseSpeakerID());
            setToDatabase(preparedStatement);
            sleep(1000);
        }
        return getCourseID(course);
    }

    public static int setCompany(Company company) throws SQLException, ClassNotFoundException {
        if (!hasCompany(company)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO company (name, address_id, contact_person_id) VALUES (?, ?, ?)");
            preparedStatement.setString(1, company.getName());
            preparedStatement.setInt(2, setAddress(company.getAddress()));
            preparedStatement.setInt(3, setPerson(company.getContactPerson()));
            setToDatabase(preparedStatement);
            sleep(1000);
        }
        return getCompanyID(company);
    }

    private static int setAddress(Address address) throws SQLException, ClassNotFoundException {
        if (!hasAddress(address)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO address (street, number, postal_code, city, country) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, address.getStreet());
            preparedStatement.setString(2, address.getNumber());
            preparedStatement.setString(3, address.getPostcode());
            preparedStatement.setString(4, address.getCity());
            preparedStatement.setString(5, address.getCountry());
            setToDatabase(preparedStatement);
            sleep(100);
        }
        return getAddressID(address);
    }

    private static int setPerson(Person person) throws SQLException, ClassNotFoundException {
        if (!hasPerson(person)) {
            PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO person (first_name, last_name, birthdate, email, address_id) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, person.getForeName());
            preparedStatement.setString(2, person.getName());
            if (person.getBirthday() == null)
                preparedStatement.setDate(3, null);
            else
                preparedStatement.setDate(3, new Date(person.getBirthday().getTime()));
            preparedStatement.setString(4, person.getEmail());
            if (person.getAddress() == null)
                preparedStatement.setInt(5, -1);
            else
                preparedStatement.setInt(5, setAddress(person.getAddress()));
            setToDatabase(preparedStatement);
            sleep(500);
        }
        return getPersonID(person);
    }

    //--------------------------------getObject-----------------------
    public static DualStudent getStudent(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM student LEFT JOIN person ON student.person_id = person.person_id WHERE student_id = " + id);
        if (rs.next())
            return new DualStudent(rs.getInt("matriculation_number"), rs.getInt("student_id"), rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email"), getCourse(rs.getInt("course_id")), rs.getInt("java_knowlage"), getCompany(rs.getInt("company_id")));
        return null;
    }

    public static Docent getDocent(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM docent LEFT JOIN person ON docent.person_id = person.person_id WHERE docent_id = " + id);
        if (rs.next())
            return new Docent(rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email"), rs.getInt("docent_id"));
        return null;
    }

    public static Course getCourse(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM course WHERE course_id = " + id);
        if (rs.next())
            return new Course(rs.getString("name"), StudyCourse.AInformatik.getDeclaringClass().getEnumConstants()[rs.getInt("course_type")], getDocent(rs.getInt("study_director_id")), rs.getInt("study_director_id"), new java.util.Date(rs.getDate("registry_date").getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[rs.getInt("room")]);
        return null;
    }

    public static Company getCompany(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM company WHERE company_id = " + id);
        if (rs.next())
            return new Company(rs.getString("name"), getAddress(rs.getInt("address_id")), getPerson(rs.getInt("contact_person_id")));
        return null;
    }

    private static Address getAddress(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM address WHERE address_id = " + id);
        if (rs.next())
            return new Address(rs.getString("street"), rs.getString("number"), rs.getString("postal_code"), rs.getString("city"), rs.getString("country"));
        return null;
    }

    private static Person getPerson(int id) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT * FROM person WHERE person_id = " + id);
        if (rs.next())
            return new Person(rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email"));
        return null;
    }

    //--------------------------------deleteObject-----------------------
    public static void deleteStudent(DualStudent student) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM student WHERE student_id = ?");
        preparedStatement.setInt(1, student.getStudentNumber());
        setToDatabase(preparedStatement);
        if (connectToPeron(student) == 0)
            deletePerson(student);
    }

    public static void deleteDocent(Docent docent) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM docent WHERE docent_id = ?");
        preparedStatement.setInt(1, docent.getDocentNumber());
        setToDatabase(preparedStatement);
        if (connectToPeron(docent) == 0)
            deletePerson(docent);
    }

    public static void deleteCourse(Course course) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM course WHERE student_id = ? AND  room = ? AND name = ? AND registry_date = ? AND course_type = ? AND study_director_id = ? AND representative_student_id = ?");
        preparedStatement.setInt(1, getRoomID(course.getRoom()));
        preparedStatement.setString(2, course.getName());
        preparedStatement.setDate(3, new Date(course.getRegistrationDate().getTime()));
        preparedStatement.setInt(4, getCourseTypeID(course.getStudyCourse()));
        preparedStatement.setInt(5, course.getStudyDirector().getDocentNumber());
        preparedStatement.setInt(6, course.getCourseSpeakerID());
        setToDatabase(preparedStatement);
    }

    public static void deleteCompany(Company company) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM company WHERE name = ?, address_id = ?, contact_person_id = ?");
        preparedStatement.setString(1, company.getName());
        preparedStatement.setInt(2, getAddressID(company.getAddress()));
        preparedStatement.setInt(3, getPersonID(company.getContactPerson()));
        setToDatabase(preparedStatement);
        if (connectToAddress(company.getAddress()) == 0)
            deleteAddress(company.getAddress());
        if (connectToPeron(company.getContactPerson()) == 0)
            deletePerson(company.getContactPerson());
    }

    private static void deleteAddress(Address address) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM address WHERE street = ? AND address.number = ? AND postal_code = ? AND city = ? AND country = ?");
        preparedStatement.setString(1, address.getStreet());
        preparedStatement.setString(2, address.getNumber());
        preparedStatement.setString(3, address.getPostcode());
        preparedStatement.setString(4, address.getCity());
        preparedStatement.setString(5, address.getCountry());
        setToDatabase(preparedStatement);
    }

    private static void deletePerson(Person person) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE FROM person WHERE first_name = ? AND last_name = ? AND birthdate = ? AND email = ? AND address_id = ?");
        preparedStatement.setString(1, person.getForeName());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setDate(3, new Date(person.getBirthday().getTime()));
        preparedStatement.setString(4, person.getEmail());
        preparedStatement.setInt(5, getAddressID(person.getAddress()));
        setToDatabase(preparedStatement);
        if (connectToAddress(person.getAddress()) == 0)
            deleteAddress(person.getAddress());
    }

    //------------------------CheckMultipleUse--------------------------
    private static int connectToPeron(Person person) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT student_id FROM student LEFT JOIN person ON student.person_id = person.person_id WHERE person.last_name = '" + person.getName() + "' AND person.first_name = '" + person.getForeName() + "' AND person.birthdate = '" + (dateFormat.format(person.getBirthday())) + "' AND person.email = '" + person.getEmail() + "' AND person.address_id = " + getAddressID(person.getAddress()) +
                " UNION SELECT docent_id FROM docent LEFT JOIN person ON docent.person_id = person.person_id WHERE person.last_name = '" + person.getName() + "' AND person.first_name = '" + person.getForeName() + "' AND person.birthdate = '" + (dateFormat.format(person.getBirthday())) + "' AND person.email = '" + person.getEmail() + "' AND person.address_id = " + getAddressID(person.getAddress()) +
                " UNION SELECT company_id FROM company LEFT JOIN person ON company.contact_person_id = person.person_id WHERE person.last_name = '" + person.getName() + "' AND person.first_name = '" + person.getForeName() + "' AND person.birthdate = '" + (dateFormat.format(person.getBirthday())) + "' AND person.email = '" + person.getEmail() + "' AND person.address_id = " + getAddressID(person.getAddress()));
        int rowCount = 0;
        if (rs.last()) {
            rowCount = rs.getRow();
            rs.beforeFirst();
        }
        return rowCount;
    }

    private static int connectToAddress(Address address) throws SQLException, ClassNotFoundException {
        ResultSet rs = getFromDatabase("SELECT person_id FROM person LEFT JOIN address ON person.address_id = address.address_id WHERE street = '" + address.getStreet() + "' AND number = '" + address.getNumber() + "' AND postal_code = '" + address.getPostcode() + "' AND city = '" + address.getCity() + "' AND country = '" + address.getCountry() + "'" +
                "UNION SELECT company_id FROM company LEFT JOIN address ON company.address_id = address.address_id WHERE street = '" + address.getStreet() + "' AND number = '" + address.getNumber() + "' AND postal_code = '" + address.getPostcode() + "' AND city = '" + address.getCity() + "' AND country = '" + address.getCountry()+"'");
        int rowCount = 0;
        if (rs.last()) {
            rowCount = rs.getRow();
            rs.beforeFirst();
        }
        return rowCount;
    }

    //-----------------------------GetAllObject------------------------------
    public static DualStudent[] getAllStudent() throws SQLException, ClassNotFoundException {
        List<DualStudent> students = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM student LEFT JOIN person ON student.person_id = person.person_id");
        while (rs.next())
            students.add(new DualStudent(rs.getInt("matriculation_number"), rs.getInt("student_id"), rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email"), getCourse(rs.getInt("course_id")), rs.getInt("java_knowlage"), getCompany(rs.getInt("company_id"))));
        return students.toArray(DualStudent[]::new);
    }

    public static Docent[] getAllDocent() throws SQLException, ClassNotFoundException {
        List<Docent> docents = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM docent LEFT JOIN person ON docent.person_id = person.person_id");
        if (rs.next())
            docents.add(new Docent(rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email"), rs.getInt("docent_id")));
        return docents.toArray(Docent[]::new);
    }

    public static Course[] getAllCourse() throws SQLException, ClassNotFoundException {
        List<Course> courses = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM course LEFT JOIN docent ON course.study_director_id = docent.docent_id LEFT JOIN person ON person.person_id = docent.person_id");
        if (rs.next())
            courses.add(new Course(rs.getString("name"), StudyCourse.AInformatik.getDeclaringClass().getEnumConstants()[rs.getInt("course_type")], getDocent(rs.getInt("study_director_id")), rs.getInt("study_director_id"), new java.util.Date(rs.getDate("registry_date").getTime()), CourseRoom.A222.getDeclaringClass().getEnumConstants()[rs.getInt("room")]));
        return courses.toArray(Course[]::new);
    }

    public static Company[] getAllCompany() throws SQLException, ClassNotFoundException {
        List<Company> companies = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM course LEFT JOIN docent ON course.study_director_id = docent.docent_id LEFT JOIN person ON person.person_id = docent.person_id");
        if (rs.next())
            companies.add(new Company(rs.getString("name"), getAddress(rs.getInt("address_id")), getPerson(rs.getInt("contact_person_id"))));
        return companies.toArray(Company[]::new);
    }

    public static Address[] getAllAddress() throws SQLException, ClassNotFoundException {
        List<Address> addresses = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM course LEFT JOIN docent ON course.study_director_id = docent.docent_id LEFT JOIN person ON person.person_id = docent.person_id");
        if (rs.next())
            addresses.add(new Address(rs.getString("street"), rs.getString("number"), rs.getString("postal_code"), rs.getString("city"), rs.getString("country")));
        return addresses.toArray(Address[]::new);
    }

    public static Person[] getAllPerson() throws SQLException, ClassNotFoundException {
        List<Person> persons = new ArrayList<>();
        ResultSet rs = getFromDatabase("SELECT * FROM course LEFT JOIN docent ON course.study_director_id = docent.docent_id LEFT JOIN person ON person.person_id = docent.person_id");
        if (rs.next())
            persons.add(new Person(rs.getString("last_name"), rs.getString("first_name"), new java.util.Date(rs.getDate("birthdate").getTime()), getAddress(rs.getInt("address_id")), rs.getString("email")));
        return persons.toArray(Person[]::new);
    }

    //-----------------------------UpdateObject------------------------------
    public static void updateStudent(DualStudent student, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE student SET java_knowledge = ?, person_id = ?, course_id = ?, company_id = ? WHERE student.student_id = ?");
        preparedStatement.setInt(1, student.getJavaKnowledge());
        int person_id;
        if (connectToPeron(student) > 1)
            person_id = setPerson(student);
        else
            person_id = updatePerson(student, getPersonID(student));
        preparedStatement.setInt(2, person_id);
        preparedStatement.setInt(3, updateCourse(student.getCourse(), getCourseID(student.getCourse())));
        preparedStatement.setInt(4, updateCompany(student.getCompany(), getCompanyID(student.getCompany())));
        preparedStatement.setInt(5, id);
        setToDatabase(preparedStatement);
    }

    public static void updateDocent(Docent docent, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE docent SET person_id = ? WHERE docent_id = ?");
        int person_id;
        if (connectToPeron(docent) > 1)
            person_id = setPerson(docent);
        else
            person_id = updatePerson(docent, docent.getDocentNumber());
        preparedStatement.setInt(1, person_id);
        preparedStatement.setInt(2, docent.getDocentNumber());
        setToDatabase(preparedStatement);
    }

    public static int updateCourse(Course course, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE course SET room = ?, name = ?, registry_date = ?, course_type = ?, study_director_id = ?, representative_student_id = ? WHERE course.course_id = ?");
        preparedStatement.setInt(1, getRoomID(course.getRoom()));
        preparedStatement.setString(2, course.getName());
        preparedStatement.setDate(3, new Date(course.getRegistrationDate().getTime()));
        preparedStatement.setInt(4, getCourseTypeID(course.getStudyCourse()));
        preparedStatement.setInt(5, course.getStudyDirector().getDocentNumber());
        preparedStatement.setInt(6, course.getCourseSpeakerID());
        preparedStatement.setInt(7, id);
        setToDatabase(preparedStatement);
        return id;
    }

    public static int updateCompany(Company company, int company_id) throws SQLException, ClassNotFoundException {
        int id = getCompanyID(company);
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE company SET name = ?, address_id = ?, contact_person_id = ? WHERE company_id = ?");
        preparedStatement.setString(1, company.getName());
        int address_id;
        if (connectToAddress(company.getAddress()) > 1)
            address_id = setAddress(company.getAddress());
        else
            address_id = updateAddress(company.getAddress(), getAddressID(company.getAddress()));
        preparedStatement.setInt(2, address_id);
        int person_id;
        if (connectToPeron(company.getContactPerson()) > 1)
            person_id = setPerson(company.getContactPerson());
        else
            person_id = updatePerson(company.getContactPerson(), getPersonID(company.getContactPerson()));
        preparedStatement.setInt(3, person_id);
        preparedStatement.setInt(4, id);
        setToDatabase(preparedStatement);
        return id;
    }

    private static int updateAddress(Address address, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE address SET street = ? AND number = ? AND postal_code = ? AND city = ? AMD country = ? WHERE address_id = ?");
        preparedStatement.setString(1, address.getStreet());
        preparedStatement.setString(2, address.getNumber());
        preparedStatement.setString(3, address.getPostcode());
        preparedStatement.setString(4, address.getCity());
        preparedStatement.setString(5, address.getCountry());
        preparedStatement.setInt(6, id);
        setToDatabase(preparedStatement);
        return id;
    }

    private static int updatePerson(Person person, int id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE person SET first_name = ?, last_name = ?, birthdate = ?, email = ?, address_id = ? WHERE person_id = ?");
        preparedStatement.setString(1, person.getForeName());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setDate(3, new Date(person.getBirthday().getTime()));
        preparedStatement.setString(4, person.getEmail());
        int address_id;
        if (connectToAddress(person.getAddress()) > 1)
            address_id = setAddress(person.getAddress());
        else
            address_id = updateAddress(person.getAddress(), getAddressID(person.getAddress()));
        preparedStatement.setInt(5, address_id);
        preparedStatement.setInt(6, id);
        setToDatabase(preparedStatement);
        return id;
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
    private static void sleep(int milli){
        try {
            Thread.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
