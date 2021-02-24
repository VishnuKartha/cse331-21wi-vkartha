package pathfinder;

import graph.DirectedLabeledGraph;
import java.util.*;
import pathfinder.datastructures.Path;
;

/**
 * Performs Dijkstra's shortest path algorithm on a DLG with Edge Labels as doubles
 */
public  class Dijkstra {

    // class does not represent an ADT

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
    public static <D> Path<D> findPath(DirectedLabeledGraph<D,Double> dlg, DirectedLabeledGraph.Node<D> start,
                                                                  DirectedLabeledGraph.Node<D> end) {
        // Uses a breadth first search algorithm

        assert (dlg != null) : "the dlg is null";
        assert (start != null) : "the start node is null";
        assert (end != null) : "the end node is null";;
        assert (dlg.containsNode(start)) : "the dlg does not contain the given start node";
        assert (dlg.containsNode(end)) : "the dlg does not contain the given end node";


        // A map which maps  a node that is encountered during the search to the path to that node from start.

        // a queue holding the  nodes to visit
        // Each element is a path from start to a given node.
        // A path's “priority” in the queue is the total cost of that path.
        // Nodes for which no path is known yet are not in the queue.
        PriorityQueue<Path<D>> active = new PriorityQueue<>(new Comparator<Path<D>>() {
            @Override
            public int compare(Path<D> o1, Path<D> o2) {
                return Double.compare(o1.getCost(),o2.getCost());
            }
        });


                // Initially we only know of the path from start to itself
                active.add(new Path<>(start.getData()));

        //  finished = set of nodes for which we know the minimum-cost path from start.
        Set<DirectedLabeledGraph.Node<D>> finished = new HashSet<>();


        while (!active.isEmpty()) {
            Path<D> minPath = active.poll();
            DirectedLabeledGraph.Node<D> minDest = new DirectedLabeledGraph.Node<>(minPath.getEnd());
            if (minDest.equals(end)) { // search has found the end node, return the path
                return minPath;
            } else  if(finished.contains(minDest)) {
                continue;
            } else {
                for (DirectedLabeledGraph.Edge<D,Double> e : dlg.getOutgoingEdges(minDest)) {
                    if (e!= null && !finished.contains(e.getChild())) {
                        // the child of edge e is a new node which has not been encountered yet the label of e has
                        // the distance between the nodes
                        Path<D> newPath = minPath.extend(e.getChild().getData(),e.getLabel());
                        active.add(newPath);
                    }
                }
                finished.add(minDest);
            }
        }
        // did not find a path to the end node
        return null;
    }
}
