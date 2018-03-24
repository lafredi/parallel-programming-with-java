// -*- coding: utf-8 -*-

import java.lang.Math; 
import java.text.DateFormat;
import java.util.Date;

public class Chronometre {
    private long debut, fin, duree;
    private String[] infos;
    private final String tag="  #_CHRONO_#  ";

    Chronometre(String[] infos){
        this.infos = infos;
        afficheInfos();
        this.debut = System.nanoTime();
    }
    
    void stop(){
        fin = System.nanoTime();
        duree = (fin - debut) / 1000000 ;
    }

    void afficheInfos(){
        Date aujourdhui = new Date();
        DateFormat shortDateFormat =
            DateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.SHORT);
        System.out.print(tag + " Date du test : "
                         + shortDateFormat.format(aujourdhui));
        System.out.println(" sur une machine avec "
                           + Runtime.getRuntime().availableProcessors()
                           + " coeurs.");
        System.out.println(tag + " Temps de calcul en ms. ");
        for (int i = 0; i < infos.length ; i++) {
            System.out.println(tag + " " + infos[i]);
        }	    	
    }

    void affiche(long parametre, String nomDuParametre){
        System.out.println(parametre + " " + duree
                           + tag + duree + " ms. "
                           + " pour " + parametre + " " + nomDuParametre);
    }

    void affiche(){
        System.out.println(duree + tag
                           + " # en " + duree + " ms.");
    }
    
}
