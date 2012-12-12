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
        
        // niet een xml bestand
        assertEquals(XMLBinder.GetContainerList("C:/one/a.png"), null);

        // geen bestand
        assertEquals(XMLBinder.GetContainerList(""), null);
        
        // een xml bestand met fouten
        //assertEquals(XMLBinder.GetContainerList("C:/one/xml0.xml"), null);
        
	// een goed xml bestand
	assertEquals(XMLBinder.GetContainerList("C:/one/xml1.xml").size(), 10);
	assertEquals(XMLBinder.GetContainerList("C:/one/xml1.xml").get(0).getId(), "id0");
        assertEquals(XMLBinder.GetContainerList("C:/one/xml1.xml").get(1).getId(), "id1");

    }
}
