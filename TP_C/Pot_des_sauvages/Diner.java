// -*- coding: utf-8 -*-

public class Diner {
    public static void main(String args[]) {
        int nbSauvages = 10;               // La tribu comporte 10 sauvages affamés
        int nbPortions = 5;                // Le pôt contient 5 parts, lorsqu'il est rempli
        System.out.println("Il y a " + nbSauvages + " sauvages.");
        System.out.println("Le pôt peut contenir "+ nbPortions + " portions.");
        Pot pot = new Pot(nbPortions);
        new Cuisinier(pot).start();
        for (int i = 0; i < nbSauvages; i++) {
            new Sauvage(pot).start();
        }
    } // CE PROGRAMME N'EST PAS SENSÉ TERMINER !
}  

class Sauvage extends Thread {
    public Pot pot;
    public Sauvage(Pot pot) {
        this.pot = pot;
    }
    public void run() {
        while (true) {
            try {
                System.out.println(getName() + ": J'ai faim!");
                pot.seServir();
                System.out.println(getName() + ": Je me suis servi et je vais manger!");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                break;
            }; 
        }
    }
}

class Cuisinier extends Thread {
    public Pot pot;
    public Cuisinier(Pot pot) {
        this.pot = pot;
    }
    public void run() {
        while(true){
            System.out.println("Cuisinier: Je m’endors.");
            try {
                pot.remplir();
            } catch (InterruptedException e) {
                break;
            }; 
        }
    }
}

class Pot {
    private volatile int nbPortions;
    private final int volume;

    public Pot(int nbPortions) {
		this.volume = nbPortions;
		this.nbPortions = nbPortions;
	}

	synchronized public boolean estVide() {
		return (nbPortions == 0); 
	}

	synchronized public void seServir() throws InterruptedException {
		if ( ! estVide() ) {
            System.out.println(Thread.currentThread().getName() + ": Il y a une part disponible ! ");
        } else { // Le pot est vide: on réveille le cuisinier
			System.out.println(Thread.currentThread().getName() + ": Le pôt est vide!");
			System.out.println(Thread.currentThread().getName() + ": Je réveille le cuisinier.");
			notifyAll();
			System.out.println(Thread.currentThread().getName() + ": J'attends que le pôt soit plein!");
            while ( estVide() ) { // Tant que le pôt est vide, je ne me sers pas.
                wait();
            }
			System.out.println(Thread.currentThread().getName() + ": Je me réveille! Je me sers.");
        }
		nbPortions--;	
	}
    
	synchronized public void remplir() throws InterruptedException {		
		while( ! estVide() ) { // Tant que le pôt n'est pas vide, je ne le remplis pas.
			wait();
		}
		System.out.println("Cuisinier: Je suis réveillé et je cuisine...");
        Thread.sleep(2000);
		nbPortions = volume;
		System.out.println("Cuisinier: Le pôt est plein!");
		notifyAll();
	}   
}


/*
  $ make
  javac *.java
  $ java Diner
  Il y a 10 sauvages.
  Le pôt peut contenir 5 portions.
  Cuisinier: Je m’endors.
  Thread-1: J'ai faim!
  Thread-2: J'ai faim!
  Thread-1: Il y a une part disponible ! 
  Thread-3: J'ai faim!
  Thread-4: J'ai faim!
  Thread-2: Il y a une part disponible ! 
  Thread-1: Je me suis servi et je vais manger!
  Thread-5: J'ai faim!
  Thread-4: Il y a une part disponible ! 
  Thread-2: Je me suis servi et je vais manger!
  Thread-7: J'ai faim!
  Thread-4: Je me suis servi et je vais manger!
  Thread-8: J'ai faim!
  Thread-3: Il y a une part disponible ! 
  Thread-6: J'ai faim!
  Thread-3: Je me suis servi et je vais manger!
  Thread-10: J'ai faim!
  Thread-9: J'ai faim!
  Thread-8: Il y a une part disponible ! 
  Thread-8: Je me suis servi et je vais manger!
  Thread-7: Le pôt est vide!
  Thread-7: Je réveille le cuisinier.
  Thread-7: J'attends que le pôt soit plein!
  Thread-5: Le pôt est vide!
  Thread-5: Je réveille le cuisinier.
  Thread-5: J'attends que le pôt soit plein!
  Cuisinier: Je suis réveillé et je cuisine...
  Thread-1: J'ai faim!
  Thread-4: J'ai faim!
  Thread-8: J'ai faim!
  Thread-3: J'ai faim!
  Thread-2: J'ai faim!
  Cuisinier: Le pôt est plein!
  Cuisinier: Je m’endors.
  Thread-9: Il y a une part disponible ! 
  Thread-9: Je me suis servi et je vais manger!
  Thread-10: Il y a une part disponible ! 
  Thread-10: Je me suis servi et je vais manger!
  Thread-6: Il y a une part disponible ! 
  Thread-6: Je me suis servi et je vais manger!
  Thread-7: Je me réveille! Je me sers.
  Thread-7: Je me suis servi et je vais manger!
  Thread-5: Je me réveille! Je me sers.
  Thread-5: Je me suis servi et je vais manger!
  Thread-2: Le pôt est vide!
  Thread-2: Je réveille le cuisinier.
  ^C
*/
