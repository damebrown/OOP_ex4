package oop.ex4.data_structures;

/*
 * A Singleton class that manages everything to do with the balancing of the tree.
 * this class manages the balance factors and checks if needed and if so, calls the Rotator class to balance
 * the tree.
 */
class Balancer {


    // FIELDS //

    /* the instance of the balancer */
    private static Balancer balanceManager = new Balancer();
    /* the tree root */
    private Node treeRoot;
    /*the class's rotator instance */
    private static Rotator rotator;


    /*
     * the class constructor, creating the Rotator instance
     */
    private Balancer() {
        rotator = Rotator.instance();
    }

    /*
     * the class's instance method
     * @return the only Balancer instance
     */
    static Balancer instance() {
        return balanceManager;
    }

    /*
     * the class's main function that manages the whole balancing procedure. receives the tree's current root
     * (and assigning it to the class's treeRoot field) and the inserted node or the parent of deleted node.
     * the function updates the balance factors of all of the node's ancestors and then checks if the tree is
     * balanced.
     * if it's not, it will diagnose the violation type and call the relevant Rotator functions accordingly.
     * @param suspectedNode the inserted node or the parent of the deleted node
     * @param currentTreeRoot the current root
     * @return true if the tree is balanced?
     */
    Node balanceManager(Node suspectedNode, Node currentTreeRoot) {

        if (suspectedNode != null) {
            //updating the class's tree root-
            treeRoot = currentTreeRoot;
            //updating the suspected node's ancestor's balance factor so that we will be able to work with
            // them properly
            updateAncestorsBalanceFactor(suspectedNode);
            updateOffspringsBalanceFactor(suspectedNode);

            //searching for a violation-
            Node violatingNode = findViolation(suspectedNode);

            //if a violation is found, we will call the Rotator 'rotateManager' function to make things right-
            //we'll call it again until the tree is balanced, while updating the tree root to the new one
            // created by the rotator
            while (violatingNode != null) {
                treeRoot = rotator.rotateManager(violatingNode, currentTreeRoot);
                updateAncestorsBalanceFactor(violatingNode);
                updateOffspringsBalanceFactor(violatingNode);
                violatingNode = findViolation(suspectedNode);
            }
        } else {
            treeRoot = currentTreeRoot;
        }
        //we will return the root of the new fixed tree-
        return treeRoot;
    }

    /*
     * searches for the first node which violates the AVL property on the way up fro the parent of
     * deleted/inserted node if exists, the function returns the violating node. else, returns null.
     * @param suspectedNode the inserted/parent of deleted node
     * @return if found, the violating node is returned. else, null is returned
     */
    private Node findViolation(Node suspectedNode) {

        //starting from the suspected node's parent- this is the first possible place for a violation
        Node parent = suspectedNode;

        //we will check all nodes up to the root if they are violating the balance, and return them if so
        while ((parent != treeRoot) && (parent != null)) {
            if (!isBalanced(parent)) {
                return parent;
            }
            parent = parent.getParentNode();

        } //if every node was balanced, it must be the root
        if ((!isBalanced(treeRoot)) && (treeRoot != null)) {
            return treeRoot;

            //no violation found, returning null-
        }
        return null;
    }

    /*
     * checks if the given node's subtree is balanced
     * @param node a node in the tree
     * @return boolean value, true if balanced, false else
     */
    private boolean isBalanced(Node node) {

        if (node != null) {
            //checking if the given node's subtree is balanced
            if ((Math.abs(node.getBalanceFactor())) > 1) {
                //if the node's balance factor is greater then one in absolute value- returns false
                return false;
                //returns false otherwise
            }
        }
        return true;
    }

    /*
     * this function gets the node inserted or the parent of the deleted node and updates his ancestors
     * balance factor
     * @param node the node inserted or the parent of the deleted node
     */
    private void updateAncestorsBalanceFactor(Node node) {

        //iterating over the node's ancestors and updating their balance factors using setBalanceFactor method
        Node currentSubRoot = node;
        while ((currentSubRoot != treeRoot) && (currentSubRoot != null)) {
            setBalanceFactor(currentSubRoot);
            currentSubRoot = currentSubRoot.getParentNode();

        }
        setBalanceFactor(treeRoot);
    }

    /*
     * A recursive function, that gets a sub root and a counter (0)
     * @param subRoot a root of a subtree
     * @param heightSoFar the height of the tree counted until this point in the recursion
     * @return returns the height of the higher subtree of the subroot
     */
    private int getHeightOfSubtree(Node subRoot, int heightSoFar) {

        // initializing the left and right height counters
        int heightOfRightSubtree = 0, heightOfLeftSubtree = 0;
        // base case- if the node is a leaf, return heightSoFar
        if ((subRoot.getLeftSon() == null) && (subRoot.getRightSon() == null)) {
            return heightSoFar;
        }
        // if a right subtree exists, calling the recursive function for it
        if (subRoot.getRightSon() != null) {
            heightOfRightSubtree = getHeightOfSubtree(subRoot.getRightSon(), heightSoFar + 1);
//            return heightOfRightSubtree;
        }
        // if a left subtree exists, calling the recursive function for it
        if (subRoot.getLeftSon() != null) {
            heightOfLeftSubtree = getHeightOfSubtree(subRoot.getLeftSon(), heightSoFar + 1);
//            return heightOfLeftSubtree;
        }
        // returning the bigger height of the subtrees
        if (heightOfLeftSubtree > heightOfRightSubtree) {
            return heightOfLeftSubtree;
        }
        return heightOfRightSubtree;
    }

    /*
     * setting the balance factor of the given subroot using the getHeightOfSubtree function
     * @param subRoot a subroot of a tree
     */
    private void setBalanceFactor(Node subRoot) {

        if (subRoot != null) {
            if ((subRoot.getRightSon() == null) && (subRoot.getLeftSon() != null)) {
                subRoot.setBalanceFactor(getHeightOfSubtree(subRoot.getLeftSon(), 1));

            } else if ((subRoot.getRightSon() != null) && (subRoot.getLeftSon() == null)) {
                subRoot.setBalanceFactor(-getHeightOfSubtree(subRoot.getRightSon(), 1));

            } else if ((subRoot.getRightSon() != null) && (subRoot.getLeftSon() != null)) {
                subRoot.setBalanceFactor(getHeightOfSubtree(subRoot.getLeftSon(), 1) -
                        getHeightOfSubtree(subRoot.getRightSon(), 1));

            } else if ((subRoot.getRightSon() == null) && (subRoot.getLeftSon() == null)) {
                subRoot.setBalanceFactor(0);
            }
        }
    }

    /*
     * This method is updating the balance factor for all the children in a given subTree
     * @param subroot - The subRoot for which we are updating the
     */
    private void updateOffspringsBalanceFactor(Node subroot) {

        if (subroot != null) {
            if ((subroot.getLeftSon() == null) && ((subroot.getRightSon() == null))) {
                setBalanceFactor(subroot);

            } else {
                if (subroot.getLeftSon() != null) {
                    updateOffspringsBalanceFactor(subroot.getLeftSon());
                    setBalanceFactor(subroot);
                }
                if (subroot.getRightSon() != null) {
                    updateOffspringsBalanceFactor(subroot.getRightSon());
                    setBalanceFactor(subroot);
                }
            }
        }
    }

}
