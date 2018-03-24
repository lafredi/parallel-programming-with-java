// -*- coding: utf-8 -*-

import java.lang.Math; 

public class MonteCarlo{
    
    public static void main(String[] args) {
        int nombreDeTirages = 1000 ;
        int tiragesDansLeDisque = 0 ;
        double x, y, resultat ;
      
        if (args.length>0) {
            try { nombreDeTirages = Integer.parseInt(args[0]); } 
            catch(NumberFormatException e) { 
                System.err.println 
                    ("Usage : java MonteCarlo <nb de tirages>"); 
                System.exit(1); 
            }
        }

        String infos [] = {"MODE: Sequentiel"};
        Chronometre t = new Chronometre(infos); // Lance un chronomètre

        for (long i = 0; i < nombreDeTirages; i++) {
            x = Math.random() ;
            y = Math.random() ;
            if (x * x + y * y <= 1) tiragesDansLeDisque++ ;
        }
        resultat = (double) tiragesDansLeDisque / nombreDeTirages ;

        t.stop(); // Stoppe le chronomètre
        t.affiche(); // Pour afficher le temps de calcul

        System.out.println("Estimation de Pi/4: " + resultat) ;
        System.out.println("Pourcentage d'erreur: "
                           + 100 * Math.abs(resultat-Math.PI/4)/(Math.PI/4)
                           + " %");
    }
}

/*
  $ make
  javac *.java
  $ java MonteCarlo 1000
  #_CHRONO_#   Date du test : 30/01/16 10:58 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   MODE: Sequentiel
  1  #_CHRONO_#   # en 1 ms.
  Estimation de Pi/4: 0.776
  Pourcentage d'erreur: 1.1966113285513689 %
  $ java MonteCarlo 100000
  #_CHRONO_#   Date du test : 30/01/16 10:58 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   MODE: Sequentiel
  7  #_CHRONO_#   # en 7 ms.
  Estimation de Pi/4: 0.78564
  Pourcentage d'erreur: 0.030791592573325813 %
  $ java MonteCarlo 10000000
  #_CHRONO_#   Date du test : 30/01/16 10:58 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   MODE: Sequentiel
  400  #_CHRONO_#   # en 400 ms.
  Estimation de Pi/4: 0.7855008
  Pourcentage d'erreur: 0.013068098110611584 %
  $ java MonteCarlo 1000000000
  #_CHRONO_#   Date du test : 04/02/17 05:56 sur une machine avec 8 coeurs.
  #_CHRONO_#   Temps de calcul en ms. 
  #_CHRONO_#   MODE: Sequentiel
  39101  #_CHRONO_#   # en 39101 ms.
  Estimation de Pi/4: 0.785421692
  Pourcentage d'erreur: 0.0029957547201213716 %
  $
*/
