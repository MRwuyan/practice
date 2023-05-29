package example.queue;

public class ArrayQueueDemo2 {
    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue(10);
    }

    static class ArrayQueue {
        private int maxSize;//队列大小
        private int front;//头
        private int rear;//尾
        private int[] arr;//该数组用于存放数组

        public void addQueue(int i) {
            if (isFull()) {
                System.out.println("队列已满");
                throw new RuntimeException();
            }
            arr[rear] = i;
            rear = (rear + 1) % maxSize;
        }

        public int getQueue() {
            if (isEmpty()) {
                System.out.println("队列为空");
                throw new RuntimeException();
            }
            front++;
            return arr[rear];
        }

        public ArrayQueue(int maxSize) {
            this.maxSize = maxSize;
            arr = new int[maxSize];
        }

        public boolean isFull() {
            return (rear + 1) % maxSize == front;
        }

        public boolean isEmpty() {
            return rear == front;
        }

        public int getLength() {
            return (rear + maxSize - front) % maxSize;
        }
    }
}
