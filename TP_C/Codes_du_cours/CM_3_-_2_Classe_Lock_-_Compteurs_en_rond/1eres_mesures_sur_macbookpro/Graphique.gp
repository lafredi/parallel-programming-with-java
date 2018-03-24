if (!exists("titre")) titre = "Performances des verrous pour le systeme des compteurs en rond"
if (!exists("xtitre")) xtitre = "Nombre de threads"
if (!exists("ytitre")) ytitre = "Temps de calcul (en ms.)"
if (!exists("mode_S")) mode_S = "donnees_S.dat"
if (!exists("mode_L")) mode_L = "donnees_L.dat"
if (!exists("mode_A")) mode_A = "donnees_A.dat"
if (!exists("mode_F")) mode_F = "donnees_F.dat"
if (!exists("mode_RWL")) mode_RWL = "donnees_RWL.dat"
if (!exists("mode_STAMP")) mode_STAMP = "donnees_STAMP.dat"

set title titre
set xlabel xtitre
set ylabel ytitre

set xrange [1:32]
set yrange [0:170000]

#set xrange [1:16]
#set yrange [0:80000]
# set logscale y


# set terminal  # Pour avoir la liste des "terminaux" disponibles

set term svg
set out "figure.svg"        # Je souhaite un graphique au format SVG
plot mode_S title "Verrou intrinseque" with lines,\
     mode_L title "Verrou Lock" with lines, \
     mode_F title "Verrou equitable" with lines
#     mode_A title "Variable atomique" with lines, \
#     mode_RWL title "Verrou RWL" with lines,\
#     mode_STAMP title "Verrou STAMP" with lines

set term postscript
set out "figure.eps"        # Je le souhaite aussi au format EPS
plot mode_S title "Verrou intrinseque" with lines,\
     mode_L title "Verrou Lock" with lines, \
     mode_F title "Verrou equitable" with lines
#     mode_A title "Variable atomique" with lines, \
#     mode_RWL title "Verrou RWL" with lines,\
#     mode_STAMP title "Verrou STAMP" with lines

set term pstricks              # Je le souhaite aussi au format LaTeX
set out "figure.tex"
plot mode_S title "Verrou intrinseque" with lines,\
     mode_L title "Verrou Lock" with lines, \
     mode_F title "Verrou equitable" with lines
#     mode_A title "Variable atomique" with lines, \
#     mode_RWL title "Verrou RWL" with lines,\
#     mode_STAMP title "Verrou STAMP" with lines

