package oop.ex4.data_structures;


/**
 * This class is representing a binary search tree, which has up to 2 children for each node.
 */
public abstract class BinaryTree implements Iterable<Integer> {

    /*This represents the node of the tree's root*/
    Node treeRoot;
    /*Represents the size of the tree*/
    int treeSize;

    // DEFAULT CONSTRUCTOR //

    // METHODS //

    /**
     * @return the number of nodes in the tree.
     */
    protected abstract int size();
    // is implemented in the inherited trees

    /**h
     * Check whether the tree contains the given input value.
     *
     * @param searchVal value to search for
     * @return if val is found in the tree, return the depth of the node (0 for the root)
     * with the given value if it was found in the tree, -1 otherwise.
     */
    protected abstract int contains(int searchVal);
    // is implemented in the inherited trees


    /**
     * Add a new node with the given key to the tree.
     *
     * @param newValue The value of the new node to add.
     * @return true if the value to add is not already in the tree and it was successfully added,
     * false otherwise.
     */
    protected abstract boolean add(int newValue);
    // is implemented in the inherited trees


    /**
     * Removes the node with the given value from the tree, if it exists.
     *
     * @param toDelete The value to remove from the tree.
     * @return true if the given value was found and deleted, false otherwise.
     */
    protected abstract boolean delete(int toDelete);
    // is implemented in the inherited trees
}
