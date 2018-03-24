// -*- coding: utf-8 -*-

class SyncTest extends Thread { 
    String msg;
    private Object monVerrou = new Object() ;
    public SyncTest(String s) { 
        msg = s;
    } 
    public void run() { 
        synchronized (monVerrou) { 
            System.out.print("[" + msg); 
            try { Thread.sleep(1000); } 
            catch (InterruptedException e) {}; 
            System.out.println("]");
        }
    }
    public static void main(String [] args) { 
        new SyncTest("Hello").start();
        new SyncTest("Synchronized").start(); 
        new SyncTest("World").start();
    } 
} 

/*
  $ javac SyncTest.java 
  $ java SyncTest
  [Hello[World[Synchronized]
  ]
  ]
*/
