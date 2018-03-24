// -*- coding: utf-8 -*-

class Producteur extends Thread {	
    Buffer buffer;    
    byte donnee = 0;
    public Producteur(Buffer buffer){
        this.buffer = buffer;
        start();
    }
    public void run(){
        while (true) { 
            try{ Thread.sleep(1000) ;}
            catch(InterruptedException e){}
            buffer.produire(donnee);
            try{ Thread.sleep(2000) ;}
            catch(InterruptedException e){}
        }
    }
}	
