
package assignment;
import java.util.Random;
import java.util.Scanner;


public class OsAssignment {
    
    Philosopher[] philosophers;
    Fork[] forks;
    Thread[] threads;
    
    Scanner scan;
    int numb;

    
    public static void main(String[] args) {
        
        OsAssignment obj1 = new OsAssignment();
        obj1.X();
        obj1.thinkingEating();
        
    }
    
    public void X(){
        
        scan = new Scanner(System.in);
        
        System.out.println("Dining Philosopher Problem");
        
        try{
            
            System.out.println("Enter the number of Philosophers: ");
            number=scan.nextInt();
            
            if(numb<2){
                
                System.out.println("Number must be greater than 1.");
                return;
                
            }
            
        }catch(Exception e){
            System.out.println("you must enter an integer.");
        }
        
        philosophers=new Philosopher[number];
        forks=new Fork[numb];
        threads=new Thread[numb];
        
        for (int i=0;i<numb; i++){
            
            philosophers[i]=new Philosopher(i+1);
            forks[i]=new Fork(i+1);
            
        }
        
    }
    
    public void thinkingEating(){
        
        for(int i=0;i<numb;i++){
            final int index=i;
            threads[i]=new Thread(new Runnable(){
                public void run(){
                    try{
                        philosophers[index].start(forks[index],(index-1>0)? forks[index-1]:forks[number-1]);
                       
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }
                } 
            });
            threads[i].start();
        }        
    }
   
    public class Fork{
        
        private int forkId;
        private boolean status;
        
        Fork(int forkId){
            
            this.forkId=forkId;
            this.status=true;
        }
        
        public synchronized void free() throws InterruptedException{
            status=true;
            
        }
        
        public synchronized boolean pick(int philosopherId) throws InterruptedException{
            
            int counter = 0;
            int waitUntil = new Random().nextInt(10)+5;
            
            while(!status){
                Thread.sleep(new Random().nextInt(100)+50);
                
                counter++;
            
                if(counter>waitUntil){
                    return false;
                }
            }
            status = false;
            return true;   
        } 
            
    }
    
    public class Philosopher{
        
        private int philosopherId;
        private Fork left, right;
        
        private Philosopher(int philosopherId){
            this.philosopherId=philosopherId;
        }
        
        public void start(Fork left,Fork right) throws InterruptedException{
            
            this.left=left;
            this.right=right;
            
            while(true){
                if(new Random().nextBoolean()){
                    eat();
                }else{
                    think();
                }
            }
        }
        
        public void think() throws InterruptedException{
            
            System.out.println("The Philosopher: "+philosopherId+"is now thinking");
            Thread.sleep(new Random().nextInt(1000)+100);
            System.out.println("The Philosopher : "+ philosopherId+"has stopped thinking");
            
        }
        
        public void eat() throws InterruptedException{
            
            boolean rightpick=false;
            boolean leftpick=false;
            
            System.out.println("The philosopher : "+philosopherId+"is now hungry and wants to eat");
            
            System.out.println("The philosopher : "+philosopherId+"is now picking up the fork: "+left.forkId);
            leftpick=left.pick(philosopherId);
            
            if (!leftpick){
                return;
            }
            System.out.println("The philosopher : "+philosopherId+"is now picking up the fork: "+right.forkId);
            rightpick=right.pick(philosopherId);    
            
            if (!rightpick){
                left.free();
                return;
            }
            
            System.out.println("The philosopher : "+philosopherId+"is now eating");
            
            Thread.sleep(new Random().nextInt(1000)+100);
            left.free();
            right.free();   
            
            System.out.println("The philosopher : "+philosopherId+"has stopped eating and freed the forks");
   
        }
             
         
    }
}

