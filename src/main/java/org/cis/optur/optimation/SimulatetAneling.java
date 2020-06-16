package org.cis.optur.optimation;

import org.cis.optur.engine.commons.Commons;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class SimulatetAneling extends Sn {

    public SimulatetAneling(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int initialTemp, double coolingRate, int iteration, int penaltyRecordRange) {
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
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand = (int) (Math.random() * 3);
            int llh = rand;
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
                delta = countPenalty() - currPenalty;
                d = Math.abs(delta)/TAwal;
                prob = Math.exp(-d);
                if (countPenalty() <= currPenalty) {
                    currPenalty = countPenalty();
                    Commons.copyArray(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Commons.copyArray(solution, bestSol);
                        Commons.copyArray(solution, newSolution);
                    } else {
                        if (prob >= Math.random()) {
                            currPenalty = countPenalty();
                            Commons.copyArray(solution, newSolution);
                        } else {
                            Commons.copyArray(newSolution, solution);
                        }
                    }
                } else {
                    if (prob >= Math.random()) {
                        currPenalty = countPenalty();
                        Commons.copyArray(solution, newSolution);
                    } else {
                        Commons.copyArray(newSolution, solution);
                    }
                }
            } else {
                Commons.copyArray(newSolution, solution);
            }
            TAwal = TAwal * coolingRate;
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = countPenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Commons.file);
    }
}
