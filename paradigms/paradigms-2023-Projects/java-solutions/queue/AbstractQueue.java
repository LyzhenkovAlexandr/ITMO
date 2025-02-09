package queue;

import java.util.Objects;

public abstract class AbstractQueue implements Queue {
    protected int size;

    @Override
    public void enqueue(final Object element) {
        Objects.requireNonNull(element);
        size++;
        enqueueImpl(element);
    }

    @Override
    public Object element() {
        assert !isEmpty();
        return elementImpl();
    }

    @Override
    public Object dequeue() {
        assert !isEmpty();
        size--;
        return dequeueImpl();
    }

    @Override
    public void clear() {
        size = 0;
        clearImpl();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int indexOf(final Object element) {
        Objects.requireNonNull(element);
        return helper(element, true);
    }

    @Override
    public int lastIndexOf(final Object element) {
        Objects.requireNonNull(element);
        return helper(element, false);
    }

    private int helper(final Object element, final boolean firstOrLast) {
        int index = 0;
        int res = -1;

        while (index < size) {
            if (takeElement(index).equals(element)) {
                res = index;
                if (firstOrLast) {
                    return res;
                }
            }
            index++;
        }
        return res;
    }

    @Override
    public Object[] toArray() {
        Object[] a = new Object[size];
        int count = size;
        int i = 0;
        while (count > 0) {
            a[i] = takeElement(i++);
            count--;
        }
        return a;
    }

    protected abstract Object takeElement(final int count);

    protected abstract void enqueueImpl(final Object element);

    protected abstract Object elementImpl();

    protected abstract Object dequeueImpl();

    protected abstract void clearImpl();
}