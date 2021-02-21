package oop.ex4.data_structures;

/**
 * a Singleton class that holds everything to do with the rotation of the tree
 */
class Rotator {

    // FIELDS //

    /* the instance */
    private static Rotator rotator = new Rotator();
    /* violation- balance factor -2 */
    private static final int RIGHT_VIOLATION=-2;
    /* violation- balance factor of right subtree is 1 */
    private static final int RIGHT_SUB_VIOLATION=1;
    /* violation- balance factor 2 */
    private static final int LEFT_VIOLATION=2;
    /* violation- balance factor of left subtree is -1 */
    private static final int LEFT_SUB_VIOLATION=-1;
    /* code for LL violation- */
    private static final int LL_CODE =1;
    /* code for LR violation- */
    private static final int LR_CODE =3;
    /* code for RR violation- */
    private static final int RR_CODE =2;
    /* code for RL violation */
    private static final int RL_CODE =4;
    /* if balance factor is |balanceFactor|>2 */
    private static final int SOMETHING_IS_WRONG = 0;
    /*the tree's root*/
    private static Node treeRoot;


    // METHODS //

    /*
     * the class constructor
     */
    private Rotator(){}

    /*
     * the class's instance method
     * @return the only Rotator instance
     */
    static Rotator instance(){
        return rotator;
    }

    /*
     * this function manages the rotating- it calls the diagnoseViolation function, calls the suitable
     * rotating function and returns the root of the corrected tree
     * @param violatingNode the first node violating, sent from the findViolation function in the Balancer
     * @return returns the root of the new tree if the violation is fixed, and null otherwise
     */
    Node rotateManager(Node violatingNode, Node currentRoot){

        treeRoot = currentRoot;
        //get violation type
        int violation = diagnoseViolation(violatingNode);
        //if balance factor is |balanceFactor|>2- return null
        if (violation==0){
            return null;
        }
        //calling the rotation methods according to the relevant violation
        if (violation==RL_CODE){
            rightLeftRotation(violatingNode);
        } else if (violation==RR_CODE) {
            rightRightRotation(violatingNode);
        } else if (violation==LR_CODE) {
            leftRightRotation(violatingNode);
        } else if (violation==LL_CODE){
            leftLeftRotation(violatingNode);

        } if (violatingNode == currentRoot){
            if (currentRoot.getParentNode()!=null){
                return currentRoot.getParentNode();
            }
        } return currentRoot;
    }

    /*
     * this function diagnoses the type of the violation and returns the matching code (a class constant)
     * @param violatingNode the first node violating, sent from the findViolation function in the Balancer
     * @return the matching code (a class constant)
     */
    private int diagnoseViolation(Node violatingNode){
        //if its a RL or RR violation- the the balance factor is -2:
        if (violatingNode.getBalanceFactor() == RIGHT_VIOLATION){
            if (violatingNode.getRightSon() != null){
                //RR violation- if the right sub tree's balance factor is -1 or 0, the violation is RR-
                if (violatingNode.getRightSon().getBalanceFactor() < RIGHT_SUB_VIOLATION){
                    return RR_CODE;
                }
            } //else- if the right sub tree's balance factor is 1, the violation is RL-
            return RL_CODE;
        }
        //if its LL or LR violation- then the balance factor is 2
        if (violatingNode.getBalanceFactor()==LEFT_VIOLATION){
            //LL violation- if the left sub tree's balance factor is 1 or 0, the violation is LL-
            if (violatingNode.getLeftSon() != null){
                if(violatingNode.getLeftSon().getBalanceFactor() == LEFT_SUB_VIOLATION){
                    return LR_CODE;
                }
            } //LR violation- if the left sub tree's balance factor is -1, the violation is LR-
            return LL_CODE;
            //if the violatingNode's balance factor is |balanceFactor|>2, there's something wrong
        } return SOMETHING_IS_WRONG;
    }

    /* DOUBLE ROTATION METHODS */

    /*
     * called if the violation is a RR one.
     */
    private void rightRightRotation(Node violatingNode){
        leftRotation(violatingNode);
    }

    /*
     * called if the violation is a LL one.
     */
    private void leftLeftRotation(Node violatingNode){
        rightRotation(violatingNode);
    }


    /*
     * called if the violation is a RL one.
     */
    private void rightLeftRotation(Node violatingNode){
        rightRotation(violatingNode.getRightSon());
        leftRotation(violatingNode);
    }

    /*
     * called if the violation is a LR one.
     */
    private void leftRightRotation(Node violatingNode){
        leftRotation(violatingNode.getLeftSon());
        rightRotation(violatingNode);
    }


    /*
     * this function performs a left rotation on a given violating node, and used in the double-rotation
     * functions
     * @param violatingNode the found violating node
     */
    private void leftRotation(Node violatingNode){
        //initializations-
        Node rightSon=violatingNode.getRightSon();
        Node rightLeftSon, violatingParent;
        if (violatingNode!=treeRoot){
            violatingParent=violatingNode.getParentNode();
        } else {
            violatingParent = null;
        }
        if (rightSon!=null){
        //checking the right-left grandson is not null
            if (rightSon.getLeftSon()==null){
                rightLeftSon = null;
            } else {
                rightLeftSon=rightSon.getLeftSon();
                rightLeftSon.setParentNode(violatingNode);
            }
            //updating the violating node's right son and the right-left grandson accordingly
            violatingNode.setRightSon(rightLeftSon);
            //updating the right son's parent to be the violating parent and the other way around-
            rightSon.setParentNode(violatingParent);
            //updating the violating node's parent to be the right son, and the other way around-
            violatingNode.setParentNode(rightSon);
            rightSon.setLeftSon(violatingNode);

        } else {
            violatingNode.setRightSon(null);
        } if (violatingParent!=null){
            if (violatingParent.getLeftSon()==violatingNode){
                violatingParent.setLeftSon(rightSon);
            } else {
                violatingParent.setRightSon(rightSon);
            }
        }
    }


    /*
     * this function performs a right rotation on a given violating node, and used in the double-rotation
     * functions
     * @param violatingNode the found violating node
     */
    private void rightRotation(Node violatingNode){
        //initializations-
        Node leftSon, violatingParent, leftRightSon;
        if (violatingNode.getLeftSon()==null){
            leftSon = null;
            leftRightSon = null;
        } else{
            leftSon = violatingNode.getLeftSon();
            leftRightSon=leftSon.getRightSon();
        } if (violatingNode.getParentNode()==null){
            violatingParent = null;
        } else{
            violatingParent=violatingNode.getParentNode();
        }
        // checking the left-right grandson is not null
        // Then updating the violating node's left son and the left-right grandson accordingly
        if (leftRightSon!=null){
            violatingNode.setLeftSon(leftRightSon);
            leftRightSon.setParentNode(violatingNode);
        } else {
            violatingNode.setLeftSon(null);
        }
        // updating the left son's parent to be the violating parent and the other way around-
        if (violatingParent!=null){
            if (violatingParent.getLeftSon()==violatingNode){
                violatingParent.setLeftSon(leftSon);
            } else {
                violatingParent.setRightSon(leftSon);
            }
        }
        //updating the violating node's parent to be the left son, and the other way around-
        if (leftSon!=null){
            leftSon.setParentNode(violatingParent);
            leftSon.setRightSon(violatingNode);
        }
        violatingNode.setParentNode(leftSon);
    }
}
