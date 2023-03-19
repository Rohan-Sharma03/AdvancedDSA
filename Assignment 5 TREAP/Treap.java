import java.util.Random;

/**
 * Author : Rohan Sharma
 * Created : 16 March 2023
 * Last Update : 16 March 2023
 * Topic : TREAP
 * Reference Link : https://users.cs.fiu.edu/~weiss/dsaajava2/code/Treap.java
 */
public class Treap {

    // This is standard node class, will be using to buld treap.
    private static class TreapNode {
        // used to identify key in currentNode
        int key;
        // used to indentify priority in current node
        int priority;
        // used to link samller value to left of current node, if present else assign
        // Null-Node.
        TreapNode left;
        // used to link bigger value to right of current node if present else assign
        // Null-Node.
        TreapNode right;

        // Constructor of treap Node, use to set dafault values in the current node.

        TreapNode(int key) {
            this.key = key;
            this.priority = RandomObj.nextInt();
            this.left = null;
            this.right = null;
        }

        TreapNode(int key, TreapNode leftNode, TreapNode RightNode, int priority) {
            this.key = key;
            this.priority = priority;
            this.left = leftNode;
            this.right = RightNode;
        }

        // Random value obj, will be used to assign priority value to nodes.
        private static Random RandomObj = new Random();

    }

    // This is the root node of the treap.
    TreapNode root;
    // This is the null node of the treap.
    TreapNode nullNode;

    // Constructor of TREAP, this will initialize the tree.
    Treap() {
        // assign initial value to nullnode.
        nullNode = new TreapNode(0);
        // assign nullNode to left & right of nullNode. (i.e updated form null to null
        // node)
        nullNode.left = nullNode;
        nullNode.right = nullNode;
        // updated the priority assign by construtor
        nullNode.priority = Integer.MAX_VALUE;
        root = nullNode;
    }

    // This method will insert a key in the treap.
    public void insert(int key, int priority) {
        // InsertHelp method will return new node (as new root node, with maximum
        // priority) to update root after insertion a key.
        root = InsertHelp(key, root, priority);

    }

    private TreapNode InsertHelp(int key, TreapNode root, int priority) {
        // if root (it can be internal node too) & nullNode are same then return a
        // newNode by updating .
        if (root == nullNode) {
            return new TreapNode(key, nullNode, nullNode, priority);
        }
        // if key is less than root.key then insert in left subtree.
        if (key < root.key) {
            // will return new node to update as left child of current root node.
            root.left = InsertHelp(key, root.left, priority);
            // if priority of root is less than left child then rotate right.
            if (root.priority < root.left.priority) {
                root = rotateRight(root);
            }

        }
        // if key is greater than root.key then insert in right subtree.
        else if (key > root.key) {
            root.right = InsertHelp(key, root.right, priority);
            // if priority of root is less than right child then rotate left.
            if (root.priority < root.right.priority) {
                root = rotateLeft(root);
            }

        }
        // if key is equal to root.key then return root.
        else {
            return root;
        }
        // else return root simply.
        return root;
    }

    // This method will rotate the node to left.
    private TreapNode rotateLeft(TreapNode root) {
        // hold the current node's value in new root
        TreapNode newRoot = root.left;
        root.left = newRoot.right;
        newRoot.right = root;
        return newRoot;
    }

    // This method will rotate the node to right.
    private TreapNode rotateRight(TreapNode root) {
        // hold the current node's value in new root
        TreapNode newRoot = root.right;
        root.right = newRoot.left;
        newRoot.left = root;
        return newRoot;

    }

    // This method will be used to print TREAP.
    public void show() {
        showHelp(root);
    }

    public void showHelp(TreapNode root) {
        if (root == nullNode) {
            return;
        }
        showHelp(root.left);
        showHelp(root.right);
        System.out.println(root.key);
    }

    private boolean isEmpty() {
        return false;
    }

    public void delete(int key) {

    }

    public void contains(int key) {

    }

    public static void main(String[] args) {
        System.out.println("Rohan");
        Treap t = new Treap();
        int priority[] = { 99, 83, 76, 3, 47, 53, 25, 10, 12 };
        int nums[] = { 35, 3, 80, 43, 86, 1, 14, 7, 27 };
        for (int i = 0; i < nums.length; i++) {
            t.insert(nums[i], priority[i]);
        }
        t.show();
    }
}