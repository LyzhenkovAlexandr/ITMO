package queue;

public class TestArrayQueueModule {
    private static void fillQueue() {
        for (int i = 1; i < 12; i += i % 3) {
            ArrayQueueModule.enqueue(i);
        }
    }

    private static void dumpQueue() {
        while (!ArrayQueueModule.isEmpty()) {
            System.out.println("size = " + ArrayQueueModule.size() + " element = " + ArrayQueueModule.dequeue());
        }
    }

    public static void main(String[] args) {
        fillQueue();
        dumpQueue();
        ArrayQueueModule.clear();
    }
}