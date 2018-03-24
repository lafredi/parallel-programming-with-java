// -*- coding: utf-8 -*-

public class Compteur extends Thread {
    static volatile int valeur = 0;

    public static void main(String[] args) {
        Compteur Premier = new Compteur();
        Compteur Second = new Compteur();
        Premier.start(); Second.start();
        try {
            Premier.join(); Second.join();
        } catch(InterruptedException e){e.printStackTrace();} 
        System.out.println("La valeur finale est " + valeur);
    }

    public void run(){
        for (int i = 1; i <= 10000; i++)
            synchronized (Compteur.class){ valeur++; }
    }
} 

/*
  $ java Compteur
  La valeur finale est 20000
  $ java Compteur
  La valeur finale est 20000
  $ java Compteur
  La valeur finale est 20000
  $ java Compteur
  La valeur finale est 20000
*/

