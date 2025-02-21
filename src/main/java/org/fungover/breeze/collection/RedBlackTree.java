package org.fungover.breeze.collection;


import java.util.Objects;

final class RedBlackTree<T extends Comparable<T>> {

    private Node<T> root;
    private static final boolean RED = false;
    private static final boolean BLACK = true;
    private int size = 0;

    public RedBlackTree() {
        root = null;
    }


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


    // Left rotation for Red-Black Tree rebalancing
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

    // Right rotation for Red-Black Tree balancing
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


    // Find a value in the Red-Black Tree
    public Node<T> findValue(T value) {
        return findValue(root, value);
    }

    // Helper method to recursively find the value in the tree
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

    // In-order traversal to print the tree
    public void inorder() {
        inorder(root);
    }

    private void inorder(Node<T> node) {
        if (node == null) return;
        inorder(node.left);
        System.out.println(node.getValue() + " ");
        inorder(node.right);
    }


    public boolean isNodeWithValueFound(T value) {
        return findValue(root, value) != null;

    }


    private void increaseSize() {
        this.size++;
    }

    private void decreaseSize() {
        this.size--;
    }

    public int getSize() {
        return size;
    }


    public void printRedBlackTree() {
        System.out.println("RedBlack Tree with Nodes and Values\n");
        printRedBlackTree(root, "", true);
        System.out.println("\nTotal size: " + getSize());
    }

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

    public void printStandard(){
        printStandardSet(root);
    };

    private void printStandardSet(Node<T> node) {
        if(node == null)
            return;
        System.out.print(node.getValue() + ", ");
        printStandardSet(node.left);
        printStandardSet(node.right);
    }

    // In the RedBlackTree class, add this method
    public void insertFromAnotherTree(RedBlackTree<T> anotherTree) {
        insertFromAnotherTreeHelper(anotherTree.root);
    }

    // Helper method to insert values from another tree into the current tree
    private void insertFromAnotherTreeHelper(Node<T> node) {
        if (node == null) return;

        // Insert the current node's value
        insert(node.getValue());

        // Recurse to insert left and right children
        insertFromAnotherTreeHelper(node.left);
        insertFromAnotherTreeHelper(node.right);
    }


    public void removalBySkip(RedBlackTree<T> anotherTree, T value) {
        removalBySkipHelper(anotherTree.root, value);
    }

    private void removalBySkipHelper(Node<T> root, T value) {
        if (root == null) return;

        if (!root.getValue().equals(value)) {
            insert(root.getValue());
        }
        removalBySkipHelper(root.left, value);
        removalBySkipHelper(root.right, value);

    }


    public void intersectWithOtherTree(FSet<T> tree, RedBlackTree<T> other, RedBlackTree<T> newTree) {

        intersectWithOtherTreeHelper(tree, other.root, newTree);

    }

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


    public void symmetricDifferenceWithOtherTree(FSet<T> tree, RedBlackTree<T> other, RedBlackTree<T> newTree) {

        symmetricDifferenceWithOtherTreeHelper(tree, other.root, newTree);
    }

    private void symmetricDifferenceWithOtherTreeHelper(FSet<T> tree, Node<T> other, RedBlackTree<T> newTree) {

        if (other == null) {
            return;
        }

        if (!tree.contains(other.getValue())) {
            newTree.insert(other.getValue());
        }
        symmetricDifferenceWithOtherTreeHelper(tree, other.left, newTree);
        symmetricDifferenceWithOtherTreeHelper(tree, other.right, newTree);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RedBlackTree<?> that)) return false;
        return Objects.equals(root, that.root);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(root);
    }
}
