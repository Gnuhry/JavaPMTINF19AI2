package org.dhbw.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Databank {
    private static final String databankPath = "";
    private static Driver driver = null;

    public static Object[] GetFromDatabank(String command, Class get_c, int column) {
        try {
            DriverManager.registerDriver(driver);
            Connection con = DriverManager.getConnection(databankPath);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(command);
            List o = new ArrayList();
            while (resultSet.next())
                o.add(resultSet.getObject(column, get_c));
            resultSet.close();
            statement.close();
            con.close();
            return o.toArray();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static void SetToDatabank(String command, Object[] objects, SQLType[] set) {
        if (objects == null) objects = new Object[0];
        if (set == null) set = new SQLType[0];
        try {
            DriverManager.registerDriver(driver);
            Connection con = DriverManager.getConnection(databankPath);
            PreparedStatement preparedStatement = con.prepareStatement(command);
            for (int f = 0; f < objects.length && f < set.length; f++)
                preparedStatement.setObject(f + 1, objects[f], set[f]);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            con.close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}
