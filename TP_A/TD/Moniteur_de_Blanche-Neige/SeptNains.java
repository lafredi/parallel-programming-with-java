// -*- coding: utf-8 -*-

import java.util.ArrayList;

public class SeptNains {
    public static void main(String[] args) {
        int nbNains = 7;
        BlancheNeige bn = new BlancheNeige();
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux", "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i = 0; i < nbNains; i++) nain[i] = new Nain(nom[i], bn);
        for(int i = 0; i < nbNains; i++) nain[i].start();
    }
}    

class BlancheNeige {
    private volatile boolean libre = true;
    // Initialement, Blanche-Neige est libre.
    public synchronized void requerir() {
        System.out.println(Thread.currentThread().getName()
                           + " veut un accès exclusif à la ressource");
    }

    public synchronized void acceder() { }

    public synchronized void relacher() { }
}

class Nain extends Thread {
    public BlancheNeige bn;
    public Nain(String nom, BlancheNeige bn) {
        this.setName(nom);
        this.bn = bn;
    }
    public void run() {
        while(true) {
            bn.requerir();
            bn.acceder();
            System.out.println("\t\t" + getName() + " a un accès exclusif à Blanche-Neige.");
            try {sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
            bn.relacher();
        }
    }	
}
