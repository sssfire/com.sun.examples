package com.sun.example.java.concurrent;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SafeLock {
    static class Friend {
        private final String name;
        
        /**
         * 1. Lock对象是一种可重入的互斥锁，类似于同步代码中的隐式锁，每次只有一个线程可以获取
         * 2. Lock对象支持wait/notify机制
         * 3. Lock对象的优势在于有能力回收获得锁的尝试。如果当前锁不可用，或者锁请求超时，tryLock方法会回收获取锁的请求。
         * 4. 如果在锁获取之前，另一个线程发送了中断，lockInterruptibly方法也会收回获取锁的请求
         * 
         */
        private final Lock lock = new ReentrantLock();

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
        
        /**
         * 判断是否即将鞠躬
         * @param bower
         * @return
         */
        public boolean impendingBow(Friend bower) {
            Boolean myLock = false;
            Boolean yourLock = false;
            try {
                myLock = lock.tryLock();
                yourLock = bower.lock.tryLock();
            } finally {
                if (!(myLock && yourLock)) {
                    if (myLock) {
                        lock.unlock();
                    }
                    if (yourLock) {
                        bower.lock.unlock();
                    }
                }
            }
            return myLock && yourLock;
        }

        public void bow(Friend bower) {
            if (impendingBow(bower)) {
                try {
                    System.out.format("%s: %s has" + " bowed to me!%n", this.name, bower.getName());
                    bower.bowBack(this);
                } finally {
                    lock.unlock();
                    bower.lock.unlock();
                }
            } else {
                System.out.format(
                        "%s: %s started" + " to bow to me, but saw that" + " I was already bowing to" + " him.%n",
                        this.name, bower.getName());
            }
        }

        public void bowBack(Friend bower) {
            System.out.format("%s: %s has" + " bowed back to me!%n", this.name, bower.getName());
        }
    }

    static class BowLoop implements Runnable {
        private Friend bower;
        private Friend bowee;

        public BowLoop(Friend bower, Friend bowee) {
            this.bower = bower;
            this.bowee = bowee;
        }

        public void run() {
            Random random = new Random();
            for (;;) {
                try {
                    Thread.sleep(random.nextInt(50));
                } catch (InterruptedException e) {
                }
                bowee.bow(bower);
            }
        }
    }

    public static void main(String[] args) {
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new BowLoop(alphonse, gaston)).start();
        new Thread(new BowLoop(gaston, alphonse)).start();
    }
}
