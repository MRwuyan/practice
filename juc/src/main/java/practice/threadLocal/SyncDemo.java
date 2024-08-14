package practice.threadLocal;

import org.openjdk.jol.info.ClassLayout;

public class SyncDemo {
    public static void main(String[] args) {
        Thread o = new Thread();
        String layout = ClassLayout.parseInstance(o).toPrintable();
        System.out.println(layout);
    }
    public synchronized void m1() {
        System.out.println("dddd");
    }
}
