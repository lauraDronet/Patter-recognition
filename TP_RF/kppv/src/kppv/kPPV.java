/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kppv;

/**
 *
 * @author hux
 */
import java.lang.Math; // headers MUST be above the first class

import java.io.*;

/**
 * @author hubert.cardot
 */
public class kPPV {

    static int NbEx = 50, NbClasse = 3, NbCaract = 4, NbExApprent = 25;
    static Double data[][][] = new Double[NbClasse][NbEx][NbCaract];

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Debut programme kPPV");
        lectureFichier(args);

        //Question 4 :
        //initialisation d'une matrice de confusion
        Double matriceConfu[][] = new Double[NbClasse][NbClasse];
        for (int index = 0; index < NbClasse; index++) {
            for (int index2 = 0; index2 < NbClasse; index2++) {
                matriceConfu[index][index2] = 0.;
            }
        }
        //initialisation du taux de reconnaissance
        Double taux = 0.;

        //calcul du taux de reconnaissance
        taux = evaluation(matriceConfu);

        //affichage du taux de reconnaissance
        System.out.println("Le taux est :" + taux);

        //affichage de la matrice de confusion
        for (int indexClasse1 = 0; indexClasse1 < NbClasse; indexClasse1++) {
            for (int indexClasse2 = 0; indexClasse2 < NbClasse; indexClasse2++) {
                System.out.print("\t" + matriceConfu[indexClasse1][indexClasse2]);
            }
            System.out.println("");
        }
    }

    /**
     * Calcule la distance entre l'ex X et tous les ex d'apprentissage. Il
     * s'agit d'une distance euclidienne entre chaque caracteristiques de
     * l'exemple avec les autres exemples de la base d'apprentissage.
     *
     * @param X : un exemple
     * @return
     */
    private static Double[] calculDistances(Double X[]) {

        //initialisation du tableau de distance
        Double Dist[] = new Double[NbClasse * NbExApprent];
        for (int index = 0; index < Dist.length; index++) {
            Dist[index] = 0.;
        }

        //variable qui stocke la somme du carre des soustractions des caracteristiques
        Double somme[] = new Double[NbExApprent * NbClasse];
        //variable temporaire pour stocker un calcul intermediaire
        Double temp = 0.;
        //parcour des classes
        for (int indexClasse = 0; indexClasse < NbClasse; indexClasse++) {
            //parcour des exempes d'apprentissage
            for (int indexEx = 0; indexEx < NbExApprent; indexEx++) {
                //(re)initialisation des variables
                temp = 0.;
                somme[indexEx * indexClasse] = 0.;
                //parcours de chaque caracteristique
                for (int indexCaract = 0; indexCaract < NbCaract; indexCaract++) {
                    /* calcul de la soustraction des valeurs de caract pour 
                     * l'exemple et pour l'exemple en base d'apprent
                     */
                    temp = (X[indexCaract] - data[indexClasse][indexEx][indexCaract]);
                    // calcule de la somme
                    somme[indexEx * indexClasse] = somme[indexEx * indexClasse] + (temp * temp);
                }
                //calcul de la racine carre de la somme
                //(dernier calcul de la distance
                Dist[indexEx * indexClasse] = Math.sqrt(somme[indexEx * indexClasse]);
            }
        }

        return Dist;
    }

    /**
     * Permet de lire un fichier et de remplir le tableau data[][][] avec les
     * données d'exemple pour chaque classes.
     *
     * @param args
     */
    private static void lectureFichier(String[] args) {
        //---lecture des donnees a partir du fichier iris.data
        String print[][][] = new String[NbClasse][NbEx][NbCaract];
        String ligne, sousChaine;
        int classe = 0, n = 0;
        try {
            BufferedReader fic = new BufferedReader(new FileReader(args[0]));
            while ((ligne = fic.readLine()) != null) {
                for (int i = 0; i < NbCaract; i++) {
                    if(print[classe][n][i] == null){
                        print[classe][n][i] = "";
                    }
                    sousChaine = ligne.substring(i * NbCaract, i * NbCaract + 3);
                    data[classe][n][i] = Double.parseDouble(sousChaine);
                    print[classe][n][i] = print[classe][n][i] + data[classe][n][i] + " " + classe + " " + n;
                }
                if (++n == NbEx) {
                    n = 0;
                    classe++;
                }
            }
            for (int i = 0; i < NbEx; i++) {
                for (int j = 0; j < NbClasse; j++) {
                    for(int k = 0; k < NbCaract ; k++)
                        System.out.print(print[j][i][k] + "\t");
                }
                System.out.println("");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Cette classe permet de retourner la classe d'un exemple a partir de ses
     * dist[]. Pour le 1-PPV, cette classe est celle de l'exemple dont la
     * distance est minimum.
     *
     * @param Dist
     * @return
     */
    public static int getClasse(Double Dist[]) {
        int indiceMin = 0;
        for (int index = 0; index < Dist.length; index++) {
            if (Dist[index] < Dist[indiceMin]) {
                indiceMin = index;
            }
        }
        return indiceMin / NbExApprent;
    }

    /** 
     *
     * @param matriceConfu
     * @return
     */
    public static Double evaluation(Double matriceConfu[][]) {
        int indexClasseFound = 0;
        for (int indexClasse = 0; indexClasse < NbClasse; indexClasse++) {
            for (int indexEx = NbExApprent; indexEx < NbEx; indexEx++) {
                indexClasseFound = getClasse(calculDistances(data[indexClasse][indexEx]));
                matriceConfu[indexClasse][indexClasseFound]++;
            }
        }

        Double tauxReco = 0.;
        for (int indexClasse = 0; indexClasse < NbClasse; indexClasse++) {
            tauxReco = tauxReco + matriceConfu[indexClasse][indexClasse];
        }

        return tauxReco / (NbEx * NbClasse);
    }

} //fin classe kPPV
