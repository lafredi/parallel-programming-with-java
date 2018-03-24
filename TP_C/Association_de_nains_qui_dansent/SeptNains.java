// -*- coding: utf-8 -*-

public class SeptNains{

    public static void main(String[] args){
        int nbNains = 7;
        BlancheNeige bn = new BlancheNeige();
        String nom [] = {"Simplet", "Dormeur",  "Atchoum", "Joyeux",
                         "Grincheux", "Prof", "Timide"};
        Nain nain [] = new Nain [nbNains];
        for(int i=0; i<nbNains; i++) {
            nain[i] = new Nain(nom[i], bn);
            nain[i].start();
        }
    }    
    
    class Nain extends Thread{
        public BlancheNeige bn;
        public Nain(String nom, BlancheNeige bn){
            this.setName(nom);
            this.bn = bn;
        }
	
        public void run(){
            Random alea = new Random();
            this.reveil = 1000 + alea.nextInt(5000);
            try { sleep(reveil); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(getName() + " veut danser avec Blanche-Neige.");
            bn.sAssocier();
            System.out.println(getName() + " est maintenant associé à Blanche-Neige.");
            bn.danser();
            System.out.println(getName() + " a commencé de danser avec un autre nain.");
            try {sleep(3000);} catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(getName() + " a fini de danser: il va se séparer.");
            bn.seSeparer();
        }
    }

    class BlancheNeige{
        ...
        public void sAssocier(){ ... }
        public void danser(){ ... }
        public void seSeparer(){ ... }
    }
}

