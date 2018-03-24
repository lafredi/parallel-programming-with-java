// -*- coding: utf-8 -*-

class Sleep {
    static Thread Cobaye, Observateur;

    public static void Affiche(){
        System.out.print("Etat du thread Cobaye: " + Cobaye.getState());
    }

    public static void main(String[] args) throws InterruptedException {
        Cobaye = new Thread(
                              new Runnable(){
                                  public void run() {
                                      System.out.println( "** Le Cobaye démarre rapidement..." ) ;
                                      System.out.println( "** pour s'endormir (avec sleep())." ) ;
                                      try {
                                          Thread.sleep(2000) ;
                                      } catch(InterruptedException e){e.printStackTrace();}
                                  }
                              }
                            );
        
        Observateur = new Thread(
                                   new Runnable(){
                                       public void run() {
                                           try {
                                               Thread.sleep(1000) ;
                                           } catch(InterruptedException e){e.printStackTrace();}
                                           System.out.println("** L'Observateur démarre plus lentement...");
                                           System.out.println("** et observe le Cobaye.");
                                           Affiche();
                                       }
                                   });
	
        System.out.println("** Début du test...");
        Cobaye.start();
        Observateur.start();
        Cobaye.join();
        Observateur.join();
        System.out.println("** Fin du test...");
    }
}

