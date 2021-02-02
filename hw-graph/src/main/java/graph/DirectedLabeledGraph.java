package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DirectedLabeledGraph {
    /**
     * This class represents the concept of a mutable Directed Labeled Graph(DLG).
     *  <p> A node n_i is a representation of a single piece of String data.
     *      An edge e(n_a,n_b,s_i) represents that the node n_b is directly reachable from n_a and
     *      is given the label s_i, where s_i is a String.
     *      An outgoing edge to a node n_i is an edge where the other node in the edge n_j is directly
     *      reachable from n_i.
     *      The directed labeled graph is a set of pairs where the first element of the pair is a node
     *      and the second element is the set of outgoing edges from that node.
     *      Thus, the directed labeled graph can be notated as
     *      {<n_a, {e(n_a,n_i,s_1),e(n_a,n_j,s_2),...}>,<n_b, {e(n_b,n_i,s_3),e(n_b,n_j,s_4),...}>,...}
     *
     * Specification fields:
     *  @spec.specfield: elements: Set<(Node,Set<Edge>)>   // The set of pairs where each pair consists of
     *                                                      a node and the set of all outgoing edges from that node.
     *
     *  Abstract Invariant:
     *  All nodes in the graph are unique(there are no two nodes in the graph which both contain the same data)
     *  All edges in the graph are unique(there are no two edges in the graph which contain the same start node,
     *  the same end node, and the same label).
     */

    /**
     * @spec.effects creates a new empty DLG
     * @spec.modifies this.elements
     */
    public void DirectedLabeledGraph(){
        throw new RuntimeException("Graph Constructor not yet implemented");
    }

    /**
     * @spec.requires n != null, n is a unique node
     *                  (there are no nodes in elements which are equivalent to n)
     * @spec.modifies this.elements
     * @spec.effects adds a node to this DLG
     */
    public void addNode(Node n){
        throw new RuntimeException("addNode not yet implemented");
    }

    /**
     * @spec.requires e != null, is a unique node
     *                  (there are no edges in the graph which are equivalent to e)
     * @spec.modifies this.elements
     * @spec.effects adds this edge to set of outgoing edges in elements corresponding to the
     *               a appropriate node.
     */
    public void addEdge(Edge e){
        throw new RuntimeException("addEdge not yet implemented");
    }

    /**
     * @returns the set of all of the nodes in this DLG.
     */
    public Set<Node> getAllNodes(){
        throw new RuntimeException("getChildren not yet implemented");
    }

    /**
     * @spec.requires this.elements contains parentNode
     * @returns the set of all of the children of the given parentNode in this DLG.
     */
    public Set<Node> getChildren(Node parentNode){
        throw new RuntimeException("getChildren not yet implemented");
    }


    public  class Node {
        /**
         This class represents a single node of the DLG, which contains some String data. Nodes
         are immutable

         Specification fields:
         *  @spec.specfield: data: String // the data contained in the node
         */

        /**
         * @spec.effects creates a new Node with the given data
         * @spec.modifies this.data
         */
        public Node (String data) {
            throw new RuntimeException("Node Constructor not yet implemented");
        }

        /**
         * @returns returns the data of this node.
         */
        public String getData() {
            throw new RuntimeException("getData() not yet implemented");
        }

    }


    public  class Edge {
        /**
         This class represents a labeled Edge of the DLG, from a start node
         to an end node. Edges are immutable and labeled by a String.

         Specification fields:
         *  @spec.specfield: label : String // the label of the edge
         *  @spec.specfield: start : Node // the node from which the edge starts
         *  @spec.specfield: end : Node // the node from which the edge ends.
         */

        /**
         * @spec.effects creates a new Edge with the given label,start node, and end node.
         * @spec.modifies this.data,this.start,this.end
         */
        public Edge (String label,Node start, Node end) {
            throw new RuntimeException("Edge Constructor not yet implemented");
        }

        /**
         * @returns returns the label of this edge.
         */
        public String getLabel() {
            throw new RuntimeException("getLabel() not yet implemented");
        }

        /**
         * @returns returns the start node of this edge.
         */
        public Node getStart() {
            throw new RuntimeException("getLabel() not yet implemented");
        }

        /**
         * @returns returns the end node of this edge.
         */
        public Node getEnd() {
            throw new RuntimeException("getLabel() not yet implemented");
        }

    }
}
