// -*- coding: utf-8 -*-

public class monThread extends Thread {
    public static void main(String[] args) {
        monThread p1 = new monThread();
        monThread p2 = new monThread();		
        p1.start(); p2.start();            
    }
    public void run() {
        for (int i = 1;i<=1000;i++) {
            System.out.println(i + " " + currentThread().getName());
            try {
                sleep(100);
            } catch(InterruptedException e){ e.printStackTrace(); } 
      }
  }
}

/*
  $ java monThread
  1 Thread-0
  1 Thread-1
  2 Thread-1
  2 Thread-0
  3 Thread-0
  3 Thread-1
  4 Thread-1
  4 Thread-0
  5 Thread-1
  5 Thread-0
  6 Thread-1
  6 Thread-0
  7 Thread-0
  7 Thread-1
  8 Thread-1
  8 Thread-0
  9 Thread-1
  9 Thread-0
  10 Thread-0
  10 Thread-1
  11 Thread-0
  11 Thread-1
  12 Thread-1
  12 Thread-0
  13 Thread-0
  13 Thread-1
  14 Thread-1
  14 Thread-0
  15 Thread-1
  15 Thread-0
  16 Thread-0
  16 Thread-1
  17 Thread-1
*/
