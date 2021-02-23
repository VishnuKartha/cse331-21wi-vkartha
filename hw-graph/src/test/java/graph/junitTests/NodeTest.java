package graph.junitTests;

import graph.DirectedLabeledGraph;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class NodeTest {

    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    // creates some simple Nodes
    private DirectedLabeledGraph.Node<String> a = new DirectedLabeledGraph.Node<String>("a");
    private DirectedLabeledGraph.Node<String> A = new DirectedLabeledGraph.Node<String>("A");
    private DirectedLabeledGraph.Node<String> b = new DirectedLabeledGraph.Node<String>("b");
    private DirectedLabeledGraph.Node<String> c = new DirectedLabeledGraph.Node<String>("c");
    private DirectedLabeledGraph.Node<String> one = new DirectedLabeledGraph.Node<String>("1");
    private DirectedLabeledGraph.Node<String> longLabel = new DirectedLabeledGraph.Node<String>("this_is_a_very_long_label_123");
    private DirectedLabeledGraph.Node<String> empty = new DirectedLabeledGraph.Node<String>("");

    private DirectedLabeledGraph.Node<String> aEqual = new DirectedLabeledGraph.Node<String>("a");
    private DirectedLabeledGraph.Node<String> AEqual = new DirectedLabeledGraph.Node<String>("A");
    private DirectedLabeledGraph.Node<String> bEqual = new DirectedLabeledGraph.Node<String>("b");
    private DirectedLabeledGraph.Node<String> cEqual = new DirectedLabeledGraph.Node<String>("c");
    private DirectedLabeledGraph.Node<String> oneEqual = new DirectedLabeledGraph.Node<String>("1");
    private DirectedLabeledGraph.Node<String> longLabelEqual = new DirectedLabeledGraph.Node<String>("this_is_a_very_long_label_123");
    private DirectedLabeledGraph.Node<String> emptyEqual = new DirectedLabeledGraph.Node<String>("");




    private String[] strs = new String[]{"NODE a", "NODE A", "NODE b", "NODE c", "NODE 1",
                                        "NODE this_is_a_very_long_label_123", "NODE "};

    private List<DirectedLabeledGraph.Node<String>> nodes= new ArrayList<>();
    private List<DirectedLabeledGraph.Node<String>> nodesEqual= new ArrayList<>();



    private int[] hashCodes = new int[]{97, 65, 98, 99, 49, -212160805,0};

    private String[] dataOfCreatedNodes = new String[]{"a","A","b","c","1","this_is_a_very_long_label_123",""};


    @Before
    public void setUp() {
        nodes.add(a);
        nodes.add(A);
        nodes.add(b);
        nodes.add(c);
        nodes.add(one);
        nodes.add(longLabel);
        nodes.add(empty);


        nodesEqual.add(aEqual);
        nodesEqual.add(AEqual);
        nodesEqual.add(bEqual);
        nodesEqual.add(cEqual);
        nodesEqual.add(oneEqual);
        nodesEqual.add(longLabelEqual);
        nodesEqual.add(emptyEqual);
    }


    // First we test the  constructors of DLG Nodes in isolation
    // (Note, all of these objects were already constructed above as
    // fields of this class (NodeTest); thus, one could argue that
    // this test case is redundant.

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testConstructor() {
        new DirectedLabeledGraph.Node<>("a");
        new DirectedLabeledGraph.Node<>("A");
        new DirectedLabeledGraph.Node<>("b");
        new DirectedLabeledGraph.Node<>("c");
        new DirectedLabeledGraph.Node<>("1");
        new DirectedLabeledGraph.Node<>("this_is_a_very_long_label_123");
        new DirectedLabeledGraph.Node<>("");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Get Data
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetData() {
        for(int i = 0; i < nodes.size(); i++){
            assertEquals("Expected Node Data: " + dataOfCreatedNodes[i] +
                            "Actual Node Data: " + nodes.get(i).getData()
                           , nodes.get(i).getData()
                           ,dataOfCreatedNodes[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testToString() {
        for(int i = 0; i < nodes.size();i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + nodes.get(i).toString(),
                    strs[i], nodes.get(i).toString());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testEquals() {
        for(int i = 0; i < nodes.size();i++){
            assertEquals(nodes.get(i).toString() +"not equal to "+ nodesEqual.get(i).toString() ,
                    nodesEqual.get(i), nodes.get(i));
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  hashCode
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testHashCode() {
        for(int i = 0; i < nodes.size();i++){
            // checks if the hash code is what  is expected
            assertEquals("Expected: " + hashCodes[i] + "Actual: " + nodes.get(i).hashCode(),
                    hashCodes[i], nodes.get(i).hashCode());
            // checks if an equal object generates the same hash code
            assertEquals(nodes.get(i).hashCode() +"not equal to "+ nodesEqual.get(i).hashCode() ,
                    nodesEqual.get(i), nodes.get(i));
        }

    }







}
