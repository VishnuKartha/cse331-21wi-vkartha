package graph.junitTests;

import graph.DirectedLabeledGraph;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class DLGTest {
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

    private DirectedLabeledGraph.Edge[] edges = new DirectedLabeledGraph.Edge[]
            {selfEdge,selfEdgeEmpty,aTob,aTob2,bToa,cToA,oneToLongLabel};
    private final  DirectedLabeledGraph.Node[] allNodes = new DirectedLabeledGraph.Node[]{a,A,b,c,one,longLabel,empty};
    private DirectedLabeledGraph emptyGraph;
    private DirectedLabeledGraph singleNodeGraph;
    private DirectedLabeledGraph singleEdgeGraph;
    private DirectedLabeledGraph noEdges;
    private DirectedLabeledGraph fullGraph;
    private DirectedLabeledGraph[] graphs;
    private String[] strs;

    @Before
    public void setUp() {
         emptyGraph = new DirectedLabeledGraph();
         singleNodeGraph = new DirectedLabeledGraph();
         singleNodeGraph.addNode(A);
        singleEdgeGraph = new DirectedLabeledGraph();
        singleEdgeGraph.addEdge(aTob);
        noEdges = new DirectedLabeledGraph();
        for(DirectedLabeledGraph.Node n:allNodes){
            noEdges.addNode(n);
        }
        fullGraph = new DirectedLabeledGraph();
        for(DirectedLabeledGraph.Node n:allNodes){
            fullGraph.addNode(n);
        }
        for(DirectedLabeledGraph.Edge e:edges){
            fullGraph.addEdge(e);
        }

        graphs = new DirectedLabeledGraph[]{emptyGraph,singleNodeGraph,singleEdgeGraph,noEdges,fullGraph};
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
        for(int i = 0; i < graphs.length;i++){
            assertEquals("Expected: " + strs[i] + "Actual: " + graphs[i].toString(),
                    strs[i],graphs[i].toString());
        }

    }





}

