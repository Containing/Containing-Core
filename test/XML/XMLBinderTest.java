/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

import Main.Container;
import java.util.ArrayList;
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
public class XMLBinderTest {
    
    public XMLBinderTest() {
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
     * Test of GetContainerList method, of class XMLBinder.
     */
    @Test
    public void testGetContainerList() throws Exception {
        System.out.println("GetContainerList");
        
        String fileName1 = "C:/one/a.png";
        ArrayList expResult1 = null;
        ArrayList result1 = XMLBinder.GetContainerList(fileName1);
        assertEquals(expResult1, result1);
        
        String fileName2 = "C:/one/xml0.xml";
        ArrayList<Container> result2 = XMLBinder.GetContainerList(fileName2);
        assertEquals(2, result2.size());
        assertEquals("id0", result2.get(0).getId());
        assertEquals("id1", result2.get(1).getId());
    }
}
