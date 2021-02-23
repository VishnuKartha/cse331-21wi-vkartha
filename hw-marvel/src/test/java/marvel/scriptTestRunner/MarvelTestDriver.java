/*
 * Copyright (C) 2021 Hal Perkins.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Winter Quarter 2021 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package marvel.scriptTestRunner;

import graph.DirectedLabeledGraph;
import java.io.*;
import java.util.*;
import marvel.MarvelPaths;

/**
 * This class implements a testing driver which reads test scripts from
 * files for testing Graph, the Marvel parser, and your BFS algorithm.
 */
public class MarvelTestDriver {
    /**
     * String -> Graph: maps the names of graphs to the actual graph
     **/
    private final Map<String, DirectedLabeledGraph<String,String>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;
    // Leave this constructor public
    public MarvelTestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }



    /**
     * @throws IOException if the input or output sources encounter an IOException
     * @spec.effects Executes the commands read from the input and writes results to the output
     **/
    // Leave this method public
    public void runTests()
            throws IOException {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch (command) {
                case "LoadGraph":
                    loadGraph(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

private void createGraph(List<String> arguments) {
    if (arguments.size() != 1) {
        throw new CommandException("Bad arguments to CreateGraph: " + arguments);
    }

    String graphName = arguments.get(0);
    createGraph(graphName);
}

    private void createGraph(String graphName) {
        DirectedLabeledGraph<String,String> dlg = new DirectedLabeledGraph<>();
        graphs.put(graphName, dlg);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        DirectedLabeledGraph<String,String> dlg = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> n = new DirectedLabeledGraph.Node<>(nodeName);
        dlg.addNode(n);
        output.print("added node " + nodeName + " to " + graphName);
        output.println();

    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {

        DirectedLabeledGraph<String,String> dlg  = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> parent = new DirectedLabeledGraph.Node<>(parentName);
        DirectedLabeledGraph.Node<String> child = new DirectedLabeledGraph.Node<>(childName);
        DirectedLabeledGraph.Edge<String,String> e = new DirectedLabeledGraph.Edge<>(edgeLabel,parent,child);
        dlg.addEdge(e);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName
                + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {

        DirectedLabeledGraph<String,String> dlg  = graphs.get(graphName);
        output.print(graphName + " contains:");
        List<DirectedLabeledGraph.Node<String>> nodes =  new ArrayList<>(dlg.getAllNodes());
        Collections.sort(nodes, new Comparator<>() {
            public int compare(DirectedLabeledGraph.Node<String> a, DirectedLabeledGraph.Node<String> b) {
                return a.getData().compareTo(b.getData());
            }
        });
        for(DirectedLabeledGraph.Node<String> n :nodes) {
            output.print(" " + n.getData());
        }
        output.println();
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        DirectedLabeledGraph<String,String> dlg  = graphs.get(graphName);
        DirectedLabeledGraph.Node<String> parent = new DirectedLabeledGraph.Node<>(parentName);
        output.print("the children of " + parent.getData() + " in " + graphName +  " are:");
        List<DirectedLabeledGraph.Edge<String,String>> edges =  new ArrayList<>(dlg.getOutgoingEdges(parent));
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
        for(DirectedLabeledGraph.Edge<String,String> edge : edges) {
            output.print(" " + edge.getChild().getData() + "(" + edge.getLabel() + ")");
        }
        output.println();
    }


    private void loadGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String file = arguments.get(1);
        loadGraph(graphName,file);
    }

    private void loadGraph(String graphName, String file) {
         graphs.put(graphName,MarvelPaths.loadGraph(file));
         output.println("loaded graph " + graphName);
    }


    private void findPath(List<String> arguments) {
        if (arguments.size() != 3) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        String node_a = arguments.get(1);
        String node_b = arguments.get(2);
        findPath(graphName,node_a,node_b);
    }

    private void findPath(String graphName, String node_a,String node_b) {
        node_a = node_a.replace('_',' ');
        node_b = node_b.replace('_',' ');
        DirectedLabeledGraph.Node<String> a = new DirectedLabeledGraph.Node<>(node_a);
        DirectedLabeledGraph.Node<String> b = new DirectedLabeledGraph.Node<>(node_b);
        DirectedLabeledGraph<String,String> dlg = graphs.get(graphName);
        if(!dlg.containsNode(a) || !dlg.containsNode(b)){
            if(!dlg.containsNode(a)) {
                output.println("unknown character " + node_a);
            }
            if(!dlg.containsNode(b)) {
                output.println("unknown character " + node_b);
            }

        } else {
            output.println("path from " + node_a + " to " + node_b +":");
            List<DirectedLabeledGraph.Edge<String,String>> path = MarvelPaths.findPath(dlg,a,b);
            if(path == null){
                output.println("no path found");
            } else {
                for(DirectedLabeledGraph.Edge<String,String> e: path){
                    output.println(e.getParent().getData() + " to " +
                            e.getChild().getData() +" via " + e.getLabel());
                }
            }
        }
    }



    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
