package oop.ex4.data_structures;


/**
 * This class is used to construct a tree structure and hold it's values
 */
public class Node {

    // FIELDS //

    /* The node's data value */
    private int nodeData;
    /* The node's balance factor */
    private int balanceFactor;
    /* The smaller value son of the node */
    private Node leftSon;
    /* The bigger value son of the node */
    private Node rightSon;
    /* The parentNode of this node */
    private Node parentNode;


    // CONSTRUCTORS //

    /**
     * The node constructor, builds a new node with the input as it's data
     *
     * @param data   The value of the node
     * @param parent The parent of the specific node
     */
    public Node(int data, Node parent) {
        nodeData = data;
        leftSon = null;
        rightSon = null;
        parentNode = parent;
    }


    // METHODS //

    /**
     * getter function for the node's Data
     *
     * @return the node's data
     */
    int getData() {
        return nodeData;
    }

    /*
     * getter function for the node's left son
     *
     * @return the node's left son
     */
    Node getLeftSon() {
        return leftSon;
    }

    /*
     * getter function for the node's right son
     *
     * @return the node's right son
     */
    Node getRightSon() {
        return rightSon;
    }

    /*
     * getter function for the node's balance factor
     *
     * @return the node's balance factor
     */
    int getBalanceFactor() {
        return balanceFactor;
    }

    /*
     * getter function for the node's parentNode
     *
     * @return the node's parentNode
     */
    Node getParentNode() {
        return parentNode;
    }

    /*
     * A setter function for the node's left son
     *
     * @param newLeftSon The node's left son
     */
    void setLeftSon(Node newLeftSon) {
        leftSon = newLeftSon;
    }


    /*
     * A setter for the node's right son
     *
     * @param newRightSon The node's right son
     */
    void setRightSon(Node newRightSon) {
        rightSon = newRightSon;
    }

    /*
     * setter function for the node's balance factor
     *
     * @param newBalanceFactor the node new balance factor
     */
    void setBalanceFactor(int newBalanceFactor) {
        balanceFactor = newBalanceFactor;
    }

    /*
     * setter function for the node's parentNode
     *
     * @param newParent the node's new parentNode
     */
    void setParentNode(Node newParent) {
        parentNode = newParent;
    }


    /*
     * This method is responsible for setting a node data from outside the class
     * @param newValue - The value we want to set
     */
    void setNodeData(int newValue) {
        nodeData = newValue;
    }


    /**
     * Responsible for returning the string representing the node.
     *
     * @return The Node string
     */
    public String toString() {
        return "" + this.nodeData;
    }
}
