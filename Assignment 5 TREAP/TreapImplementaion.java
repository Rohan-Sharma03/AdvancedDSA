import java.util.Random;

class TreapNode {
    int key;
    int priority;
    TreapNode left, right;

    TreapNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.priority = new Random().nextInt(100);
    }
}

public class TreapImplementaion {

    public static TreapNode rotateLeft(TreapNode root) {
        TreapNode R = root.right;
        TreapNode X = root.right.left;
        // Rotation
        R.left = root;
        root.right = X;
        // make new root;
        return R;
    }

    public static TreapNode rotateRight(TreapNode root) {
        TreapNode L = root.left;
        TreapNode X = root.left.right;
        // Rotation
        L.right = root;
        root.left = X;
        // make new root;
        return L;
    }

    // Recursive function to insert a key with priority in treap.
    public static TreapNode insert(TreapNode root, int key) {
        // if root(CURRENT node) is return a new node with key. (having Random
        // priority);
        if (root == null) {
            return new TreapNode(key);
        }
        // if key samller then root(CURRENT node) then insert in left subtree.
        if (key < root.key) {
            root.left = insert(root.left, key);
            // while inserting if heap priority property violates raotate accordingly.
            if (root.left != null && root.priority < root.left.priority) {
                root = rotateRight(root);
            }
        }
        // if key greater then root(CURRENT node) then insert in right subtree.
        else if (key > root.key) {
            root.right = insert(root.right, key);
            // while inserting if heap priority property violates raotate accordingly.
            if (root.right != null && root.priority < root.right.priority) {
                root = rotateLeft(root);
            }
        }
        // if key is equal to root(CURRENT node) then return root.
        else {
            return root;
        }
        // else return root simply.
        return root;
    }

    // Recursive function to delete a key from treap.
    public static TreapNode delete(TreapNode root, int key) {
        // if root(CURRENT node) is null then return null.
        if (root == null) {
            return null;
        }
        // if key samller then root(CURRENT node) then delete in left subtree.
        if (key < root.key) {
            root.left = delete(root.left, key);
        }
        // if key greater then root(CURRENT node) then delete in right subtree.
        else if (key > root.key) {
            root.right = delete(root.right, key);
        }
        // if key is found
        else {
            // Case 1 : node to be deleted is leaf (has no children)
            if (root.left == null && root.right == null) {
                // make it null (i.e deallocate memory)
                root = null;
            }
            // Case 2 : node to be deleted has only 1 children
            // A : Has only right child.
            else if (root.left == null) {
                root = root.right;
            }
            // B : Has only left child;
            else if (root.right == null) {
                root = root.left;
            }
            // if both left & right child are not null then rotate accordingly.
            else {
                // if left child priority is greater then right child priority then rotate
                // right.
                if (root.left.priority > root.right.priority) {
                    // call rotateRight on CURRENT root node.
                    root = rotateRight(root);
                    // recursively delete that key. (it will become leaf node)
                    root.right = delete(root.right, key);
                }
                // if right child priority is greater then left child priority then rotate
                // left.
                else {
                    // call rotateLeft on CURRENT root node.
                    root = rotateLeft(root);
                    // recursively delete that key. (it will become leaf node)
                    root.left = delete(root.left, key);
                }
            }
        }
        // else return root simply.
        return root;
    }

    // Recursive function to search a key in treap.
    public static boolean search(TreapNode root, int key) {
        // if root(CURRENT node) is null then return false.
        if (root == null) {
            return false;
        }
        // if key samller then root(CURRENT node) then search in left subtree.
        if (key < root.key) {
            return search(root.left, key);
        }
        // if key greater then root(CURRENT node) then search in right subtree.
        else if (key > root.key) {
            return search(root.right, key);
        }
        // if key is equal to root(CURRENT node) (i.e found ) then return true.
        else {
            return true;
        }
    }

    // Recursive function to print treap in inorder.
    public static void printTreap(TreapNode root) {
        if (root == null) {

            return;
        }
        printTreap(root.left);
        System.out.print(root.key + " ");
        printTreap(root.right);
    }

    public static void main(String[] args) {
        TreapImplementaion treap = new TreapImplementaion();
        TreapNode root = null;
        int nums[] = { 35, 3, 80, 43, 86, 1, 14, 7, 27 };
        for (int x : nums) {
            root = insert(root, x);
        }
        treap.printTreap(root);
        treap.delete(root, 27);
        System.out.println();
        System.out.println(treap.search(root, 80));
        treap.printTreap(root);

    }

}
