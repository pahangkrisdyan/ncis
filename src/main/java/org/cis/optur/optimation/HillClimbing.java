package org.cis.optur.optimation;
import org.cis.optur.engine.commons.Commons;
import org.cis.optur.engine.commons.OptimationResult;
import org.cis.optur.engine.commons.Sn;

import java.util.LinkedList;

public class HillClimbing extends Sn{

    public HillClimbing(int[][] solution) {
        super(solution);
    }

    public OptimationResult getOptimationResult(int iteration, int penaltyRecordRange) {
        int day = Commons.planningHorizon[(Commons.file - 1)] * 7;
        int [][] bestSolution = new int [Commons.emp.length][day];
        double bestPenalty = countPenalty();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int[][] backupSol = new int [Commons.emp.length][day];
            Commons.copyArray(solution, backupSol);
            int rand = (int) (Math.random() * 3);
            int llh = rand;

            if (llh == 0) Commons.twoExchange(solution);
            else if (llh == 1) Commons.threeExchange(solution);
            else Commons.doubleTwoExchange(solution);

            double currPenalty = countPenalty();
            if (Commons.validAll(solution) == 0 && bestPenalty > currPenalty ) {
                bestPenalty = currPenalty;
                Commons.copyArray(solution, bestSolution);
            }else {
                Commons.copyArray(backupSol, solution);
            }
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = countPenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSolution, bestPenalty, Commons.file);
    }
}
