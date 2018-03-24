if (!exists("titre")) titre = "Estimation de PI/4 avec 1 millliard de tirages"
if (!exists("xtitre")) xtitre = "Nombre de threads"
if (!exists("ytitre")) ytitre = "Temps de calcul (en ms.)"
if (!exists("mode_LockFree")) mode_LockFree = "mesures_0_-_LockFree.dat"
if (!exists("mode_ThreadPool")) mode_ThreadPool = "mesures_1_-_ThreadPool.dat"
if (!exists("mode_Futures")) mode_Futures = "mesures_2_-_Futures.dat"
if (!exists("mode_Service")) mode_Service = "mesures_3_-_ExecutorCompletionService.dat"

set title titre
set xlabel xtitre
set ylabel ytitre

set xrange [1:16]
# set logscale y
# set yrange [1000:1000000]

# set arrow 20 from 1,40000 to 16,40000 nohead # Pour indiquer le temps séquentiel

# set terminal  # Pour avoir la liste des "terminaux" disponibles
set term svg
set out "graphique.svg"        # Je souhaite un graphique au format SVG
plot mode_LockFree title "LockFree" with lines,\
     mode_ThreadPool title "ThreadPool" with lines,\
     mode_Futures title "Futures" with lines,\
     mode_Service title "Service de completion" with lines     
set term postscript
set out "graphique.eps"        # Je le souhaite aussi au format EPS
plot mode_LockFree title "LockFree" with lines,\
     mode_ThreadPool title "ThreadPool" with lines,\
     mode_Futures title "Futures" with lines,\
     mode_Service title "Service de completion" with lines     
set term pstricks              # Je le souhaite aussi au format LaTeX
set out "graphique.tex"
plot mode_LockFree title "LockFree" with lines,\
     mode_ThreadPool title "ThreadPool" with lines,\
     mode_Futures title "Futures" with lines,\
     mode_Service title "Service de completion" with lines     

#     mode_Futures title "Futures" with lines,\   
