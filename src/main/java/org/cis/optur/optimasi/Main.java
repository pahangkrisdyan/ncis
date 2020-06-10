package org.cis.optur.optimasi;

import org.cis.optur.engine.commons.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Main {
//    public static void main(String[] args) throws Exception {
//        //Initial
//        String[] options = {"Buat Initial Solution", "Optimasi"};
//
//        int option = JOptionPane.showOptionDialog(null, "Mau ngapain mbak?",
//                "NCIS'S TA",
//                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
//
//        if(option==0){
//            AtomicBoolean repeat = new AtomicBoolean(false);
//            doIt: do {
//                repeat.set(false);
//                JFileChooser j = new JFileChooser("e:");
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel", "xls", "excel");
//                j.setFileFilter(filter);
//                int result = j.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    Commons.exceptionListener = e -> {
//                        JOptionPane.showMessageDialog(null, "Program Error");
//                    };
//                    Commons.feasibilityListener = hc -> {
//                        JOptionPane.showMessageDialog(null, "Not Feasible " + hc);
//                        repeat.set(true);
//                    };
//                    Commons.iterationListener = count -> {
//                        System.out.println("Iteration => " + count);
//                    };
//                    if(repeat.get()) continue ;
//                    File opturFile = j.getSelectedFile();
//                    InitialSolutionResult initialSol = Commons.getInitialSolutionResult(opturFile);
//                    String[] options2 = {"Save Yeay", "Jelek Run Lagi"};
//                    Sn sn = new Sn(initialSol.getInitialSolution());
//                    int option2 = JOptionPane.showOptionDialog(null, "Penalty = "+sn.countPenalty()+" | Gimana mbak?",
//                            "NCIS'S TA",
//                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);
//                    if(option2==0){
//                        JFileChooser fileChooserSaveSol = new JFileChooser();
//                        fileChooserSaveSol.setDialogTitle("Specify a file to save");
//                        int userSelection = fileChooserSaveSol.showSaveDialog(null);
//
//                        File fileToSave = null;
//
//                        if (userSelection == JFileChooser.APPROVE_OPTION) {
//                            fileToSave = fileChooserSaveSol.getSelectedFile();
//                        }
//                        FileOutputStream f = new FileOutputStream(fileToSave);
//                        ObjectOutputStream o = new ObjectOutputStream(f);
//                        o.writeObject(initialSol);
//                        o.close();
//                        f.close();
//                        String[] options4 = {"Run Lagi", "Udah"};
//                        int option4 = JOptionPane.showOptionDialog(null, "Udah ke save yaa, jangan lupa dimana nyimpennya tadi.",
//                                "NCIS'S TA",
//                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
//                        if (option4==0) repeat.set(true);
//                        else System.exit(0);
//                    }else {
//                        repeat.set(true);
//                    }
//                }
//            }while (repeat.get());
//        }else {
//            AtomicBoolean repeat = new AtomicBoolean(false);
//            do {
//                JOptionPane.showMessageDialog(null, "Pilih optur dulu ya, buat inisisasi SC :)");
//                JFileChooser j2 = new JFileChooser("e:");
//                FileNameExtensionFilter filter2 = new FileNameExtensionFilter("Excel", "xls", "excel");
//                j2.setFileFilter(filter2);
//                int result2 = j2.showOpenDialog(null);
//                if (result2 == JFileChooser.APPROVE_OPTION) {
//                    Commons.initSC(j2.getSelectedFile());
//                }
//                JOptionPane.showMessageDialog(null, "Oke sekarang pilih initial solusi dari optur tadi, yang .sol");
//                JFileChooser j = new JFileChooser("e:");
//                FileNameExtensionFilter filter = new FileNameExtensionFilter("Sol", "sol", "sol");
//                j.setFileFilter(filter);
//                int result = j.showOpenDialog(null);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File solFile = j.getSelectedFile();
//                    FileInputStream fi = new FileInputStream(solFile);
//                    ObjectInputStream oi = new ObjectInputStream(fi);
//                    // Read objects
//                    InitialSolutionResult initialSol = (InitialSolutionResult) oi.readObject();
//
//                    Sn sn = new Sn(initialSol.getInitialSolution());
//
//                    JTextField initialTemp = new JTextField("1000000");
//                    JTextField coolingRate = new JTextField("0.99998");
//                    JTextField iteration = new JTextField("1000000");
//                    JTextField tabuListLength = new JTextField("1");
//                    JTextField penaltyRecordRange = new JTextField("5000");
//                    JTextField reHeatRange = new JTextField("500000");
//                    JTextField reHeatRate = new JTextField("0.1");
//                    JPanel panel = new JPanel(new GridLayout(0, 1));
//                    panel.add(new JLabel("Initial Temp:"));
//                    panel.add(initialTemp);
//                    panel.add(new JLabel("Cooling Rate:"));
//                    panel.add(coolingRate);
//                    panel.add(new JLabel("Itreation:"));
//                    panel.add(iteration);
//                    panel.add(new JLabel("Tabu List length:"));
//                    panel.add(tabuListLength);
//                    panel.add(new JLabel("Penalty Record Range:"));
//                    panel.add(penaltyRecordRange);
//                    panel.add(new JLabel("Re-Heat Range:"));
//                    panel.add(reHeatRange);
//                    panel.add(new JLabel("Re-Heat Rate:"));
//                    panel.add(reHeatRate);
//                    int resul = JOptionPane.showConfirmDialog(null, panel, "Oke ya?",
//                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
//                    if (resul == JOptionPane.OK_OPTION) {
//                        int vInitialtemp = Integer.parseInt(initialTemp.getText());
//                        double vCoolingRate = Double.parseDouble(coolingRate.getText());
//                        int vIteration = Integer.parseInt(iteration.getText());
//                        int vTabuListLength = Integer.parseInt(tabuListLength.getText());
//                        int vPenaltyRecordRange = Integer.parseInt(penaltyRecordRange.getText());
//                        int vReHeatRange = Integer.parseInt(reHeatRange.getText());
//                        double vReHeatRate = Double.parseDouble(reHeatRate.getText());
//                        OptimationResult optimationResult = sn.TSAR2(vInitialtemp, vCoolingRate, vIteration,vTabuListLength,vPenaltyRecordRange, vReHeatRange, vReHeatRate);
//                        System.out.println("Best penalty: " + optimationResult.getPenalties());
//                        System.out.println("Time : " + optimationResult.getTime());
//                        JFileChooser fileChooserSaveSol = new JFileChooser();
//                        fileChooserSaveSol.setDialogTitle("Specify a file to save optimation result");
//                        int userSelection = fileChooserSaveSol.showSaveDialog(null);
//
//                        File fileToSave = null;
//
//                        if (userSelection == JFileChooser.APPROVE_OPTION) {
//                            fileToSave = fileChooserSaveSol.getSelectedFile();
//                        }
//                        FileOutputStream f = new FileOutputStream(fileToSave);
//                        ObjectOutputStream o = new ObjectOutputStream(f);
//
////                        o.writeObject(optimationResult);
//                        o.writeObject(optimationResult);
//                        o.close();
//                        f.close();
//
//                        String[] options4 = {"Run Lagi", "Udah"};
//                        int option4 = JOptionPane.showOptionDialog(null, "Best Penalty = " + optimationResult.getBestPenalties(),
//                                "NCIS'S TA",
//                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options4, options4[0]);
//                        if (option4==0) repeat.set(true);
//                        else System.exit(0);
//                    } else {
//                        System.out.println("Cancelled");
//                    }
//                }
//            }while (repeat.get());
//        }
//    }

}
