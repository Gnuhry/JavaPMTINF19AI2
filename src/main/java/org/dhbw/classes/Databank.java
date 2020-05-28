package org.dhbw.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Databank {

    public static Object[] GetFromDatabank(String command, int column) {
        try {
            Connection con = getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            List o = new ArrayList();
            while (resultSet.next())
                o.add(resultSet.getObject(column));
            resultSet.close();
            statement.close();
            con.close();
            return o.toArray();
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static void SetToDatabank(String command, Object[] objects, SQLType[] set) {
        if (objects == null) objects = new Object[0];
        if (set == null) set = new SQLType[0];
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

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://85.214.247.101:3306/dhbw","mlg_dhbw","Reisebus1!");
    }
}
