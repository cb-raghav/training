package dao;

import java.io.*;
import java.util.*;
import java.sql.*;
import javax.servlet.RequestDispatcher;

public class DaoImp {
    // JDBC driver name and database URL
    final static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    final static  String DB_URL = "jdbc:mysql://localhost:3306/self_service";
    // Database credentials
    final static String USER = "root";
    final static String PASS = "";
    
    public static List< HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();
        int columns = md.getColumnCount();
        List<HashMap<String, Object>> list = new ArrayList<>();

        while (rs.next()) {
            HashMap<String, Object> row = new HashMap<>(columns);
            for (int i = 1; i <= columns; ++i) {
                row.put(md.getColumnName(i), rs.getObject(i));
            }
            list.add(row);
        }

        return list;
    }

    public static List< HashMap<String, Object>> select(String query, String[] parameters) {
        List<HashMap<String, Object>> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
            Class.forName(JDBC_DRIVER); // register JDBC driver
            conn = DriverManager.getConnection(DB_URL, USER, PASS); // open a connection

            //String query = "SELECT * FROM Users WHERE email = ?";
            preparedStatement = conn.prepareStatement(query);
            for (int i = 0; i < parameters.length; i++) { // set parameters for the SELECT query
                preparedStatement.setString((i + 1), parameters[i]);
            }
            
            //System.out.println("The query being executed is: " + preparedStatement.toString());
            rs = preparedStatement.executeQuery(); // execute the prepared statement
            result = convertResultSetToList(rs);
        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    //
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return result;
    }
    
    public static String update(String query, String[] parameters) {
        String result = "";
        Connection conn = null;
        PreparedStatement preparedStatement = null;
        boolean matchFound = false;
        try {
            Class.forName("com.mysql.jdbc.Driver"); // register JDBC driver
            conn = DriverManager.getConnection(DB_URL, USER, PASS); // open a connection
            preparedStatement = conn.prepareStatement(query); 
            
            for (int i = 0; i < parameters.length; i++) { // set parameters for the SELECT query
                preparedStatement.setString((i + 1), parameters[i]);
            }
            
            //System.out.println("The query being executed is: " + preparedStatement.toString());
            preparedStatement.executeUpdate(); // execute the prepared statement
            result = "SUCCESS!";
        } catch (SQLException se) { // Handle errors for JDBC
            //se.printStackTrace();
            result = se.toString();
        } catch (Exception e) { // Handle errors for Class.forName
            //e.printStackTrace();
        } finally {
            //finally block used to close resources
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    //ex.printStackTrace();
                }
            }
        }
        return result;
    }
}
