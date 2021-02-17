### Written Answers: 10/10

### Design: 3/3

### Documentation & Specification (including JavaDoc): 3/3

### Code quality (code and internal comments including RI/AF): 2/3

### Testing (test suite quality & implementation): 3/3

### Mechanics: 3/3

#### Overall Feedback

Good job on designing and implementing your graph. Make sure to check out my specific comments as you move forward with hw6.

#### More Details
- your debug/checkrep flag should be static and final.
- your rep invariant should also include that the Nodes are not null, that none of the sets of edges are null, and that none of the edges inside those sets are null. Additionally, each edge's parent and child should be nodes inside the graph.
- your abstraction function should explain how your adjacency list represents the abstract graph. What are the keys of your adjacency list? What are the values? What do these represent?
- because you `spec.requires` that the node/edge being added is unique, you should have some way to actually check that (like a `containsNode()` method).
- your `if(this.useCheckRep)` should be included in your checkRep. This way, you can still do simple checks (like `adjList != null`) and just avoid the expensive checks.
- your `addEdge` should specify what happens if one of the edge's parent or child is not in the graph already
- you should specify what happens if `parentNode == null` in `getOutgoingEdges`.
