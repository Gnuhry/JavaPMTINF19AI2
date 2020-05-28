package org.dhbw.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Databank {

    public static Object[][] getFromDatabank(String command) {
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
            Object [][] o = new Object[rowCount][count];

            while (resultSet.next())
                for(int i = 1; i <= count; i++)
                    o[resultSet.getRow()-1][i-1] = resultSet.getObject(i);
            resultSet.close();
            statement.close();
            con.close();
            return o;
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static void setToDatabank(String command, Object[] objects, int[] set) {
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

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        return DriverManager.getConnection(
                "jdbc:mysql://85.214.247.101:3306/dhbw","mlg_dhbw","Reisebus1!");
    }
}
