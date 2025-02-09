package queue;

public class TestArrayQueueADT {
    private static void fillQueue(final ArrayQueueADT queue) {
        for (int i = 1; i < 12; i += i % 3) {
            ArrayQueueADT.enqueue(queue, i);
        }
    }

    private static void dumpQueue(final ArrayQueueADT queue) {
        while (!ArrayQueueADT.isEmpty(queue)) {
            System.out.println("size = " + ArrayQueueADT.size(queue) + " element = " + ArrayQueueADT.dequeue(queue));
        }
    }

    private static void takeElementFromQueue(final ArrayQueueADT queue1, final ArrayQueueADT queue2) {
        while (!ArrayQueueADT.isEmpty(queue2)) {
            ArrayQueueADT.enqueue(queue1, ArrayQueueADT.dequeue(queue2));
        }
    }

    public static void main(String[] args) {
        ArrayQueueADT queue1 = new ArrayQueueADT();
        ArrayQueueADT queue2 = new ArrayQueueADT();

        fillQueue(queue1);
        fillQueue(queue2);
        takeElementFromQueue(queue1, queue2);
        System.out.println("size queue1 = " + ArrayQueueADT.size(queue1));
        dumpQueue(queue1);
        System.out.println("size queue2 = " + ArrayQueueADT.size(queue2));
        dumpQueue(queue2);
    }
}
