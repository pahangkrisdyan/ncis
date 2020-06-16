package org.cis.optur.auto;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.cis.optur.engine.commons.Commons;
import org.cis.optur.engine.commons.InitialSolutionResult;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;
import org.cis.optur.optimation.HillClimbing;

import java.io.*;
import java.text.ParseException;
import java.util.Collections;

public class HC {
    static SP sp = new SP(1000000, 1000000, 0.99998, 0.0, 0, 0);

    //example C:\Users\5216100056\Desktop\OpTur (1)\Optur7.xls
    static final String XLS_FILE_PATH = "C:\\Users\\5216100056\\Desktop\\OpTur (1)\\Optur5.xls";
    //example C:\Users\5216100056\Desktop\OpTur (1)\Optur7\Optur7.sol
    static final String INIT_SOL_FILE_PATH = "C:\\Users\\5216100056\\Desktop\\OpTur (1)\\Optur5\\Optur5.sol";
    //example C:\Users\5216100056\Desktop\OpTur (1)\Optur7
    static final String RESULT_FOLDER = "C:\\Users\\5216100056\\Desktop\\OpTur (1)\\Optur5\\HC";

    public static void main(String[] args) throws ParseException, InvalidFormatException, IOException, ClassNotFoundException {
        Commons.initSC(new File(XLS_FILE_PATH));
        File solFile = new File(INIT_SOL_FILE_PATH);
        FileInputStream fi = new FileInputStream(solFile);
        ObjectInputStream oi = new ObjectInputStream(fi);
        InitialSolutionResult initialSol = (InitialSolutionResult) oi.readObject();
        try {
            System.out.println("Print Init Sol");
            File dir = new File(RESULT_FOLDER);
            if (!dir.exists()){
                dir.mkdir();
            }
            File myObj = new File(dir.getAbsolutePath() + "\\init.txt");

            if (!myObj.createNewFile()) {
                System.out.println("Error");
                System.exit(-2);
            }
            FileWriter myWriter = new FileWriter(myObj);
            int[][] sol = initialSol.getInitialSolution();
            for (int i = 0; i < sol.length; i++) {
                for (int i1 = 0; i1 < sol[0].length; i1++) {
                    System.out.print(sol[i][i1] + "\t");
                    myWriter.write(sol[i][i1] + "\t");
                }
                System.out.println();
                myWriter.write("\n");
            }
            myWriter.close();
            System.out.println("Print Init Sol Done");
        } catch (IOException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < 10; i++) {
            int[][] temp = new int[initialSol.getInitialSolution().length][initialSol.getInitialSolution()[0].length];
            Commons.copyArray(initialSol.getInitialSolution(), temp);
            HillClimbing hillClimbing = new HillClimbing(temp);
            System.out.println("Start Percobaan " + (i+1));
            OptimationResult optimationResult;
            optimationResult = hillClimbing.getOptimationResult(sp.iterasi, 5000);
            try {
                File dir = new File(RESULT_FOLDER);
                if (!dir.exists()){
                    dir.mkdir();
                }
                File myObj = new File(dir.getAbsolutePath()+"\\P"+(i+1)+".txt");
                if (!myObj.createNewFile()) {
                    System.out.println("Error");
                    System.exit(-2);
                }
                System.out.println("File created: " + myObj.getName());
                FileWriter myWriter = new FileWriter(myObj);
                Collections.reverse(optimationResult.getPenalties());
                optimationResult.getPenalties().forEach(aDouble -> {
                    try {
                        myWriter.write(aDouble + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                System.out.print("Best Penalty:" + optimationResult.getBestPenalties() + "\n");
                System.out.print("Time:" + optimationResult.getTime() + "\n");
                myWriter.write("Best Penalty:" + optimationResult.getBestPenalties() + "\n");
                myWriter.write("Time:" + optimationResult.getTime() + "\n");
                int[][] sol = optimationResult.getSolution();
                for (int j = 0; j < sol.length; j++) {
                    for (int j1 = 0; j1 < sol[0].length; j1++) {
                        System.out.print(sol[j][j1] + "\t");
                        myWriter.write(sol[j][j1] + "\t");
                    }
                    System.out.println();
                    myWriter.write("\n");
                }
                myWriter.close();
                myWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
