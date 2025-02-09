package queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
    Model: a[1]..a[n]
    Invariant: n >= 0 && for i = 1..n: a[i] != null

    Let immutable(n): for i = 1..n: a'[i] = a[i]
 */


public class ArrayQueueModule {
    private static Object[] elements = new Object[2];
    private static int size = 0;
    private static int head = 0;


//    Pred: element != null
//    Post: n' = n + 1 && a[n'] = element && immutable(n)
    public static void enqueue(final Object element) {
        Objects.requireNonNull(element);

        ensureCapacity();
        elements[(head + size) % elements.length] = element;
        size++;
    }

    private static void ensureCapacity() {
        if (size == elements.length) {
            Object[] temp = new Object[elements.length * 2];
            System.arraycopy(elements, head, temp, 0, elements.length - head);
            System.arraycopy(elements, 0, temp, elements.length - head, head);
            elements = temp;
            head = 0;
        }
    }

//    Pred: true
//    Post: R == a[n] && immutable(n) && n' == n
    public static Object element() {
        assert !isEmpty();
        return elements[head];
    }

//    Pred: n >= 1
//    Post: n' = n - 1 && R = a[1] && immutable(n')
    public static Object dequeue() {
        assert !isEmpty();
        Object element = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return element;
    }

//    Pred: true
//    Post: R == n && n' == n && immutable(n)
    public static int size() {
        return size;
    }

//    Pred: true
//    Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty() {
        return size() == 0;
    }

//    Pred: true
//    Post: n' == 0 && n' == n && immutable(n')
    public static void clear() {
        elements = new Object[2];
        head = 0;
        size = 0;
    }

//    Pred: true
//    Post: R == a[1]..a[n] && n' == n && immutable(n)
    public static Object[] toArray() {
        Object[] a = new Object[size];
        int start = head;
        int count = size;
        int i = 0;
        while (count > 0) {
            a[i++] = elements[start];
            start = (start + 1) % elements.length;
            count--;
        }
        return a;
    }
}