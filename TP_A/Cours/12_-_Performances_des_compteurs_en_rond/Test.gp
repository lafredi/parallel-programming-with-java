if (!exists("titre")) titre = "Comparaison des performances des modes d\'attente"
if (!exists("xtitre")) xtitre = "Nombre de threads"
if (!exists("ytitre")) ytitre = "Temps de calcul (en ms.)"
if (!exists("mode_AAN")) mode_AAN = "donnees_AAN.dat"
if (!exists("mode_AAY")) mode_AAY = "donnees_AAY.dat"
if (!exists("mode_AP")) mode_AP = "donnees_AP.dat"
if (!exists("mode_ATP")) mode_ATP = "donnees_ATP.dat"

set title titre
set xlabel xtitre
set ylabel ytitre

set xrange [2:16]
set logscale y
set yrange [0.1:150000]

# set terminal  # Pour avoir la liste des "terminaux" disponibles
set term postscript
set out "graphique.eps"        # Je le souhaite aussi au format EPS
plot mode_AAN title "Attente active naive, sans yield()" with lines,\
     mode_AAY title "Attente active avec yield()" with lines, \
     mode_AP title "Attente passive sur un objet commun" with lines,\
     mode_ATP title "Attente passive sur un objet individuel" with lines
set term pstricks              # Je le souhaite aussi au format LaTeX
set out "graphique.tex"
plot mode_AAN title "Attente active naive (sans yield)" with lines,\
     mode_AAY title "Attente active avec yield()" with lines, \
     mode_AP title "Attente passive sur un objet commun" with lines,\
     mode_ATP title "Attente passive sur un objet individuel" with lines
set term svg
set out "graphique.svg"        # Je souhaite un graphique au format SVG
plot mode_AAN title "Attente active naive (sans yield)" with lines,\
     mode_AAY title "Attente active avec yield()" with lines, \
     mode_AP title "Attente passive sur un objet commun" with lines,\
     mode_ATP title "Attente passive sur un objet individuel" with lines

#-------------------------
# notify() vs notifyAll()
#-------------------------
if (!exists("titre")) titre = "notify() vs notifyAll()"
unset logscale y
set yrange [0:120]
set term postscript            # Je le souhaite au format EPS
set out "notifyVSnotifyAll.eps"        
plot mode_AP title "Attente passive sur un objet commun avec notifyAll()" with lines,\
     mode_ATP title "Attente passive sur un objet individuel avec notify()" with lines
set term pstricks              # Je le souhaite aussi au format LaTeX
set out "notifyVSnotifyAll.tex"        
plot mode_AP title "Attente passive sur un objet commun avec notifyAll()" with lines,\
     mode_ATP title "Attente passive sur un objet individuel avec notify()" with lines
set term svg
set out "notifyVSnotifyAll.svg"        
plot mode_AP title "Attente passive sur un objet commun avec notifyAll()" with lines,\
     mode_ATP title "Attente passive sur un objet individuel avec notify()" with lines

