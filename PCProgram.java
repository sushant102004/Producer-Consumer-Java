import java.util.LinkedList;

class PCProgram {
    public static void main(String[] args) throws Exception {
        final PC pc = new PC();

        Thread t1 = new Thread(new Runnable(){
            public void run(){
                try {
                    pc.produce();    
                } catch(Exception e){
                    System.out.println(e);
                }
            }
        });

        Thread t2 = new Thread(new Runnable(){
            public void run(){
                try {
                    pc.consume();
                } catch(Exception e){
                    System.out.println(e);
                }
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
    }
}

class PC {
    LinkedList <Integer> list = new LinkedList<>();
    int capacity = 2;

    public void produce() throws Exception {
        int value = 0;
        while(true){
            synchronized(this){
                while(list.size() == capacity){
                    wait();
                }
                System.out.println("Produced: " + value);
                
                list.add(value++);
                notify();

                Thread.sleep(1000);
            }
        }
    }

    public void consume() throws Exception {
        while(true){
            synchronized(this){
                while(list.size() == 0){
                    wait();
                }
                int val = list.removeFirst();

                System.out.println("Consumed: " + val);

                notify();
                Thread.sleep(1000);
            }
        }
    }
}