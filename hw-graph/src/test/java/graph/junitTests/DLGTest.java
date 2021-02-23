package graph.junitTests;

import graph.DirectedLabeledGraph;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class DLGTest {
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

    private List<DirectedLabeledGraph.Edge<String,String>> edges = new ArrayList<>();
    private List<DirectedLabeledGraph.Node<String>> allNodes= new ArrayList<>();


    private DirectedLabeledGraph<String,String> emptyGraph;
    private DirectedLabeledGraph<String,String> singleNodeGraph;
    private DirectedLabeledGraph<String,String> singleEdgeGraph;
    private DirectedLabeledGraph<String,String> noEdges;
    private DirectedLabeledGraph<String,String> fullGraph;
    private List<DirectedLabeledGraph<String,String>> graphs = new ArrayList<>();
    private String[] strs;

    @Before
    public void setUp() {
        edges.add(selfEdge);
        edges.add(selfEdgeEmpty);
        edges.add(aTob);
        edges.add(aTob2);
        edges.add(bToa);
        edges.add(cToA);
        edges.add(oneToLongLabel);

        allNodes.add(a);
        allNodes.add(A);
        allNodes.add(b);
        allNodes.add(c);
        allNodes.add(one);
        allNodes.add(longLabel);
        allNodes.add(empty);





        emptyGraph = new DirectedLabeledGraph<>();
         singleNodeGraph = new DirectedLabeledGraph<>();
         singleNodeGraph.addNode(A);
        singleEdgeGraph = new DirectedLabeledGraph<>();
        singleEdgeGraph.addEdge(aTob);
        noEdges = new DirectedLabeledGraph<>();
        for(DirectedLabeledGraph.Node<String> n:allNodes){
            noEdges.addNode(n);
        }
        fullGraph = new DirectedLabeledGraph<>();
        for(DirectedLabeledGraph.Node<String> n:allNodes){
            fullGraph.addNode(n);
        }
        for(DirectedLabeledGraph.Edge<String,String> e:edges){
            fullGraph.addEdge(e);
        }

        graphs.add(emptyGraph);
        graphs.add(singleNodeGraph);
        graphs.add(singleEdgeGraph);
        graphs.add(noEdges);
        graphs.add(fullGraph);
        strs = new String[]{"[]","[{NODE A: }]",
                "[{NODE a: <EDGE aTob from NODE a to NODE b>}{NODE b: }]",
                "[{NODE : }{NODE a: }{NODE A: }{NODE 1: }{NODE this_is_a_very_long_label_123: }{NODE b: }{NODE c: }]",
                "[{NODE : <EDGE selfEdge from NODE  to NODE >}{NODE a: <EDGE aTob2 from NODE a to NODE b>," +
                        " <EDGE aTob from NODE a to NODE b>, <EDGE selfEdge from NODE a to NODE a>}{NODE A: }" +
                        "{NODE 1: <EDGE oneToLongLabel from NODE 1 to NODE this_is_a_very_long_label_123>}" +
                        "{NODE this_is_a_very_long_label_123: }{NODE b: <EDGE bToa from NODE b to NODE a>}" +
                        "{NODE c: <EDGE cToA from NODE c to NODE A>}]"};
    }



    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void testToString() {
        for(int i = 0; i < graphs.size();i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + graphs.get(i).toString(),
                    strs[i],graphs.get(i).toString());
        }

    }





}

