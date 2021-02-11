package graph;

import java.util.*;

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

    /**  maps each Node in this DLG to set of that Node's outgoing edges */
    private Map<Node, Set<Edge>> adjList;
    private boolean useCheckRep = true;


    // Representation Invariant:
    //  adjList is not null and
    //  for each edge in the adjList,
    //  the adjList contains that edge's parent and child
    //  node mapped the corresponding outgoing edges of that node.

    // Abstraction Function:
    //  AF(r) = a Directed Labeled Graph g
    //  such that g.elements = r.adjList

    /**
     * @spec.effects creates a new empty DLG
     * @spec.modifies this.elements
     */
    public void DirectedLabeledGraph(){
        this.adjList = new HashMap<>();
        if(this.useCheckRep) {
            checkRep();
        }
    }

    /**
     * @spec.requires n != null, n is a unique node different from all the other nodes in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds a node to this DLG
     */
    public void addNode(Node n){
        if(this.useCheckRep) {
            checkRep();
        }
        assert (!adjList.keySet().contains(n)) :
                 n.toString() + " is not unique";
        adjList.put(n, new HashSet<>());
        if(this.useCheckRep) {
            checkRep();
        }
    }

    /**
     * @spec.requires e != null, e is a unique edge different from all the other edges in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds this edge to set of outgoing edges in elements corresponding to the
     *               a appropriate node.
     */
    public void addEdge(Edge e){
        if(this.useCheckRep) {
            checkRep();
        }
        // adds the parent node of the edge to the adjList if not there already
        if(!adjList.keySet().contains(e.getParent())) {
            addNode(e.getParent());
        }

        // adds the parent node of the edge to the adjList if not there already
        if(!adjList.keySet().contains(e.getChild())){
            addNode(e.getChild());
        }
        Set<Edge> edges = adjList.get(e.getParent());
        edges.add(e);
        adjList.put(e.getParent(), edges);
        if(this.useCheckRep) {
            checkRep();
        }
    }

    /**
     * @return the set of all of the nodes in this DLG.
     */
    public Set<Node> getAllNodes(){
        if(this.useCheckRep) {
            checkRep();
        }
        Set<Node> allNodes = adjList.keySet();
        if(this.useCheckRep) {
            checkRep();
        }
        return allNodes;
    }

    /**
     * @spec.requires this.elements contains parentNode
     * @return the set of all outgoing edges from the given parent node.
     */
    public Set<Edge> getOutgoingEdges(Node parentNode){
        if(this.useCheckRep) {
            checkRep();
        }
        Set<Edge> outgoingEdges = adjList.get(parentNode);
        if(this.useCheckRep) {
            checkRep();
        }
        return outgoingEdges;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.adjList != null) : "adjList == null";
        for(Node parent : this.adjList.keySet()){
            for(Edge e : adjList.get(parent)){
                assert (adjList.keySet().contains(e.getParent())) :
                        "adjList does not contain the parent node of " + e.toString();
                assert (adjList.keySet().contains(e.getChild())) :
                     "adjList does not contain the child node of " + e.toString();
            }
        }
    }
    /**
     * @return a String which represents this DLG
     */
    @Override
    public String toString() {
        if(this.useCheckRep) {
            checkRep();
        }
        String result = "";
        for(Node n : adjList.keySet()){
            result += "{";
            result += n.toString();
            result += ":";
            for(Edge e : adjList.get(n)){
                result += " " + e.toString() + ",";
            }
            result += "}";
        }
        if(this.useCheckRep) {
            checkRep();
        }
        return result;
    }

    /**
     This class represents a single node of the DLG, which contains some String data. Nodes
     are immutable

     Specification fields:
     *  @spec.specfield data: String // the data contained in the node
     */
    public static class Node {

        /** the data which is held by this node */
        private final String data;

        // Representation Invariant:
        //  data != null

        // Abstraction Function:
        //  AF(r) = a Node n
        //  such that n.data equals r.data


        /**
         * @spec.effects creates a new Node with the given data
         * @spec.modifies this.data
         */
        public Node (String data) {
            this.data = data;
            checkRep();
        }

        /**
         * @return returns the data of this node.
         */
        public String getData() {
            return this.data;
        }

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            assert(this.data != null) : "the data of this node is null";
        }

        /**
         * @spec.effects  standard equality operation.
         * @param obj the object to be compared for equality
         * @return true iff 'obj' is an instance of an Node and 'this' and 'obj' represent the same
         * Node.
         */
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Node) {
                Node n = (Node) obj;
                return(this.data.equals(n.data));
            } else {
                return false;
            }
        }

        /**
         * @spec.effects  hash code function.
         * @return an int that all objects equal to this Node will also
         */
        @Override
        public int hashCode() {
                return this.data.hashCode();
        }

        /**
         * @return a String which represents this Node
         */
        @Override
        public String toString() {
            return "Node: " + this.getData();
        }

    }

    /**
     This class represents a labeled Edge of the DLG, from a start node
     to an end node. Edges are immutable and labeled by a String.

     @spec.specfield label : String // the label of the edge
     <br>
     @spec.specfield parent : Node // the node from which the edge starts
     <br>
     @spec.specfield child : Node // the node from which the edge ends.
     <br>
     */


    public static class Edge {

        // Representation Invariant:
        //  label != null && parent != null && child != null

        // Abstraction Function:
        //  AF(r) = an Edge e
        //  such that: e.label equals r.label and
        //             e.parent equals r.parent and
        //             e.child equals r.child

        /** the label of this edge */
        private final String label;

        /** the parent node of this edge */
        private final Node parent;

        /** the child node of this edge */
        private final Node child;

        /**
         * @spec.effects creates a new Edge with the given label, parent node, and child node.
         * @spec.modifies this.data,this.start,this.end
         */
        public Edge (String label,Node parent, Node child) {
            this.label = label;
            this.parent = parent;
            this.child = child;
            checkRep();
        }

        /**
         * @return returns the label of this edge.
         */
        public String getLabel() {
            return this.label;
        }

        /**
         * @return returns the parent node of this edge.
         */
        public Node getParent() {
            return this.parent;
        }

        /**
         * @return returns the child node of this edge.
         */
        public Node getChild() {
            return this.child;
        }

        /**
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            assert(this.label != null) : "the label of this edge is null";
            assert(this.parent != null) : "the parent of this edge is null";
            assert(this.child != null) : "the child of this edge is null";
        }

        /**
         * @spec.effects standard equality operation.
         * @param obj the object to be compared for equality
         * @return true iff 'obj' is an instance of an Edge and 'this' and 'obj' represent the same
         * Edge.
         */
        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Edge) {
                Edge n = (Edge) obj;
                return(this.label.equals(n.label) &&
                        this.parent.equals(n.parent) &&
                        this.child.equals(n.child));
            } else {
                return false;
            }
        }

        /**
         * @spec.effects  hashCode function.
         * @return an int that all objects equal to this Edge will also
         */
        @Override
        public int hashCode() {
            return (this.label.hashCode() * 7) +
                    (this.parent.hashCode() * 43) +
                    (this.child.hashCode() * 43);
        }

        /**
         * @return a String which represents this Edge
         */
         @Override
         public String toString() {
             return "Edge: " + this.getLabel() + " Parent Node:" + this.getParent().toString() +
                     " Child Node:" + this.getChild().toString();
         }

    }
}
