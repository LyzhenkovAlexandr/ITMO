package queue;

import java.util.Objects;

public class LinkedQueue extends AbstractQueue {
    private Node head;
    private Node tail;
    private Node start;

    @Override
    protected void enqueueImpl(final Object element) {
        Node newTail = new Node(element, null);
        if (size == 1) {
            head = newTail;
        } else {
            tail.next = newTail;
        }
        tail = newTail;
    }

    @Override
    protected Object elementImpl() {
        return head.element;
    }

    @Override
    protected Object dequeueImpl() {
        Object element = head.element;
        head = head.next;
        return element;
    }

    @Override
    protected void clearImpl() {
        head = null;
        tail = null;
    }

    @Override
    protected Object takeElement(final int count) {
        start = count == 0 ? head : start.next;
        return start.element;
    }

    private static class Node {
        private final Object element;
        private Node next;
        public Node(Object element, Node next) {
            Objects.requireNonNull(element);
            this.element = element;
            this.next = next;
        }
    }
}
