/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helpers;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author EightOneGulf
 */
public class Vector2Test {
    
    public Vector2Test() {
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
     * Test of distance method, of class Vector2.
     */
    @Test
    public void testDistance_Vector2() {
        System.out.println("distance");
        Vector2 o = new Vector2(5,5);
        Vector2 instance = new Vector2(-5,-5);
        float expResult = (float) Math.sqrt( 10*10 + 10*10 );
        float result = instance.distance(o);
        assertEquals(expResult, result, 0.0);
    }

    /**
     * Test of distance method, of class Vector2.
     */
    @Test
    public void testDistance_Vector2_Vector2() {
        System.out.println("distance");
        Vector2 v1 = new Vector2(5,5);
        Vector2 v2 = new Vector2(-5,-5);
        float expResult = (float) Math.sqrt( 10*10 + 10*10 );
        float result = Vector2.distance(v1, v2);
        assertEquals(expResult, result, 0.0);
    }
}
