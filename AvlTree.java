package oop.ex4.data_structures;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This class is implementing the AvlTree, implementing the Iterable interface.
 */
public class AvlTree extends BinaryTree implements Iterable<Integer> {

    // FIELDS //

    /* The Balancer object is responsible for maintaining the tree balanced */
    private Balancer balancer;
    /* An int representing a case in which the node isn't in the tree */
    private static final int NOT_CONTAINS = -1;
    /* An int representing the RIGHT child of a node */
    private static final int RIGHT = -1;
    /* An int representing the LEFT child of a node */
    private static final int LEFT = 1;
    /* An int representing the height of the root in the tree */
    private static final int ROOT_DEPTH = 0;
    /* An int representing the base of max nodes */
    private static final int BASE_OF_MAX_NODES = 2;
    /* An int representing the difference of max nodes */
    private static final int DELTA_MAX_NODES = 1;
    /* An int representing the base case of min nodes */
    private static final int DELTA_MIN_NODES_0 = 0;
    /* An int representing the base case of min nodes */
    private static final int DELTA_MIN_NODES_1 = 1;
    /* An int representing the base case of min nodes */
    private static final int DELTA_MIN_NODES_2 = 2;


    // CONSTRUCTORS //

    /**
     * The default constructor of the AvlTree class.
     */
    public AvlTree() {
        assignBalancer();
    }

    /**
     * A copy constructor that creates a deep copy of the given AvlTree.
     * The new tree contains all the values of the given tree, but not necessarily in the same structure.
     *
     * @param tree The AVL tree to be copied.
     */
    public AvlTree(AvlTree tree) {

        assignBalancer();
        if (tree != null) {
            for (int nodeValue : tree) {
                add(nodeValue);
            }
        }
    }

    /**
     * A constructor that builds a new AVL tree containing all unique values in the input array.
     *
     * @param data The values to add to tree.
     */
    public AvlTree(int[] data) {

        assignBalancer();
        if ((data != null) && (data.length > 0)) {
            for (int i = 0; i < data.length; i++) {
                add(data[i]); // add is maintaining the tree balanced
            }
        }
    }


    // METHODS //

    /*
     * This method is responsible for creating a Balance Manager instance for the class.
     */
    private void assignBalancer() {
        if (balancer == null) {
            balancer = Balancer.instance();
        }
    }

    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue The value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added,
     * false otherwise.
     */
    public boolean add(int newValue) {

        if (contains(newValue) == NOT_CONTAINS) { // not in tree

            Node toAddNode = new Node(newValue, null);
            Node newParent = getSpotInTree(treeRoot, newValue);

            if (newParent != null) {
                // Add to the correct son
                if (newValue > newParent.getData()) {
                    newParent.setRightSon(toAddNode);
                } else if (newValue < newParent.getData()) {
                    newParent.setLeftSon(toAddNode);
                }
                toAddNode.setParentNode(newParent);
                // check for violations
            } else { // tree is empty
                treeRoot = toAddNode;
            }
            treeSize++; // update tree size
            treeRoot = balancer.balanceManager(toAddNode, treeRoot);
            return true;
        }
        // was already in tree
        return false;

    }

    /*
     * This method is used for add and delete methods by returning the 'spot' in which the wanted node is
     * or should be in the future.
     * The methods returns the node itself if found (for DELETE) or the parent if doesn't exist (for ADD).
     * @param root - The root of the checked tree
     * @param wanted - The node we are looking for
     * @return The node itself if found, and parent if not
     */
    private Node getSpotInTree(Node root, int wantedValue) {

        if (root != null) {
            if (wantedValue != root.getData()) { // go to right son
                if (wantedValue > root.getData()) { // go to right son
                    if (root.getRightSon() == null) {
                        return root; // returns the \parent
                    } else {
                        return getSpotInTree(root.getRightSon(), wantedValue);
                    }
                } else { // go to right son
                    if (root.getLeftSon() == null) {
                        return root; // returns the parent
                    } else {
                        return getSpotInTree(root.getLeftSon(), wantedValue);
                    }
                }
            }
            return root;
        }
        return null;
    }


    /**
     * Check whether the tree contains the given input value.
     *
     * @param searchVal value to search for
     * @return if val is found in the tree, return the depth of the node (0 for the root)
     * with the given value if it was found in the tree, -1 otherwise.
     */
    public int contains(int searchVal) {
        return containsHelper(searchVal, treeRoot, ROOT_DEPTH);
    }


    /*
     * This method is a helper for containing method running recursively to find a node
     * @param searchVal - value to search for
     * @param currentRoot - current node for which we check the subTree
     * @param depth - the location of the node in tree
     * @return - The depth of the node if found, -1 if not.
     */
    private int containsHelper(int searchVal, Node currentRoot, int depth) {

        if (currentRoot == null) {
            return NOT_CONTAINS;
        } else if (currentRoot.getData() == searchVal) {
            return depth;
        }
        // check for right son:
        if (searchVal > currentRoot.getData()) {
            return containsHelper(searchVal, currentRoot.getRightSon(), depth + 1);
        } // else:
        return containsHelper(searchVal, currentRoot.getLeftSon(), depth + 1);
    }


    /*
     * This method is responsible for deleting a node that is a leaf
     * @param foundNode - The node we want to delete
     * @param parent - The parent of the node we want to delete
     */
    private void deleteLeaf(Node foundNode, Node parent) {
        //if the found node's parent is not null
        if (parent != null) {
            //if he's left son is not null
            if (parent.getLeftSon() != null) {
                //if the found node is it's parent's left child
                if (parent.getLeftSon().equals(foundNode)) {
                    //delete
                    parent.setLeftSon(null);
                }
                //if he's right son is not null
            }
            if (parent.getRightSon() != null) {
                //if the found node is it's parent's right child
                if (parent.getRightSon().equals(foundNode)) {
                    //delete
                    parent.setRightSon(null);
                }
            }
            foundNode.setParentNode(null);
        } else {
            // if the found node's parent is null and both of it's children are null, it's a single-node tree
            // Then make the tree root null:
            treeRoot = null;
        }
    }

    /*
     * This method is responsible for deleting a node that has 1 child
     * @param foundNode - The node we want to delete (or actually update it's value)
     * @param parent - The parent of the node we want to delete
     * @param rightOrLeft - is the node we want to delete the RIGHT or LEFT child of the parent?
     */
    private void deleteNodeOneChild(Node foundNode, Node parent, int rightOrLeft) {

        if (rightOrLeft == RIGHT) {

            if ((parent != null) || (foundNode != treeRoot)) {
                // place right child instead of node and remove it
                if (foundNode.getRightSon().getData() > parent.getData()) {
                    parent.setRightSon(foundNode.getRightSon());
                } else {
                    parent.setLeftSon(foundNode.getRightSon());
                }

                foundNode.getRightSon().setParentNode(parent);
                foundNode.setRightSon(null);
                foundNode.setParentNode(null);

            } else {
                Node newRoot = foundNode.getRightSon();
                newRoot.setParentNode(null);
                foundNode.setRightSon(null);
                treeRoot = newRoot;
            }
        } else if (rightOrLeft == LEFT) {

            if ((parent != null) || (foundNode != treeRoot)) {
                // place left child instead of node and remove it
                if (foundNode.getLeftSon().getData() > parent.getData()) {
                    parent.setRightSon(foundNode.getLeftSon());
                } else {
                    parent.setLeftSon(foundNode.getLeftSon());
                }

                foundNode.getLeftSon().setParentNode(parent);
                foundNode.setLeftSon(null);
                foundNode.setParentNode(null);

            } else {
                Node newRoot = foundNode.getLeftSon();
                newRoot.setParentNode(null);
                foundNode.setLeftSon(null);
                treeRoot = newRoot;
            }
        }
    }

    /*
     * This method is responsible for deleting a node that has 2 children
     * @param foundNode - The node we want to delete (or actually update it's value)
     */
    private Node deleteNodeTwoChildren(Node foundNode) {

        // get the data of followingNode and delete followingNode
        Node successor = successor(foundNode);
        Node successorParent = successor.getParentNode();
        int dataFollowing = successor.getData();
        if ((successor.getLeftSon() == null) && (successor.getRightSon() == null)) {
            deleteLeaf(successor, successorParent);
        } else if (successor.getLeftSon() != null) {
            deleteNodeOneChild(successor, successorParent, LEFT);
        } else if (successor.getRightSon() != null) {
            deleteNodeOneChild(successor, successorParent, RIGHT);
        }
        // update the found node to have (Y) data
        foundNode.setNodeData(dataFollowing);
        if (foundNode == successorParent) {
            return foundNode;
        }
        return successorParent;
    }

    /**
     * Removes the node with the given value from the tree, if it exists.
     *
     * @param toDelete The value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    public boolean delete(int toDelete) {

        if (contains(toDelete) != NOT_CONTAINS) { // The tree DOES contain this value
            Node foundNode = getSpotInTree(treeRoot, toDelete);
            if ((foundNode != null) && (foundNode.getData() == toDelete)) { // sanity check
                Node parent = foundNode.getParentNode();

                // if node has one child or no children
                if ((foundNode.getRightSon() == null) || (foundNode.getLeftSon() == null)) {
                    //if it's the right son
                    if (foundNode.getRightSon() != null) {
                        deleteNodeOneChild(foundNode, parent, RIGHT);
                        //if it's the left son
                    } else if (foundNode.getLeftSon() != null) {
                        deleteNodeOneChild(foundNode, parent, LEFT);
                        // if node has no children
                    } else {
                        deleteLeaf(foundNode, parent);
                    }
                    treeRoot = balancer.balanceManager(parent, treeRoot);
                    treeSize--;
                    // if node has 2 children
                } else if ((foundNode.getRightSon() != null) && (foundNode.getLeftSon() != null)) {
                    Node successorParent = deleteNodeTwoChildren(foundNode);
                    treeRoot = balancer.balanceManager(successorParent, treeRoot);
                    treeSize--;
                }
                return true;
            }
        }
        return false;
    }

    /*
     * This method find the Node with the highest value in tree
     *
     * @param root - The root of the tree (or subtree) we are checking
     * @return - The minimal node
     */
    private Node minNodeInTree(Node root) {
        Node currentNode = root;
        if (currentNode != null) {
            if (currentNode.getLeftSon() != null) {
                while (currentNode.getLeftSon() != null) {
                    currentNode = currentNode.getLeftSon();
                }
            }
            return currentNode;
        }
        return null;
    }

    /*
     * This method returns the 'following' node - that has the next lowest value (Y)
     *
     * @param checkedNode - The node for which we are looking for a follower
     * @return - The 'follower' node (Y)
     */
    private Node successor(Node checkedNode) {
        if (checkedNode.getData() >= maxNodeInSubTree(treeRoot).getData()) { // no successor
            return null;
        }
        if ((checkedNode != null) && (checkedNode.getRightSon() != null)) {
            return minNodeInTree(checkedNode.getRightSon());
        } else {
            if ((checkedNode != null) && (checkedNode.getParentNode() != null)) {
                Node currentNode = checkedNode.getParentNode();
                if (currentNode != null) {
                    while ((currentNode.getData() < checkedNode.getData()) &&
                            (currentNode.getParentNode() != null)) {
                        // keep looking for parents in the right
                        currentNode = currentNode.getParentNode();
                    }
                    if (currentNode.getData() > checkedNode.getData()) {
                        return currentNode;
                    }
                }
            }
        }
        // if got here means there is no node bigger that checkedNode
        return null;
    }

    /*
     * This method find the Node with the highest value in tree
     *
     * @param root - The root of the tree (or subtree) we are checking
     * @return - The maximal node
     */
    private Node maxNodeInSubTree(Node root) {
        Node currentNode = root;
        while (currentNode.getRightSon() != null) {
            currentNode = currentNode.getRightSon();
        }
        return currentNode;
    }

    /**
     * @return An iterator for the Avl Tree. The returned iterator iterates over the tree nodes
     * in an ascending order, and does NOT implement the remove() method.
     */
    public Iterator<Integer> iterator() {

        /* inner class of iterator */
        class IteratorElement implements Iterator<Integer> {

            private Node nextNode = minNodeInTree(treeRoot);

            @Override
            public boolean hasNext() {
                return (nextNode != null);
            }

            @Override
            public Integer next() throws NoSuchElementException {
                if (hasNext()) {
                    Node currentNode = nextNode;
                    nextNode = successor(nextNode);
                    return currentNode.getData();
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() throws UnsupportedOperationException {
                throw new UnsupportedOperationException();
            }
        }
        return new IteratorElement();
    }


    /**
     * Calculates the minimum number of nodes in an AVL tree of height h.
     *
     * @param h The height of the tree (a non-negative number) in question.
     * @return the minimum number of nodes in an AVL tree of the given height.
     */
    public static int findMinNodes(int h) {
        if (h == DELTA_MIN_NODES_0) {
            return DELTA_MIN_NODES_1;
        } else if (h == DELTA_MIN_NODES_1) {
            return DELTA_MIN_NODES_2;
        }
        int heightH = findMinNodes(h - DELTA_MIN_NODES_1) +
                findMinNodes(h - DELTA_MIN_NODES_2) + DELTA_MIN_NODES_1;
        return heightH;
    }

    /**
     * Calculates the maximum number of nodes in an AVL tree of height h.
     *
     * @param h The height of the tree (a non-negative number) in question.
     * @return the maximum number of nodes in an AVL tree of the given height.
     */
    public static int findMaxNodes(int h) {
        return (int) Math.pow(BASE_OF_MAX_NODES, (h + DELTA_MAX_NODES)) - DELTA_MAX_NODES;
    }

    /**
     * @return the number of nodes in the tree.
     */
    public int size() {
        return treeSize;
    }


}