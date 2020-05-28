package org.dhbw.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Databank {

    public static Object[][] GetFromDatabank(String command) {
        try {
            Connection con = getConnection();
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(command);

            int columnsNumber = resultSet.getMetaData().getColumnCount();
            List<Object[]> o = new ArrayList<>();
            Object[] oo = new Object[columnsNumber];
            while (resultSet.next()) {
                for (int i = 1; i < columnsNumber; i++)
                    oo[i-1]=resultSet.getObject(i);
                o.add(oo);
            }
            resultSet.close();
            statement.close();
            con.close();

            Object[][]erg=new Object[o.size()][o.get(0).length];
            for(int f=0; f<erg.length;f++){
                for(int g=0; g<erg[f].length;g++){
                    erg[f][g]= o.get(f)[g];
                }
            }
            return erg;
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }


    public static void SetToDatabank(String command, Object[] objects, int[] set) {
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
