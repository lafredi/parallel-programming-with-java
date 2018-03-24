// -*- coding: utf-8 -*-

public class Compteur extends Thread {
    static volatile int valeur = 0;

    public static void main(String[] args) {
        Compteur Premier = new Compteur();
        Compteur Second = new Compteur();
        Premier.start(); Second.start();
        try{
            Premier.join(); Second.join();
        } catch(InterruptedException e){e.printStackTrace();} 
        System.out.println("La valeur finale est " + valeur);
    }

    public void run(){
        for (int i = 1; i <= 10000; i++)
            valeur++;
    }
} 

/*
$ java Compteur
La valeur finale est 4593
$ java Compteur
La valeur finale est 10000
$ java Compteur
La valeur finale est 19522
$ java Compteur
La valeur finale est 10000
$ java Compteur
La valeur finale est 10000
$ java Compteur
La valeur finale est 10000
$ java Compteur
La valeur finale est 10000
$ java Compteur
La valeur finale est 19591
*/

