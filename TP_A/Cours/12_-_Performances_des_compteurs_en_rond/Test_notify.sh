#!/bin/bash
MACHINE="macbookpro" # Pas d'espaces ici, merci
VALEURCIBLE=100000
DONNEES="notify_"$MACHINE
DATE=`date "+DATE: %Y-%m-%d%tTIME: %H:%M:%S"`

echo "notify() vs notifyAll() sur "$MACHINE" "$DATE

# Je crée le répertoire dans lequel seront placés les résultats
if [ ! -e $DONNEES ]
then
    echo "Creation du repertoire "$DONNEES
    mkdir $DONNEES
fi
# Je sauvegarde les informations de la machine
if [ -e /proc/cpuinfo ]
then 
    cat /proc/cpuinfo > $DONNEES/info.txt
fi
if [ -e /usr/sbin/sysctl ]
then 
    sysctl hw.availcpu > $DONNEES/info.txt
fi

# Je compile si c'est pas fait
if [ ! -e Compteur.class ]
then 
    javac Compteur.java
fi

for MODE in AP ATP
do
    TEST="donnees_"$MODE".dat"
    echo "# Tests d'attentes sur "$MACHINE" "$DATE > $DONNEES/$TEST
    echo -n "MODE : "$MODE" - " 
    for NBCOMPTEUR in 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
    do
	echo -n " "$NBCOMPTEUR
	java Compteur $NBCOMPTEUR $VALEURCIBLE $MODE | grep "#" >> $DONNEES/$TEST
    done
    echo " (fini)"
done
cp Test.gp $DONNEES
echo "Lancez \"gnuplot Test_macbookpro.gp\" pour fabriquer les graphiques."
