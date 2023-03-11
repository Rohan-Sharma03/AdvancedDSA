/**
 * Author : Rohan Sharma
 * Created : 11 March 2023
 * Last Update : 11 March 2023
 * Topic : B Tree
 * Reference Link : https://www.programiz.com/dsa/b-tree
 */

class BTree {
    private int T;

    public class BTreeNode {
        // number of keys in the node.
        int order;
        // keys in current node
        int keys[] = new int[2 * T - 1];
        // children of current node.
        BTreeNode children[] = new BTreeNode[2 * T];
        // boolean value to check wheather current node is leaf or not.
        boolean leaf = true;

        // Fucntion to find key in current node.
        public int FindKey(int key) {
            for (int i = 0; i < this.order; i++) {
                if (key == this.keys[i]) {
                    return i;
                }
            }
            return -1;
        }
    }

    BTree(int t) {
        // object initailization with order of t
        T = t;
        root = new BTreeNode();
        root.order = 0;
        root.leaf = true;
    }

    private BTreeNode root;

    // Searching key
    public BTreeNode search(BTreeNode node, int key) {
        int i = 0;
        // if node is null i.e(empty) then return null because we can't find specified
        // key in current node when it's empty.
        if (node == null) {
            return null;
        }
        // loop out every key in the current node to find it's location in that node.
        for (i = 0; i < node.order; i++) {
            // if the key value is smaller then the smallest value in current node then
            // return null (no possiblity to get found in it)
            if (key < node.keys[i]) {
                break;
            }
            // if key found return that node.
            if (key == node.keys[i]) {
                return node;
            }
        }
        // if not found in the current node, check if it is leaf if so return null else
        // go with the next node (i.e check in the children nodes)
        if (node.leaf) {
            return null;
        } else {
            return search(node.children[i], key);
        }
    }

    private void splitChild(BTreeNode newRoot, int position, BTreeNode oldRoot) {
        /*
         * If # of values in node exceeded the limit (order), then we split the node on
         * middle value, and this value is then promoted to upper level and current node
         * then becomes the child of the promoted value. keys which are samller than the
         * medain value becomes left child and keys greater than the medain becomes
         * right child.
         */
        // make a new node
        BTreeNode newNode1 = new BTreeNode();
        // make leaf status of newNode1 to same as oldRoot
        newNode1.leaf = oldRoot.leaf;
        // newNode1 can hold upto T-1 keys.
        newNode1.order = T - 1;
        // assigning keys to newNode1 (the keys will greater than medain)
        for (int j = 0; j < T - 1; j++) {
            // j+T beacuse we need values next to medain.
            newNode1.keys[j] = oldRoot.keys[j + T];
        }
        // if old root is internal node, then copy it's children in way that children
        // right to medain becomes children of newNode1.
        if (!oldRoot.leaf) {
            // copy children of the of the keys right to median.
            for (int j = 0; j < T; j++) {
                newNode1.children[j] = oldRoot.children[j + T];
            }
        }

        // order of oldRoot is changed to T-1, because split decreases size of the node,
        // helps to shrink node.
        oldRoot.order = T - 1;
        // if order of newRoot is Zero then skip the loop and make newNode1 as the 2nd
        // children of newRoot, if not order is non zero then make assign right(medain)
        // child accordingly .
        for (int j = newRoot.order; j >= position + 1; j--) {
            newRoot.children[j + 1] = newRoot.children[j];
        }
        // lastly update right child of medain as new node.(where split took place).
        newRoot.children[position + 1] = newNode1;
        // if order of newRoot is Zero then skip the loop and make oldRoot.keys[T-1] as
        // the 1st key of newRoot, if order is non zero then make space for the medain
        // value which is poped up.
        for (int j = newRoot.order - 1; j >= position; j--) {
            newRoot.keys[j + 1] = newRoot.keys[j];
        }
        // assigned the poped value.
        newRoot.keys[position] = oldRoot.keys[T - 1];
        // update the order of newRoot
        newRoot.order = newRoot.order + 1;
    }

    private void insertNonFull(BTreeNode Node, int key) {
        /*
         * Firstly we insert in leaf node, if insertion not possible perform split
         * operation
         */
        // inserting in leaf node (i.e Inserting value).
        if (Node.leaf) {
            int i = 0;
            // loop out every key in the current node to find it's proper fit location in
            // that node.
            for (i = Node.order - 1; i >= 0 && key < Node.keys[i]; i--) {
                Node.keys[i + 1] = Node.keys[i];
            }
            Node.keys[i + 1] = key;
            Node.order = Node.order + 1;
        }
        // inserting in internal node
        else {
            int i = 0;
            for (i = Node.order - 1; i >= 0 && key < Node.keys[i]; i--) {
            }
            i++;
            BTreeNode temp = Node.children[i];
            if (temp.order == ((2 * T) - 1)) {
                splitChild(Node, i, temp);
                if (key > temp.keys[i]) {
                    i++;
                }
            }
            insertNonFull(Node.children[i], key);
        }
    }

    // inseting a value
    void Insert(int key) {
        // get the root of the tree
        BTreeNode oldRoot = root;
        // if the order of root exceeded the limit of its maximum order, then make new
        // root out of it and assign childrens accordingly. else go with inserting
        // values
        // in sorted form in the current root node.
        if (oldRoot.order == ((2 * T) - 1)) {
            // make a new
            BTreeNode newRoot = new BTreeNode();
            root = newRoot;
            /*
             * make leaf status of newRoot to false as oldRoot has been splited and it
             * became leaf.
             */
            newRoot.leaf = false;
            // order is 0 for new node
            newRoot.order = 0;
            // assigning children
            newRoot.children[0] = oldRoot;
            // call split child and insert value.
            splitChild(newRoot, 0, oldRoot);
            insertNonFull(newRoot, key);
        } else {
            insertNonFull(oldRoot, key);
        }
    }

    // printing the tree level wise
    void show() {
        // Starting from root node.
        Show(root);
        System.out.println();
    }

    // show helper function
    void Show(BTreeNode root) {
        // printing the keys of the current node
        for (int i = 0; i < root.order; i++) {
            System.out.print(root.keys[i] + " ");
        }
        // if node is not leaf then check for its children and print the key values in
        // it.
        if (!root.leaf) {
            for (int i = 0; i < root.order + 1; i++) {
                Show(root.children[i]);
            }
        }
    }

    // function to check wheater the given key is present inside the tree or not.
    public boolean Contain(int key) {
        if (search(root, key) != null) {
            return true;
        }
        return false;
    }

    public void delete(int key) {
        // will return the node which contains the key.
        BTreeNode node = search(root, key);
        if (node == null) {
            System.out.println("Key not found deletion can be done");
            return;
        }
        // recursive call for delete.
        deleteByCondition(root, key);

    }

    private void deleteByCondition(BTreeNode node, int key) {
        // finding the index value where the key is placed in node.
        int position = node.FindKey(key);
        // if key present in node.
        if (position != -1) {
            // if node is a leaf node.
            if (node.leaf) {
                int i = 0;
                // finding its position in the node.
                for (i = 0; i < node.order && node.keys[i] != key; i++) {
                }
                ;
                for (; i < node.order; i++) {
                    // if the index i is whithin the range of node order then the key can be deleted
                    // by coyping next element to current position such that the value of that key
                    // must be lost.
                    if (i != ((2 * T) - 2)) {
                        node.keys[i] = node.keys[i + 1];
                    }
                }
                // after deleting the key the order of node must be decreased by 1.
                node.order--;
                return;
            }
            // if node is an internal node
            if (!node.leaf) {
                // as we are going to delete key in internal node, there might be a possiblity
                // that key has childrens. we need to store that children node in temp node to
                // get predecessor form it.
                BTreeNode predecessor = node.children[position];
                int predecessorKey = 0;
                // if the order of predecessor is greater than T then we can find the
                // predecessor of the key and replace the key with predecessor.
                if (predecessor.order >= T) {
                    for (;;) {
                        if (predecessor.leaf) {
                            // extracting last key in the predecessor node, i.e inorder predecessor
                            System.out.println(predecessor.order);
                            predecessorKey = predecessor.keys[predecessor.order - 1];
                            break;
                        } else {
                            // if predecessor is not leaf then we need to go to the last child of the
                            // predecessor node.
                            predecessor = predecessor.children[predecessor.order];

                        }
                    }
                    // deleting that predecesor key form precesesor node
                    deleteByCondition(predecessor, predecessorKey);
                    // replacing the key with predecessor key.
                    node.keys[position] = predecessorKey;
                    return;
                }
                // if the order of predecessor is less than T then we need to find the successor
                // of the key and replace the key with successor.
                BTreeNode successor = node.children[position + 1];
                // if the order of successor is greater than T then we can find the successor of
                // the key and replace the key with successor.
                if (successor.order >= T) {
                    int successorKey = successor.keys[0];
                    if (!successor.leaf) {
                        successor = successor.children[0];
                        for (;;) {
                            if (successor.leaf) {
                                // extracting first key in the successor node, i.e inorder successor
                                successorKey = successor.keys[successor.order - 1];
                                break;
                            } else {
                                // if successor is not leaf then we need to go to the first child of the
                                // successor node.
                                successor = successor.children[successor.order];
                            }
                        }
                    }
                    // deleting that successor key form successor node
                    deleteByCondition(successor, successorKey);
                    // replacing the key with successor key.
                    node.keys[position] = successorKey;
                    return;
                }
                // add the key from the given node(which was supposed to be deleted) to
                // predecessor node.
                predecessor.keys[predecessor.order++] = node.keys[position];
                // copying the keys form successor node to predecessor node bcoz the both have
                // minimum number of element in it. i.e mergeing of predecessor and succesor
                // node taking place
                for (int i = 0, j = predecessor.order; i < successor.order; i++) {
                    predecessor.keys[j++] = successor.keys[i];
                    predecessor.order++;
                }
                // after copying keys, copy the childrens too in predecessor node.
                for (int i = 0, j = predecessor.order + 1; i < successor.order + 1; i++) {
                    predecessor.children[j++] = successor.children[i];
                }
                // updating the childrens of node from which the key is needed to be deleted.
                node.children[position] = predecessor;
                // updating the keys in node to deleted KEY form node.
                for (int i = position; i < node.order; i++) {
                    // do not exceed the maximum order node
                    if (i != ((2 * T) - 1)) {
                        node.keys[i] = node.keys[i + 1];
                    }
                }
                for (int i = position + 1; i < node.order + 1; i++) {
                    // do not exceed the maximum order node
                    if (i != ((2 * T) - 1)) {
                        node.children[i] = node.children[i + 1];
                    }
                }
                // after deleting key from node decrease the order of node by 1;
                node.order = node.order - 1;
                // if order of node becomes Zero, make that node first children node => root.
                if (node.order == 0) {
                    if (node == root) {
                        root = node.children[0];
                    }
                    node = node.children[0];
                }
                // delete the key form predecessor node as it was copied into it.
                deleteByCondition(predecessor, key);
                return;
            }

        } else {
            // loop out each key and compare it with the given key to find it's positon in
            // the node where it would be found if it is not present in that node.
            for (position = 0; position < node.order; position++) {
                if (node.keys[position] > key) {
                    break;
                }
            }
            // find the node which has possiblity of having key.
            BTreeNode childNode = node.children[position];
            // if order of that childNode is greater than eqaull to required value, then
            // attempt to delete given key.
            if (childNode.order >= T) {
                deleteByCondition(childNode, key);
                return;
            }
            // if order is < T
            if (true) {
                //
                BTreeNode nb = null;
                int divider = -1;
                if (position != node.order && node.children[position + 1].order >= T) {
                    divider = node.keys[position];
                    nb = node.children[position + 1];
                    node.keys[position] = nb.keys[0];
                    childNode.keys[childNode.order++] = divider;
                    childNode.children[childNode.order] = nb.children[0];
                    for (int i = 1; i < nb.order; i++) {
                        nb.keys[i - 1] = nb.keys[i];
                    }
                    for (int i = 1; i <= nb.order; i++) {
                        nb.children[i - 1] = nb.children[i];
                    }
                    nb.order--;
                    deleteByCondition(childNode, key);
                    return;
                } else if (position != 0 && node.children[position - 1].order >= T) {

                    divider = node.keys[position - 1];
                    nb = node.children[position - 1];
                    node.keys[position - 1] = nb.keys[nb.order - 1];
                    BTreeNode child = nb.children[nb.order];
                    nb.order--;

                    for (int i = childNode.order; i > 0; i--) {
                        childNode.keys[i] = childNode.keys[i - 1];
                    }
                    childNode.keys[0] = divider;
                    for (int i = childNode.order + 1; i > 0; i--) {
                        childNode.children[i] = childNode.children[i - 1];
                    }
                    childNode.children[0] = child;
                    childNode.order++;
                    deleteByCondition(childNode, key);
                    return;
                } else {
                    BTreeNode lt = null;
                    BTreeNode rt = null;
                    boolean last = false;
                    if (position != node.order) {
                        divider = node.keys[position];
                        lt = node.children[position];
                        rt = node.children[position + 1];
                    } else {
                        divider = node.keys[position - 1];
                        rt = node.children[position];
                        lt = node.children[position - 1];
                        last = true;
                        position--;
                    }
                    for (int i = position; i < node.order - 1; i++) {
                        node.keys[i] = node.keys[i + 1];
                    }
                    for (int i = position + 1; i < node.order; i++) {
                        node.children[i] = node.children[i + 1];
                    }
                    node.order--;
                    lt.keys[lt.order++] = divider;

                    for (int i = 0, j = lt.order; i < rt.order + 1; i++, j++) {
                        if (i < rt.order) {
                            lt.keys[j] = rt.keys[i];
                        }
                        lt.children[j] = rt.children[i];
                    }
                    lt.order += rt.order;
                    if (node.order == 0) {
                        if (node == root) {
                            root = node.children[0];
                        }
                        node = node.children[0];
                    }
                    deleteByCondition(lt, key);
                    return;
                }
            }
        }

    }

    public class BTreeImplementation {
        public static void main(String[] args) {
            BTree obj = new BTree(3);
            int values[] = { 10, 80, 147, 11, 67, 7, 3, 91, 48, 85, 86, 88 };
            for (int x : values) {
                obj.Insert(x);
            }

            obj.show();
            if (obj.Contain(85)) {
                System.out.println("Found");
            } else {
                System.out.println("Not Found");
            }
            obj.delete(86);
            obj.show();
        }
    }
}
