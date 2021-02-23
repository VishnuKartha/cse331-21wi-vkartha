package marvel;

import graph.*;
import java.util.*;

/**
 * Allows for a DLG to be loaded from a tsv file and allows a user to find the shortest path between nodes of the DLG.
 */
public  class MarvelPaths {

    // class does not represent an ADT
    /**
     * Constructs a new DLG containing the paths represented by the tsv file with the name fileName
     * @spec.requires filename is a valid tsv file in the resources/data folder.
     * @param fileName the tsv file that will be read to create the MarvelPaths object
     * @spec.effects constructs a DLG loaded from the tsv file in fileName
     * @return the DLG which is loaded from fileName
     */
    public static DirectedLabeledGraph<String,String> loadGraph(String fileName) {
        DirectedLabeledGraph<String,String> dlg = new DirectedLabeledGraph<>();
        // a map mapping comic books to the characters in them
        Map<String, Set<DirectedLabeledGraph.Node<String>>> booksToCharacters = new HashMap<>();
        Iterator<CharacterAppearance> iter= MarvelParser.parseData(fileName);
        while(iter.hasNext()) {
            CharacterAppearance currEntry = iter.next(); // gets the next entry of the file

            // add the hero of the current entry to the graph if not previously in the graph
            DirectedLabeledGraph.Node<String> hero = new DirectedLabeledGraph.Node<>(currEntry.getHero());
            if(!dlg.containsNode(hero)){
                dlg.addNode(hero);
            }

            // gets the set of the other characters that appear in the comic book of the current entry
            Set<DirectedLabeledGraph.Node<String>> charactersInCurrBook = booksToCharacters.getOrDefault
                                            (currEntry.getBook(),new HashSet<DirectedLabeledGraph.Node<String>>());


            // adds both edges between the hero of the current entry and  every other character which
            // appears in the comic book of the current entry
            for(DirectedLabeledGraph.Node<String> character : charactersInCurrBook) {
                DirectedLabeledGraph.Edge<String,String> edgeToAdd = new DirectedLabeledGraph.Edge<>(currEntry.getBook(),
                                                                                        character,hero);
                if(!dlg.getOutgoingEdges(character).contains(edgeToAdd)) {
                    dlg.addEdge(edgeToAdd);
                    dlg.addEdge(new DirectedLabeledGraph.Edge<>(currEntry.getBook(),hero, character));
                }

            }
            charactersInCurrBook.add(hero);
            booksToCharacters.put(currEntry.getBook(),charactersInCurrBook);
        }
        return dlg;
    }


    /**
     * Returns the shortest path between the Nodes start and end if there is one.
     * @spec.requires dlg != null, start != null, end != null and dlg contains the nodes start and end.
     * @spec.effects returns the list of edges which represents the shortest path
     *               between nodeA and nodeB in dlg. returns null if there is no path between
     *               nodeA and nodeB in dlg. If there are multiple shortest path, returns the lexicographically
     *               least path.
     * @param dlg the graph which is used to search for a path
     * @param start the start node of the path
     * @param end the end node of the path
     */
    public static List<DirectedLabeledGraph.Edge<String,String>> findPath(DirectedLabeledGraph<String,String> dlg, DirectedLabeledGraph.Node<String> start,
                                                                    DirectedLabeledGraph.Node<String> end) {
        // Uses a breadth first search algorithm

        assert (dlg != null) : "the dlg is null";
        assert (start != null) : "the start node is null";
        assert (end != null) : "the end node is null";;
        assert (dlg.containsNode(start)) : "the dlg does not contain the given start node";
        assert (dlg.containsNode(end)) : "the dlg does not contain the given end node";


        // A map which maps  a node that is encountered during the search to the path to that node from start.
        Map<DirectedLabeledGraph.Node<String>, List<DirectedLabeledGraph.Edge<String,String>>> nodesEncounteredToPaths = new HashMap<>();

        // a queue holding the  nodes to visit
        Queue<DirectedLabeledGraph.Node<String>> nodesToVisit = new LinkedList<>();

        nodesToVisit.add(start);
        nodesEncounteredToPaths.put(start, new ArrayList<>());
        while (!nodesToVisit.isEmpty()) {
            DirectedLabeledGraph.Node<String> curr = nodesToVisit.poll();
            if (curr.equals(end)) { // search has found the end node, return the path
                return nodesEncounteredToPaths.get(curr);
            } else { // search has not found the end node
                List<DirectedLabeledGraph.Edge<String,String>> edges =  new ArrayList<>(dlg.getOutgoingEdges(curr));

                // sorts the outgoing edges lexicographically to favor the lexicographically least path
                Collections.sort(edges, new Comparator<>() {
                    public int compare(DirectedLabeledGraph.Edge<String,String> a, DirectedLabeledGraph.Edge<String,String> b) {
                        int childComp = a.getChild().getData().compareTo(b.getChild().getData());
                        if(childComp == 0) {
                            return a.getLabel().compareTo(b.getLabel());
                        } else {
                            return childComp;
                        }
                    }
                });
                for (DirectedLabeledGraph.Edge<String,String> e : edges) {
                    if (!nodesEncounteredToPaths.keySet().contains(e.getChild()) && e!= null) {
                        // the child of edge e is a new node which has not been encountered yet

                        List<DirectedLabeledGraph.Edge<String,String>> pathToCurr = nodesEncounteredToPaths.get(curr);
                        List<DirectedLabeledGraph.Edge<String,String>> pathToChild = new ArrayList<>(pathToCurr);
                        pathToChild.add(e);
                        nodesEncounteredToPaths.put(e.getChild(), pathToChild);
                        nodesToVisit.add(e.getChild());
                    }
                }
            }
        }

        return  nodesEncounteredToPaths.get(end);
    }

    /**
     * Runs an interactive program allowing the user to find paths between characters in a tsv file
     * @spec.modifies System.out
     * @spec.effects prompts the user via messages printed to System.out for a tsv file and characters
     *               to find paths between in the tsv file. Outputs the shortest
     *               paths between the characters if there exists one. If the user inputs
     *               an invalid character name the program outputs "unknown character" followed
     *               by the name of the unknown character. After finding a path, the user can choose
     *               to find another path between two characters, or exit the program.
     *
     * @param args the arguments passed in from the command line
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);  // Create a Scanner object

        // Read user inputted input tsv file
        System.out.println("Hi welcome to MarvelPaths!");
        System.out.println("Enter TSV file to read from");
        String fileName = input.nextLine();

        // Construct a DLG from the user inputted tsv file
        DirectedLabeledGraph<String,String> dlg = MarvelPaths.loadGraph(fileName);

        // lets the user find paths between characters in the graph interactively
        interactiveMarvelPathsLoop(dlg,input);
    }


    /**
     * Finds the shortest path between two characters inputted by the user in the dlg and outputs that path
     *
     * @spec.requires dlg != null
     * @spec.effects prompts the user for characters
     *               to find paths between in the dlg. Outputs the shortest
     *               paths between the characters if there exists one. If the user inputs
     *               an invalid character name the program outputs "unknown character" followed
     *               by the name of the unknown character. After finding a path, the user can choose
     *               to find another path between two characters, or exit the program.
     * @param dlg the graph which is used to find paths between characters
     * @param input a scanner which is used to read in user input
     *
     */
    private static void interactiveMarvelPathsLoop(DirectedLabeledGraph<String,String> dlg,Scanner input) {
        boolean loopAgain = true;
        while(loopAgain) {
            System.out.println("Enter the Name of the First Character");
            String characterA = input.nextLine();  // Read user input
            System.out.println("Enter the Name of the Second Character");
            String characterB = input.nextLine();  // Read user input
            System.out.println();
            DirectedLabeledGraph.Node<String> a = new DirectedLabeledGraph.Node<>(characterA);
            DirectedLabeledGraph.Node<String> b = new DirectedLabeledGraph.Node<>(characterB);

            //checks if the nodes are valid nodes in the dlg
            if (!dlg.containsNode(a) || !dlg.containsNode(b)) {
                if (!dlg.containsNode(a)) {
                    System.out.println("unknown character " + characterA);
                }
                if (!dlg.containsNode(b)) {
                    System.out.println("unknown character " + characterB);
                }

            } else { // both nodes are valid, look for a path
                System.out.println("path from " + characterA + " to " + characterB + ":");
                List<DirectedLabeledGraph.Edge<String,String>> path = MarvelPaths.findPath(dlg, a, b);
                if (path == null) {
                    System.out.println("no path found");
                } else {
                    for (DirectedLabeledGraph.Edge<String,String> e : path) {
                        System.out.println(e.getParent().getData() + " to " +
                                e.getChild().getData() + " via " + e.getLabel());
                    }
                }
            }
            System.out.println("Would You like to look for another path? type yes if so");
            String result = input.nextLine();  // Read user input
            if(!result.trim().equalsIgnoreCase("yes")){
                System.out.println("Closing MarvelPaths");
                loopAgain = false;
                System.out.close();
            }
        }

    }


}
