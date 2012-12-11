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
public class NodeScoreTest {
    
    public NodeScoreTest() {
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
     * Test of compareTo method, of class NodeScore.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        NodeScore o = new NodeScore(null, 0, null);
        NodeScore instance = new NodeScore(null, 1, null);

        int expResult = 1;  //Instance is higher as o
        int result = instance.compareTo(o);
        assertEquals(expResult, result);

        o.score = 1;
        expResult = 0;  //Instance is equal as o
        result = instance.compareTo(o);
        assertEquals(expResult, result);

        o.score = 2;
        expResult = -1;  //Instance is lower as o
        result = instance.compareTo(o);
        assertEquals(expResult, result);
    }
}
