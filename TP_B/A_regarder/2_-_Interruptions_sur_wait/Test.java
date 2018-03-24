// -*- coding: utf-8 -*-

import java.lang.*;

class Test {
    static Object objet = new Object();
    static Thread Cobaye, Observateur;

    public static void Affiche(){
        System.out.print("Etat du thread Cobaye: " + Cobaye.getState());
        if ( Cobaye.isInterrupted() )
            { System.out.println(" (interrompu)."); }
        else
            { System.out.println(" (non interrompu)."); }
    }

    public static void main(String[] args) throws InterruptedException {
        Cobaye = new Thread (
                               new Runnable(){
                                   public void run() {
                                       System.out.print( "** Le Cobaye démarre en premier" ) ;
                                       synchronized(objet){
                                           System.out.println(" et s'endort (avec wait())." ) ;
                                           try {
                                               objet.wait() ;
                                           } catch(InterruptedException e) {
                                               System.out.print("** Interrompu dans son sommeil, le Cobaye");
                                               if (Thread.holdsLock(objet))
                                                   System.out.println(" possède au réveil à nouveau le verrou!");
                                               else
                                                   System.out.println(" ne possède plus le verrou au réveil!");
                                           }
                                       }
                                   }
                               });

        Observateur = new Thread(
                                   new Runnable(){
                                       public void run(){
                                           try {
                                               Thread.sleep(1000) ;
                                           } catch(InterruptedException e){e.printStackTrace();}
                                           System.out.print("** L'Observateur démarre en second");
                                           synchronized(objet){
                                               System.out.println(" puis prend le verrou et patiente (sleep())");
                                               Affiche();
                                               try {
                                                   Thread.sleep(10_000) ;
                                               } catch(InterruptedException e){
                                                   System.out.print("** Interrompu, l'Observateur");
                                                   if (Thread.holdsLock(objet))
                                                       System.out.println(" possède encore le verrou!");
                                                   else
                                                       System.out.println(" ne possède plus le verrou!");
                                                   Affiche();
                                               }
                                               try {
                                                   Thread.sleep(3000) ;
                                               } catch(InterruptedException e){e.printStackTrace();}
                                           }
                                       }
                                   }
                                 );
	
        System.out.println("** Début du test...");
        Cobaye.start();
        Observateur.start();
        try {
            Thread.sleep(3000) ;
        } catch(InterruptedException e){e.printStackTrace();}
        Cobaye.interrupt();
        Observateur.interrupt();
        Cobaye.join();
        Observateur.join();
        System.out.println("** Fin du test...");
    }
}

/*

  Un thread ne perd pas un verrou intrinsèque s'il est interrompu.

  Un thread doit reprendre le verrou intrinsèque s'il est interrompu pendant
  un wait(), avant d'exécuter le catch.

  $ java Test
  ** Début du test...
  ** Le Cobaye démarre en premier et s'endort (avec wait()).
  ** L'Observateur démarre en second puis prend le verrou et patiente (sleep())
  Etat du thread Cobaye: WAITING (non interrompu).
  ** Interrompu, l'Observateur possède encore le verrou!
  Etat du thread Cobaye: BLOCKED (interrompu).
  ** Interrompu dans son sommeil, le Cobaye possède au réveil à nouveau le verrou!
  ** Fin du test...
  $
*/
