package marvel;

import graph.*;
import java.util.Iterator;

public class MarvelPaths {
    private String fileName; //the name of the file which the MarvelPaths was constructed fom
    private DirectedLabeledGraph dlg; // the graph which is used to represent the connections between the characters

    /**
     * @spec.requires filename is a valid file in the resources/data folder.
     * @spec.effects constructs a MarvelPaths object from the given fileName
     * @param fileName the file that will be read to create the MarvelPaths objet
     */
    public MarvelPaths(String fileName) {
        this.fileName = fileName;
        this.dlg = new DirectedLabeledGraph();
        Iterator<CharacterAppearance> iter= MarvelParser.parseData(fileName);
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next();
            DirectedLabeledGraph.Node hero = new DirectedLabeledGraph.Node(currEntry.getHero());
            if(!dlg.getAllNodes().contains(hero)){ // adds the hero to the graph if not previously added
                dlg.addNode(hero);
            }
            // searches if the hero has any connections to the previous heroes added to the graph
            for(DirectedLabeledGraph.Node n : dlg.getAllNodes()) {
                DirectedLabeledGraph.Edge edgeToAdd = new DirectedLabeledGraph.Edge(currEntry.getBook(),n,hero);
                // dont search for a connection if the edgeToAdd is already added or if the edgeToAdd is the current character
                if(!dlg.getOutgoingEdges(n).contains(edgeToAdd) && !n.equals(hero)) {
                    for (DirectedLabeledGraph.Edge e : dlg.getOutgoingEdges(n)) {
                        if (e.getLabel().equals(currEntry.getBook())) {
                            dlg.addEdge(edgeToAdd);
                            dlg.addEdge(new DirectedLabeledGraph.Edge(currEntry.getBook(),hero, n));
                            break; // we dont have to search for any more connections between the two characters once we found one.
                        }
                    }
                }
            }


        }
    }
}
