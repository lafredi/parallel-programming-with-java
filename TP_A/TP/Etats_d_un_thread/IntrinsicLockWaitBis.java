// -*- coding: utf-8 -*-

class IntrinsicLockWaitBis {
    static Object objet = new Object();
    static Thread Cobaye, Observateur;

    public static void Affiche(){
        System.out.print("Etat du thread Cobaye: " + Cobaye.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        Cobaye = new Thread(
                              new Runnable(){
                                  public void run() {
                                      System.out.println( "** Le Cobaye démarre..." ) ;
                                      synchronized(objet){
                                          try {
                                              objet.wait(2000) ;
                                          } catch(InterruptedException e){e.printStackTrace();}   
                                      }
                                  }
                              }
                            );

        Observateur = new Thread(
                                   new Runnable(){
                                       public void run(){
                                           System.out.println("** L'Observateur démarre...");
                                           try {
                                               Thread.sleep(1000) ;
                                           } catch(InterruptedException e){e.printStackTrace();}
                                           Affiche();
                                           synchronized(objet){
                                               objet.notify() ;
                                               Affiche();
                                           }
                                       }
                                   }
                                 );
	
        System.out.println("** Début du test...");
        Affiche();
        Cobaye.start();
        Observateur.start();
        Cobaye.join();
        Affiche();
        Observateur.join();
        System.out.println("** Fin du test...");
    }
}

