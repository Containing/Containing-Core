/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import java.sql.Connection;
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
}
