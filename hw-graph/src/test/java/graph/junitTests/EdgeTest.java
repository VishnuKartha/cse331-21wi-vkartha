package graph.junitTests;

import graph.DirectedLabeledGraph;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class EdgeTest {
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

    private DirectedLabeledGraph.Edge<String,String> selfEdge = new DirectedLabeledGraph.Edge<>("selfEdge",a,a);
    private DirectedLabeledGraph.Edge<String,String> selfEdgeEmpty = new DirectedLabeledGraph.Edge<>("selfEdge",empty,empty);
    private DirectedLabeledGraph.Edge<String,String> aTob = new DirectedLabeledGraph.Edge<>("aTob",a,b);
    private DirectedLabeledGraph.Edge<String,String> aTob2 = new DirectedLabeledGraph.Edge<>("aTob2",a,b);
    private DirectedLabeledGraph.Edge<String,String> bToa = new DirectedLabeledGraph.Edge<>("bToa",b,a);
    private DirectedLabeledGraph.Edge<String,String> cToA = new DirectedLabeledGraph.Edge<>("cToA",c,A);
    private DirectedLabeledGraph.Edge<String,String> oneToLongLabel = new DirectedLabeledGraph.Edge<>("oneToLongLabel",one,longLabel);


    private DirectedLabeledGraph.Edge<String,String> selfEdgeEqual = new DirectedLabeledGraph.Edge<>("selfEdge",a,a);
    private DirectedLabeledGraph.Edge<String,String> selfEdgeEmptyEqual = new DirectedLabeledGraph.Edge<>("selfEdge",empty,empty);
    private DirectedLabeledGraph.Edge<String,String> aTobEqual = new DirectedLabeledGraph.Edge<>("aTob",a,b);
    private DirectedLabeledGraph.Edge<String,String> aTob2Equal = new DirectedLabeledGraph.Edge<>("aTob2",a,b);
    private DirectedLabeledGraph.Edge<String,String> bToaEqual = new DirectedLabeledGraph.Edge<>("bToa",b,a);
    private DirectedLabeledGraph.Edge<String,String> cToAEqual = new DirectedLabeledGraph.Edge<>("cToA",c,A);
    private DirectedLabeledGraph.Edge<String,String> oneToLongLabelEqual = new DirectedLabeledGraph.Edge<>("oneToLongLabel",one,longLabel);
    private List<DirectedLabeledGraph.Edge<String,String>> edges = new ArrayList<>();
    private List<DirectedLabeledGraph.Edge<String,String>> edgesEqual = new ArrayList<>();
    private List<DirectedLabeledGraph.Node<String>> parents= new ArrayList<>();
    private List<DirectedLabeledGraph.Node<String>> children= new ArrayList<>();

//    private DirectedLabeledGraph.Edge[] edges = new DirectedLabeledGraph.Edge[]
//                                                {selfEdge,selfEdgeEmpty,aTob,aTob2,bToa,cToA,oneToLongLabel};
//    private DirectedLabeledGraph.Edge[] edgesEqual = new DirectedLabeledGraph.Edge[]
//            {selfEdgeEqual,selfEdgeEmptyEqual,aTobEqual,aTob2Equal,bToaEqual,cToAEqual,oneToLongLabelEqual};
    private String[] edgeLabels = new String[]{"selfEdge","selfEdge","aTob","aTob2","bToa","cToA","oneToLongLabel"};
//    private DirectedLabeledGraph.Node[] parents = new DirectedLabeledGraph.Node[]{a,empty,a,a,b,c,one};
//    private DirectedLabeledGraph.Node[] children = new DirectedLabeledGraph.Node[]{a,empty,b,b,a,A,longLabel};


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



    @Before
    public void setUp() {
        edges.add(selfEdge);
        edges.add(selfEdgeEmpty);
        edges.add(aTob);
        edges.add(aTob2);
        edges.add(bToa);
        edges.add(cToA);
        edges.add(oneToLongLabel);
        edgesEqual.add(selfEdgeEqual);
        edgesEqual.add(selfEdgeEmptyEqual);
        edgesEqual.add(aTobEqual);
        edgesEqual.add(aTob2Equal);
        edgesEqual.add(bToaEqual);
        edgesEqual.add(cToAEqual);
        edgesEqual.add(oneToLongLabelEqual);

        parents.add(a);
        parents.add(empty);
        parents.add(a);
        parents.add(a);
        parents.add(b);
        parents.add(c);
        parents.add(one);

        children.add(a);
        children.add(empty);
        children.add(b);
        children.add(b);
        children.add(a);
        children.add(A);
        children.add(longLabel);





        //    private DirectedLabeledGraph.Node[] parents = new DirectedLabeledGraph.Node[]{a,empty,a,a,b,c,one};
//    private DirectedLabeledGraph.Node[] children = new DirectedLabeledGraph.Node[]{a,empty,b,b,a,A,longLabel};


    }

    // First we test the  constructors of DLG Edges in isolation (assumes that the Node constructors work as specifies)
    // (Note, all of these objects were already constructed above as
    // fields of this class (EdgeTest); thus, one could argue that
    // this test case is redundant.

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  Constructor
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testConstructor() {
        new DirectedLabeledGraph.Edge<>("selfEdge",a,a);
        new DirectedLabeledGraph.Edge<>("selfEdge",empty,empty);
        new DirectedLabeledGraph.Edge<>("aTob",a,b);
        new DirectedLabeledGraph.Edge<>("aTob2",a,b);
        new DirectedLabeledGraph.Edge<>("bToa",b,a);
        new DirectedLabeledGraph.Edge<>("cToA",c,A);
        new DirectedLabeledGraph.Edge<>("oneToLongLabel",one,longLabel);
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getLabel
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetLabel() {
        for(int i = 0; i < edges.size(); i++){
            assertEquals("Expected edge label: " + edgeLabels[i] +
                            "Actual edge label: " + edges.get(i).getLabel()
                    , edges.get(i).getLabel()
                    ,edgeLabels[i]);
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getParent
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetParent() {
        for(int i = 0; i < edges.size(); i++){
            assertEquals("Expected Parent: " + parents.get(i).getData() +
                            "Actual Parent: " + edges.get(i).getParent().getData()
                    , edges.get(i).getParent()
                    , parents.get(i));
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  getChild
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testGetChild() {
        for(int i = 0; i < edges.size(); i++){
            assertEquals("Expected child: " + children.get(i).getData() +
                            "Actual child: " + edges.get(i).getChild().getData()
                    , edges.get(i).getChild()
                    , children.get(i));
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testToString() {
        for(int i = 0; i < edges.size();i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + edges.get(i).toString(),
                    strs[i], edges.get(i).toString());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  equals
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testEquals() {
        for(int i = 0; i < edges.size();i++){
            assertEquals(edges.get(i).toString() +"not equal to "+ edgesEqual.get(i).toString() ,
                    edgesEqual.get(i), edges.get(i));
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    ////  hashCode
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testHashCode() {
        for(int i = 0; i < edges.size();i++){
            assertEquals("Expected: " + hashCodes[i] + "Actual: " + edges.get(i).hashCode(),
                    hashCodes[i], edges.get(i).hashCode());
            assertEquals(edges.get(i).hashCode() +"not equal to "+ edgesEqual.get(i).hashCode() ,
                    edgesEqual.get(i), edges.get(i));
        }

    }

}
