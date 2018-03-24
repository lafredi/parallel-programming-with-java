// -*- coding: utf-8 -*-

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public static void main(String[] args)  {
        if (args.length > 0) {    
            try { nbTirages = Long.parseLong(args[0]); } 
            catch(NumberFormatException nfe) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages> ..."); 
                System.exit(1); 
            }
        }

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
            "MODE: Runnables affectés à un réservoir manuellement." };
        Chronometre t = new Chronometre(infos); // Lance un chronomètre

        // Création du réservoir formé de nbThreads esclaves
        ExecutorService executeur = Executors.newFixedThreadPool(nbThreads) ;
      
        // Remplissage de la liste des tâches
        MultipleLancer[] tableDesTaches = new MultipleLancer[nbThreads] ;
        for (int j = 0; j < nbThreads ; j++){
            tableDesTaches[j] = new MultipleLancer( nbTirages/nbThreads ) ;
            executeur.execute(tableDesTaches[j]);
        }
        executeur.shutdown(); // Il n'y a plus aucune tâche à soumettre
        // Il faut maintenant attendre la fin des calculs
        try{
            while (! executeur.awaitTermination(1, TimeUnit.SECONDS)) {
                System.out.print("#") ;
            }
        } catch (InterruptedException e) {e.printStackTrace();}
        System.out.println() ;

        long tiragesDansLeDisque = 0 ;
        for (int j = 0; j < nbThreads ; j++)
            tiragesDansLeDisque += tableDesTaches[j].tiragesDansLeDisque ;
        double resultat = (double) tiragesDansLeDisque / nbTirages ;

        t.stop(); // Stoppe le chronomètre
        t.affiche(nbThreads, " threads"); // Afficher le temps de calcul

        System.out.println("Estimation de Pi/4: " + resultat) ;
        System.out.println("Pourcentage d'erreur: "
                           + 100 * Math.abs(resultat-Math.PI/4)/(Math.PI/4)
                           + " %");
    }
}


class MultipleLancer implements Runnable{
    long nbTirages;
    long tiragesDansLeDisque = 0 ;

    MultipleLancer(long part){
        nbTirages = part;
    }
    
    public void run(){
        double x, y;
        for (long i = 0; i < nbTirages; i++) {
            x = ThreadLocalRandom.current().nextDouble(1);
            y = ThreadLocalRandom.current().nextDouble(1);
            if (x * x + y * y <= 1) {
                tiragesDansLeDisque++ ;
            }
        }
    }
}

/*
  $ make
  javac *.java
  $ java MonteCarlo 100000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 11:32 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 100000000 actions avec 4 threads.
  #_CHRONO_#   soit 25000000 actions par thread.
  #_CHRONO_#   MODE: Runnables affectés à un réservoir manuellement.

  4 220  #_CHRONO_#  220 ms.  pour 4  threads
  Estimation de Pi/4: 0.7853904
  Pourcentage d'erreur: 9.884664632588797E-4 %
  $ java MonteCarlo 1000000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 11:32 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 1000000000 actions avec 4 threads.
  #_CHRONO_#   soit 250000000 actions par thread.
  #_CHRONO_#   MODE: Runnables affectés à un réservoir manuellement.
  ##
  4 2005  #_CHRONO_#  2005 ms.  pour 4  threads
  Estimation de Pi/4: 0.785385612
  Pourcentage d'erreur: 0.0015980935572823182 %
  $ java MonteCarlo 10000000000 4
  Il y a 8 coeurs disponibles.
  #_CHRONO_#   Date du test : 30/01/16 11:32 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 10000000000 actions avec 4 threads.
  #_CHRONO_#   soit 2500000000 actions par thread.
  #_CHRONO_#   MODE: Runnables affectés à un réservoir manuellement.
  ####################
  4 20761  #_CHRONO_#  20761 ms.  pour 4  threads
  Estimation de Pi/4: 0.7854015408
  Pourcentage d'erreur: 4.300242487365825E-4 %
*/

    
