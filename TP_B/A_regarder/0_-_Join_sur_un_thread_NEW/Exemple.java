// -*- coding: utf-8 -*-

public class Exemple {
    public static void main(String[] args) throws Exception {
        A a = new A(); // Création d'un objet a de la classe A
        System.out.println("Le main attend que le thread A termine.");  
        a.join();
        System.out.println("Le thread A est dans l'état " + a.getState());  
        System.out.println("Le main termine.");  
    }
    
    static class A extends Thread {
        public void run() {
            System.out.println("Le thread A est lancé et termine.");
        }  
    }


}
/*
  Un join() sur un thread non démarré peut ne pas bloquer!
 
  $ make
  javac *.java
  $ java Exemple
  Le main attend que le thread A termine.
  Le thread A est dans l'état NEW
  Le main termine.

 */
