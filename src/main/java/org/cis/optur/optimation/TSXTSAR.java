package org.cis.optur.optimation;

import org.cis.optur.engine.commons.Utils;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class TSXTSAR extends Sn {

    public TSXTSAR(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int initiaTemperatureInt, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange, double reHeatRate, int reHeatRange) {
        int dayLength = Utils.manpowerPlan[(Utils.file - 1)] * 7;
        int [][] newSolutionMatrix = new int [Utils.employees.length][dayLength];
        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = getCandidatePenalty();
        int[][] bestSol = new int[newSolutionMatrix.length][newSolutionMatrix[0].length];
        double initiaTemperature = initiaTemperatureInt;
        double penaltyDelta = 0;
        double probability = 0;
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
//        int[][] tabuAudit = new int[3][3];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int random;
            do {
                random = (int) (Math.random() * 3);
            }while (tabuList.contains(random));

            int llh = random;
            //tabuAudit[0] => Terpanggil
//            tabuAudit[0][llh]++;
            if (llh == 0)
            {
                Utils.twoExchange(solutionMatrix);
            }
            else if (llh == 1)
            {
                Utils.threeExchange(solutionMatrix);
            }
            else
            {
                Utils.doubleTwoExchange(solutionMatrix);
            }
            if (Utils.isFeasibleAllHC(solutionMatrix) == 0) {
                //tabuAudit[1] => memenuhi HC
//                tabuAudit[1][llh]++;
                penaltyDelta = getCandidatePenalty() - currPenalty;
                probability = Math.exp(-(Math.abs(penaltyDelta)/initiaTemperature));
                if (getCandidatePenalty() <= currPenalty) {
                    //tabuAudit[2] => memproduksi solusi lebih baik
//                    tabuAudit[2][llh]++;
                    currPenalty = getCandidatePenalty();
                    Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Utils.copySolutionMatrix(solutionMatrix, bestSol);
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    } else {
                        if (probability >= Math.random()) {
                            if(tabuList.size()==tabuListLength){
                                tabuList.pollLast();
                                tabuList.offerFirst(llh);
                            }else {
                                tabuList.offerFirst(llh);
                            }
                            currPenalty = getCandidatePenalty();
                            Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                        } else {
                            Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
                        }
                    }
                } else {
                    if (probability >= Math.random()) {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = getCandidatePenalty();
                        Utils.copySolutionMatrix(solutionMatrix, newSolutionMatrix);
                    } else {
                        Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
                    }
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Utils.copySolutionMatrix(newSolutionMatrix, solutionMatrix);
            }
            initiaTemperature = initiaTemperature * coolingRate;
            if((i+1)%reHeatRange == 0){
                initiaTemperature = initiaTemperature + (initiaTemperature*reHeatRate);
                System.out.println("ReHeat!");
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = getCandidatePenalty();
                penalties.push(penaltyTemp);
//                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("[Frekuensi LLH terpanggil] | LLH0: " + tabuAudit[0][0] + " | LLH1: " + tabuAudit[0][1] + " | LLH2: " + tabuAudit[0][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC] | LLH0: " + tabuAudit[1][0] + " | LLH1: " + tabuAudit[1][1] + " | LLH2: " + tabuAudit[1][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC dan memproduksi solusi lebih baik] | LLH0: " + tabuAudit[2][0] + " | LLH1: " + tabuAudit[2][1] + " | LLH2: " + tabuAudit[2][2]);
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Utils.file);
    }
}
