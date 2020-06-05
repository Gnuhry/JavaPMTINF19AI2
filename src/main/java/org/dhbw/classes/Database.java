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

    //----------------------------------------General----------------------------
    public static List<Course> getCourses() {
        updateCourse();
        return courses;
    }

    public static List<Docent> getDocents() {
        updateDocent();
        return docents;
    }

    public static List<Address> getAddresses() {
        updateAddress();
        return addresses;
    }

    public static List<Company> getCompanies() {
        updateCompany();
        return companies;
    }

    public static List<Person> getPersons() {
        updatePerson();
        return persons;
    }

    public static List<DualStudent> getStudents() {
        updateStudent();
        return dualStudents;
    }

    public static int addStudent(DualStudent student) {
        if (student == null) return Integer.MIN_VALUE;
        if (hasID("student", "student_id", student.getStudentNumber())) return student.getStudentNumber();
        int person_id = addPerson(student);
        int course_id = addCourse(student.getCourse());
        int address_id = addCompany(student.getCompany());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO student (student_id, matriculation_number, person_id, java_knowlage, course_id, company_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, student.getStudentNumber());
            statement.setInt(2, student.getMatriculationNumber());
            statement.setInt(3, person_id);
            statement.setInt(4, student.getJavaKnowledge());
            statement.setInt(5, course_id);
            statement.setInt(6, address_id);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

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
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    public static int addCourse(Course course) {
        if (course == null) return Integer.MIN_VALUE;
        int id = getCourseId(course);
        if (id >= 0) return id;
        int docent_id = addDocent(course.getStudyDirector());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO course (room, name, registry_date, course_type, study_director_id, representative_student_id) VALUES (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, getRoomID(course.getRoom()));
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            statement.setInt(5, docent_id);
            statement.setInt(6, -1);
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    public static int addPerson(Person person) {
        if (person == null) return Integer.MIN_VALUE;
        int id = getPersonId(person);
        if (id >= 0) return id;
        int address_id = addAddress(person.getAddress());
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO person (first_name, last_name, birthdate, email, address_id) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
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
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
            else {
                System.out.println("Nope");
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

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
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    public static int addAddress(Address address) {
        if (address == null) return Integer.MIN_VALUE;
        int id = getAddressId(address);
        if (id >= 0) return id;
        try {
            initialize();
            statement = connection.prepareStatement("INSERT INTO address (street, number, postal_code, city, country) VALUES (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getNumber());
            statement.setString(3, address.getPostcode());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getCountry());
            statement.execute();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    public static void deleteStudent(DualStudent student) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM student WHERE student_id = ?");
            statement.setInt(1, student.getStudentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        deletePerson(student); //TODO passiert nichts
    }

    public static void deleteDocent(Docent docent) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM docent WHERE docent_id = ?");
            statement.setInt(1, docent.getDocentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        deletePerson(docent);
    }

    public static void deleteCourse(Course course) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM course WHERE room = ? AND name = ? AND registry_date = ? AND course_type = ? AND study_director_id = ?");
            statement.setInt(1, getRoomID(course.getRoom()));
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            statement.setInt(5, course.getStudyDirector().getDocentNumber());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
    }

    public static void deleteCompany(Company company) {
        int id = getCompanyId(company);
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM company WHERE compamy_id = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        deletePerson(company.getContactPerson());
        deleteAddress(company.getAddress());
        deleteStudentByCompanyId(id);
    }

    public static void deletePerson(Person person) {
        if (!isNotUsedPerson(person))
            return;
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM person WHERE email = ?");
            statement.setString(1, person.getEmail());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        deleteAddress(person.getAddress());
    }

    public static void deleteAddress(Address address) {
        if (!isNotUsedAddress(address))
            return;
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM address WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getNumber());
            statement.setString(3, address.getPostcode());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getCountry());
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
    }

    private static boolean isNotUsedAddress(Address address) {
        try {
            initialize();
            statement = connection.prepareStatement("SELECT company_id, person_id FROM address LEFT JOIN company c on address.address_id = c.address_id LEFT JOIN person p on address.address_id = p.address_id WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getNumber());
            statement.setString(3, address.getPostcode());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getCountry());
            resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt("company_id") == 0 && resultSet.getInt("person_id") == 0){
//                System.out.println(resultSet.getInt("company_id")+", "+resultSet.getInt("person_id"));
                return true;
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        return false;
    }

    private static boolean isNotUsedPerson(Person person) {
        try {
            initialize();
            statement = connection.prepareStatement("SELECT student_id,docent_id,company.company_id FROM person LEFT JOIN student s on person.person_id = s.person_id LEFT JOIN docent d on person.person_id = d.person_id LEFT JOIN company ON contact_person_id=person.person_id WHERE email = ?");
            statement.setString(1, person.getEmail());
            resultSet = statement.executeQuery();
            if (resultSet.next() && resultSet.getInt("company_id") == 0 && resultSet.getInt("student_id") == 0 && resultSet.getInt("company_id") == 0){
//                System.out.println(resultSet.getInt("company_id")+","+resultSet.getInt("student_id")+","+resultSet.getInt("docent_id"));
                return true;
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
        return false;
    }

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
    }

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
                                new Course(resultSet.getString("name"), getCourseTypeById(resultSet.getInt("course_type")), new Docent(resultSet.getString("docent_last_name"), resultSet.getString("docent_first_name"), convertDate(resultSet.getDate("docent_birthdate")), new Address(resultSet.getString("docent_street"), resultSet.getString("docent_number"), resultSet.getString("docent_postal_code"), resultSet.getString("docent_city"), resultSet.getString("docent_country")), resultSet.getString("docent_email"), resultSet.getInt("study_director_id")), convertDate(resultSet.getDate("registry_date")), getRoomById(resultSet.getInt("room"))),
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
            closeConnection();
        }
    }

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
            closeConnection();
        }
    }

    private static void updateCourse() {
        try {
            initialize();
            courses.clear();
            statement = connection.prepareStatement("SELECT * FROM updated_courses");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Course course =
                        new Course(resultSet.getString("name"),
                                getCourseTypeById(resultSet.getInt("course_type")),
                                new Docent(resultSet.getString("last_name"), resultSet.getString("first_name"), convertDate(resultSet.getDate("birthdate")), new Address(resultSet.getString("street"), resultSet.getString("number"), resultSet.getString("postal_code"), resultSet.getString("city"), resultSet.getString("country")), resultSet.getString("email"), resultSet.getInt("docent_id")),
                                convertDate(resultSet.getDate("registry_date")),
                                getRoomById(resultSet.getInt("room")));
//                int id = courses.indexOf(course);
//                if (id >= 0)
//                    courses.set(id, course);
//                else
                courses.add(course);
            }
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } finally {
            closeConnection();
        }
    }

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
            closeConnection();
        }
    }

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
            closeConnection();
        }
    }

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
            closeConnection();
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

    private static int getCourseId(Course course) {
        try {
            initialize();
            statement = connection.prepareStatement("SELECT course_id FROM course WHERE room = ? AND name = ? AND registry_date = ? AND course_type = ? AND study_director_id = ?");
            statement.setInt(1, getRoomID(course.getRoom()));
            statement.setString(2, course.getName());
            statement.setDate(3, convertDate(course.getRegistrationDate()));
            statement.setInt(4, getCourseTypeID(course.getStudyCourse()));
            statement.setInt(5, course.getStudyDirector().getDocentNumber());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    private static int getCompanyId(Company company) {
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
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    private static int getPersonId(Person person) {
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
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }

    private static int getAddressId(Address address) {
        try {
            initialize();
            statement = connection.prepareStatement("SELECT address_id FROM address WHERE street = ? AND number = ? AND postal_code = ? AND city = ? AND country = ?");
            statement.setString(1, address.getStreet());
            statement.setString(2, address.getNumber());
            statement.setString(3, address.getPostcode());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getCountry());
            resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
        return Integer.MIN_VALUE;
    }


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
            closeConnection();
        }
        return false;
    }

    private static void closeConnection() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    private static int getRoomID(CourseRoom room) {
        CourseRoom[] rooms = CourseRoom.values();
        for (int f = 0; f < rooms.length; f++)
            if (rooms[f].equals(room))
                return f;
        return Integer.MIN_VALUE;
    }

    private static int getCourseTypeID(StudyCourse course) {
        StudyCourse[] courses = StudyCourse.values();
        for (int f = 0; f < courses.length; f++)
            if (courses[f].equals(course))
                return f;
        return Integer.MIN_VALUE;
    }

    private static CourseRoom getRoomById(int id) {
        return id < 0 || id >= CourseRoom.values().length ? null : CourseRoom.values()[id];
    }

    private static StudyCourse getCourseTypeById(int id) {
        return id < 0 || id >= StudyCourse.values().length ? null : StudyCourse.values()[id];
    }

    private static Date convertDate(java.sql.Date date) {
        return date == null ? null : new Date(date.getTime());
    }

    private static java.sql.Date convertDate(Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    private static void deleteStudentByCompanyId(int id) {
        try {
            initialize();
            statement = connection.prepareStatement("DELETE FROM student WHERE compamy_id = ?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();

        } finally {
            closeConnection();
        }
    }
}