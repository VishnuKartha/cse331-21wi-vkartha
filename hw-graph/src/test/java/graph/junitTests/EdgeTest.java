package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class EdgeTest {
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

    private DirectedLabeledGraph.Edge selfEdge = new DirectedLabeledGraph.Edge("selfEdge",a,a);
    private DirectedLabeledGraph.Edge selfEdgeEmpty = new DirectedLabeledGraph.Edge("selfEdge",empty,empty);
    private DirectedLabeledGraph.Edge aTob = new DirectedLabeledGraph.Edge("aTob",a,b);
    private DirectedLabeledGraph.Edge aTob2 = new DirectedLabeledGraph.Edge("aTob2",a,b);
    private DirectedLabeledGraph.Edge bToa = new DirectedLabeledGraph.Edge("bToa",b,a);
    private DirectedLabeledGraph.Edge cToA = new DirectedLabeledGraph.Edge("cToA",c,A);
    private DirectedLabeledGraph.Edge oneToLongLabel = new DirectedLabeledGraph.Edge("oneToLongLabel",one,longLabel);


    private DirectedLabeledGraph.Edge selfEdgeEqual = new DirectedLabeledGraph.Edge("selfEdge",a,a);
    private DirectedLabeledGraph.Edge selfEdgeEmptyEqual = new DirectedLabeledGraph.Edge("selfEdge",empty,empty);
    private DirectedLabeledGraph.Edge aTobEqual = new DirectedLabeledGraph.Edge("aTob",a,b);
    private DirectedLabeledGraph.Edge aTob2Equal = new DirectedLabeledGraph.Edge("aTob2",a,b);
    private DirectedLabeledGraph.Edge bToaEqual = new DirectedLabeledGraph.Edge("bToa",b,a);
    private DirectedLabeledGraph.Edge cToAEqual = new DirectedLabeledGraph.Edge("cToA",c,A);
    private DirectedLabeledGraph.Edge oneToLongLabelEqual = new DirectedLabeledGraph.Edge("oneToLongLabel",one,longLabel);
    private DirectedLabeledGraph.Edge[] edges = new DirectedLabeledGraph.Edge[]
                                                {selfEdge,selfEdgeEmpty,aTob,aTob2,bToa,cToA,oneToLongLabel};
    private DirectedLabeledGraph.Edge[] edgesEqual = new DirectedLabeledGraph.Edge[]
            {selfEdgeEqual,selfEdgeEmptyEqual,aTobEqual,aTob2Equal,bToaEqual,cToAEqual,oneToLongLabelEqual};
    private String[] edgeLabels = new String[]{"selfEdge","selfEdge","aTob","aTob2","bToa","cToA","oneToLongLabel"};
    private DirectedLabeledGraph.Node[] parents = new DirectedLabeledGraph.Node[]{a,empty,a,a,b,c,one};
    private DirectedLabeledGraph.Node[] children = new DirectedLabeledGraph.Node[]{a,empty,b,b,a,A,longLabel};


    private String[] strs = new String[]{"EDGE selfEdge from NODE a to NODE a",
                                        "EDGE selfEdge from NODE  to NODE ",
                                        "EDGE aTob from NODE a to NODE b",
                                        "EDGE aTob2 from NODE a to NODE b",
                                        "EDGE bToa from NODE b to NODE a",
                                        "EDGE cToA from NODE c to NODE A",
                                        "EDGE oneToLongLabel from NODE 1 to" +
                                                " NODE this_is_a_very_long_label_123"
                                                                                 };
    private int[] hashCodes = new int[]{-248820043,-248828385,20826315,645364565
                                        ,21034845,21241825,-229596571};

    // First we test the  constructors of DLG Edges in isolation (assumes that the Node constructors work as specifies)
    // (Note, all of these objects were already constructed above as
    // fields of this class (EdgeTest); thus, one could argue that
    // this test case is redundant.

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testConstructor() {
        new DirectedLabeledGraph.Edge("selfEdge",a,a);
        new DirectedLabeledGraph.Edge("selfEdge",empty,empty);
        new DirectedLabeledGraph.Edge("aTob",a,b);
        new DirectedLabeledGraph.Edge("aTob2",a,b);
        new DirectedLabeledGraph.Edge("bToa",b,a);
        new DirectedLabeledGraph.Edge("cToA",c,A);
        new DirectedLabeledGraph.Edge("oneToLongLabel",one,longLabel);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getLabel
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetLabel() {
        for(int i = 0; i < edges.length; i++){
            assertEquals("Expected edge label: " + edgeLabels[i] +
                            "Actual edge label: " + edges[i].getLabel()
                    ,edges[i].getLabel()
                    ,edgeLabels[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getParent
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetParent() {
        for(int i = 0; i < edges.length; i++){
            assertEquals("Expected Parent: " + parents[i].getData() +
                            "Actual Parent: " + edges[i].getParent().getData()
                    ,edges[i].getParent()
                    ,parents[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getChild
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetChild() {
        for(int i = 0; i < edges.length; i++){
            assertEquals("Expected child: " + children[i].getData() +
                            "Actual child: " + edges[i].getChild().getData()
                    ,edges[i].getChild()
                    ,children[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testToString() {
        for(int i = 0; i < edges.length;i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + edges[i].toString(),
                    strs[i],edges[i].toString());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testEquals() {
        for(int i = 0; i < edges.length;i++){
            assertEquals(edges[i].toString() +"not equal to "+ edgesEqual[i].toString() ,
                    edgesEqual[i],edges[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  hashCode
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testHashCode() {
        for(int i = 0; i < edges.length;i++){
            assertEquals("Expected: " + hashCodes[i] + "Actual: " + edges[i].hashCode(),
                    hashCodes[i],edges[i].hashCode());
            assertEquals(edges[i].hashCode() +"not equal to "+ edgesEqual[i].hashCode() ,
                    edgesEqual[i],edges[i]);
        }

    }

}
