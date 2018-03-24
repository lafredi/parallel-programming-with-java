// -*- coding: utf-8 -*-

public class Exemple {
    public static void main(String[] args) {
        Thread t = new Thread( new monRunnable() );
        t.start();
    }
}
