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
public class Main {
    
    public static void main(String[] args){
        
        try {
            svm_train.main(args);
        } catch (IOException ex) {
            Logger.getLogger("Attention erreur dans svm_train"+Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            svm_predict.main(new String[] {"/home/hux/Téléchargements/Iris/iris.test", "iris.app.model", "/home/hux/Téléchargements/Iris/iris.output.file"});
        } catch (IOException ex) {
            Logger.getLogger("Attention erreur dans svm_predict"+Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
