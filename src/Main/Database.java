/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite::memory:");

                InputStream inputStream = Database.class.getResourceAsStream("schema.sql");
                
                InputStreamReader is = new InputStreamReader(inputStream);
                StringBuilder sb = new StringBuilder();
                BufferedReader br = new BufferedReader(is);
                String read = br.readLine();

                while(read != null) {
                    sb.append(read);
                    read =br.readLine();
                }

                executeUpdate(sb.toString());
            }
            catch (Exception e) {  
                e.printStackTrace();  
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
                connection = null;
            }
            catch (Exception e) {  
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
    
    /**
     * Dump in-memory database to file
     * @return boolean true if succeeded
     */
    public static boolean dumpDatabase() {
        return dumpDatabase("backup");
    }
    /**
     * Dump in-memory database to file
     * @param fileName the Db name
     * @return boolean true if succeeded
     */
    public static boolean dumpDatabase(String fileName) {
        try {
            executeUpdate(createStatement(), "backup to db/" + fileName + ".db");
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @return boolean true if succeeded
     */
    public static boolean restoreDump() {
        try {
            executeUpdate(createStatement(), "restore from db/backup.db");
        }
        catch(Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Create a Statement
     * @return Statement
     */
    public static Statement createStatement() throws Exception {
        return getConnection().createStatement();
    }
    
    /**
     * Create a PreparedStatement
     * @param sql
     * @return PreparedStatement
     * @throws Exception 
     */
    public static PreparedStatement createPreparedStatement(String sql) throws Exception {
        return getConnection().prepareStatement(sql);
    }
    
    public static ResultSet executeQuery(String sql) throws Exception {
        Statement stm = createStatement();
        return executeQuery(stm, sql);
    }
    
    /**
     * Execute prepared query
     * @param stm
     * @return 
     * @throws Exception 
     */
    public static ResultSet executeQuery(PreparedStatement stm) throws Exception {
        return executeQuery(stm, null);
    }
    
    /**
     * Execute query
     * @param stm
     * @param sql Query to execute
     * @return ResultSet
     * @throws Exception 
     */
    public static ResultSet executeQuery(Statement stm, String sql) throws Exception {
        ResultSet rs = null;
        if(stm instanceof PreparedStatement)
            rs = ((PreparedStatement)stm).executeQuery();
        else if(stm instanceof Statement)
            rs = stm.executeQuery(sql);
        else
            throw new Exception("Unsupported Statement interface");
        return rs;
    }
    
    /**
     * Execute update for prepared query
     * @param stm
     * @return number of modified rows
     * @throws Exception 
     */
    public static int executeUpdate(PreparedStatement stm) throws Exception {
        return executeUpdate(stm, null);
    }
    
    /**
     * Execute update query
     * @param sql
     * @return number of modified rows
     * @throws Exception 
     */
    public static int executeUpdate(String sql) throws Exception {
        return executeUpdate(createStatement(), sql);
    }
    
    /**
     * Execute update query
     * @param stm
     * @param sql
     * @return number of modified rows
     * @throws Exception 
     */
    public static int executeUpdate(Statement stm, String sql) throws Exception {
        int modifiedRows = 0;
        if(stm instanceof PreparedStatement)
            modifiedRows = ((PreparedStatement)stm).executeUpdate();
        else if(stm instanceof Statement)
            modifiedRows = stm.executeUpdate(sql);
        else
            throw new Exception("Unsupported Statement interface");
        return modifiedRows;
    }
}
