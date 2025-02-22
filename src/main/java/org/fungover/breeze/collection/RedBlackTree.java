package org.fungover.breeze.collection;


import java.util.Objects;



/**
 * A Red-Black Tree implementation for storing and manipulating a set of elements.
 * This is a self-balancing binary search tree where each node stores a value and
 * a color (either RED or BLACK). It ensures that the tree is balanced after each
 * insertion by applying rotations and color changes.
 * <p>
 * The tree operations such as insertions, deletions, and searches are performed
 * in {@code O(log n)} time.
 * </p>
 *
 * @param <T> The type of elements stored in the tree. The elements must implement
 *            the {@link Comparable} interface for ordering.
 */

final class RedBlackTree<T extends Comparable<T>> {

    /**
     * The root of the Red-Black Tree.
     */
    private Node<T> root;

    /**
     * The constant value representing the color Red for the RedBlack Tree.
     */
    private static final boolean RED = false;

    /**
     * The constant value representing the color Black for the RedBlack Tree.
     */
    private static final boolean BLACK = true;

    /**
     * Number of nodes present in RedBlack Tree represented by integer.
     */
    private int size = 0;


    /**
     * The Default Constructor of the Red-Black Tree.
     * <p>
     *     The Red-Black Tree is instantiated with:
     *     <ul>
     *         <li>
     *            Field `root`: {@code null}
     *         </li>
     *     </ul>
     *
     * </p>
     */
    public RedBlackTree() {
        root = null;
    }

    /**
     * Constructor that copies the elements from another RedBlackTree into the current tree.
     *
     * @param anotherTree The tree whose elements should be copied into this one.
     */
    public RedBlackTree(RedBlackTree<T> anotherTree) {
        this();
        insertFromAnotherTree(anotherTree);
    }



    /**
     * Inserts a new value into the Red-Black Tree. If the value already exists,
     * the insertion is ignored.
     * <p>
     * This method ensures that the tree remains balanced and follows Red-Black
     * properties by calling {@link #fixInsert(Node)}.
     * </p>
     *
     * @param value The value to insert into the tree.
     * @throws IllegalArgumentException If the value is {@code null}.
     */
    public void insert(T value) {
        if (value == null) {
            throw new IllegalArgumentException("Cannot insert null value");
        }

        if (isNodeWithValueFound(value)) {
            return;
        }
        Node<T> newNode = new Node<>(value);
        if (root == null) {
            root = newNode;
            root.color = BLACK;
        } else {
            root = insertNode(root, newNode);
            fixInsert(newNode);
        }


        increaseSize();
        root.color = BLACK;

    }


    /**
     * The method is used for fixing the tree's Red-Black properties after insertion by performing rotations
     * and recoloring as necessary.
     *
     * @param current The newly inserted node.
     */
    private void fixInsert(Node<T> current) {

        Node<T> parent;
        Node<T> grandParent;

        while (current != root && current.parent.color == RED) {
            parent = current.parent;
            grandParent = parent.parent;

            if (parent == grandParent.left) {
                Node<T> uncle = grandParent.right;

                current = balanceLeftChild(current, uncle, grandParent, parent);

            } else {
                // Check for Case 1
                Node<T> uncle = grandParent.left;

                current = balanceRightChild(current, uncle, grandParent, parent);
            }
        }
        // Ensures that root always is black.
        root.color = BLACK;

    }

    /**
     * Balances the tree when the current node is the left child of its parent.
     *
     * @param current The current node.
     * @param uncle The uncle node.
     * @param grandParent The grandparent node.
     * @param parent The parent node.
     * @return The new current node after balancing.
     */
    private Node<T> balanceRightChild(Node<T> current, Node<T> uncle, Node<T> grandParent, Node<T> parent) {
        if (uncle != null && uncle.color == RED) {
            grandParent.color = RED;
            parent.color = BLACK;
            uncle.color = BLACK;
            current = grandParent;

        } else {
            // Case 2 current is left child
            if (current == parent.left) {
                current = parent;
                rightRotate(current);
            }
            // Case 3 current is right child
            parent.color = BLACK;
            grandParent.color = RED;
            leftRotate(grandParent);
        }
        return current;
    }

    /**
     * Balances the tree when the current node is the right child of its parent.
     *
     * @param current The current node.
     * @param uncle The uncle node.
     * @param grandParent The grandparent node.
     * @param parent The parent node.
     * @return The new current node after balancing.
     */
    private Node<T> balanceLeftChild(Node<T> current, Node<T> uncle, Node<T> grandParent, Node<T> parent) {
        // Check if Uncle is Red Case 1
        if (uncle != null && uncle.color == RED) {
            grandParent.color = RED;
            parent.color = BLACK;
            uncle.color = BLACK;
            current = grandParent;
        } else {

            // Case current is right child
            if (current == parent.right) {
                current = parent;
                leftRotate(current);
            }
            // Case current is left child
            parent.color = BLACK;
            grandParent.color = RED;
            rightRotate(grandParent);
        }
        return current;
    }


    /**
     * Performs a left rotation on the given node to rebalance the tree.
     *
     * @param node The node to rotate.
     */
    private void leftRotate(Node<T> node) {
        Node<T> rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }
        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;  // If node was root, update root
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }
        rightChild.left = node;
        node.parent = rightChild;
    }

    /**
     * Rotates the node to right to rebalance the tree.
     *
     * @param node The node to rotate
     */
    private void rightRotate(Node<T> node) {
        Node<T> leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null) {
            leftChild.right.parent = node;
        }
        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;  // If node was root, update root
        } else if (node == node.parent.right) {
            node.parent.right = leftChild;
        } else {
            node.parent.left = leftChild;
        }
        leftChild.right = node;
        node.parent = leftChild;
    }


    /**
     * Inserts a node recursively in the tree.
     *
     * @param root The current root node of the subtree.
     * @param newNode The new node to insert.
     * @return The root of the modified subtree.
     */
    private Node<T> insertNode(Node<T> root, Node<T> newNode) {

        if (root == null) {
            return newNode;

        }
        int compare = newNode.compareTo(root);
        if (compare < 0) {
            root.left = insertNode(root.left, newNode);
            root.left.parent = root;

        } else if (compare > 0) {
            root.right = insertNode(root.right, newNode);
            root.right.parent = root;
        }

        return root;

    }


    /**
     * Finds a value in the Red-Black Tree.
     *
     * @param value The value to search for.
     * @return The node containing the value, or {@code null} if the value is not found.
     */
    public Node<T> findValue(T value) {
        return findValue(root, value);
    }

    /**
     * Recursively searches for a value in the tree.
     *
     * @param node The current node to search.
     * @param value The value to search for.
     * @return The node containing the value, or {@code null} if not found.
     */
    private Node<T> findValue(Node<T> node, T value) {
        if (value == null) {
            return node;
        }
        if (node == null) {
            return null; // Value not found
        }

        int comparison = value.compareTo(node.getValue());

        if (comparison < 0) {
            // Search in the left subtree
            return findValue(node.left, value);
        } else if (comparison > 0) {
            // Search in the right subtree
            return findValue(node.right, value);
        } else {
            // Value found
            return node;
        }
    }


    // Helper methods that are not part of the bulk work but are good to have things.

    /**
     * Checks if a node with the given value already exists in the tree.
     *
     * @param value The value to check for.
     * @return {@code true} if the value is found, {@code false} otherwise.
     */
    public boolean isNodeWithValueFound(T value) {
        return findValue(root, value) != null;

    }

    /**
     * Traverses the Red-Black Tree in natural order from root node.
     *
     */
    public void inorder() {
        inorder(root);
    }

    /**
     * Travers in natural order from the given node.
     *
     * @param node Stating point of traversal in the Red-Black Tree.
     */
    private void inorder(Node<T> node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.getValue() + " ");
        inorder(node.right);
    }

    /**
     * Increases the size of the tree by one
     */
    private void increaseSize() {
        this.size++;
    }

    /**
     * Return the number of nodes i.e. the size of the Red-Black Tree
     *
     * @return The number of nodes in the tree
     */
       public int getSize() {
        return size;
    }


    /**
     * Prints the Red-Black Tree in a human-readable format with indentation and node colors.
     *
     * <ul>
     * <li><code> `/` is left of node </code></li>
     * <li><code> `\` is right of node </code></li>
     * </ul>
     */
    public void printRedBlackTree() {
        System.out.println("RedBlack Tree with Nodes and Values\n");
        printRedBlackTree(root, "", true);
        System.out.println("\nTotal size: " + getSize());
    }

    /**
     * Recursively prints the Red-Black Tree in a human-readable format.
     *
     * @param node The current node.
     * @param indent The indentation for the current level.
     * @param isLeft {@code true} if the current node is the left child, {@code false} otherwise.
     */
    private void printRedBlackTree(Node<T> node, String indent, boolean isLeft) {
        if (node == null) {
            return;
        }

        // Print the current node
        System.out.print(indent);
        if (isLeft) {
            System.out.print(" /-- ");
        } else {
            System.out.print(" \\-- ");
        }
        System.out.println(node.getValue() + (node.color == RED ? "R" : "B"));

        // Recurse for the left and right children
        printRedBlackTree(node.left, indent + "     ", true);
        // Right child
        printRedBlackTree(node.right, indent + "     ", false);

    }

    /**
     * Prints the tree in a standard format (values only) single line.
     *
     * @return
     */
    public String stringSetBuilder(){
        StringBuilder setStringBuilder  = new StringBuilder();
        stringSetBuilderHelper(root, setStringBuilder);
        return !setStringBuilder.isEmpty() ? setStringBuilder.substring(0, setStringBuilder.length() - 2) : "";
    }

    /**
     * Recursively prints the tree in a standard format (value only).
     *
     * @param node The current node.
     */
    private void stringSetBuilderHelper(Node<T> node, StringBuilder setStringStandard) {
        if(node == null)
            return;
        setStringStandard.append(node.getValue() + ", ");
        stringSetBuilderHelper(node.left, setStringStandard);
        stringSetBuilderHelper(node.right, setStringStandard);
    }


    public String stringRedBlackTree() {
        StringBuilder sb = new StringBuilder();
        sb.append("RedBlack Tree with Nodes and Values\n\n");
        stringRedBlackTree(root, "", true, sb);
        sb.append("\nTotal size: ").append(getSize());
        return sb.toString();
    }

    /**
     * Recursively generates a string representation of the Red-Black Tree.
     *
     * @param node The current node.
     * @param indent The indentation for the current level.
     * @param isLeft {@code true} if the current node is the left child, {@code false} otherwise.
     * @param sb The StringBuilder used to accumulate the string.
     */
    private void stringRedBlackTree(Node<T> node, String indent, boolean isLeft, StringBuilder sb) {
        if (node == null) {
            return;
        }

        // Add the current node to the StringBuilder
        sb.append(indent);
        if (isLeft) {
            sb.append(" /-- ");
        } else {
            sb.append(" \\-- ");
        }
        sb.append(node.getValue()).append(node.color == RED ? "R" : "B").append("\n");

        // Recurse for the left and right children
        stringRedBlackTree(node.left, indent + "     ", true, sb);
        stringRedBlackTree(node.right, indent + "     ", false, sb);
    }


    /**
     * Inserts all the values from another RedBlackTree into the current tree.
     *
     * @param anotherTree The RedBlackTree whose elements are to be inserted into this tree.
     */
    public void insertFromAnotherTree(RedBlackTree<T> anotherTree) {
        insertFromAnotherTreeHelper(anotherTree.root);
    }

    /**
     * Helper method that recursively inserts values from another tree into the current tree.
     *
     * @param node The current node in the other tree that needs to be inserted into this tree.
     */
    private void insertFromAnotherTreeHelper(Node<T> node) {
        if (node == null) return;

        // Insert the current node's value
        insert(node.getValue());

        // Recurse to insert left and right children
        if (node.left != null) {
            insertFromAnotherTreeHelper(node.left);
        }
        if (node.right != null) {
            insertFromAnotherTreeHelper(node.right);
        }
    }


    /**
     * Includes all the nodes in the tree that are present in another tree, except for a specific value.
     *
     * @param anotherTree The RedBlackTree to compare against.
     * @param value The value to skip during removal (i.e., this value will not be removed).
     */
    public void removalBySkip(RedBlackTree<T> anotherTree, T value) {
        removalBySkipHelper(anotherTree.root, value);
    }

    /**
     * Helper method that recursively removes nodes from the tree, skipping a specific value.
     *
     * @param root The current node in the tree.
     * @param value The value to skip during removal.
     */
    private void removalBySkipHelper(Node<T> root, T value) {
        if (root == null) return;

        if (!root.getValue().equals(value)) {
            insert(root.getValue());
        }
        removalBySkipHelper(root.left, value);
        removalBySkipHelper(root.right, value);

    }


    /**
     * Computes the intersection of the current tree with another RedBlackTree and inserts the
     * resulting nodes into a new tree.
     *
     * @param tree The set that provides the values to compare with.
     * @param other The RedBlackTree to intersect with.
     * @param newTree The RedBlackTree where the intersected values will be inserted.
     */
    public void intersectWithOtherTree(FSet<T> tree, RedBlackTree<T> other, RedBlackTree<T> newTree) {

        intersectWithOtherTreeHelper(tree, other.root, newTree);

    }

    /**
     * Helper method to recursively compute the intersection of two trees and insert the resulting
     * nodes into the new tree.
     *
     * @param tree The set that provides the values to compare with.
     * @param other The current node in the other tree.
     * @param newTree The RedBlackTree where the intersected values will be inserted.
     */
    private void intersectWithOtherTreeHelper(FSet<T> tree, Node<T> other, RedBlackTree<T> newTree) {

        if (other == null) {
            return;
        }

        if (tree.contains(other.getValue())) {
            newTree.insert(other.getValue());
        }
        intersectWithOtherTreeHelper(tree, other.left, newTree);
        intersectWithOtherTreeHelper(tree, other.right, newTree);
    }


    /**
     * Computes the symmetric difference between the current tree and another RedBlackTree and
     * inserts the resulting nodes into a new tree.
     *
     * @param tree The set that provides the values to compare with.
     * @param other The RedBlackTree to compute the symmetric difference with.
     * @param newTree The RedBlackTree where the resulting values will be inserted.
     */
    public void symmetricDifferenceWithOtherTree(RedBlackTree<T> tree, RedBlackTree<T> other, RedBlackTree<T> newTree) {

        symmetricDifferenceWithOtherTreeHelper(tree.root, other, newTree);
        symmetricDifferenceWithOtherTreeHelper(other.root, tree, newTree);
    }

    /**
     * Helper method to recursively compute the symmetric difference between two trees and insert
     * the resulting nodes into the new tree.
     *
     * @param node The set that provides the values to compare with.
     * @param other The current node in the other tree.
     * @param newTree The RedBlackTree where the resulting values will be inserted.
     */
    private void symmetricDifferenceWithOtherTreeHelper(Node<T> node, RedBlackTree<T> other, RedBlackTree<T> newTree) {

        if (node == null) {
            return;
        }

        if (!(other.isNodeWithValueFound(node.getValue()) )) {
            newTree.insert(node.getValue());
        }
        symmetricDifferenceWithOtherTreeHelper(node.left, other, newTree);
        symmetricDifferenceWithOtherTreeHelper(node.right, other, newTree);
    }

    public void differenceWithOtherTree(RedBlackTree<T> tree, RedBlackTree<T> tree1, RedBlackTree<T> newTree) {
        symmetricDifferenceWithOtherTreeHelper(tree.root, tree1, newTree);
    }

    /**
     * Checks whether two RedBlackTrees are equal.
     *
     * @param o The object to compare this RedBlackTree with.
     * @return true if the trees are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RedBlackTree<?> that)) return false;
        return Objects.equals(root, that.root);
    }

    /**
     * Generates a hash code for the RedBlackTree.
     *
     * @return The hash code for this RedBlackTree.
     */
    @Override
    public int hashCode() {
        return Objects.hashCode(root);
    }

    @Override
    public String toString() {
        return stringRedBlackTree();
    }



}
