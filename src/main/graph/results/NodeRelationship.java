package main.graph.results;


import main.graph.pycharm.api.GraphRelationship;
import main.graph.visualization.api.GraphNode;
import main.graph.visualization.api.GraphPropertyContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Node Relationship class
 */
public class NodeRelationship implements GraphRelationship {

    private final String id;
    private double weight;
    private final List<String> types;
    private String callsCount;
    private GraphPropertyContainer propertyContainer;
    private  String startNodeId;
    private  String endNodeId;
    private GraphNode startNode;
    private GraphNode endNode;

    public NodeRelationship(String id) {
        this.id = String.valueOf(id);
        this.types = new ArrayList<>();
        this.propertyContainer = new ResultsPropertyContainer();

    }

    @Override
    public String getStartNodeId() {
        return startNodeId;
    }

    @Override
    public String getEndNodeId() {
        return endNodeId;
    }

    public void setStartNode(GraphNode startNode) {
        this.startNode = startNode;
        this.startNodeId = startNode.getId();
    }

    public void setEndNode(GraphNode endNode) {
        this.endNode = endNode;
        this.endNodeId = endNode.getId();
    }

    @Override
    public String getId() {
        return String.valueOf(id);
    }

    @Override
    public List<String> getTypes() {
        return types;
    }

    @Override
    public String getTypesName() {
        return "relation_info";
    }

    @Override
    public boolean isTypesSingle() {
        return true;
    }

    @Override
    public GraphPropertyContainer getPropertyContainer() {
        return propertyContainer;
    }

    @Override
    public boolean hasStartAndEndNode() {
        return startNode != null && endNode != null;
    }

    @Override
    public double relationWeight() {
        return this.weight;
    }

    @Override
    public void setWeight(double weight){
        this.weight = weight;
    }

    @Override
    public GraphNode getStartNode() {
        return startNode;
    }

    @Override
    public GraphNode getEndNode() {
        return endNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NodeRelationship that = (NodeRelationship) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public void setPropertyContainer (ResultsPropertyContainer resultsPropertyContainer){
        this.propertyContainer=resultsPropertyContainer;
    }

    public String getCallsCount() {
        return callsCount;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    public void setCallsCount(String callsCount) {
        this.callsCount = callsCount;
    }
}
