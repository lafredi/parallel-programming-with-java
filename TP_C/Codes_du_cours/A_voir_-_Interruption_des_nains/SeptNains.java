// -*- coding: utf-8 -*-

import java.util.ArrayList;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SeptNains {
    public static void main(String[] args) {
        int nbNains = 7;
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(nom[i]);
        for(int i = 0; i < nbNains; i++) nain[i].start();
        
        /* Attendre 9s avant de lancer l'interruption des nains */
        try { Thread.sleep(9_000); } catch (InterruptedException e) {e.printStackTrace();}	

        /* Interrompre chaque nain */
        for(int i = 0; i < nbNains; i++) nain[i].interrupt();
        
        /* Attendre la terminaison de chaque nain */
       for(int i = 0; i < nbNains; i++){
            try { nain[i].join(); } catch (InterruptedException e) {e.printStackTrace();}	
        }
        System.out.println("Tous les nains ont terminé.");        
    }
}    


class Nain extends Thread {
    private static ReentrantLock bn = new ReentrantLock(true);
    public Nain(String nom) {
        this.setName(nom);
    }
    public void run() {
        while (true) {
            System.out.println(getName() + " veut accéder à Blanche-Neige.");
            try { 
                bn.lockInterruptibly();
                try {
                    System.out.println("\t" + getName() + " a un accès exclusif à Blanche-Neige.");
                    sleep(5_000);
                    System.out.println("\t\t" + getName() + " quitte Blanche-Neige.");
                }
                finally { bn.unlock(); }
            } catch (InterruptedException e) {
                // Le nain peut être interrompu alors qu'il attend le verrou (avec lockInterruptibly())
                // ou bien alors qu'il a accès à Blanche-Neige (avec sleep()).
                System.out.println("\t\t\t" + getName() + " est interrompu.");
                if ( bn.isHeldByCurrentThread() )
                    System.out.println("\t\t\t" + getName() + " garde le verrou!");                    
                break;
            }
        }
        System.out.println("Le nain " + getName() + " termine.");
    }	
}

/*
  Le bloc "finally" s'exécute même lors d'une "interrupted exception" sur l'instruction sleep().

  $ java SeptNains
  Dormeur veut accéder à Blanche-Neige.
  Prof veut accéder à Blanche-Neige.
  Grincheux veut accéder à Blanche-Neige.
  Joyeux veut accéder à Blanche-Neige.
  Atchoum veut accéder à Blanche-Neige.
  Simplet veut accéder à Blanche-Neige.
  Timide veut accéder à Blanche-Neige.
  ....Dormeur a un accès exclusif à Blanche-Neige.
  ........Dormeur quitte Blanche-Neige.
  Dormeur veut accéder à Blanche-Neige.
  ....Prof a un accès exclusif à Blanche-Neige.
  ............Simplet est interrompu.
  ............Atchoum est interrompu.
  Le nain Atchoum termine.
  ............Prof est interrompu.
  Le nain Prof termine.
  ............Grincheux est interrompu.
  Le nain Grincheux termine.
  ............Timide est interrompu.
  ............Joyeux est interrompu.
  ............Dormeur est interrompu.
  Le nain Dormeur termine.
  Le nain Joyeux termine.
  Le nain Timide termine.
  Le nain Simplet termine.
  Tous les nains ont terminé.
  $
*/
