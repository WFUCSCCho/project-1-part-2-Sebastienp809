/*******************************************************
 * @file: BST.java
 * @description: Generic Binary Search Tree (BST) class.
 *               Supports insert, remove, search, size,
 *               clear, and iteration (in-order).
 *               Works with any Comparable type, including
 *               Integers (Part 1) or a dataset class (Part 2).
 * @author: Sebatien pierre
 * @date: September 25, 2025
 *******************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

class BST<E extends Comparable<? super E>> implements Iterable<E> {

    private BSTNode<E> root;
    private int nodecount;

    // Implement the constructor
    // start with an empty tree
    public BST() {
        root = null;
        nodecount = 0;
    }

    // Implement the clear method
    // wipe everything
    public void clear() {
        root = null;
        nodecount = 0;
    }

    // Implement the size method
    // how many nodes are in the tree
    public int size() {
        return nodecount;
    }

    // Implement the insert method
    // insert only if the value is not already present
    public void insert(E elem) {
        AddedFlag flag = new AddedFlag();
        root = insertRec(root, elem, flag);
        if (flag.added) nodecount++;
    }

    public E find(E probe) {
        return probe;
    }

    private static class AddedFlag {
        boolean added = false;
    }

    private BSTNode<E> insertRec(BSTNode<E> rt, E elem, AddedFlag flag) {
        if (rt == null) {
            flag.added = true;
            return new BSTNode<>(elem);
        }
        int cmp = elem.compareTo(rt.getElement());
        if (cmp < 0) {
            rt.setLeft(insertRec(rt.getLeft(), elem, flag));
        } else if (cmp > 0) {
            rt.setRight(insertRec(rt.getRight(), elem, flag));
        } else {
            // equal -> do nothing (ignore duplicate)
        }
        return rt;
    }

    // Implement the remove method
    // remove and return the element if found; otherwise return null
    public E remove(E key) {
        RemovedBox<E> box = new RemovedBox<>();
        root = removeRec(root, key, box);
        if (box.removed != null) nodecount--;
        return box.removed;
    }

    private static class RemovedBox<T> {
        T removed = null;
    }

    private BSTNode<E> removeRec(BSTNode<E> rt, E key, RemovedBox<E> box) {
        if (rt == null) return null;

        int cmp = key.compareTo(rt.getElement());
        if (cmp < 0) {
            rt.setLeft(removeRec(rt.getLeft(), key, box));
        } else if (cmp > 0) {
            rt.setRight(removeRec(rt.getRight(), key, box));
        } else {
            // found it
            box.removed = rt.getElement();

            // cases: 0 or 1 child
            if (rt.getLeft() == null) return rt.getRight();
            if (rt.getRight() == null) return rt.getLeft();

            // 2 children: copy inorder successor value, then delete successor
            BSTNode<E> succ = minNode(rt.getRight());
            rt.setElement(succ.getElement());
            rt.setRight(deleteMin(rt.getRight()));
        }
        return rt;
    }

    private BSTNode<E> minNode(BSTNode<E> rt) {
        while (rt.getLeft() != null) rt = rt.getLeft();
        return rt;
    }

    private BSTNode<E> deleteMin(BSTNode<E> rt) {
        if (rt.getLeft() == null) return rt.getRight();
        rt.setLeft(deleteMin(rt.getLeft()));
        return rt;
    }

    // Implement the search method
    // return the element if found; otherwise null
    public E search(E key) {
        BSTNode<E> cur = root;
        while (cur != null) {
            int cmp = key.compareTo(cur.getElement());
            if (cmp == 0) return cur.getElement();
            if (cmp < 0) cur = cur.getLeft();
            else cur = cur.getRight();
        }
        return null;
    }

    // Implement the iterator method
    // in-order iterator (ascending)
    @Override
    public Iterator<E> iterator() {
        return new BSTIterator(root);
    }

    // Implement the BSTIterator class
    // simple stack-based in-order traversal
    private class BSTIterator implements Iterator<E> {
        private final Stack<BSTNode<E>> st = new Stack<>();

        BSTIterator(BSTNode<E> start) {
            pushLeft(start);
        }

        private void pushLeft(BSTNode<E> n) {
            while (n != null) {
                st.push(n);
                n = n.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return !st.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) throw new NoSuchElementException();
            BSTNode<E> n = st.pop();
            if (n.getRight() != null) pushLeft(n.getRight());
            return n.getElement();
        }
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        for (E val : this) {
            sb.append(val).append(" ");
        }
        if (sb.length() > 0) sb.setLength(sb.length() - 1); // trim trailing space
        return sb.toString();
    }
}
