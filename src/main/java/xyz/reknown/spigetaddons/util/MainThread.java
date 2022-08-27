package xyz.reknown.spigetaddons.util;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MainThread {
    private static final ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

    public static void scheduleTask(Runnable callback) {
        queue.add(callback);
    }

    public static void executeTasks() {
        Iterator<Runnable> iterator = queue.iterator();
        while (iterator.hasNext()) {
            iterator.next().run();
            iterator.remove();
        }
    }
}
