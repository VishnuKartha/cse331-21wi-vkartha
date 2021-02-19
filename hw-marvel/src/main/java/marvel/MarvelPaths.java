package marvel;

import graph.*;
import java.util.*;

public  class MarvelPaths {

    //class does not represent an ADT
    /**
     * @spec.requires filename is a valid file in the resources/data folder.
     * @spec.effects constructs a MarvelPaths object from the given fileName
     * @param fileName the file that will be read to create the MarvelPaths objet
     */
    public static void loadGraph(DirectedLabeledGraph dlg,String fileName) {
        // a map mapping comic books to the characters in them
        Map<String, Set<DirectedLabeledGraph.Node>> booksToCharacters = new HashMap<>();
        Iterator<CharacterAppearance> iter= MarvelParser.parseData(fileName);
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next(); // gets the next entry of the file

            DirectedLabeledGraph.Node hero = new DirectedLabeledGraph.Node(currEntry.getHero()); // the new hero to add to the graph

            if(!dlg.containsNode(hero)){ // adds the hero to the graph if not previously added
                dlg.addNode(hero);
            }

            // gets the set of the other characters in the current comic book of hero
            Set<DirectedLabeledGraph.Node> charactersInCurrBook = booksToCharacters.getOrDefault
                                            (currEntry.getBook(),new HashSet<DirectedLabeledGraph.Node>());

            // adds both edges between hero to each character which appears in the current comic book of hero
            for(DirectedLabeledGraph.Node character : charactersInCurrBook) {
                DirectedLabeledGraph.Edge edgeToAdd = new DirectedLabeledGraph.Edge(currEntry.getBook(),character,hero);
                if(!dlg.getOutgoingEdges(character).contains(edgeToAdd)) {
                    dlg.addEdge(edgeToAdd);
                    dlg.addEdge(new DirectedLabeledGraph.Edge(currEntry.getBook(),hero, character));
                }

            }
            charactersInCurrBook.add(hero);
            booksToCharacters.put(currEntry.getBook(),charactersInCurrBook);
        }
    }


    /**
     * Add start to Q
     * Add start->[] to M (start mapped to an empty list)
     * while Q is not empty:
     *     dequeue next node n
     *     if n is dest
     *         return the path associated with n in M
     *     for each edge e=⟨n,m⟩:
     *         if m is not in M, i.e. m has not been visited:
     *             let p be the path n maps to in M
     *             let p' be the path formed by appending e to p
     *             add m->p' to M
     *             add m to Q
     */

    /**
     * @spec.requires dlg != null, nodeA != null, nodeB != null
     * @spec.effects returns the list of edges which represents the shortest path
     *               between nodeA and nodeB in dlg. returns null if there is no path between
     *               nodeA and nodeB in dlg.
     */
    public static List<DirectedLabeledGraph.Edge> findPath(DirectedLabeledGraph dlg, DirectedLabeledGraph.Node nodeA, DirectedLabeledGraph.Node nodeB) {
        Map<DirectedLabeledGraph.Node, List<DirectedLabeledGraph.Edge>> nodesToPaths = new HashMap<>();
        Queue<DirectedLabeledGraph.Node> nodesToVisit = new LinkedList<>();
        nodesToVisit.add(nodeA);
        nodesToPaths.put(nodeA, new ArrayList<>());
        while (!nodesToVisit.isEmpty()) {
            DirectedLabeledGraph.Node curr = nodesToVisit.poll();
            if (curr.equals(nodeB)) {
                return nodesToPaths.get(curr);
            } else {
                List<DirectedLabeledGraph.Edge> edges =  new ArrayList<>(dlg.getOutgoingEdges(curr));
                // sorts the outgoing edges lexicographically first based on the character then based on comic book name
                Collections.sort(edges, new Comparator<>() {
                    public int compare(DirectedLabeledGraph.Edge a, DirectedLabeledGraph.Edge b) {
                        int childComp = a.getChild().getData().compareTo(b.getChild().getData());
                        if(childComp == 0) {
                            return a.getLabel().compareTo(b.getLabel());
                        } else {
                            return childComp;
                        }
                    }
                });
                for (DirectedLabeledGraph.Edge e : edges) {
                    if (!nodesToPaths.keySet().contains(e.getChild()) && e!= null) {
                        List<DirectedLabeledGraph.Edge> pathToCurr = nodesToPaths.get(curr); // p
                        List<DirectedLabeledGraph.Edge> pathToChild = new ArrayList<>(pathToCurr); // p'
                        pathToChild.add(e);
                        nodesToPaths.put(e.getChild(), pathToChild);
                        nodesToVisit.add(e.getChild());
                    }
                }
            }
        }

        return  nodesToPaths.get(nodeB);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Create a Scanner object

        // Read user inputted input tsv file
        System.out.println("Hi welcome to MarvelPaths!");
        System.out.println("Enter TSV file to read from");
        String fileName = input.nextLine();

        // Read a DLG from the user inputted tsv file
        DirectedLabeledGraph dlg = new DirectedLabeledGraph();
        MarvelPaths.loadGraph(dlg, fileName);

        // lets the user find paths between Characters in the graph
        interactiveMarvelPathsLoop(dlg,input);
    }

    private static void interactiveMarvelPathsLoop(DirectedLabeledGraph dlg,Scanner input) {
        boolean loopAgain = true;
        while(loopAgain) {
            System.out.println("Enter the Name of the First Character");
            String node_a = input.nextLine();  // Read user input
            System.out.println("Enter the Name of the Second Character");
            String node_b = input.nextLine();  // Read user input
            System.out.println();
            DirectedLabeledGraph.Node a = new DirectedLabeledGraph.Node(node_a);
            DirectedLabeledGraph.Node b = new DirectedLabeledGraph.Node(node_b);
            if (!dlg.containsNode(a) || !dlg.containsNode(b)) { //checks if the nodes are valid nodes in the dlg
                if (!dlg.containsNode(a)) {
                    System.out.println("unknown character " + node_a);
                }
                if (!dlg.containsNode(b)) {
                    System.out.println("unknown character " + node_b);
                }

            } else {
                System.out.println("path from " + node_a + " to " + node_b + ":");
                List<DirectedLabeledGraph.Edge> path = MarvelPaths.findPath(dlg, a, b);
                if (path == null) {
                    System.out.println("no path found");
                } else {
                    for (DirectedLabeledGraph.Edge e : path) {
                        System.out.println(e.getParent().getData() + " to " +
                                e.getChild().getData() + " via " + e.getLabel());
                    }
                }
            }
            System.out.println("Would You like to look for another path? type yes if so");
            String result = input.nextLine();  // Read user input
            if(!result.trim().equalsIgnoreCase("yes")){
                loopAgain = false;
            } else{
                System.out.println("Closing MarvelPaths");
                System.out.close();
            }
        }

    }


}
