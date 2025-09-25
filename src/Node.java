/*******************************************************
 * @file: BSTNode.java
 * @description: A single node in the Binary Search Tree.
 *               Holds one element and pointers to left/right children.
 * @author: Sebastien Pierre
 * @date: September 25, 2025
 *******************************************************/
class BSTNode<E extends Comparable<? super E>> {
    private E element;          // the data/value stored in this node
    private BSTNode<E> left;    // link to left child
    private BSTNode<E> right;   // link to right child

    // Implement the constructor
    public BSTNode(E elem) {
        this.element = elem;
        this.left = null;
        this.right = null;
    }

    // Implement the setElement method
    public void setElement(E elem) {
        this.element = elem;
    }

    // Implement the setLeft method
    public void setLeft(BSTNode<E> node) {
        this.left = node;
    }

    // Implement the setRight method
    public void setRight(BSTNode<E> node) {
        this.right = node;
    }

    // Implement the getLeft method
    public BSTNode<E> getLeft() {
        return this.left;
    }

    // Implement the getRight method
    public BSTNode<E> getRight() {
        return this.right;
    }

    // Implement the getElement method
    public E getElement() {
        return this.element;
    }

    // Implement the isLeaf method
    public boolean isLeaf() {
        return (this.left == null && this.right == null);
    }
}
