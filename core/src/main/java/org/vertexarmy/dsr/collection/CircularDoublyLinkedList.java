package org.vertexarmy.dsr.collection;

import com.beust.jcommander.internal.Lists;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

/**
 * created by Alex
 * on 3/9/2015.
 */
public class CircularDoublyLinkedList<T> {

    private int size = 0;

    private Node root = null;

    public void add(T item) {
        if (root == null) {
            root = new Node();
            root.value = item;
            root.next = root;
            root.previous = root;
        } else {
            insertNodeAfter(root.previous, item);
        }

        size += 1;
    }

    public void remove(T item) {
        Node n = findNode(item);
        removeNode(n);
    }

    public void removeAt(int index) {
    }

    public int size() {
        return size;
    }

    public void clear() {

    }

    public Iterator<T> getIterator() {
        if (root != null) {
            return new NodeIterator(root);
        } else {
            return new NullIterator();
        }
    }


    private void insertNodeAfter(Node pivot, T value) {
        Node newNode = new Node(value, pivot, pivot.next);
        pivot.next = newNode;
        newNode.next.previous = newNode;
    }

    private Node findNode(T item) {
        if (size() == 0) {
            return null;
        }

        if (root.value.equals(item)) {
            return root;
        }

        Node it = root.next;

        while (it != root) {
            if (it.value.equals(item)) {
                return it;
            }
            it = it.next;
        }

        return null;
    }

    private void removeNode(Node n) {
        if (n == null) {
            return;
        }

        if (size() == 1) {
            Preconditions.checkArgument(n.equals(root), "Internal state error. Attempting to remove an non-existing node");
            root = null;
        } else {
            Node next = n.next;
            Node previous = n.previous;
            next.previous = previous;
            previous.next = next;
        }

        size -= 1;
    }

    @Override
    public boolean equals(Object other) {
        // TODO: implement
        return false;
    }

    public static <T> CircularDoublyLinkedList<T> fromCollection(Collection<T> collection) {
        // TODO: implement
        return null;
    }

    public static <T> CircularDoublyLinkedList<T> fromIterator(Iterator<T> iterator) {
        // TODO: implement
        return null;
    }

    public List<T> asList() {
        List<T> result = Lists.newArrayList();
        if (root != null) {
            result.add(root.value);
            Node node = root.next;
            while (node != root) {
                result.add(node.value);
                node = node.next;
            }
        }
        return result;
    }

    @RequiredArgsConstructor
    private class Node {
        public Node next;
        public Node previous;
        public T value;

        Node(T value, Node previous, Node next) {
            this.value = value;
            this.previous = previous;
            this.next = next;
        }
    }

    public interface Iterator<T> {
        T next();

        T current();

        T previous();

        void forward();

        void backward();

        Iterator<T> duplicate();
    }


    public class NodeIterator implements Iterator<T> {
        private Node currentNode;

        public NodeIterator(Node initialNode) {
            currentNode = initialNode;
        }

        public T next() {
            return currentNode.next.value;
        }

        public T current() {
            return currentNode.value;
        }

        public T previous() {
            return currentNode.previous.value;
        }

        public void forward() {
            currentNode = currentNode.next;
        }

        public void backward() {
            currentNode = currentNode.previous;
        }

        public Iterator<T> duplicate() {
            return new NodeIterator(currentNode);
        }
    }

    public class NullIterator implements Iterator<T> {
        @Override
        public T next() {
            return null;
        }

        @Override
        public T current() {
            return null;
        }

        @Override
        public T previous() {
            return null;
        }

        @Override
        public void forward() {
        }

        @Override
        public void backward() {
        }

        @Override
        public Iterator<T> duplicate() {
            return null;
        }
    }
}
