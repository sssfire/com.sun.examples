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
         * ÿһ��������һ����֮����������ڲ��������մ�ͳ����������һ���߳���Ҫ��һ��������ֶν��������Է��ʲ���
         * �ַ��ʵ�һ����ʱ���������ڷ���ǰ�Ȼ�ȡ�ö�����ڲ�����Ȼ����ܷ���֮������ͷŸ��ڲ��������̻߳�ȡ�����
         * �ڲ������ͷŶ�����ڲ��������ʱ�䣬����˵���߳�ӵ�иö�����ڲ�����ֻҪ��һ���߳��Ѿ�ӵ����һ���ڲ�����
         * �����߳̾Ͳ�����ӵ�и����ˡ������߳̽�������ͼ��ȡ������ʱ�������ˡ�
         * 
         * ��һ���̵߳���һ��ͬ��������ʱ�������Զ��ػ���˸÷�������������ڲ��������ڷ������ص�ʱ���ͷŸ�������
         * ʹ�����ڳ�����û�б�������쳣�����·������أ�����Ҳ�ᱻ�ͷš�
         * 
         * �������bow�����У���ִ��bower.bowback()ʱ����ǰ�߳�Ҫ�Ȼ�ȡbower���ڲ�����������bower���ڲ����Ѿ���
         * �����̻߳�ȡ�ˣ������������ͻ�ȡ�����˴Ӷ����ڵȴ�״̬��
         * 
         * ��ʹ��ͬ�����ʱ����ͬ��ͬ���������ڲ�����������ָ����ͬ�������ڲ�������ȷ��ָ������ȡ��
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
     * �������ᵼ������
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
