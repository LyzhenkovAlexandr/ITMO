package queue;

public class TestArrayQueue {
    private static void fillQueue(final ArrayQueue queue) {
        for (int i = 1; i < 12; i++) {
            queue.enqueue(i);
        }
    }

    private static void dumpQueue(final ArrayQueue queue) {
        while (!queue.isEmpty()) {
            System.out.println("size = " + queue.size() + " element = " + queue.dequeue());
        }
    }

    private static void takeElementFromQueue(final ArrayQueue queue1, final ArrayQueue queue2) {
        while (!queue2.isEmpty()) {
            queue1.enqueue(queue2.dequeue());
        }
    }
    public static void main(String[] args) {
        ArrayQueue queue1 = new ArrayQueue();
        ArrayQueue queue2 = new ArrayQueue();

        fillQueue(queue1);
        fillQueue(queue2);
        takeElementFromQueue(queue1, queue2);
        System.out.println("size queue1 = " + queue1.size());
        dumpQueue(queue1);
        System.out.println("size queue2 = " + queue2.size());
        dumpQueue(queue2);
    }
}
