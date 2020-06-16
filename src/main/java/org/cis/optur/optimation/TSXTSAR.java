package org.cis.optur.optimation;

import org.cis.optur.engine.commons.Commons;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class TSXTSAR extends Sn {

    public TSXTSAR(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int initialTemp, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange, double reHeatRate, int reHeatRange) {
        int day = Commons.planningHorizon[(Commons.file - 1)] * 7;
        int [][] newSolution = new int [Commons.emp.length][day];
        Commons.copyArray(solution, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = countPenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        double TAwal = initialTemp;
        double delta = 0;
        double d;
        double prob = 0;
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
//        int[][] tabuAudit = new int[3][3];
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

            int llh = rand;
            //tabuAudit[0] => Terpanggil
//            tabuAudit[0][llh]++;
            if (llh == 0)
            {
                Commons.twoExchange(solution);
            }
            else if (llh == 1)
            {
                Commons.threeExchange(solution);
            }
            else
            {
                Commons.doubleTwoExchange(solution);
            }
            if (Commons.validAll(solution) == 0) {
                //tabuAudit[1] => memenuhi HC
//                tabuAudit[1][llh]++;
                delta = countPenalty() - currPenalty;
                d = Math.abs(delta)/TAwal;
                prob = Math.exp(-d);
                if (countPenalty() <= currPenalty) {
                    //tabuAudit[2] => memproduksi solusi lebih baik
//                    tabuAudit[2][llh]++;
                    currPenalty = countPenalty();
                    Commons.copyArray(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Commons.copyArray(solution, bestSol);
                        Commons.copyArray(solution, newSolution);
                    } else {
                        if (prob >= Math.random()) {
                            if(tabuList.size()==tabuListLength){
                                tabuList.pollLast();
                                tabuList.offerFirst(llh);
                            }else {
                                tabuList.offerFirst(llh);
                            }
                            currPenalty = countPenalty();
                            Commons.copyArray(solution, newSolution);
                        } else {
                            Commons.copyArray(newSolution, solution);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = countPenalty();
                        Commons.copyArray(solution, newSolution);
                    } else {
                        Commons.copyArray(newSolution, solution);
                    }
                }
            } else {
                if(tabuList.size()==tabuListLength){
                    tabuList.pollLast();
                    tabuList.offerFirst(llh);
                }else {
                    tabuList.addFirst(llh);
                }
                Commons.copyArray(newSolution, solution);
            }
            TAwal = TAwal * coolingRate;
            if((i+1)%reHeatRange == 0){
                TAwal = TAwal + (TAwal*reHeatRate);
                System.out.println("ReHeat!");
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = countPenalty();
                penalties.push(penaltyTemp);
//                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
//        System.out.println("[Frekuensi LLH terpanggil] | LLH0: " + tabuAudit[0][0] + " | LLH1: " + tabuAudit[0][1] + " | LLH2: " + tabuAudit[0][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC] | LLH0: " + tabuAudit[1][0] + " | LLH1: " + tabuAudit[1][1] + " | LLH2: " + tabuAudit[1][2]);
//        System.out.println("[Frekuensi LLH terpanggil dan memenuhi HC dan memproduksi solusi lebih baik] | LLH0: " + tabuAudit[2][0] + " | LLH1: " + tabuAudit[2][1] + " | LLH2: " + tabuAudit[2][2]);
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Commons.file);
    }
}
