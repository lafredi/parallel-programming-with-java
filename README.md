# Parallel Programming

If writing correct programs is a difficult exercise, writing a correct parallel program is even more difficult.
So why are we interested in parallelism ? 
- Threads are an essential feature of the Java language and allow to *simplify* the development of applications by transforming the complicated code into a more correct and structured code.
- Moreover, threads are the most direct way *to exploit the power* of multiprocessor systems. As the number of cores increases in machines, the exploitation of concurrency becomes essential.

Lesson Plan (French)
the following git, contains all the courses studied during the year 2017 in first year Master degree, with the associated practical work "TP". if you appreciate this work, like and follow my github <3 


## 1.	Threads en java (les instructions de base)
### a.	Construction d’un thread
### b.	Propriété d'un thread 
### c.	Etat d’un thread en java
### d.	Synchronisation des verrous 
### e.	Signaler et attendre en Java 
### f.	Le mot-clef « volatile »

## 2.	Atomicité, verrous, variable de condition et moniteurs (les conceptions fondamentaux)
### a.	Indépendance et atomicité 
#### i.	Indépendance et parallélisation
#### ii.	La notion cruciale d'atomicité
#### iii.	Instruction atomique
### b.	Techniques fondamentales 
#### i.	B-A BA pour éviter les interblocage 
#### ii.	Sémaphore 
#### iii.	Moniteurs 
#### iv.	Protection contre les signaux intempestifs
### c.	Les variables atomiques 
#### i.	Les objets atomiques depuis Java 5 
#### ii.	Exemple des compteurs anarchiques 
#### iii.	Construction artisanale d'un verrou

## 3.	Classes concurrentes et autres outils en java (pourquoi réinventer la roue ?)
### a.	Verrous, barrière et loquets
### b.	Outil pratique pour la multitâche

## 4.	Construction de verrous et programmation sans verrou (question de performance !)
### a.	La programmation optimiste 
### b.	Exemple d'une pile concurrente

## 5.	Le model mémoire java (qu'est-ce qu'un programme bien synchroniser ?)
### a.	Aperçu du modèle mémoire Java 
### b.	Consistance séquentielle data-race 
### c.	Que veut dire le mot final
### d.	Les origines des inconsistances 
