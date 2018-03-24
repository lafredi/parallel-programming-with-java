// -*- coding: utf-8 -*-

class Consommateur extends Thread {	
    Buffer buffer;    
    byte donnee;
    public Consommateur(Buffer buffer){
        this.buffer = buffer;
        start();
    }
    public void run(){
        while (true) { 
            donnee = buffer.consommer();
            try{ Thread.sleep(500) ;}
            catch(InterruptedException e){}
        }
    }
}	
