package com.sun.example.java.thread;

public class Deadlock4FunctionSync {
    private class Friend {
        private String name;

        public Friend(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
        
        /**
         * 每一个对象都有一个与之相关联动的内部锁。按照传统的做法，当一个线程需要对一个对象的字段进行排他性访问并保
         * 持访问的一致性时，他必须在访问前先获取该对象的内部锁，然后才能访问之，最后释放该内部锁。在线程获取对象的
         * 内部锁到释放对象的内部锁的这段时间，我们说该线程拥有该对象的内部锁。只要有一个线程已经拥有了一个内部锁，
         * 其他线程就不能再拥有该锁了。其他线程将会在试图获取该锁的时候被阻塞了。
         * 
         * 当一个线程调用一个同步方法的时候，他就自动地获得了该方法所属对象的内部锁，并在方法返回的时候释放该锁。即
         * 使是由于出现了没有被捕获的异常而导致方法返回，该锁也会被释放。
         * 
         * 在下面的bow函数中，在执行bower.bowback()时，当前线程要先获取bower的内部锁。但由于bower的内部锁已经被
         * 其他线程获取了，所以他的锁就获取不到了从而处于等待状态。
         * 
         * 在使用同步语句时，不同于同步方法中内部锁会隐含的指定，同步语句的内部锁会明确的指定并获取。
         * 
         */
        

        public synchronized void bow(Friend bower) {
            System.out.format("%s: %s" + "  has bowed to me!%n", this.name, bower.getName());
            bower.bowBack(this); //dead lock
            //bowBack(this); //will not dead lock
        }

        public synchronized void bowBack(Friend bower) {
            System.out.format("%s: %s" + " has bowed back to me!%n", this.name, bower.getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
    	new Deadlock4FunctionSync().testDeadlock();
    }
    
    /**
     * 下面语句会导致死锁
     */
    public void testDeadlock(){
        final Friend alphonse = new Friend("Alphonse");
        final Friend gaston = new Friend("Gaston");
        new Thread(new Runnable() {
            public void run() {
                alphonse.bow(gaston);
            }
        }).start();
        new Thread(new Runnable() {
            public void run() {
                gaston.bow(alphonse);
            }
        }).start();
    }
}
