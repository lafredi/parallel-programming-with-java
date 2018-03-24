// -*- coding: utf-8 -*-

class Tache1 implements Runnable{
    public void run(){
        for(int i = 1; i <= 26; i++) System.out.print(i + " ");
    }
}
class Tache2 implements Runnable{
    public void run(){
        for(char c = 'a'; c <= 'z'; c++) System.out.print(c + " ");
    }
}
class MonProgramme {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Tache1());
        Thread t2 = new Thread(new Tache2());
        t1.start();
        t2.start();
        try{
            t1.join();
            t2.join(); }
        catch(InterruptedException e){
            e.printStackTrace();
        }
        System.out.println();
    }
}

/*
  $ javac MonProgramme.java 
  $ java MonProgramme
  1 2 3 4 5 a b c d e f g h i j k l m n o 6 7 p q r s t u v w x y z 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25 26 
*/

