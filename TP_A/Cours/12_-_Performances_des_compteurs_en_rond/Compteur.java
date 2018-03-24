// -*- coding: utf-8 -*-

import java.text.DateFormat;
import java.util.Date;

public class Compteur extends Thread {
    static volatile String mode;
    static volatile int valeurCible;
    static volatile int nbCompteurs;
    static volatile int part;
    static volatile Thread[] T;
    
    static volatile int valeur = 0;
    static volatile int tour = 0;
    // valeur et tour sont partagés par tous les Compteurs
    
    final int identite;
    // Chaque thread Compteur possède une identité propre.
    
    Compteur(int id) {
        this.identite = id ;
    }
    
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println 
                ("Usage: java Compteur <nb_de_Compteurs> <valeur_cible> <mode>"); 
            System.err.println 
                ("       Modes possibles : AAN AAY AP ATP"); 
            System.exit(1); 
        }
        try { nbCompteurs = Integer.parseInt(args[0]); } 
        catch(NumberFormatException nfe) { 
            System.err.println 
                ("Usage: java Compteur *nb_de_Compteurs* <valeur_cible> <mode>"); 
            System.err.println(nfe.getMessage()); 
            System.exit(1); 
        }
        try { valeurCible = Integer.parseInt(args[1]); } 
        catch(NumberFormatException nfe) { 
            System.err.println 
                ("Usage: java Compteur <nb_de_Compteurs> *valeur_cible* <mode>"); 
            System.err.println(nfe.getMessage()); 
            System.exit(1); 
        }
        mode = args[2];
        part = valeurCible / nbCompteurs;
	
        T = new Thread[nbCompteurs];
        for(int id=0; id<nbCompteurs; id++){
            T[id] = new Compteur(id);
        }
        Date aujourdhui = new Date();
        DateFormat shortDateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
                                                                    DateFormat.SHORT);
        System.out.println("# Date du test : " + shortDateFormat.format(aujourdhui));
        System.out.println("# sur une machine avec " +
                           Runtime.getRuntime().availableProcessors()
                           + " coeurs.");
       	System.out.println("# Temps de calcul en ms. pour "
                           + (valeurCible/nbCompteurs) * nbCompteurs
                           + " incrémentations"
                           + " avec " + nbCompteurs + " threads.") ;
       	System.out.println("# soit " + valeurCible / nbCompteurs
                           + " incrémentations par thread.") ;
        System.out.println("# Mode d'attente de tour : " + mode );
        final long startTime = System.nanoTime();
        final long endTime;
        for(int id=0; id<nbCompteurs; id++) T[id].start();
        try{
            for(int id=0; id<nbCompteurs; id++){
                T[id].join();}
        } catch(InterruptedException e){e.printStackTrace();}
        endTime = System.nanoTime();
        final double duree = ((double) endTime - startTime) / 1000000 ;
        System.out.println(nbCompteurs + " " + (double) duree + " # ms.");
    }
    
    
    /*
      Les Compteurs comptent jusqu'à valeurCible
      tous ensemble, mais à tour de rôle
    */

    public void run(){
        if (mode.equals("AAN")) run_AAN();
        else if (mode.equals("AAY")) run_AAY();
        else if (mode.equals("AP")) run_AP();
        else if (mode.equals("ATP")) run_ATP();
        else {
            System.err.println 
                ("Usage: java Compteur <nb_de_Compteurs> <valeur_cible> <mode>"); 
            System.err.println 
                ("       Modes possibles : AA"); 
            System.exit(1); 
        }
    }
    public void run_AAN(){
        for (int i = 1;i<=part;i++){
            while(tour!=identite); // Attente active naïve.
            valeur++;
            tour = (tour+1) % nbCompteurs;
        } 
    }
    public void run_AAY(){
        for (int i = 1;i<=part;i++){
            while(tour!=identite) yield() ; // Attente active
            valeur++;
            tour = (tour+1) % nbCompteurs;
        } 
    }
    public void run_AP(){
        for (int i = 1;i<=part;i++){
            synchronized(Compteur.class){
                while(tour!=identite){
                    try{ Compteur.class.wait(); } // Attente passive sur la classe
                    catch(Exception e){e.printStackTrace();};
                }
                valeur++;
                tour = (tour+1) % nbCompteurs;
                Compteur.class.notifyAll();
            }
        } 
    }
    public void run_ATP(){
        final Thread suivant = T[(identite+1) % nbCompteurs];
        final Thread courant = T[identite];
        for (int i = 1;i<=part;i++){
            synchronized(courant){
                while(tour!=identite){
                    try{courant.wait(); } // Attente passive sur soi-même
                    catch(Exception e){e.printStackTrace();};
                }
                synchronized(suivant){
                    valeur++;
                    tour = (tour+1) % nbCompteurs;
                    suivant.notifyAll(); // Il faut mettre notifyAll()
                    // car join() effectue lui aussi un wait() sur le thread!
                }
            }
        }
    }
}



