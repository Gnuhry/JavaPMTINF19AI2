package org.dhbw.classes;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Database {

    private static Connection connection;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    private static SimpleDateFormat dateFormat;

    private static List<DualStudent> dualStudents;
    private static List<Course> courses;
    private static List<Docent> docents;
    private static List<Address> addresses;
    private static List<Company> companies;
    private static List<Person> persons;
    private static List<CourseRoom> rooms;

    //----------------------------------------GetObjects----------------------------

    /**
     * get all courses
     *
     * @return List of all courses
     */
    public static List<Course> getCourses() {
        updateCourse();
        return courses;
    }

    /**
     * get all courses
     *
     * @return List of all courses
     */
    public static List<Docent> getDocents() {
        updateDocent();
        return docents;
    }

    /**
     * get all addresses
     *
     * @return List of all addresses
     */
    public static List<Address> getAddresses() {
        updateAddress();
        return addresses;
    }

    /**
     * get all companies
     *
     * @return List of all companies
     */
    public static List<Company> getCompanies() {
        updateCompany();
        return companies;
    }

    /**
     * get all persons
     *
     * @return List of all persons
     */
    public static List<Person> getPersons() {
        updatePerson();
        return persons;
    }

    /**
     * get all students
     *
     * @return List of all students
     */
    public static List<DualStudent> getStudents() {
        updateStudent();
        return dualStudents;
    }

    /**
     * get all rooms
     *
     * @return List of all rooms
     */
    public static List<CourseRoom> getRooms() {
        updateRoom();
        return rooms;
    }

    //--------------------------------------------AddObject-----------------------------------------------------------------

    /**
     * add a student to the database
     *
     * @param student the adding student
     */
    public static void addStudent(DualStudent student) {
        if (student == null) return;
        if (hasID("student", "student_id", student.getStudentNumber())) return;
        int person_id = addPerson(student);
        int course_id = addCourse(student.getCourse());
        int company_id = addCompany(student.getCompany());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO student (student_id, matriculation_number, person_id, java_knowlage, course_id, company_id) VALUES (?, ?, ?, ?, ?, ?)");
            statement.setInt(1, student.getStudentNumber());
            statement.setInt(2, student.getMatriculationNumber());
            statement.setInt(3, person_id);
            statement.setInt(4, student.getJavaKnowledge());
            statement.setInt(5, course_id);
            statement.setInt(6, company_id);
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * add a docent to the database
     *
     * @param docent the adding docent
     * @return id of docent if success, Integer.Min_Value if not
     */
    public static int addDocent(Docent docent) {
        if (docent == null) return Integer.MIN_VALUE;
        if (hasID("docent", "docent_id", docent.getDocentNumber())) return docent.getDocentNumber();
        int person_id = addPerson(docent);
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO docent (docent_id, person_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, docent.getDocentNumber());
            statement.setInt(2, person_id);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * add a course to the database
     *
     * @param course the adding course
     * @return id of course if success, Integer.Min_Value if not
     */
    public static int addCourse(Course course) {
        if (course == null) return Integer.MIN_VALUE;
        int id = getCourseId(course);
        if (id >= 0) return id;
        int docent_id = addDocent(course.getStudyDirector());
        int room_id = addRoom(course.getRoom());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO course (room_id, name, registry_date, course_type, study_director_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, room_id);
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            statement.setInt(5, docent_id);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * add a person to the database
     *
     * @param person the adding person
     * @return id of person if success, Integer.Min_Value if not
     */
    public static int addPerson(Person person) {
        if (person == null) return Integer.MIN_VALUE;
        int id = getPersonId(person);
        if (id >= 0) return id;
        int address_id = addAddress(person.getAddress());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO person (first_name, last_name, birthdate, email, address_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            addPersonToStatment(person, address_id);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * add a company to the database
     *
     * @param company the adding company
     * @return id of company if success, Integer.Min_Value if not
     */
    public static int addCompany(Company company) {
        if (company == null) return Integer.MIN_VALUE;
        int id = getCompanyId(company);
        if (id >= 0) return id;
        int address_id = addAddress(company.getAddress());
        int person_id = addPerson(company.getContactPerson());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO company (name, address_id, contact_person_id) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, company.getName());
            statement.setInt(2, address_id);
            statement.setInt(3, person_id);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * add a address to the database
     *
     * @param address the adding address
     * @return id of address if success, Integer.Min_Value if not
     */
    public static int addAddress(Address address) {
        if (address == null) return Integer.MIN_VALUE;
        int id = getAddressId(address);
        if (id >= 0) return id;
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO address (street, number, postal_code, city, country) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            addAddressToStatement(address);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * add a room to the database
     *
     * @param room the adding room
     * @return id of room if success, Integer.Min_Value if not
     */
    public static int addRoom(CourseRoom room) {
        if (room == null) return Integer.MIN_VALUE;
        int id = getRoomID(room);
        if (id >= 0) return id;
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO room (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, room.getName());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    //-------------------------------------updateObject-------------------------------------------------------

    /**
     * update student in the database
     *
     * @param student student with the new data
     * @param old     student with the old data
     */
    public static void updateStudent(DualStudent student, DualStudent old) {
        if (student == null) return;
        if (student.equals(old)) return;
        int person_id = updatePerson(student, old);
        int course_id = updateCourse(student.getCourse(), old.getCourse());
        int company_id = updateCompany(student.getCompany(), old.getCompany());
        try {
            initialize();
            statement = connection.prepareStatement("UPDATE student SET person_id=?, java_knowlage=?, course_id=?, company_id=? WHERE student_id=?");
            statement.setInt(1, person_id);
            statement.setInt(2, student.getJavaKnowledge());
            statement.setInt(3, course_id);
            statement.setInt(4, company_id);
            statement.setInt(5, student.getStudentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * update docent in the database
     *
     * @param docent docent with the new data
     * @param old    docent with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updateDocent(Docent docent, Docent old) {
        if (docent == null) return Integer.MIN_VALUE;
        if (docent.equals(old)) return old.getDocentNumber();
        int person_id = updatePerson(docent, old);
        try {
            initialize();
            statement = connection.prepareStatement("UPDATE docent SET person_id=? WHERE docent_id=?", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, person_id);
            statement.setInt(2, docent.getDocentNumber());
            statement.execute();
            return docent.getDocentNumber();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * update course in the database
     *
     * @param course course with the new data
     * @param old    course with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updateCourse(Course course, Course old) {
        if (course == null) return Integer.MIN_VALUE;
        int id = getCourseId(old);
        if (course.equals(old)) return id;
        if (id >= 0) {
            int docent_id = updateDocent(course.getStudyDirector(), old.getStudyDirector());
            int room_id = updateRoom(course.getRoom(), old.getRoom());
            try {
                initialize();
                statement = connection.prepareStatement("UPDATE course SET room_id=?, name=?, study_director=?, WHERE course_id=?", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, room_id);
                statement.setString(2, course.getName());
                statement.setInt(3, docent_id);
                statement.setInt(4, id);
                statement.execute();
                return id;
            } catch (SQLException | ClassNotFoundException exception) {
                exception.printStackTrace();
            } finally {
                closeStatement();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * update person in the database
     *
     * @param person person with the new data
     * @param old    person with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updatePerson(Person person, Person old) {
        if (person == null) return Integer.MIN_VALUE;
        int id = getPersonId(old);
        if (person.equals(old)) return id;
        if (countUsedPerson(old) > 1) return addPerson(person);
        if (id >= 0) {
            int address_id = updateAddress(person.getAddress(), old.getAddress());
            try {
                initialize();
                statement = connection.prepareStatement("UPDATE person SET first_name=?, last_name=?, birthdate=?, email=?, address_id=? WHERE person_id=?", Statement.RETURN_GENERATED_KEYS);
                addPersonToStatment(person, address_id);
                statement.setInt(6, id);
                statement.execute();
                return id;
            } catch (SQLException | ClassNotFoundException exception) {
                exception.printStackTrace();
            } finally {
                closeStatement();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * update company in the database
     *
     * @param company company with the new data
     * @param old     company with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updateCompany(Company company, Company old) {
        if (company == null) return Integer.MIN_VALUE;
        int id = getCompanyId(old);
        if (company.equals(old)) return id;
        if (id >= 0) {
            int address_id = updateAddress(company.getAddress(), old.getAddress());
            int person_id = updatePerson(company.getContactPerson(), old.getContactPerson());
            try {
                initialize();
                statement = connection.prepareStatement("UPDATE company SET name=?, address_id=?, contact_person_id=? WHERE company_id=?", Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, company.getName());
                statement.setInt(2, address_id);
                statement.setInt(3, person_id);
                statement.setInt(4, id);
                statement.execute();
                return id;
            } catch (SQLException | ClassNotFoundException exception) {
                exception.printStackTrace();
            } finally {
                closeStatement();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * update address in the database
     *
     * @param address address with the new data
     * @param old     address with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updateAddress(Address address, Address old) {
        if (address == null) return Integer.MIN_VALUE;
        int id = getAddressId(old);
        if (address.equals(old)) return id;
        if (countUsedAddress(old) > 1) return addAddress(address);
        if (id >= 0) {
            try {
                initialize();
                statement = connection.prepareStatement("UPDATE address SET street=?, number=?, postal_code=?, city=?, country=? WHERE address_id=?", Statement.RETURN_GENERATED_KEYS);
                addAddressToStatement(address);
                statement.setInt(6, id);
                statement.execute();
                return id;
            } catch (SQLException | ClassNotFoundException exception) {
                exception.printStackTrace();
            } finally {
                closeStatement();
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * update room in the database
     *
     * @param room room with the new data
     * @param old  room with the old data
     * @return id if exists, Integer.Min_Value if not
     */
    public static int updateRoom(CourseRoom room, CourseRoom old) {
        if (room == null) return Integer.MIN_VALUE;
        int id = getRoomID(old);
        if (room.equals(old)) return id;
        if (countUsedRoom(old) > 1) return addRoom(room);
        if (id >= 0) {
            try {
                initialize();
                statement = connection.prepareStatement("UPDATE room SET name=? WHERE room_id=?", Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, id);
                statement.execute();
                return id;
            } catch (SQLException | ClassNotFoundException exception) {
                exception.printStackTrace();
            } finally {
                closeStatement();
            }
        }
        return Integer.MIN_VALUE;
    }

    //----------------------------------------DeleteObject----------------------------------

    /**
     * delete a student from the database
     *
     * @param student the student to delete
     */
    public static void deleteStudent(DualStudent student) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM student WHERE student_id = ?");
            statement.setInt(1, student.getStudentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        deletePerson(student);
    }

    /**
     * delete a docent from the database
     *
     * @param docent the docent to delete
     */
    public static void deleteDocent(Docent docent) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM docent WHERE docent_id = ?");
            statement.setInt(1, docent.getDocentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        deletePerson(docent);
    }

    /**
     * delete a course from the database
     *
     * @param course the course to delete
     */
    public static void deleteCourse(Course course) {
        int room_id = getRoomID(course.getRoom());
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM course WHERE room_id = ? AND name = ? AND registry_date = ? AND course_type = ? AND study_director_id = ?");
            statement.setInt(1, room_id);
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            statement.setInt(5, course.getStudyDirector().getDocentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        deleteRoom(course.getRoom());
    }

    /**
     * delete a company from the database
     *
     * @param company the student to company
     */
    public static void deleteCompany(Company company) {
        int id = getCompanyId(company);
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM company WHERE company_id = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        deletePerson(company.getContactPerson());
        deleteAddress(company.getAddress());
        deleteStudentByCompanyId(id);
    }

    /**
     * delete a person from the database
     *
     * @param person the person to delete
     */
    public static void deletePerson(Person person) {
        if (countUsedPerson(person) > 0)
            return;
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM person WHERE email = ?");
            statement.setString(1, person.getEmail());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        if (person.getAddress() != null && person.getAddress().getNumber() != null)
            deleteAddress(person.getAddress());
    }

    /**
     * delete a address from the database
     *
     * @param address the address to delete
     */
    public static void deleteAddress(Address address) {
        if (countUsedAddress(address) > 0)
            return;
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM address WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            addAddressToStatement(address);
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * delete a room from the database
     *
     * @param courseRoom the room to delete
     */
    public static void deleteRoom(CourseRoom courseRoom) {
        if (countUsedRoom(courseRoom) > 0)
            return;
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM room WHERE name = ?");
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    //------------------------------------------getIDS---------------------------------------------------

    /**
     * get all student ids
     *
     * @return int list of student ids
     */
    public static List<Integer> getStudentIDs() {
        try {
            List<Integer> erg = new ArrayList<>();
            initialize();
            statement = connection.prepareStatement("SELECT student_id FROM student");
            resultSet = statement.executeQuery();
            while (resultSet.next())
                erg.add(resultSet.getInt("student_id"));
            return erg;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return new ArrayList<>();
    }

    /**
     * get all student marticulationnumbers
     *
     * @return int list of all marticulationnumbers
     */
    public static List<Integer> getMatriculationNumbers() {
        try {
            List<Integer> erg = new ArrayList<>();
            initialize();
            statement = connection.prepareStatement("SELECT matriculation_number FROM student");
            resultSet = statement.executeQuery();
            while (resultSet.next())
                erg.add(resultSet.getInt("matriculation_number"));
            return erg;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return new ArrayList<>();
    }

    /**
     * get all docent ids
     *
     * @return int list of docent ids
     */
    public static List<Integer> getDocentIDs() {
        try {
            List<Integer> erg = new ArrayList<>();
            initialize();
            statement = connection.prepareStatement("SELECT docent_id FROM docent");
            resultSet = statement.executeQuery();
            while (resultSet.next())
                erg.add(resultSet.getInt("docent_id"));
            return erg;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return new ArrayList<>();
    }

    //---------------------------------------private...........................................

    /**
     * get amount of connection to the address
     *
     * @param address where the amount of connection is going to count
     * @return amount of connection
     */
    private static int countUsedAddress(Address address) {
        int counter = 0;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT company_id, person_id FROM address LEFT JOIN company c on address.address_id = c.address_id LEFT JOIN person p on address.address_id = p.address_id WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            addAddressToStatement(address);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                if (resultSet.getInt("company_id") > 0 || resultSet.getInt("person_id") > 0)
                    counter++;
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeStatement();
        }
        return counter;
    }

    /**
     * get amount of connection to the person
     *
     * @param person where the amount of connection is going to count
     * @return amount of connection
     */
    private static int countUsedPerson(Person person) {
        int counter = 0;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT student_id,docent_id,company.company_id FROM person LEFT JOIN student s on person.person_id = s.person_id LEFT JOIN docent d on person.person_id = d.person_id LEFT JOIN company ON contact_person_id=person.person_id WHERE email = ?");
            statement.setString(1, person.getEmail());
            resultSet = statement.executeQuery();
            while (resultSet.next())
                if (resultSet.getInt("company_id") > 0 || resultSet.getInt("student_id") > 0 || resultSet.getInt("company_id") > 0)
                    counter++;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return counter;
    }

    /**
     * get amount of connection to the room
     *
     * @param room where the amount of connection is going to count
     * @return amount of connection
     */
    private static int countUsedRoom(CourseRoom room) {
        int counter = 0;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT COUNT(room_id) FROM room WHERE name = ?");
            statement.setString(1, room.getName());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                counter = resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return counter;
    }

    /**
     * initialize to use the database connection
     */
    private static void initialize() throws ClassNotFoundException, SQLException {
        if (connection == null || connection.isClosed()) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://85.214.247.101:3306/dhbw?useSSL=false", "mlg_dhbw", "Reisebus1!");
        }
        if (dateFormat == null)
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (dualStudents == null)
            dualStudents = new ArrayList<>();
        if (courses == null)
            courses = new ArrayList<>();
        if (docents == null)
            docents = new ArrayList<>();
        if (addresses == null)
            addresses = new ArrayList<>();
        if (companies == null)
            companies = new ArrayList<>();
        if (persons == null)
            persons = new ArrayList<>();
        if (rooms == null)
            rooms = new ArrayList<>();
    }

    /**
     * compare student list with database and updating the list
     */
    private static void updateStudent() {
        //checkListStudent();
        try {
            initialize();
            dualStudents.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_students");
            resultSet = statement.executeQuery();
            //List<Integer> student_id = dualStudents.stream().map(DualStudent::getMatriculationNumber).collect(Collectors.toList());
            while (resultSet.next()) {
                DualStudent dualStudent =
                        new DualStudent(resultSet.getInt("matriculation_number"),
                                resultSet.getInt("student_id"),
                                resultSet.getString("student_last_name"),
                                resultSet.getString("student_first_name"),
                                convertDate(resultSet.getDate("birthdate")),
                                new Address(resultSet.getString("student_street"), resultSet.getString("student_number"), resultSet.getString("student_postal_code"), resultSet.getString("student_city"), resultSet.getString("student_country")),
                                resultSet.getString("student_email"),
                                new Course(resultSet.getString("name"), getCourseTypeById(resultSet.getInt("course_type")), new Docent(resultSet.getString("docent_last_name"), resultSet.getString("docent_first_name"), convertDate(resultSet.getDate("docent_birthdate")), new Address(resultSet.getString("docent_street"), resultSet.getString("docent_number"), resultSet.getString("docent_postal_code"), resultSet.getString("docent_city"), resultSet.getString("docent_country")), resultSet.getString("docent_email"), resultSet.getInt("study_director_id")), convertDate(resultSet.getDate("registry_date")), new CourseRoom(resultSet.getString("room"))),
                                resultSet.getInt("java_knowlage"),
                                new Company(resultSet.getString("company_name"), new Address(resultSet.getString("street"), resultSet.getString("number"), resultSet.getString("postal_code"), resultSet.getString("city"), resultSet.getString("country")), new Person(resultSet.getString("last_name"), resultSet.getString("first_name"), resultSet.getString("email"))));
//                int id = dualStudents.indexOf(dualStudent);
//                if (id >= 0)
//                    dualStudents.set(id, dualStudent);
//                else
                dualStudents.add(dualStudent);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare docent list with database and updating the list
     */
    private static void updateDocent() {
//        checkListDocents();
        try {
            initialize();
            docents.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_docents");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Docent docent =
                        new Docent(resultSet.getString("last_name"),
                                resultSet.getString("first_name"),
                                convertDate(resultSet.getDate("birthdate")),
                                new Address(resultSet.getString("street"), resultSet.getString("number"), resultSet.getString("postal_code"), resultSet.getString("city"), resultSet.getString("country")),
                                resultSet.getString("email"),
                                resultSet.getInt("docent_id"));
//                int id = docents.indexOf(docent);
//                if (id >= 0)
//                    docents.set(id, docent);
//                else
                docents.add(docent);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare course list with database and updating the list
     */
    private static void updateCourse() {
        try {
            initialize();
            courses.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_courses");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course =
                        new Course(resultSet.getString("course_name"),
                                getCourseTypeById(resultSet.getInt("course_type")),
                                new Docent(resultSet.getString("last_name"), resultSet.getString("first_name"), convertDate(resultSet.getDate("birthdate")), new Address(resultSet.getString("street"), resultSet.getString("number"), resultSet.getString("postal_code"), resultSet.getString("city"), resultSet.getString("country")), resultSet.getString("email"), resultSet.getInt("docent_id")),
                                convertDate(resultSet.getDate("registry_date")),
                                new CourseRoom(resultSet.getString("room")));
//                int id = courses.indexOf(course);
//                if (id >= 0)
//                    courses.set(id, course);
//                else
                courses.add(course);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare address list with database and updating the list
     */
    private static void updateAddress() {
        try {
            initialize();
            addresses.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_address");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Address address =
                        new Address(resultSet.getString("street"),
                                resultSet.getString("number"),
                                resultSet.getString("postal_code"),
                                resultSet.getString("city"),
                                resultSet.getString("country"));
//                int id = addresses.indexOf(address);
//                if (id >= 0)
//                    addresses.set(id, address);
//                else
                addresses.add(address);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare company list with database and updating the list
     */
    private static void updateCompany() {
        try {
            initialize();
            companies.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_companies");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Company company =
                        new Company(resultSet.getString("name"),
                                new Address(resultSet.getString("street"), resultSet.getString("number"), resultSet.getString("postal_code"), resultSet.getString("city"), resultSet.getString("country")),
                                new Person(resultSet.getString("last_name"), resultSet.getString("first_name"), resultSet.getString("email")));
//                int id = companies.indexOf(company);
//                if (id >= 0)
//                    companies.set(id, company);
//                else
                companies.add(company);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare person list with database and updating the list
     */
    private static void updatePerson() {
//        checkListPerson();
        try {
            initialize();
            persons.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_people");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Person person =
                        new Person(resultSet.getString("last_name"),
                                resultSet.getString("fore_name"),
                                resultSet.getString("email"));
//                int id = persons.indexOf(person);
//                if (id >= 0)
//                    persons.set(id, person);
//                else
                persons.add(person);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

    /**
     * compare room list with database and updating the list
     */
    private static void updateRoom() {
//        checkListPerson();
        try {
            initialize();
            rooms.clear();
            statement = connection.prepareStatement("SELECT * FROM room");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                CourseRoom room =
                        new CourseRoom(resultSet.getString("name"));
                rooms.add(room);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
    }

//    private static void checkListStudent() {
//        try {
//            List<Integer> student_ids = new ArrayList<>();
//            initialize();
//            statement = connection.prepareStatement("SELECT student_id FROM student");
//            resultSet = statement.executeQuery();
//            while (resultSet.next())
//                student_ids.add(resultSet.getInt("student_id"));
//            if (!student_ids.isEmpty())
//                dualStudents.removeIf(student -> !student_ids.contains(student.getStudentNumber()));
//        } catch (SQLException | ClassNotFoundException exception) { exception.printStackTrace();
//
//        } finally {
//            closeConnection();
//        }
//    }
//
//    private static void checkListDocents() {
//        try {
//            List<Integer> docent_ids = new ArrayList<>();
//            initialize();
//            statement = connection.prepareStatement("SELECT docent_id FROM docent");
//            resultSet = statement.executeQuery();
//            while (resultSet.next())
//                docent_ids.add(resultSet.getInt("docent_id"));
//            if (!docent_ids.isEmpty())
//                docents.removeIf(docent -> !docent_ids.contains(docent.getDocentNumber()));
//        } catch (SQLException | ClassNotFoundException exception) { exception.printStackTrace();
//
//        } finally {
//            closeConnection();
//        }
//    }
//
//    private static void checkListPerson() {
//        try {
//            List<String> emails = new ArrayList<>();
//            initialize();
//            statement = connection.prepareStatement("SELECT email FROM student");
//            resultSet = statement.executeQuery();
//            while (resultSet.next())
//                emails.add(resultSet.getString("email"));
//            if (!emails.isEmpty())
//                persons.removeIf(person -> !emails.contains(person.getEmail()));
//        } catch (SQLException | ClassNotFoundException exception) { exception.printStackTrace();
//
//        } finally {
//            closeConnection();
//        }
//    }

    /**
     * get the database id from course
     *
     * @param course the data from the id row
     * @return id if exist, Integer.Minvalue if not
     */
    private static int getCourseId(Course course) {
        if (course == null) return Integer.MIN_VALUE;
        int room_id = getRoomID(course.getRoom());
        try {
            initialize();
            statement = connection.prepareStatement("SELECT course_id FROM course WHERE room_id = ? AND name = ? AND registry_date = ? AND course_type = ? AND study_director_id = ?");
            statement.setInt(1, room_id);
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            if (course.getStudyDirector() == null || course.getStudyDirector().getDocentNumber() == 0)
                statement.setInt(5, Integer.MIN_VALUE);
            else
                statement.setInt(5, course.getStudyDirector().getDocentNumber());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * get the database id from company
     *
     * @param company the data from the id row
     * @return id if exist, Integer.Minvalue if not
     */
    private static int getCompanyId(Company company) {
        if (company == null) return Integer.MIN_VALUE;
        int address_id = getAddressId(company.getAddress());
        int person_id = getPersonId(company.getContactPerson());
        try {
            initialize();
            statement = connection.prepareStatement("SELECT company_id FROM company WHERE name = ? AND address_id = ? AND contact_person_id = ?");
            statement.setString(1, company.getName());
            statement.setInt(2, address_id);
            statement.setInt(3, person_id);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * get the database id from person
     *
     * @param person the data from the id row
     * @return id if exist, Integer.Minvalue if not
     */
    private static int getPersonId(Person person) {
        if (person == null) return Integer.MIN_VALUE;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT person_id FROM person WHERE email = ?");
            statement.setString(1, person.getEmail());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * get the database id from address
     *
     * @param address the data from the id row
     * @return id if exist, Integer.Minvalue if not
     */
    private static int getAddressId(Address address) {
        if (address == null) return Integer.MIN_VALUE;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT address_id FROM address WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            addAddressToStatement(address);
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * get the database id from room
     *
     * @param room the data from the id row
     * @return id if exist, Integer.Minvalue if not
     */
    private static int getRoomID(CourseRoom room) {
        if (room == null) return Integer.MIN_VALUE;
        try {
            initialize();
            statement = connection.prepareStatement("SELECT room_id FROM room WHERE name = ?");
            statement.setString(1, room.getName());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return Integer.MIN_VALUE;
    }

    /**
     * search in database if id exist
     *
     * @param table  the table where to search
     * @param column the column from the id
     * @param id     the id you want to search for if its exist
     * @return {true} if id exist, {false} if not
     */
    private static boolean hasID(String table, String column, int id) {
        try {
            initialize();
            statement = connection.prepareStatement("SELECT COUNT(" + column + "), " + column + " FROM " + table + " WHERE IF(" + column + " = ?, 0, 1)=0 GROUP BY " + column);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return resultSet.next() && resultSet.getInt(1) == 1;
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        return false;
    }

    /**
     * function to close java conform the statement
     */
    private static void closeStatement() {
        if (resultSet != null)
            try {
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        if (statement != null)
            try {
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }

    /**
     * function to close java conform the connection to the database
     */
    public static void closeConnection() {
        if (connection != null)
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
    }


    /**
     * get the id from the enum StudyCourse
     *
     * @param course the StudyCourse you need the id
     * @return Integer.MinValue if course not exist. id if exists
     */
    private static int getCourseTypeID(StudyCourse course) {
        StudyCourse[] courses = StudyCourse.values();
        for (int f = 0; f < courses.length; f++)
            if (courses[f].equals(course))
                return f;
        return Integer.MIN_VALUE;
    }

    /**
     * get the StudyCourse from the id
     *
     * @param id from the StudyCourse
     * @return StudyCourse if exists, null if not
     */
    private static StudyCourse getCourseTypeById(int id) {
        return id < 0 || id >= StudyCourse.values().length ? null : StudyCourse.values()[id];
    }

    /**
     * convert sql.Date to util.Date
     *
     * @param date sql.Date to convert
     * @return converted util.Date
     */
    private static Date convertDate(java.sql.Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    /**
     * convert util.Date to sql.Date
     *
     * @param date util.Date to convert
     * @return converted sql.Date
     */
    private static java.sql.Date convertDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    /**
     * delete all students, who are in the company with the company id
     *
     * @param id of the company
     */
    private static void deleteStudentByCompanyId(int id) {
        List<String> emails = new ArrayList<>();
        List<Integer> studentIDs = new ArrayList<>();
        List<Address> addresses1 = new ArrayList<>();
        try {
            initialize();
            statement = connection.prepareStatement("SELECT student_id, email, street, number, postal_code, city, country FROM student LEFT JOIN person ON person.person_id=student.person_id LEFT JOIN address ON address.address_id=person.address_id WHERE company_id = ?");
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                emails.add(resultSet.getString("email"));
                studentIDs.add(resultSet.getInt("student_id"));
                addresses1.add(new Address(resultSet.getString("street"),
                        resultSet.getString("number"),
                        resultSet.getString("postal_code"),
                        resultSet.getString("city"),
                        resultSet.getString("country")));
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeStatement();
        }
        for (int f = 0; f < emails.size() && f < studentIDs.size() && f < addresses1.size(); f++)
            deleteStudent(new DualStudent(-1, studentIDs.get(f), null, null, null, addresses1.get(f), emails.get(f), null, 0, null));
    }

    /**
     * help function to add address attributes to sql statement
     *
     * @param address address object with the attributes
     */
    private static void addAddressToStatement(Address address) throws SQLException {
        statement.setString(1, address.getStreet());
        statement.setString(2, address.getNumber());
        statement.setString(3, address.getPostcode());
        statement.setString(4, address.getCity());
        statement.setString(5, address.getCountry());
    }

    /**
     * help function to add person attributes to sql statement
     *
     * @param person person object with the attributes
     */
    private static void addPersonToStatment(Person person, int address_id) throws SQLException {
        statement.setString(1, person.getForename());
        statement.setString(2, person.getName());
        if (person.getBirthday() == null)
            statement.setDate(3, null);
        else
            statement.setDate(3, convertDate(person.getBirthday()));
        statement.setString(4, person.getEmail());
        if (person.getAddress() == null)
            statement.setInt(5, -1);
        else
            statement.setInt(5, address_id);
    }

}