// -*- coding: utf-8 -*-

/*
   On veut ici comparer les performances des verrous:
   - synchronized
   - Lock
   - Fair Lock
  en cas de forte contention, sur l'exemple des compteurs en rond.
*/


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Acteur extends Thread {
    static private int nbActeurs ;
    static private int nbActions ;
    static private String mode ;
    // Ce sont les trois paramètres à fournir  lors d'une exécution

    static private volatile Thread[] T ;
    static private volatile int partIndividuelle ;
    // Il s'agit du tableau des threads lancés et leur part de travail

    static private final Object verrou = new Object();          // Pour le mode S=synchronized
    static private final ReentrantLock verrouLock = new ReentrantLock();     // Pour le mode L 
    static private final ReentrantLock verrouFair = new ReentrantLock(true); // Pour le mode F

    static volatile int valeur;
    static volatile int tour;
    

    private final int id ;                // Chaque thread Acteur possède une identité propre.
    Acteur(int identite) {
        this.id = identite ;
    }

    public static void afficheUsage() {
        System.err.println 
            ("Usage: java Acteur <nb_Acteurs> <nb_Actions> <mode> \n"
             + "       sert à mesurer le temps de calcul nécessaire à <nb_Acteurs>\n"
             + "       threads pour effectuer ensemble <nb_Actions> dans le mode \n"
             + "       <mode>  parmi: S (synchronized), L (lock) ou F (fair lock)");
    }
    
    public static void main(String[] args) {
        if (args.length < 3) {
            afficheUsage();
            System.exit(1); 
        }
        try { nbActeurs = Integer.parseInt(args[0]); } 
        catch(NumberFormatException nfe) { 
            afficheUsage();
            System.err.println 
                ("Le premier paramètre doir être un entier >0."); 
            System.err.println(nfe.getMessage()); 
            System.exit(1); 
        }
        try { nbActions = Integer.parseInt(args[1]); } 
        catch(NumberFormatException nfe) { 
            afficheUsage();
            System.err.println 
                ("Le second paramètre doir être un entier >1000."); 
            System.err.println(nfe.getMessage()); 
            System.exit(1); 
        }
        mode = args[2];
        if (! (mode.equals("S") || mode.equals("L") || mode.equals("F"))) {
            afficheUsage();
            System.err.println 
                ("Le troisième paramètre doit être S, L ou F."); 
            System.exit(1); 
        }

        partIndividuelle = nbActions / nbActeurs;
	
        T = new Thread[nbActeurs];
        for(int id=0; id<nbActeurs; id++){
            T[id] = new Acteur(id);
        }

        String infos [] = {
            "pour " + partIndividuelle * nbActeurs
            + " actions avec " + nbActeurs + " threads.",
            "soit " + nbActions / nbActeurs
            + " actions par thread.",
            "Mode d'action adopté: " + mode};
        Chronometre t = new Chronometre(infos);
        // Lance un chronomètre pour mesurer le temps de calcul global
		
        for(int id=0; id<nbActeurs; id++) {
            T[id].start();
        }
        // Lance tous les threads acteurs

        try{
            for(int id=0; id<nbActeurs; id++){
                T[id].join();}
        } catch(InterruptedException e){e.printStackTrace();}
        // Attend que tous les threads aient terminé.
	
        t.stop();
        // Stoppe le chronomètre
        t.affiche(nbActeurs, " threads");
        // Affiche sur la sortie standard le temps de calcul observé
    }

    public void run(){
        if (mode.equals("S")) run_S();
        else if (mode.equals("L")) run_L();
        else if (mode.equals("F")) run_F();
    }

    public void run_S() {
        for (int i = 1; i <= partIndividuelle;){
            synchronized(verrou){
                if ( tour == id)                       // Si c'est mon tour
                    {
                        valeur++ ;                     // J'incrémente valeur
                        tour = (tour+1) % nbActeurs ;  // et je passe au voisin
                        i++ ;
                    }
            }
        }
    }

    public void run_L() {
        for (int i = 1; i <= partIndividuelle;){
            verrouLock.lock();
            try {
                if ( tour == id)
                    {
                        valeur++ ;
                        tour = (tour+1) % nbActeurs ;
                        i++ ;
                    }
            } finally { verrouLock.unlock(); }	    
        }
    }
    
    public void run_F() {
        for (int i = 1; i <= partIndividuelle;){
            verrouFair.lock();
            try {
                if ( tour == id)
                    {
                        valeur++;
                        tour = (tour+1) % nbActeurs ;
                        i++ ;
                    }
            } finally { verrouFair.unlock(); }	    
        }
    }
}

/*
  $ make
  javac *.java
  $ java Acteur 4 1000000 S
    #_CHRONO_#   Date du test : 18/01/17 06:08 sur une machine avec 8 coeurs.
    #_CHRONO_#   Temps de calcul en ms. 
    #_CHRONO_#   pour 1000000 actions avec 4 threads.
    #_CHRONO_#   soit 250000 actions par thread.
    #_CHRONO_#   Mode d'action adopté: S
  4 8099  #_CHRONO_#  8099 ms.  pour 4  threads
  $ java Acteur 4 1000000 L
    #_CHRONO_#   Date du test : 18/01/17 06:09 sur une machine avec 8 coeurs.
    #_CHRONO_#   Temps de calcul en ms. 
    #_CHRONO_#   pour 1000000 actions avec 4 threads.
    #_CHRONO_#   soit 250000 actions par thread.
    #_CHRONO_#   Mode d'action adopté: L
  4 9475  #_CHRONO_#  9475 ms.  pour 4  threads
  $ java Acteur 4 1000000 F
    #_CHRONO_#   Date du test : 18/01/17 06:09 sur une machine avec 8 coeurs.
    #_CHRONO_#   Temps de calcul en ms. 
    #_CHRONO_#   pour 1000000 actions avec 4 threads.
    #_CHRONO_#   soit 250000 actions par thread.
    #_CHRONO_#   Mode d'action adopté: F
  4 6862  #_CHRONO_#  6862 ms.  pour 4  threads
*/

