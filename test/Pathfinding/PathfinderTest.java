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
public class PathfinderTest {
    
    public PathfinderTest() {
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
     * Test of generateGrid method, of class Pathfinder.
     */
    @Test
    public void testGenerateGrid() {
        System.out.println("generateGrid");
        Pathfinder.generateGrid();
        // TODO review the generated test code and remove the default call to fail.
  
        
    }

    /**
     * Test of generatePaths method, of class Pathfinder.
     */
    @Test
    public void testGeneratePaths() {
        System.out.println("generatePaths");
        Pathfinder.generatePaths();
        // TODO review the generated test code and remove the default call to fail.

        
    }

    /**
     * Test of findShortest method, of class Pathfinder.
     */
    @Test
    public void testFindShortest_Node_Node() {
        System.out.println("findShortest");
        Pathfinder.generateGrid();
        
        Node startNode = Pathfinder.Nodes[0];
        Node endNode = Pathfinder.Nodes[5];
        Node[] expResult = new Node[] {
            Pathfinder.Nodes[0],
            Pathfinder.Nodes[1],
            Pathfinder.Nodes[2],
            Pathfinder.Nodes[3],
            Pathfinder.Nodes[4],
            Pathfinder.Nodes[5]
        };
        Node[] result = Pathfinder.findShortest(startNode, endNode);
        assertEquals(expResult, result);
    }

    /**
     * Test of findShortest method, of class Pathfinder.
     */
    @Test
    public void testFindShortest_3args() {
        Pathfinder.generateGrid();
        testBlockCargo();
        Pathfinder.generateGrid();  //Clean grid
        testOneWay();
        testBlockedPath();
    }
    
    private void testBlockedPath(){
        for(int i = 0 ; i < Pathfinder.Paths.length ; i++)Pathfinder.Paths[i].FilterCargo=2;    //Block full cargo on all paths
        Node startNode = Pathfinder.Nodes[0];
        Node endNode = Pathfinder.Nodes[5];
        
        boolean cargoFilled = true;
        Node[] expResult = null;
        Node[] result = Pathfinder.findShortest(startNode, endNode, cargoFilled);
        assertEquals(expResult, result);
    }
    
    private void testBlockCargo(){
        System.out.println("findShortest");
        Pathfinder.generateGrid();
        Pathfinder.Paths[2].FilterCargo=2;  //Block loaded cargo
        
        Node startNode = Pathfinder.Nodes[0];
        Node endNode = Pathfinder.Nodes[5];
       // Pathfinder.
        boolean cargoFilled = true;
        Node[] expResult = new Node[] {
            Pathfinder.Nodes[0],
            Pathfinder.Nodes[1],
            Pathfinder.Nodes[2],        
            Pathfinder.Nodes[13],       //Path closed, reroute
            Pathfinder.Nodes[14],
            Pathfinder.Nodes[5]
        };
        Node[] result = Pathfinder.findShortest(startNode, endNode, cargoFilled);
        assertEquals(expResult, result);
    }
    
    private void testOneWay(){
        
        //Test one way traffic
        Pathfinder.Paths[2].OneWay = true;
        boolean cargoFilled = false;
        
        Node startNode = Pathfinder.Nodes[0];
        Node endNode = Pathfinder.Nodes[5];
        
        Node[] expResult = new Node[] {
                    Pathfinder.Nodes[0],
                    Pathfinder.Nodes[1],
                    Pathfinder.Nodes[2],        
                    Pathfinder.Nodes[3],      
                    Pathfinder.Nodes[4],
                    Pathfinder.Nodes[5]
                };
        Node[] result = Pathfinder.findShortest(startNode, endNode, cargoFilled);
        assertEquals(expResult, result);
        
        startNode = Pathfinder.Nodes[5];    //Reverse direction
        endNode = Pathfinder.Nodes[0];
     
        expResult = new Node[] {
                    Pathfinder.Nodes[5],
                    Pathfinder.Nodes[4],
                    Pathfinder.Nodes[3],        
                    Pathfinder.Nodes[12],      //One way, road blocked
                    Pathfinder.Nodes[11],
                    Pathfinder.Nodes[0]
                };
        
        result = Pathfinder.findShortest(startNode, endNode, cargoFilled);
        assertEquals(expResult, result);
    }
}
