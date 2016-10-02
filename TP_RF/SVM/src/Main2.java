/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static libsvm.svm.svm_train;
import libsvm.svm_problem;

/**
 *
 * @author hux
 */
public class Main2 {

    public static void main(String[] args) {
        String t = "2";
        String c = "";
        String g = "";
        Double accuracy = 100.0;
        Double accuracy_temp = 0.0;
        String c_temp = "";
        String g_temp = "";
        while (accuracy != accuracy_temp) {
            for (Integer indexC = 0; indexC < 1000; indexC += 100) {
                for (Integer indexG = 0; indexG < 1000; indexG += 100) {

                    c = indexC.toString();
                    g = indexG.toString();
                    try {
                        svm_train.main(args);
                    } catch (IOException ex) {
                        Logger.getLogger("Attention erreur dans svm_train" + Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    try {
                        accuracy = svm_predict.main(new String[]{"/home/hux/Téléchargements/Iris/iris.test", "iris.app.model", "/home/hux/Téléchargements/Iris/iris.output.file"});
                    } catch (IOException ex) {
                        Logger.getLogger("Attention erreur dans svm_predict" + Main.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    if (accuracy_temp < accuracy) {
                        accuracy_temp = accuracy;
                        c_temp = c;
                        g_temp = g;
                    }

                }
            }
        }

        System.out.println("c : " + c_temp + " g : " + g_temp);
    }
}
