// -*- coding: utf-8 -*-
import java.util.ArrayList;

import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.*;

import java.util.concurrent.ThreadLocalRandom;
/*
  A random number generator isolated to the current thread. Like the
  global Random generator used by the Math class, a ThreadLocalRandom
  is initialized with an internally generated seed that may not
  otherwise be modified. When applicable, use of ThreadLocalRandom
  rather than shared Random objects in concurrent programs will
  typically encounter much less overhead and contention. Use of
  ThreadLocalRandom is particularly appropriate when multiple tasks
  (for example, each a ForkJoinTask) use random numbers in parallel
  in thread pools.
*/

public class MonteCarlo {  
    static int nbThreads = Runtime.getRuntime().availableProcessors() ;
    // A priori, on lance un thread par coeur disponible

    static long nbTirages = 1000_000_000 ;

    public static void main(String[] args) {
        if (args.length > 0) {    
            try { nbTirages = Long.parseLong(args[0]); } 
            catch(NumberFormatException nfe) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages> ..."); 
                System.exit(1); 
            }
        }
        System.out.println("Il y a " + nbThreads + " coeurs disponibles.");
        if (args.length > 1) {    
            try { nbThreads = Integer.parseInt(args[1]); } 
            catch(NumberFormatException nfe) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages> <nb de threads>"); 
                System.exit(1); 
            }
        }
      
        System.out.println("Il y a " + nbThreads + " coeurs disponibles.");

        String infos [] = {
            "pour " + (nbTirages/nbThreads) * nbThreads
            + " actions avec " + nbThreads + " threads.",
            "soit " + nbTirages / nbThreads
            + " actions par thread.",
            "MODE: Futures avec service de completion." };
        Chronometre t = new Chronometre(infos); // Lance un chronomètre

        // Création du réservoir formé de nbThreads esclaves
        ExecutorService executeur = Executors.newFixedThreadPool(nbThreads);
        CompletionService<Long> ecs =
            new ExecutorCompletionService<Long>(executeur);
        for (int j = 0; j < nbThreads ; j++) {
            MultipleLancer tache = new MultipleLancer( nbTirages/nbThreads );
            ecs.submit(tache);
        }
        int tiragesDansLeDisque = 0;
        for (int j = 0; j < nbThreads; j++) {
            try{
                Future<Long> promesse = ecs.take() ;     // take() est bloquant
                Long resultatAttendu = promesse.get() ;  // get() ne bloquera pas!
                tiragesDansLeDisque += resultatAttendu ; 
            } catch(Exception ignoree){};
        }      
        double resultat = (double) tiragesDansLeDisque / nbTirages ;

        t.stop(); // Stoppe le chronomètre
        t.affiche(nbThreads, " threads"); // Afficher le temps de calcul

        System.out.println("Estimation de Pi/4: " + resultat) ;
        System.out.println("Pourcentage d'erreur: "
                           + 100 * Math.abs(resultat-Math.PI/4)/(Math.PI/4)
                           + " %");
        executeur.shutdown(); // Il n'y a plus aucune tâche à soumettre
    }
}


class MultipleLancer implements Callable<Long>{
    long nbTirages;
    long tiragesDansLeDisque = 0 ;

    MultipleLancer(long part){
        nbTirages = part;
    }
    
    public Long call(){
        double x, y;
        for (long i = 0; i < nbTirages; i++) {
            x = ThreadLocalRandom.current().nextDouble(1);
            y = ThreadLocalRandom.current().nextDouble(1);
            if (x * x + y * y <= 1) tiragesDansLeDisque++ ;
        }
        return tiragesDansLeDisque;
    }
}

/*
  $ make
  javac *.java
  $ java MonteCarlo 1000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 10:31 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 1000000 actions avec 4 threads.
  #_CHRONO_#   soit 250000 actions par thread.
  #_CHRONO_#   MODE: Futures avec service de completion.
  4 33  #_CHRONO_#  33 ms.  pour 4  threads
  Estimation de Pi/4: 0.786239
  Pourcentage d'erreur: 0.10705864130296412 %
  $ java MonteCarlo 10000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 10:31 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 10000000 actions avec 4 threads.
  #_CHRONO_#   soit 2500000 actions par thread.
  #_CHRONO_#   MODE: Futures avec service de completion.
  4 47  #_CHRONO_#  47 ms.  pour 4  threads
  Estimation de Pi/4: 0.7856111
  Pourcentage d'erreur: 0.027111930289041398 %
  $ java MonteCarlo 100000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 10:31 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 100000000 actions avec 4 threads.
  #_CHRONO_#   soit 25000000 actions par thread.
  #_CHRONO_#   MODE: Futures avec service de completion.
  4 231  #_CHRONO_#  231 ms.  pour 4  threads
  Estimation de Pi/4: 0.78535832
  Pourcentage d'erreur: 0.005073018922767995 %
  $ java MonteCarlo 1000000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 10:31 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 1000000000 actions avec 4 threads.
  #_CHRONO_#   soit 250000000 actions par thread.
  #_CHRONO_#   MODE: Futures avec service de completion.
  4 2173  #_CHRONO_#  2173 ms.  pour 4  threads
  Estimation de Pi/4: 0.785403939
  Pourcentage d'erreur: 7.353725563589294E-4 %
*/

    
