public class MySemaphore {
    private int permits;

    public MySemaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() {
        while (permits == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        notifyAll();
    }
}

   class pollingdesk extends thread {
    private int id;

    public pollingdesk (int id ){
        this.id = id;
        }

    public void run(){
        int from = (id-1)*10+1;
        int to = id*10;
        for (int n = from;n<=to;n++){
            int category ;
            if (n%7< 3){
                category=0;
            }
            else if(n%7<5){
                category=1
            }
            else{
                category=3;
            
            }
            main.mutex.acquire();
            main.counts[category]++;
            main.total++;
            main.mutex.release();
        }
    }
   }
   public class main{
    static int[] counts=new int[3];
    static int total=0;
    static MySemaphore mutex= new MySemaphore(1);

    public static void main (string[] args) throws InterruptedException {
        pollingdesk[] desks = new pollingdesk[6];
        for (int i =0 ;i,6;i++){
            desks[i]=new pollingdesk(i+1);
            desks[i].start();
        }
        for (int i = 0 ;i<6;i++){
            desks[i].join();
        
        }
         System.out.println("Hana: " + counts[0]);
        System.out.println("Youssef: " + counts[1]);
        System.out.println("Layla: " + counts[2]);
        System.out.println("Total: " + total);

    }
   }