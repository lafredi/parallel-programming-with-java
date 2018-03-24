// -*- coding: utf-8 -*-

public class WaitNotify {    
    public static void main(String[] args) {
        Moniteur moniteur = new Moniteur();	
        Afficheur afficheur = new Afficheur(moniteur);
        Travailleur travailleur = new Travailleur(moniteur);
        afficheur.start();
        travailleur.start();
    }
}

class Moniteur {
	int data = 0;
	synchronized void unitialiser(){
	    try{
            Thread.sleep(1000);
        } catch(InterruptedException e){}		
	    data = 1;
	    notify();
	}	
	synchronized int recuperer(){
	    try{
            wait();
        } catch(InterruptedException e){}
	    return data;
	}
}

class Afficheur extends Thread {
	Moniteur moniteur;
	public Afficheur(Moniteur moniteur){
		this.moniteur = moniteur;
	}
	public void run(){
		System.out.println("Le résultat est " + moniteur.recuperer());
	}
}

class Travailleur extends Thread {
	Moniteur moniteur;	
	public Travailleur(Moniteur moniteur){
		this.moniteur = moniteur;
	}
	public void run(){
	    moniteur.unitialiser();
	}
}

/*
 $ javac WaitNotify.java
 $ java WaitNotify 
 Le résultat est 1
 $ java WaitNotify
 $ Le résultat est 1
 $ java WaitNotify
 (Le programme est bloqué)
*/



