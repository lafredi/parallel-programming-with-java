// -*- coding: utf-8 -*-

import java.util.Random;

public class Diner {    
    public static void main(String[] argv){
        Fourchette f[] = new Fourchette [5];
        for(int i = 0; i<5; i++) f[i] = new Fourchette();	
        String nom [] = {"Socrate", "Aristote",  "Epicure", "Platon", "Sénèque"};
        Philosophe p[] = new Philosophe [5];
        for(int i = 0; i<5; i++) p[i] = new Philosophe(f[i], f[(i+1)%5], nom[i]);
        for(int i = 0; i<5; i++) p[i].start();
    }
}	

class Fourchette {	
    boolean libre;

    public Fourchette(){
        libre = true ;
    }	
    synchronized void prendre(){	
        while ( !libre ) {
            try { wait() ; } // Le philosophe s'endort sur la fourchette!
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        libre = false ;      // Cette fourchette n'est plus libre
    }
    synchronized void lacher(){
        libre = true; 
        notifyAll();         // Réveille de tous les philosophes endormis
    }
}

class Philosophe extends Thread {	
    Fourchette fg;      // La fourchette à gauche
    Fourchette fd;      // La fourchette à droite
    String nom;         // Le nom du philosophe (optionnel)
    int patience;       // Le temps de la reflexion

    public Philosophe(Fourchette fg, Fourchette fd, String nom){
        this.fg = fg;
        this.fd = fd;
        this.nom = nom;
        Random alea = new Random();
        this.patience = 1000 + alea.nextInt(2000);
    }
    public void run(){
        while(true){
            System.out.println("[" + nom + "] Je réfléchis.");
            try { sleep(patience); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("[" + nom + "] Je suis affamé.");
            fg.prendre(); // Le philosophe prend d'abord la fourchette à gauche
            fd.prendre(); // Puis la fourchette à droite
            System.out.println("[" + nom + "] Je mange pendant 3s.");
            try { sleep(3000); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("[" + nom + "] J'ai bien mangé.");
            fg.lacher();
            fd.lacher();
        }
    }	
}
	
/*
  $ javac Diner.java
  $ java Diner
  [Aristote] Je réfléchis.
  [Sénèque] Je réfléchis.
  [Platon] Je réfléchis.
  [Socrate] Je réfléchis.
  [Epicure] Je réfléchis.
  [Sénèque] Je suis affamé.
  [Sénèque] Je mange pendant 3s.
  [Epicure] Je suis affamé.
  [Epicure] Je mange pendant 3s.
  [Platon] Je suis affamé.
  [Socrate] Je suis affamé.
  [Aristote] Je suis affamé.
  [Sénèque] J'ai bien mangé.
  [Sénèque] Je réfléchis.
  [Epicure] J'ai bien mangé.
  [Epicure] Je réfléchis.
  ...
*/
