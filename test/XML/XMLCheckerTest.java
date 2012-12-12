/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Gebruiker
 */
public class XMLCheckerTest {
    
    public XMLCheckerTest() {
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
     * Test of GetApprovedContainers method, of class XMLChecker.
     */
    @Test
    public void testGetApprovedContainers() {
        try {
            System.out.println("GetApprovedContainers");
            
            XMLChecker instance = new XMLChecker("C:/one/xml0.xml");
            ArrayList result = instance.GetApprovedContainers();
            assertEquals(2, result.size());
        } catch (Exception ex) {
            Logger.getLogger(XMLCheckerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
