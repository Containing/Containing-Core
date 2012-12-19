/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import org.sqlite.SQLiteJDBCLoader;
/**
 *
 * @author Christiaan
 */
public class Database {
    
    /**
     * in-memory database connection
     */
    private static Connection connection = null;
    
    /**
     * Get connection to in-memory database
     * @return Connection
     */
    public static Connection getConnection() {
        if(connection == null) {
            Statement statement = null;
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite::memory:");
                
                statement = connection.createStatement();
         
                BufferedReader input = new BufferedReader(new FileReader("db/schema.sql"));
                String contents;
                String sql = "";
                while((contents = input.readLine()) != null) {
                    sql += contents;
                }
                input.close();
         
                statement.executeUpdate(sql);
            }
            catch (Exception e) 
            {  
                e.printStackTrace();  
            }
            finally {
                try {
                    statement.close();
                }
                catch (Exception e) 
                {  
                    e.printStackTrace();  
                }
            }
        }
        return connection;
    }

    /**
     * Close in-memory database connection
     */
    public static void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            }
            catch (Exception e) 
            {  
                e.printStackTrace();  
            }
        }
    }
    
    /**
     * Check if SQLite database is running in native mode or pure-java
     * @return boolean true if running in native mode, false if running pure-java
     */
    public static boolean isNativeMode() {
        return SQLiteJDBCLoader.isNativeMode();
    }
}
