// -*- coding: utf-8 -*-

public class Exemple {
    public static void main(String[] args) throws Exception {
        A a = new A(); // Création d'un objet a de la classe A
        a.start();     // Lancement du thread a
        Thread.sleep(1000);
        a.fin = true;  // Fin du thread a
        System.out.println("Le main termine.");  
    }
    
    static class A extends Thread {
        public boolean fin = false;
        public void run() {
            while(! fin) ; // Attente active
            System.out.println("L'objet a termine.");  
        }
    }  
}

/*
  $ javac Exemple.java
  $ java Exemple
  Le main termine.
  ^C
*/
