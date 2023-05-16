package net.renfei.mascloud.util;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.ejtone.mars.kernel.util.NamedThreadFactory
 *
 * @author renfei
 */
public class NamedThreadFactory implements ThreadFactory {
    private final ThreadGroup group;
    private final AtomicInteger counter;
    private final String namePrefix;
    private static Map<String, AtomicInteger> map = new ConcurrentHashMap();

    public NamedThreadFactory(String name) {
        this.counter = getCounter(name);
        this.group = new ThreadGroup(name);
        this.namePrefix = name;
    }

    public Thread newThread(Runnable runnable) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(this.namePrefix);
        buffer.append('-');
        buffer.append(this.counter.getAndIncrement());
        Thread t = new Thread(this.group, runnable, buffer.toString(), 0L);
        t.setDaemon(false);
        t.setPriority(5);
        return t;
    }

    private static AtomicInteger getCounter(String name) {
        AtomicInteger counter = (AtomicInteger) map.get(name);
        if (counter == null) {
            synchronized (map) {
                counter = (AtomicInteger) map.get(name);
                if (counter == null) {
                    counter = new AtomicInteger();
                    map.put(name, counter);
                }
            }
        }

        return counter;
    }

    public static class UncaughtException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private String data;

        public UncaughtException() {
        }

        public UncaughtException(String message, Throwable cause, String data) {
            super(message, cause);
            this.data = data;
        }

        public String getData() {
            return this.data;
        }
    }

    private static class DefaultUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
        private String name;

        public DefaultUncaughtExceptionHandler() {
            this.name = "";
        }

        public DefaultUncaughtExceptionHandler(String name) {
            this.name = name;
        }

        public void uncaughtException(Thread t, Throwable e) {
            if (e instanceof UncaughtException) {
                System.err.println("e.clz=" + e.getCause().getClass().getSimpleName() + ", e.msg=" + e.getMessage() + ", data=" + ((UncaughtException) e).getData());
            } else {
                System.err.println("e.clz=" + e.getCause().getClass().getSimpleName() + ", e.msg=" + e.getMessage() + ", data=" + Arrays.toString(e.getStackTrace()));
            }

        }
    }
}
