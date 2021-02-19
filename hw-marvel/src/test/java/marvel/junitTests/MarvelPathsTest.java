package marvel.junitTests;

import graph.DirectedLabeledGraph;
import java.util.Iterator;
import marvel.CharacterAppearance;
import marvel.MarvelParser;
import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertEquals;

public class MarvelPathsTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested
    private DirectedLabeledGraph dlg = new DirectedLabeledGraph();

    // builds two marvelPaths objects and tests the constructor
    private final String expectedStaff = "[{NODE Notkin-of-the-Superhuman-Beard: " +
            "<EDGE CSE331 from NODE Notkin-of-the-Superhuman-Beard to NODE Grossman-the-Youngest-of-them-all>," +
            " <EDGE CSE331 from NODE Notkin-of-the-Superhuman-Beard to NODE Ernst-the-Bicycling-Wizard>," +
            " <EDGE CSE403 from NODE Notkin-of-the-Superhuman-Beard to NODE Ernst-the-Bicycling-Wizard>," +
            " <EDGE CSE331 from NODE Notkin-of-the-Superhuman-Beard to NODE Perkins-the-Magical-Singing-Instructor>}" +
            "{NODE Perkins-the-Magical-Singing-Instructor: " +
            "<EDGE CSE331 from NODE Perkins-the-Magical-Singing-Instructor to NODE Grossman-the-Youngest-of-them-all>," +
            " <EDGE CSE331 from NODE Perkins-the-Magical-Singing-Instructor to NODE Notkin-of-the-Superhuman-Beard>," +
            " <EDGE CSE331 from NODE Perkins-the-Magical-Singing-Instructor to NODE Ernst-the-Bicycling-Wizard>}" +
            "{NODE Grossman-the-Youngest-of-them-all: <EDGE CSE331 from NODE Grossman-the-Youngest-of-them-all to NODE Notkin-of-the-Superhuman-Beard>, " +
            "<EDGE CSE331 from NODE Grossman-the-Youngest-of-them-all to NODE Perkins-the-Magical-Singing-Instructor>," +
            " <EDGE CSE331 from NODE Grossman-the-Youngest-of-them-all to NODE Ernst-the-Bicycling-Wizard>}" +
            "{NODE Ernst-the-Bicycling-Wizard: <EDGE CSE331 from NODE Ernst-the-Bicycling-Wizard to NODE Notkin-of-the-Superhuman-Beard>," +
            " <EDGE CSE403 from NODE Ernst-the-Bicycling-Wizard to NODE Notkin-of-the-Superhuman-Beard>, " +
            "<EDGE CSE331 from NODE Ernst-the-Bicycling-Wizard to NODE Grossman-the-Youngest-of-them-all>, " +
            "<EDGE CSE331 from NODE Ernst-the-Bicycling-Wizard to NODE Perkins-the-Magical-Singing-Instructor>}]";


    ///////////////////////////////////////////////////////////////////////////////////////
    ////  toString
    ///////////////////////////////////////////////////////////////////////////////////////


    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void findPathsTest() {
        MarvelPaths.loadGraph(dlg,"staffSuperheroes.tsv");
//        System.out.println(dlg.toString());
        DirectedLabeledGraph.Node A = new DirectedLabeledGraph.Node("Perkins-the-Magical-Singing-Instructor");
        DirectedLabeledGraph.Node B = new DirectedLabeledGraph.Node("Ernst-the-Bicycling-Wizard");
        for(DirectedLabeledGraph.Edge e :MarvelPaths.findPath(dlg,A,B)){
//            System.out.println(e.toString());
        }
    }


}
