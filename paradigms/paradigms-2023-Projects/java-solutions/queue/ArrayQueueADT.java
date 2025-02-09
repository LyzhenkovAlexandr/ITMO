package queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*
    Model: a[1]..a[n]
    Invariant: n >= 0 && for i = 1..n: a[i] != null

    Let immutable(n): for i = 1..n: a'[i] = a[i]
 */


public class ArrayQueueADT {
    private Object[] elements = new Object[2];
    private int size = 0;
    private int head = 0;


//    Pred: element != null && queue != null
//    Post: n' = n + 1 && a[n'] = element && immutable(n)
    public static void enqueue(final ArrayQueueADT queue, final Object element) {
        Objects.requireNonNull(element);
        Objects.requireNonNull(queue);

        ensureCapacity(queue);
        queue.elements[(queue.head + queue.size) % queue.elements.length] = element;
        queue.size++;
    }

    private static void ensureCapacity(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        if (queue.size == queue.elements.length) {
            Object[] temp = new Object[queue.elements.length * 2];
            System.arraycopy(queue.elements, queue.head, temp, 0, queue.elements.length - queue.head);
            System.arraycopy(queue.elements, 0, temp, queue.elements.length - queue.head, queue.head);
            queue.elements = temp;
            queue.head = 0;
        }
    }

//    Pred: queue != null
//    Post: R == a[n] && immutable(n) && n' == n
    public static Object element(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        assert !isEmpty(queue);
        return queue.elements[queue.head];
    }

//    Pred: n >= 1 && queue != null
//    Post: n' = n - 1 && R = a[1] && immutable(n')
    public static Object dequeue(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        assert !isEmpty(queue);
        Object element = queue.elements[queue.head];
        queue.elements[queue.head] = null;
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return element;
    }

//    Pred: queue != null
//    Post: R == n && n' == n && immutable(n)
    public static int size(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        return queue.size;
    }

//    Pred: queue != null
//    Post: R == (n == 0) && n' == n && immutable(n)
    public static boolean isEmpty(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        return size(queue) == 0;
    }

//    Pred: queue != null
//    Post: n' == 0 && n' == n && immutable(n')
    public static void clear(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        queue.elements = new Object[2];
        queue.head = 0;
        queue.size = 0;
    }

//    Pred: queue != null
//    Post: R == a[1]..a[n] && n' == n && immutable(n)
    public static Object[] toArray(final ArrayQueueADT queue) {
        Objects.requireNonNull(queue);
        Object[] a = new Object[queue.size];
        int start = queue.head;
        int count = queue.size;
        int i = 0;
        while (count > 0) {
            a[i++] = queue.elements[start];
            start = (start + 1) % queue.elements.length;
            count--;
        }
        return a;
    }
}