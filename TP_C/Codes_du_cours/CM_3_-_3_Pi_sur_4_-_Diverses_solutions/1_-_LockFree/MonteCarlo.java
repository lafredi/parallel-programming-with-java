// -*- coding: utf-8 -*-

import java.lang.Thread; 
import java.lang.Math;

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
    static volatile int nombreDeThreads = Runtime.getRuntime().availableProcessors() ;
    // A priori, on lance un thread par coeur disponible ;
    
    static volatile int nombreDeTirages = 1_000_000_000 ; 
    public volatile int tiragesDansLeDisque = 0 ;

    public static void main (String args[]) {
        if (args.length>0) {
            try { nombreDeTirages = Integer.parseInt(args[0]); } 
            catch(NumberFormatException e) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages>"); 
                System.exit(1); 
            }
        }

        if (args.length>1) {
            try { nombreDeThreads = Integer.parseInt(args[1]); } 
            catch(NumberFormatException e) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages> <nb de threads>"); 
                System.exit(1); 
            }
        }
      
        String infos [] = {
            "pour " + (nombreDeTirages/nombreDeThreads) * nombreDeThreads
            + " actions avec " + nombreDeThreads + " threads.",
            "soit " + nombreDeTirages / nombreDeThreads
            + " actions par thread.",
            "MODE: Gestion manuelle du réservoir, sans verrou." };
        Chronometre t = new Chronometre(infos); // Lance un chronomètre

        MultipleLancer[] tableDesTaches = new MultipleLancer[nombreDeThreads] ;
        Thread[] tableDesThreads = new Thread[nombreDeThreads] ;
        for (int j = 0; j < nombreDeThreads ; j++){
            tableDesTaches[j] = new MultipleLancer( nombreDeTirages/nombreDeThreads ) ;
            tableDesThreads[j] = new Thread (tableDesTaches[j]);
            tableDesThreads[j].start();
        }

        // Il faut maintenant attendre la fin des calculs
        for(int i=0; i<nombreDeThreads; i++){
            try{ tableDesThreads[i].join();}
            catch(InterruptedException e){e.printStackTrace();}
        }

        int somme = 0;
        for(int i=0; i<nombreDeThreads; i++){
            somme += tableDesTaches[i].tiragesDansLeDisque ;
        }
        double resultat = (double) somme / nombreDeTirages ;

        t.stop(); // Stoppe le chronomètre
        t.affiche(nombreDeThreads, " threads"); // Afficher le temps de calcul
          
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
$ java MonteCarlo 1000000 4
  #_CHRONO_#   Date du test : 30/01/16 10:55 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 1000000 actions avec 4 threads.
  #_CHRONO_#   soit 250000 actions par thread.
  #_CHRONO_#   MODE: Gestion manuelle du réservoir, sans verrou.
4 19  #_CHRONO_#  19 ms.  pour 4  threads
Estimation de Pi/4: 0.785428
Pourcentage d'erreur: 0.0037989142249416383 %
$ java MonteCarlo 10000000 4
  #_CHRONO_#   Date du test : 30/01/16 10:55 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 10000000 actions avec 4 threads.
  #_CHRONO_#   soit 2500000 actions par thread.
  #_CHRONO_#   MODE: Gestion manuelle du réservoir, sans verrou.
4 53  #_CHRONO_#  53 ms.  pour 4  threads
Estimation de Pi/4: 0.7852504
Pourcentage d'erreur: 0.018813820089554848 %
$ java MonteCarlo 100000000 4
  #_CHRONO_#   Date du test : 30/01/16 10:55 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   pour 100000000 actions avec 4 threads.
  #_CHRONO_#   soit 25000000 actions par thread.
  #_CHRONO_#   MODE: Gestion manuelle du réservoir, sans verrou.
4 329  #_CHRONO_#  329 ms.  pour 4  threads
Estimation de Pi/4: 0.78536235
Pourcentage d'erreur: 0.0045599033862482935 %
$ 
*/
