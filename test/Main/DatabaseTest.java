/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.sqlite.SQLiteJDBCLoader;

/**
 *
 * @author Christiaan
 */
public class DatabaseTest {
    
    private String testQuery = "SELECT * FROM container";
    private String testQueryInsert = "INSERT INTO container (id,containerNr)VALUES(?,?)";
    
    public DatabaseTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
        // Make sure we work with a fresh in-memory database everytime a test is run
        Database.closeConnection();
    }

    /**
     * Test of getConnection method, of class Database.
     */
    @Test
    public void testGetConnection() {
        System.out.println("getConnection");
        Connection con = Database.getConnection();
        assertNotNull(con);
        Connection con2 = Database.getConnection();
        assertEquals(con, con2);
    }

    /**
     * Test of closeConnection method, of class Database.
     */
    @Test
    public void testCloseConnection() throws Exception {
        System.out.println("closeConnection");
        Connection con = Database.getConnection();
        assertNotNull(con);
        Database.closeConnection();
        assertEquals(true, con.isClosed());
    }

    /**
     * Test of isNativeMode method, of class Database.
     */
    @Test
    public void testIsNativeMode() {
        System.out.println("isNativeMode");
        boolean expResult = true;
        boolean result = Database.isNativeMode();
        assertEquals(expResult, result);
    }

    /**
     * Test of dumpDatabase method, of class Database.
     */
    @Test
    public void testDumpDatabase() {
        System.out.println("dumpDatabase");
        boolean expResult = true;
        boolean result = Database.dumpDatabase();
        assertEquals(expResult, result);
    }

    /**
     * Test of restoreDump method, of class Database.
     */
    @Test
    public void testRestoreDump() {
        System.out.println("restoreDump");
        boolean expResult = true;
        boolean result = Database.restoreDump();
        assertEquals(expResult, result);
    }

    /**
     * Test of createStatement method, of class Database.
     */
    @Test
    public void testCreateStatement() throws Exception {
        System.out.println("createStatement");
        Statement result = Database.createStatement();
        assertNotNull(result);
    }

    /**
     * Test of createPreparedStatement method, of class Database.
     */
    @Test
    public void testCreatePreparedStatement() throws Exception {
        System.out.println("createPreparedStatement");
        String sql = this.testQuery;
        PreparedStatement result = Database.createPreparedStatement(sql);
        assertNotNull(result);
    }

    /**
     * Test of executeQuery method, of class Database.
     */
    @Test
    public void testExecuteQuery_String() throws Exception {
        System.out.println("executeQuery");
        String sql = this.testQuery;
        ResultSet result = Database.executeQuery(sql);
        assertNotNull(result);
    }

    /**
     * Test of executeQuery method, of class Database.
     */
    @Test
    public void testExecuteQuery_PreparedStatement() throws Exception {
        System.out.println("executeQuery");
        PreparedStatement stm = Database.createPreparedStatement(this.testQuery);
        ResultSet result = Database.executeQuery(stm);
        assertNotNull(result);
    }

    /**
     * Test of executeQuery method, of class Database.
     */
    @Test
    public void testExecuteQuery_Statement_String() throws Exception {
        System.out.println("executeQuery");
        Statement stm = Database.createStatement();
        String sql = this.testQuery;
        ResultSet expResult = null;
        ResultSet result = Database.executeQuery(stm, sql);
        assertNotNull(result);
    }

    /**
     * Test of executeUpdate method, of class Database.
     */
    @Test
    public void testExecuteUpdate_PreparedStatement() throws Exception {
        System.out.println("executeUpdate");
        PreparedStatement stm = Database.createPreparedStatement(this.testQueryInsert);
        stm.setString(1, "test2");
        stm.setString(2, "test2");
        int expResult = 1;
        int result = Database.executeUpdate(stm);
        assertEquals(expResult, result);
        
        ResultSet rs = Database.executeQuery("SELECT id FROM container WHERE id='test2'");
        rs.next();
        assertEquals("test2", rs.getString("id"));
    }

    /**
     * Test of executeUpdate method, of class Database.
     */
    @Test
    public void testExecuteUpdate_String() throws Exception {
        System.out.println("executeUpdate");
        String sql = "INSERT INTO container (id,containerNr)VALUES('test1','test1')";
        int expResult = 1;
        int result = Database.executeUpdate(sql);
        assertEquals(expResult, result);
        
        ResultSet rs = Database.executeQuery("SELECT id FROM container WHERE id='test1'");
        rs.next();
        assertEquals("test1", rs.getString("id"));
    }

    /**
     * Test of executeUpdate method, of class Database.
     */
    @Test
    public void testExecuteUpdate_Statement_String() throws Exception {
        System.out.println("executeUpdate");
        Statement stm = Database.createStatement();
        String sql = "INSERT INTO container (id,containerNr)VALUES('test1','test1')";
        int expResult = 1;
        int result = Database.executeUpdate(stm, sql);
        assertEquals(expResult, result);
        
        ResultSet rs = Database.executeQuery("SELECT id FROM container WHERE id='test1'");
        rs.next();
        assertEquals("test1", rs.getString("id"));
    }
}
