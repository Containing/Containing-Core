/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package XML;

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
     * Test of GenerateContainerDatabase method, of class XMLBinder.
     */
    @Test
    public void testGenerateContainerDatabase() throws Exception {
        System.out.println("GenerateContainerDatabase");
        String fileName = "";
        XMLBinder.GenerateContainerDatabase(fileName);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
