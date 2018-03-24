// -*- coding: utf-8 -*-

public class monThread extends Thread {
    public static void main(String[] args) {
        new monThread().start();
        new monThread().start();
  }
    public void run(){
        for (int i = 1;i<=1000;i++)
            System.out.println(i);
    }
} 
