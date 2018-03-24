// -*- coding: utf-8 -*-

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SeptNains{
    public static void main(String[] args){
        int nbNains = 7;
        BlancheNeige bn = new BlancheNeige();
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux",
                         "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i=0; i<nbNains; i++) nain[i] = new Nain(nom[i], bn);
        for(int i=0; i<nbNains; i++) nain[i].start();
    }
}    

class BlancheNeige{
    private ReentrantLock verrou = new ReentrantLock(true);
    public void requerir(){
        System.out.println(Thread.currentThread().getName()
                           + " veut la ressource");
    }

    public void acceder(){
        verrou.lock();
        System.out.println("\t" + Thread.currentThread().getName()
                           + " accède à la ressource.");
    }

    public void relacher(){
        System.out.println("\t\t" + Thread.currentThread().getName()
                           + " relâche la ressource.");
        verrou.unlock();
    }
}

class Nain extends Thread{
    public BlancheNeige bn;
    public Nain(String nom, BlancheNeige bn){
        this.setName(nom);
        this.bn = bn;
    }
    public void run(){
        while(true){
            bn.requerir();
            bn.acceder();
            try {sleep(1000);}
            catch (InterruptedException e) {e.printStackTrace();}
            bn.relacher();
        }
    }	
}

/*
$ javac SeptNains.java
$ java SeptNains
Simplet veut la ressource
Timide veut la ressource
Prof veut la ressource
Atchoum veut la ressource
Dormeur veut la ressource
Grincheux veut la ressource
Joyeux veut la ressource
	Simplet accède à la ressource.
		Simplet relâche la ressource.
Simplet veut la ressource
	Prof accède à la ressource.
		Prof relâche la ressource.
Prof veut la ressource
	Timide accède à la ressource.
		Timide relâche la ressource.
Timide veut la ressource
	Atchoum accède à la ressource.
		Atchoum relâche la ressource.
Atchoum veut la ressource
	Dormeur accède à la ressource.
		Dormeur relâche la ressource.
Dormeur veut la ressource
	Grincheux accède à la ressource.
		Grincheux relâche la ressource.
Grincheux veut la ressource
	Joyeux accède à la ressource.
		Joyeux relâche la ressource.
Joyeux veut la ressource
	Simplet accède à la ressource.
		Simplet relâche la ressource.
Simplet veut la ressource
	Prof accède à la ressource.
		Prof relâche la ressource.
Prof veut la ressource
	Timide accède à la ressource.
		Timide relâche la ressource.
Timide veut la ressource
	Atchoum accède à la ressource.
		Atchoum relâche la ressource.
Atchoum veut la ressource
	Dormeur accède à la ressource.
		Dormeur relâche la ressource.
Dormeur veut la ressource
	Grincheux accède à la ressource.
		Grincheux relâche la ressource.
Grincheux veut la ressource
	Joyeux accède à la ressource.
...
*/
