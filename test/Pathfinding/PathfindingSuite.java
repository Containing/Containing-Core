/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Pathfinding;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @author EightOneGulf
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({Pathfinding.NodeScoreTest.class, Pathfinding.PathTest.class, Pathfinding.PathfinderTest.class, Pathfinding.NodeTest.class})
public class PathfindingSuite {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
}
