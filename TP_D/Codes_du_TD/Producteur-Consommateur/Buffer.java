// -*- coding: utf-8 -*-

class Buffer {
    private final int taille;
    private final byte[] buffer;    
    private int disponibles = 0;
    
    Buffer(int taille) {
        this.taille = taille ;
        this.buffer = new byte[taille];
    }

    synchronized void produire(byte b) {
        while ( disponibles == taille ) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        buffer[disponibles] = b;
        disponibles++;
        System.out.print("(" + disponibles + ")");
        notify();
    }
    
    synchronized byte consommer() {
        while (disponibles == 0) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        byte element = buffer[disponibles - 1];
        disponibles--;
        System.out.print("(" + disponibles + ")");
        notify();
        return element;
    }
    
    public static void main(String[] argv){
        Buffer monBuffer = new Buffer(1);
        for(int i=0; i<2; i++) new Producteur(monBuffer);
        for(int i=0; i<2; i++) new Consommateur(monBuffer);
    }	
}  

	
/*
$ java Buffer
(1)(0)  CTRL+C
$ java Buffer
(1)(0)(1)(0) CTRL+C
$ java Buffer
(1)(0)(1)(0)(1)(0)(1)(0)(1)(0)(1)(0) CTRL+C
*/
