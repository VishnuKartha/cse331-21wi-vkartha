package graph;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * This class represents the concept of a mutable Directed Labeled Graph(DLG).
 *  <p> A node n_i is a representation of a single piece of String data.
 *      An edge e(n_a,n_b,l_i) represents that the node n_b is directly reachable from n_a and
 *      is given the label l_i, where l_i is a String.
 *      An outgoing edge to a node n_i is an edge where the other node in the edge n_j is directly
 *      reachable from n_i.
 *      The directed labeled graph is a set of pairs where the first element of the pair is a node
 *      and the second element is the set of outgoing edges from that node.
 *      Thus, the directed labeled graph can be notated as
 *      {(n_a, {e(n_a,n_i,l_1),e(n_a,n_j,l_2),...}),(n_b, {e(n_b,n_i,l_3),e(n_b,n_j,l_4),...}),...}
 *
 *     @spec.specfield elements: Set(Node,Set(Edge))   // The set of pairs where each pair consists of
 *                                                      a node and the set of all outgoing edges from that node.
 *
 *  <p> Abstract Invariant:
 *  <ul>
 *      <li>All nodes in the graph are unique(there are no two nodes in the graph which both contain the same data</li>
 *      <li> All edges in the graph are unique(there are no two edges in the graph which contain the same start node,the same end node, and the same label).</li>
 * </ul>
 */
public class DirectedLabeledGraph {

    /**
     * @spec.effects creates a new empty DLG
     * @spec.modifies this.elements
     */
    public void DirectedLabeledGraph(){
        throw new RuntimeException("Graph Constructor not yet implemented");
    }

    /**
     * @spec.requires n != null, n is a unique node different from all the other nodes in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds a node to this DLG
     */
    public void addNode(Node n){
        throw new RuntimeException("addNode not yet implemented");
    }


    /**
     * @spec.requires e != null, is e a unique edge different from all the other edges in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds this edge to set of outgoing edges in elements corresponding to the
     *               a appropriate node.
     */
    public void addEdge(Edge e){
        throw new RuntimeException("addEdge not yet implemented");
    }


    /**
     * @return the set of all of the nodes in this DLG.
     */
    public Set<Node> getAllNodes(){
        throw new RuntimeException("getAllNodes() not yet implemented");
    }

    /**
     * @spec.requires this.elements contains parentNode
     * @return the set of all outgoing edges from the parentNode.
     */
    public Set<Edge> getOutgoingEdges(Node parentNode){
        throw new RuntimeException("getChildren not yet implemented");
    }
    /**
     * @return a String which represents this Graph
     */
    @Override
    public String toString() {
        throw new RuntimeException("toString() not yet implemented");
    }

    /**
     This class represents a single node of the DLG, which contains some String data. Nodes
     are immutable

     Specification fields:
     *  @spec.specfield data: String // the data contained in the node
     */
    public static class Node {
        /**
         * @spec.effects creates a new Node with the given data
         * @spec.modifies this.data
         */
        public Node (String data) {
            throw new RuntimeException("Node Constructor not yet implemented");
        }

        /**
         * @return returns the data of this node.
         */
        public String getData() {
            throw new RuntimeException("getData() not yet implemented");
        }

        /**
         * @spec.effects  standard equality operation.
         * @param obj the object to be compared for equality
         * @return true iff 'obj' is an instance of an Node and 'this' and 'obj' represent the same
         * Node.
         */
        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("equals() not yet implemented");

        }

        /**
         * @spec.effects  hash code function.
         * @return an int that all objects equal to this Node will also
         */
        @Override
        public int hashCode() {
                throw new RuntimeException("hasCode() not yet implemented");
        }

        /**
         * @return a String which represents this Node
         */
        @Override
        public String toString() {
            throw new RuntimeException("toString() not yet implemented");
        }

    }

    /**
     This class represents a labeled Edge of the DLG, from a start node
     to an end node. Edges are immutable and labeled by a String.

     Specification fields:
     *  <ul>
     *      <li>@spec.specfield label : String // the label of the edge</li>
     *      <li>@spec.specfield parent : Node // the node from which the edge starts</li>
     *      <li>@spec.specfield child : Node // the node from which the edge ends.</li>
     * </ul>
     *
     */
    public  static class Edge {
        /**
         * @spec.effects creates a new Edge with the given label, parent node, and child node.
         * @spec.modifies this.data,this.start,this.end
         */
        public Edge (String label,Node parent, Node child) {
            throw new RuntimeException("Edge Constructor not yet implemented");
        }

        /**
         * @return returns the label of this edge.
         */
        public String getLabel() {
            throw new RuntimeException("getLabel() not yet implemented");
        }

        /**
         * @return returns the parent node of this edge.
         */
        public Node getParent() {
            throw new RuntimeException("getParent() not yet implemented");
        }

        /**
         * @return returns the child node of this edge.
         */
        public Node getChild() {
            throw new RuntimeException("getChild() not yet implemented");
        }

        /**
         * @spec.effects standard equality operation.
         * @param obj the object to be compared for equality
         * @return true iff 'obj' is an instance of an Edge and 'this' and 'obj' represent the same
         * Edge.
         */
        @Override
        public boolean equals(Object obj) {
            throw new RuntimeException("equals() not yet implemented");
        }

        /**
         * @spec.effects  hashCode function.
         * @return an int that all objects equal to this Edge will also
         */
        @Override
        public int hashCode() {
            throw new RuntimeException("hasCode() not yet implemented");
        }

        /**
         * @return a String which represents this Edge
         */
         @Override
         public String toString() {
             throw new RuntimeException("toString() not yet implemented");
        }


    }
}
