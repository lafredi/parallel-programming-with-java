// -*- coding: utf-8 -*-

class Start {
    static Object objet = new Object();
    static Thread Cobaye, Observateur;
    static volatile int fin = 0; 

    public static void Affiche(){
        System.out.print("Etat du thread Cobaye: " + Cobaye.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        Cobaye = new Thread(
                              new Runnable(){
                                  public void run() {
                                      System.out.println( "** Le Cobaye démarre..." ) ;
                                      while ( fin == 0 ); // Attente active!
                                  }
                              }
                            );

        Observateur = new Thread(
                                   new Runnable(){
                                       public void run(){
                                           System.out.println("** L'Observateur démarre...");
                                           try{
                                               Thread.sleep(1000) ;
                                           } catch(InterruptedException e){e.printStackTrace();}
                                           Affiche();
                                           fin = 1;
                                           try{
                                               Thread.sleep(1000) ;
                                           } catch(InterruptedException e){e.printStackTrace();}
                                           Affiche();
                                       }
                                   }
                                 );
	
        System.out.println("** Début du test...");
        Observateur.start();
        Affiche();
        Cobaye.start();
        Affiche();
        Cobaye.join();
        Observateur.join();
        System.out.println("** Fin du test...");
    }
}

