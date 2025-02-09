package queue;

public class ArrayQueue extends AbstractQueue {
    private Object[] elements;
    private int head;
    private int start;

    public ArrayQueue() {
        this.elements = new Object[2];
        this.size = 0;
        this.head = 0;
    }

    @Override
    protected void enqueueImpl(final Object element) {
        ensureCapacity();
        elements[(head + size - 1) % elements.length] = element;
    }

    private void ensureCapacity() {
        if (size - 1 == elements.length) {
            Object[] temp = new Object[elements.length * 2];
            System.arraycopy(elements, head, temp, 0, elements.length - head);
            System.arraycopy(elements, 0, temp, elements.length - head, head);
            elements = temp;
            head = 0;
        }
    }

    @Override
    protected Object elementImpl() {
        return elements[head];
    }

    @Override
    protected Object dequeueImpl() {
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        return element;
    }

    @Override
    protected void clearImpl() {
        elements = new Object[2];
        head = 0;
    }

    @Override
    protected Object takeElement(final int count) {
        start = count == 0 ? head : (start + 1) % elements.length;
        return elements[start];
    }
}