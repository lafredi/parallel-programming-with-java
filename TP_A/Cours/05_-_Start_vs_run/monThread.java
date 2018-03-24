// -*- coding: utf-8 -*-

public class monThread extends Thread {
  public static void main(String[] args) {
      monThread p1 = new monThread();
      p1.setName("p1");
      monThread p2 = new monThread();		
      p2.setName("p2");
      p1.start();
      p2.run(); // Le thread main exécute la méthode run() du thread p2.
  }
  public void run() {
    for (int i = 1; i <= 10; i++)
        System.out.println(i + " " + Thread.currentThread().getName());
  }
}

/* C'est le thread main qui exécute la méthode run() du thread p2!
  $ make
  javac *.java
  $ java monThread
  1 main
  2 main
  3 main
  4 main
  5 main
  6 main
  1 p1
  7 main
  2 p1
  3 p1
  4 p1
  5 p1
  6 p1
  7 p1
  8 p1
  9 p1
  10 p1
  8 main
  9 main
  10 main
*/

