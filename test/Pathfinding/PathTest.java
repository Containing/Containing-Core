/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author EightOneGulf
 */
public class PathTest {
    
    public PathTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getPointA method, of class Path.
     */
    @Test
    public void testGetPointA() {
        System.out.println("getPointA");
    }

    /**
     * Test of getPointB method, of class Path.
     */
    @Test
    public void testGetPointB() {
        System.out.println("getPointB");
    }

    /**
     * Test of getLength method, of class Path.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        
        Path instance = new Path( new Node(-5f,-5f), new Node(5f,5f) );
        float expResult = (float)Math.sqrt(10*10 + 10*10);  //Pythagoras
        float result = instance.getLength();
        
        assertEquals(expResult, result, 0.0);
    }
}
