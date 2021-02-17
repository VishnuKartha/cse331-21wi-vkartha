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
    /**  toggles the checkRep calls in this DLG  */
    private static final boolean useCheckRep = false;



    // Representation Invariant:
    //  adjList is not null and
    //  for each edge in the adjList,
    //  the adjList contains that edge's parent and child
    //  node mapped to the corresponding outgoing edges of that node.
    //  Also, the set of outgoing edges for each node in adjLIst is not null,
    //  adjList contains no null nodes and no null edges.

    // Abstraction Function:
    //  AF(r) = a Directed Labeled Graph g
    //  such that g.elements = r.adjList
    // the keys of r.adjList represent the nodes in g.elements.
    // the values of r.adjList represent the Set of outgoing edges of each node in g.elements

    /**
     * Constructs a new empty DLG
     *
     * @spec.effects creates a new empty DLG
     * @spec.modifies this.elements
     */
    public DirectedLabeledGraph(){
        this.adjList = new HashMap<>();
            checkRep();

    }

    /**
     * Adds a Node to this DLG
     *
     * @spec.requires n != null, n is a unique node different from all the other nodes in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds a node to this DLG
     */
    public void addNode(Node n){
            checkRep();
        assert (!adjList.keySet().contains(n)) :
                 n.toString() + " is not unique";
        adjList.put(n, new HashSet<>());
            checkRep();
    }

    /**
     * Adds a given Edge to this DLG
     *
     * @spec.requires e != null, e is a unique edge different from all the other edges in the DLG.
     * @spec.modifies this.elements
     * @spec.effects adds this edge to set of outgoing edges in elements corresponding to the
     *               a appropriate node. Adds the parent and child node of e to the DLG
     *               if it is not already in the graph.
     */
    public void addEdge(Edge e){
            checkRep();
        // adds the parent node of the edge to the adjList if not there already
        if(!adjList.keySet().contains(e.getParent())) {
            addNode(e.getParent());
        }

        // adds the child node of the edge to the adjList if not there already
        if(!adjList.keySet().contains(e.getChild())){
            addNode(e.getChild());
        }
        Set<Edge> edges = adjList.get(e.getParent());
        edges.add(e);
        adjList.put(e.getParent(), edges);
            checkRep();
    }


    /**
     * Checks if the given Edge is in this DLG
     *
     * @spec.requires e != null,
     * @spec.effects returns whether or not the given Edge is in the DLG
     */
    public boolean containsEdge(Edge e) {
        checkRep();
        boolean result = false;
        for(Node n: this.adjList.keySet()){
            if(this.adjList.get(n).contains(e)){
                result = true;
                break;
            }
        }
        checkRep();
        return result;

    }

    /**
     * Checks if the given Node is in this DLG
     *
     * @spec.requires n != null,
     * @spec.effects returns whether or not the given Node is in the DLG
     */
    public boolean containsEdge(Node n) {
        checkRep();
        boolean result = this.adjList.keySet().contains(n);
        checkRep();
        return result;
    }

    /**
     * Returns an unmodifiable set of all nodes in this DLG.
     *
     * @return the set of all of the nodes in this DLG.
     */
    public Set<Node> getAllNodes(){
            checkRep();
        Set<Node> allNodes = Collections.unmodifiableSet(adjList.keySet());
            checkRep();
        return allNodes;
    }

    /**
     *  Returns an unmodifiable set of all outgoing edges from the given parent Node.
     *
     * @spec.requires this.elements contains parentNode and parentNode != null
     * @return the set of all outgoing edges from the given parent node.
     */
    public Set<Edge> getOutgoingEdges(Node parentNode){
            checkRep();
        Set<Edge> outgoingEdges = Collections.unmodifiableSet(adjList.get(parentNode));
            checkRep();
        return outgoingEdges;
    }

    /**
     * Throws an exception if the representation invariant is violated.
     */
    private void checkRep() {
        assert (this.adjList != null) : "adjList == null";
        if(this.useCheckRep) {
            for (Node parent : this.adjList.keySet()) {
                assert(parent != null):"parent == null";
                assert(adjList.get(parent)!= null):"the outgoing edges from " + parent.toString() +" is null";
                for (Edge e : adjList.get(parent)) {
                    assert (adjList.keySet().contains(e.getParent())) :
                            "adjList does not contain the parent node of " + e.toString();
                    assert(e.child != null):"the child node of" + e.toString() + "is null";
                    assert (adjList.keySet().contains(e.getChild())) :
                            "adjList does not contain the child node of " + e.toString();
                }
            }
        }
    }
    /**
     * Returns a string which represents this DLG, which follows the format
     * "[{node_1: &lt;edge_1&gt;, &lt;edge_2&gt;} {node_2: &lt;edge_3&gt;, &lt;edge_4&gt;}]"
     *  where the toString methods of node_i and edge_j are called.
     * Some Examples: "[]"
     *                "[{NODE A: }]
     *                "[{NODE a: &lt;EDGE aTob from NODE a to NODE b&gt;}{NODE b: }]"
     *
     *
     * @return a String which represents this DLG
     */
    @Override
    public String toString() {
        if(this.useCheckRep) {
            checkRep();
        }
        String result = "[";
        for(Node n : adjList.keySet()){
            result+= "{";
            result += n.toString();
            result += ": ";
            boolean first = true;
            for(Edge e : adjList.get(n)){
                if(first) {
                    result += "<" + e.toString() + ">";
                    first = false;
                } else {
                    result += ", <" + e.toString() + ">";
                }
            }
            result+= "}";
        }
        result += "]";
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
         * Constructs a new Node with the given data
         *
         * @spec.effects creates a new Node with the given data
         * @spec.modifies this.data
         */
        public Node (String data) {
            this.data = data;
            checkRep();
        }

        /**
         * Gets the data of this Node
         *
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
         * Returns whether the given object is equal to this Node
         *
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
         * Generates a hash code from this node
         *
         * @spec.effects  hash code function.
         * @return an int that all objects equal to this Node will also
         */
        @Override
        public int hashCode() {
                return this.data.hashCode();
        }

        /**
         * Returns a String representation of this Node, which follows the format
         * "NODE this.data".
         *  Some Examples: "NODE a","NODE 1", "NODE "
         *
         * @return a String which represents this Node
         */
        @Override
        public String toString() {
            return "NODE " + this.data;
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
         *  Constructs a new Edge with the given label, parent node, and child node
         *
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
         * Gets the label of this Edge
         *
         * @return returns the label of this edge.
         */
        public String getLabel() {
            return this.label;
        }

        /**
         * Gets the parent Node of this edge
         *
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
         * Gets the child Node of this edge
         *
         * Throws an exception if the representation invariant is violated.
         */
        private void checkRep() {
            assert(this.label != null) : "the label of this edge is null";
            assert(this.parent != null) : "the parent of this edge is null";
            assert(this.child != null) : "the child of this edge is null";
        }

        /**
         * Returns whether the given object is equal to this Edge
         *
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
         * Generates a hash code from this node
         *
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
         * Returns a String representation of this Edge, which follows the format
         * "EDGE this.label from this.parent to this.child".
         * Some Examples:   "EDGE selfEdge from NODE a to NODE a",
         *                  "EDGE aTob from NODE a to NODE b"
         *
         *
         * @return a String which represents this Edge
         */
         @Override
         public String toString() {
             return "EDGE " + this.label + " from " + this.parent.toString() +
                     " to " + this.child.toString();
         }

    }
}
