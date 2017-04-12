package graph.results.api;

import graph.pycharm.api.GraphRelationship;
import graph.visualization.api.GraphNode;

import java.util.List;
import java.util.Optional;

public interface GraphCoverageResult {

    List<GraphNode> getNodes();

    List<GraphRelationship> getRelationships();
}
