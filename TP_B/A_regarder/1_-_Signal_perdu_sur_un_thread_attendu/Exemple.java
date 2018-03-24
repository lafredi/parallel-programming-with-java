// -*- coding: utf-8 -*-

public class Exemple {
    public static void main(String[] args) throws Exception {
        A a = new A(); // Création d'un objet a de la classe A
        a.start();     // Lancement du thread a
        B b = new B(a); // Création d'un objet b de la classe B
        b.start();     // Lancement du thread b
        a.join();
        System.out.println("Le main est réveillé et va terminer.");  
    }
    
    static class A extends Thread {
        public void run() {
            // Le thread A attend un signal sur lui-même
            try{ Thread.sleep(1000); } catch(InterruptedException ignoree){}
            System.out.println("Le thread A attend un signal sur lui-même.");  
            synchronized(this){
                try{ this.wait(); }
                catch(InterruptedException ignoree){}
            }
        }  
    }

    static class B extends Thread {
        A a;
        B(A a) {
            this.a = a;
        }
        public void run() {
            try{ Thread.sleep(2000); } catch(InterruptedException ignoree){}
            System.out.println("Le thread B envoie un signal sur le thread A.");  
            synchronized(a){
                a.notify();
            }
        }  
    }

}
/*
  $ make
  javac *.java
  $ java Exemple
  Le thread A attend un signal sur lui-même.
  Le thread B envoie un signal sur le thread A.
  ^C (bloqué pendant plus de deux secondes)

  La méthode join() effectue un wait() sur le thread qu'il attend:
  il peut donc consommer un signal!
  Ajouter une boucle while quelconque ne changera rien:
  un thread ne doit donc pas attendre sur lui-même ou même sur un autre thread.
*/
