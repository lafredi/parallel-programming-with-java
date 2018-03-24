// -*- coding: utf-8 -*-

public class monThread extends Thread {
  public static void main(String[] args) {
      monThread p1 = new monThread();
      p1.setName("p1");
      monThread p2 = new monThread();		
      p2.setName("p2");
      p1.setPriority(MAX_PRIORITY);
      p2.setPriority(MIN_PRIORITY);	
      p1.start();
      p2.start();
  }
  public void run() {
    for (int i = 1; i <= 1000; i++)
        System.out.println(i + " " +  currentThread().getName());
  }
}

/* Sur une machine monocoeur
  morin.r@ens1:~/PP/Priorites$ cat /proc/cpuinfo | grep cpu
  cpu family	: 15
  cpu MHz		: 2499.998
  cpu cores	    : 1
  cpuid level	: 13
  morin.r@ens1:~/PP/PRIORITES$ make
  javac *.java
  morin.r@ens1:~/PP/PRIORITES$ java monThread 
  ...
  995 p2
  996 p2
  997 p2
  998 p2
  999 p2
  1000 p2
  998 p1
  999 p1
  1000 p1
*/

