package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // creates some simple Nodes
    private DirectedLabeledGraph.Node a = new DirectedLabeledGraph.Node("a");
    private DirectedLabeledGraph.Node A = new DirectedLabeledGraph.Node("A");
    private DirectedLabeledGraph.Node b = new DirectedLabeledGraph.Node("b");
    private DirectedLabeledGraph.Node c = new DirectedLabeledGraph.Node("c");
    private DirectedLabeledGraph.Node one = new DirectedLabeledGraph.Node("1");
    private DirectedLabeledGraph.Node longLabel = new DirectedLabeledGraph.Node("this_is_a_very_long_label_123");
    private DirectedLabeledGraph.Node empty = new DirectedLabeledGraph.Node("");



    private DirectedLabeledGraph.Node aEqual = new DirectedLabeledGraph.Node("a");
    private DirectedLabeledGraph.Node AEqual = new DirectedLabeledGraph.Node("A");
    private DirectedLabeledGraph.Node bEqual = new DirectedLabeledGraph.Node("b");
    private DirectedLabeledGraph.Node cEqual = new DirectedLabeledGraph.Node("c");
    private DirectedLabeledGraph.Node oneEqual = new DirectedLabeledGraph.Node("1");
    private DirectedLabeledGraph.Node longLabelEqual = new DirectedLabeledGraph.Node("this_is_a_very_long_label_123");
    private DirectedLabeledGraph.Node emptyEqual = new DirectedLabeledGraph.Node("");

    private String[] strs = new String[]{"NODE a", "NODE A", "NODE b", "NODE c", "NODE 1",
                                        "NODE this_is_a_very_long_label_123", "NODE "};

    private DirectedLabeledGraph.Node[] nodes = new DirectedLabeledGraph.Node[]{a,A,b,c,one,longLabel,empty};
    private DirectedLabeledGraph.Node[] nodesEqual = new DirectedLabeledGraph.Node[]{aEqual,AEqual,bEqual,
                                                    cEqual,oneEqual,longLabelEqual,emptyEqual};

    private int[] hashCodes = new int[]{97, 65, 98, 99, 49, -212160805,0};

    private String[] dataOfCreatedNodes = new String[]{"a","A","b","c","1","this_is_a_very_long_label_123",""};


    // First we test the  constructors of DLG Nodes in isolation
    // (Note, all of these objects were already constructed above as
    // fields of this class (NodeTest); thus, one could argue that
    // this test case is redundant.

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testConstructor() {
        new DirectedLabeledGraph.Node("a");
        new DirectedLabeledGraph.Node("A");
        new DirectedLabeledGraph.Node("b");
        new DirectedLabeledGraph.Node("c");
        new DirectedLabeledGraph.Node("1");
        new DirectedLabeledGraph.Node("this_is_a_very_long_label_123");
        new DirectedLabeledGraph.Node("");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Get Data
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetData() {
        for(int i = 0; i < nodes.length; i++){
            assertEquals("Expected Node Data: " + dataOfCreatedNodes[i] +
                            "Actual Node Data: " + nodes[i].getData()
                           ,nodes[i].getData()
                           ,dataOfCreatedNodes[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testToString() {
        for(int i = 0; i < nodes.length;i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + nodes[i].toString(),
                    strs[i],nodes[i].toString());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testEquals() {
        for(int i = 0; i < nodes.length;i++){
            assertEquals(nodes[i].toString() +"not equal to "+ nodesEqual[i].toString() ,
                    nodesEqual[i],nodes[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  hashCode
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testHashCode() {
        for(int i = 0; i < nodes.length;i++){
            // checks if the hash code is what  is expected
            assertEquals("Expected: " + hashCodes[i] + "Actual: " + nodes[i].hashCode(),
                    hashCodes[i],nodes[i].hashCode());
            // checks if an equal object generates the same hash code
            assertEquals(nodes[i].hashCode() +"not equal to "+ nodesEqual[i].hashCode() ,
                    nodesEqual[i],nodes[i]);
        }

    }







}
