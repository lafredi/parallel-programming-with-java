// -*- coding: utf-8 -*-
import java.util.Random ;

class PhilosopheIndiscipline extends Thread  {	
    String nom;         // Le nom du philosophe
    int patience;       // Le temps de la réflexion du philosophe
    static volatile int nbFourchettesDisponibles = 5;  
    public PhilosopheIndiscipline(String nom){
        this.nom = nom;
        Random alea = new Random();
        this.patience = 1000 * alea.nextInt(5);
        start();
    }  

    public void prendreDeuxFourchettes(){
        if (nbFourchettesDisponibles < 2)
            synchronized(this){
                try {
                    wait();
                } catch (InterruptedException e) { e.printStackTrace(); }
            }
        nbFourchettesDisponibles = nbFourchettesDisponibles -2 ;
    }    

    public void lacherDeuxFourchettes(){
        nbFourchettesDisponibles = nbFourchettesDisponibles +2 ;
        synchronized(this){
            notify();
        }
    }

    public void run() {
        for (;;) {
            System.out.println("[Phil] ("+this.nom+") Je pense "+patience+" ms.");
            try {
                Thread.sleep(patience);
            } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("[Phil] ("+this.nom+") Je suis affamé.");
            prendreDeuxFourchettes();   // le philosophe prend deux fourchettes
            System.out.println("[Phil] ("+this.nom+") Je mange pendant 3s.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("[Phil] ("+this.nom+") J'ai bien mangé.");
            lacherDeuxFourchettes(); 
        }
    }
    public static void main(String[] argv){
        String noms [] = {"Socrate", "Aristote",  "Epicure", "Descartes", "Nietzsche"};
        for(int i=0; i<5; i++) new PhilosopheIndiscipline(noms[i]);
    }	
}
	
/*
  $ java PhilosopheIndiscipline
  [Phil] (Socrate) Je pense 0 ms.
  [Phil] (Socrate) Je suis affamé.
  [Phil] (Socrate) Je mange pendant 3s.
  [Phil] (Aristote) Je pense 0 ms.
  [Phil] (Aristote) Je suis affamé.
  [Phil] (Aristote) Je mange pendant 3s.
  [Phil] (Epicure) Je pense 0 ms.
  [Phil] (Descartes) Je pense 0 ms.
  [Phil] (Epicure) Je suis affamé.
  [Phil] (Descartes) Je suis affamé.
  [Phil] (Nietzsche) Je pense 0 ms.
  [Phil] (Nietzsche) Je suis affamé.
  [Phil] (Socrate) J'ai bien mangé.
  [Phil] (Aristote) J'ai bien mangé.
            
  BLOCAGE

*/
