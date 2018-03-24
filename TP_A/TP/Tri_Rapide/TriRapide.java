// -*- coding: utf-8 -*-

import java.util.Random;

public class TriRapide {
    public TriRapide() {}
    
    private static void echangerElements(int[] t, int m, int n) {
        int temp = t[m];
        t[m] = t[n];
        t[n] = temp;
    }
    
    private static int partition(int[] t, int m, int n) {
        int v = t[m]; // valeur pivot
        int i = m-1;
        int j = n+1; // indice final du pivot
        while(true) {
            do { j--; } while(t[j] > v);
            do { i++; } while(t[i] < v);
            if(i < j) { echangerElements(t, i, j);}
            else {return j;}
        }
    }

    private static void triRapide(int[] t, int m, int n) {
        if(m < n) {
            int p = partition(t, m, n);
            triRapide(t, m, p);
            triRapide(t, p+1, n);
        }
    }
    
    public static void main(String[] args) {
        int taille = 0; // taille du tableau (= nombre d'entiers à trier)
        if (args.length < 1) {
            System.err.println
                ("Erreur : vous devez indiquer le nombre d'entiers à trier.");
            System.exit(1);
        }
        try { taille = Integer.parseInt(args[0]); }
        catch(NumberFormatException nfe) {
            System.err.println
                ("Erreur : vous devez indiquer le nombre d'entiers à trier.");
            System.err.println(nfe.getMessage());
            System.exit(1);
        }
        int [] entiers = new int[taille]; // le tableau d'entiers à trier 
        /* génération aléatoire de "taille" entiers */
        int range = 10 * taille;
        Random alea = new Random(System.currentTimeMillis());
        for(int i=0; i<taille; i++) {
            // valeurs entre -range et +range  (exclues)
            entiers[i] = alea.nextInt(2*range) - range;            
        }
        /* affichage du tableau initial */
        System.out.print("tableau initial = ");
        for (int i=0; i<entiers.length; i++)
            System.out.print(" " +entiers[i]);
        System.out.print("\n");
        /* tri rapide du tableau */
        // lancement du tri du tableau "entiers"
        triRapide(entiers, 0, entiers.length-1); 
        /* affichage du résultat */
        System.out.print("tableau trié = ");
        for (int i=0; i<entiers.length; i++) {
            System.out.print(" " +entiers[i]);
        }
        System.out.print("\n");
    }
}
