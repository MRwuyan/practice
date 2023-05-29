package example.queue;

public class ArrayQueueDemo {
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
            rear++;
            arr[rear] = i;
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
            front = -1;//指向队列头部
            rear = -1;//指向队列尾部
        }

        public boolean isFull() {
            return rear == maxSize - 1;
        }

        public boolean isEmpty(){
            return rear==front;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public void setMaxSize(int maxSize) {
            this.maxSize = maxSize;
        }

        public int getFront() {
            return front;
        }

        public void setFront(int front) {
            this.front = front;
        }

        public int getRear() {
            return rear;
        }

        public void setRear(int rear) {
            this.rear = rear;
        }

        public int[] getArr() {
            return arr;
        }

        public void setArr(int[] arr) {
            this.arr = arr;
        }
    }
}
