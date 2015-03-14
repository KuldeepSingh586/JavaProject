/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databaseCredentials;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Kuldeep
 */
public class database {
     /**
     * Provides a Connection to the Xampp "c0648442" DataBase
     * Created connection in getConnection Method
     * Created product Table in dataBase
     * @return - the connection object or null if a connection failed
     * @throws java.sql.SQLException
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
        System.err.print("Driver not found"+ex.getMessage());    
        }
        String hostname = System.getenv("OPENSHIFT_MYSQL_DB_HOST");
        String port = System.getenv("OPENSHIFT_MYSQL_DB_PORT");
        String username = System.getenv("OPENSHIFT_MYSQL_DB_USERNAME");
        String password = System.getenv("OPENSHIFT_MYSQL_DB_PASSWORD");

        String jdbc = "jdbc:mysql://" + hostname + ":" + port + "/librarymgmtsys";
        String query = "SELECT username FROM login";
     
        return DriverManager.getConnection(jdbc, username, password);
        
      
    }
    
    
}
