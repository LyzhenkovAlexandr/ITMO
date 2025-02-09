package queue;

public class TestLinkedQueue {
    public static void main(String[] args) {
        AbstractQueue queue = new ArrayQueue();
        queue.enqueue(1);
        System.out.println(queue.element());
        queue.enqueue(2);
        System.out.println(queue.element());
        queue.enqueue(3);
        System.out.println(queue.element());

        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        System.out.println(queue.element());
    }
}
