// -*- coding: utf-8 -*-

class TestJoin {
    static Thread Cobaye, Observateur;

    public static void Affiche(){
        System.out.print("Etat du thread Cobaye: " + Cobaye.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        Cobaye = new Thread(
                              new Runnable(){
                                  public void run() {
                                      try{ Thread.sleep(500) ;} catch(InterruptedException e){}
                                      System.out.println( "** Le Cobaye démarre rapidement..." ) ;
                                      System.out.println( "** Le Cobaye attend la fin de l'observateur..." ) ;
                                      try{ Observateur.join();} catch(InterruptedException e){}		    
                                  }
                              }
                            );
        
        Observateur = new Thread(
                                   new Runnable(){
                                       public void run(){
                                           try{ Thread.sleep(1500) ;} catch(InterruptedException e){}
                                           System.out.println("** L'Observateur démarre encore plus lentement...");
                                           Affiche();
                                       }
                                   }
                                 );
	
        System.out.println("** Début du test...");
        Cobaye.start();
        Observateur.start();
        Cobaye.join();
        Affiche();
        Observateur.join();
        System.out.println("** Fin du test...");
    }
}

