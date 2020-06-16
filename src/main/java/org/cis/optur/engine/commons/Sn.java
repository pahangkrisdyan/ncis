package org.cis.optur.engine.commons;

import org.cis.optur.engine.commons.*;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.LinkedList;
import java.util.Scanner;

public class Sn {
    IterationListener iterationListener;

    public int [][] solution;
    public double penalty = 0.0D;


    public Sn(int [][] solution) {
        this.solution = solution;
        this.penalty = penalty1() + penalty2() + penalty3() + penalty4() + penalty5() +
                penalty6() + penalty7() + penalty8() + penalty9();
    }

    public int[][] getSolution(){
        return solution;
    }

    public void setSolution(int[][] solution) {
        this.penalty = 0.0D;
        this.solution = solution;
    }

    public double countPenalty() {
        return penalty1() + penalty2() + penalty3() + penalty4() + penalty5() +
                penalty6() + penalty7() + penalty8() + penalty9();
    }

    public double getPenalty() {
        return penalty;
    }

    double penalty1() {
        double penalty1 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softConstraint = -1;
            if (i == 0) {
                softConstraint = Commons.soft.getSc1a();
                if (Commons.soft.getSc1a() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc1a());
                } else
                    continue;;
            }

            if (i == 1) {
                softConstraint = Commons.soft.getSc1b();
                if (Commons.soft.getSc1b() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc1b());
                } else
                    continue;;
            }
            if (i == 2) {
                softConstraint = Commons.soft.getSc1c();
                if (Commons.soft.getSc1c() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc1c());
                } else
                    continue;;
            }
            for (int j = 0; j < Commons.emp.length; j++) {
                for (int k = 0; k < day; k++) {
                    int count = (softConstraint+1);
                    for (int l = k; l <= k+softConstraint ; l++) {
                        if (solution[j][l] != 0) {
                            if (Commons.shiftxes[solution[j][l]-1].getCategory() == (i+1)) {
                                count--;
                            }
                        }
                    }
                    if (count == 0)
                        penalty1 = penalty1 + 1;
                }
            }
        }
        return penalty1;
    }

    double penalty2() {
        double penalty2 = 0;
        int softConstraint = Commons.soft.getSc2();
        if (softConstraint != 0) {
            int day = (Commons.planningHorizon[Commons.file - 1] * 7) - softConstraint;
            for (int i = 0; i < Commons.emp.length; i++) {
                for (int j = 0; j < day; j++) {
                    int count = (softConstraint + 1);
                    for (int k = j; k <= j + softConstraint; k++) {
                        if (solution[i][k] > 0)
                            count--;
                    }
                    if (count == 0)
                        penalty2 = penalty2 + 1;
                }
            }
        }
        return penalty2;
    }

    double penalty3() {
        double penalty3 = 0;
        for (int i = 0; i < 3; i++) {
            int day = 0;
            int softConstraint = -1;
            if (i == 0) {
                softConstraint = Commons.soft.getSc3a();
                if (Commons.soft.getSc3a() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc3a());
                } else
                    continue;;
            }
            if (i == 1) {
                softConstraint = Commons.soft.getSc3b();
                if (Commons.soft.getSc3b() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc3b());
                } else
                    continue;;
            }
            if (i == 2) {
                softConstraint = Commons.soft.getSc3c();
                if (Commons.soft.getSc3c() != 0) {
                    day = (Commons.planningHorizon[(Commons.file - 1)] * 7) - (Commons.soft.getSc3c());
                } else
                    continue;;
            }
            for (int j = 0; j < Commons.emp.length; j++) {
                for (int k = 0; k < day; k++) {
                    if (solution[j][k+1] != 0) {
                        if (Commons.shiftxes[solution[j][k+1]-1].getCategory() == (i+1)) {
                            if (solution[j][k] == 0 || Commons.shiftxes[solution[j][k] - 1].getCategory() != (i + 1)) {
                                double calculate = 0;
                                for (int l = k + 1; l <= k + softConstraint; l++) {
                                    int count = 1;
                                    for (int m = k + 1; m <= k + softConstraint; m++) {
                                        if (solution[j][m] == 0 || Commons.shiftxes[solution[j][m] - 1].getCategory() != (i + 1))
                                            count = 0;
                                    }
                                    if (count == 1)
                                        calculate = calculate + 1;
                                }
                                double penalti = softConstraint - calculate;
                                penalty3 = penalty3 + penalti;
                            }
                        }
                    }
                }
            }
        }
        return penalty3;
    }

    double penalty4() {
        double penalty4 = 0;
        int softConstraint = Commons.soft.getSc4();
        if (softConstraint != 0) {
            int day = (Commons.planningHorizon[(Commons.file-1)] * 7) - softConstraint;
            for (int i = 0; i < Commons.emp.length; i++) {
                for (int j = 0; j < day; j++) {
                    if (solution[i][j+1] != 0 && solution[i][j] == 0) {
                        double calculate = 0;
                        for (int k = j+1; k <= j+softConstraint; k++) {
                            int count = 1;
                            for (int l = j+1; l <= j+softConstraint; l++) {
                                if (solution[i][l] == 0)
                                    count = 0;
                            }
                            if (count == 1)
                                calculate = calculate + 1;
                        }
                        double penalti = softConstraint - calculate;
                        penalty4 = penalty4 - penalti;
                    }
                }
            }
        }
        return penalty4;
    }

    double penalty5() {
        double penalty5 = 0;
        for (int i = 0; i < 3; i++) {
            int min = 0;
            int max = 0;
            if (i == 0) {
                min = Commons.soft.getSc5aMin();
                max = Commons.soft.getSc5aMax();
            }
            if (i == 1) {
                min = Commons.soft.getSc5bMin();
                max = Commons.soft.getSc5aMax();
            }
            if (i == 2) {
                min = Commons.soft.getSc5cMin();
                max = Commons.soft.getSc5cMax();
            }
            if (min == 0 && max == 0)
                continue;

            for (int j = 0; j < Commons.emp.length; j++) {
                int count = 0;
                for (int k = 0; k < Commons.planningHorizon[(Commons.file - 1)] * 7; k++) {
                    if (solution[j][k] != 0)
                        if (Commons.shiftxes[solution[j][k]-1].getCategory() == (i+1))
                            count++;
                }
                min = min - count;
                max = count - max;
                if (min>max && min>0)
                    penalty5 = penalty5 + (min*min);
                if (max>min && max>0)
                    penalty5 = penalty5 + (max*max);
            }
        }
        penalty5 = Math.sqrt(penalty5);
        return penalty5;
    }

    double penalty6 () {
        double penalty6 = 0;
        if (Commons.soft.getSc6()) {
            for (int i = 0; i < Commons.emp.length; i++) {
                double hour = (Commons.clockLimit[i][1]) * (Commons.planningHorizon[(Commons.file - 1)]);
                double workingHour = 0;
                for (int j = 0; j < Commons.planningHorizon[(Commons.file - 1)] * 7; j++) {
                    if (solution[i][j] != 0)
                        workingHour = workingHour + Commons.shiftxes[solution[i][j]-1].getDuration(j % 7);
                }
                hour = hour - workingHour;
                penalty6 = penalty6 + (hour*hour);
            }
            penalty6 = Math.sqrt(penalty6);
        }
        return penalty6;
    }

    double penalty7 () {
        double penalty7 = 0;
        if (Commons.soft.getSc7()) {
            for (int i = 0; i < Commons.emp.length; i++) {
                int count = 0;
                for (int j = 0; j < (Commons.planningHorizon[(Commons.file - 1)] * 7)-1; j++) {
                    if (solution[i][j] != 0 && solution[i][j+1] == 0)
                        count++;
                }
                penalty7 = penalty7 + (count*count);
            }
            penalty7 = Math.sqrt(penalty7);
        }
        return penalty7;
    }

    double penalty8 () {
        double penalty8 = 0;
        int day = (Commons.planningHorizon[(Commons.file-1)] * 7);
        int emp = Commons.emp.length;
        if (Commons.soft.getSc8() != 0) {
            penalty8 = (double) emp * day;
            for (int i = 0; i < emp; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Commons.want.length; k++) {
                        if (day % 7 == Commons.want[k].day) {
                            if (solution[i][j] != 0) {
                                if (Commons.shiftxes[solution[i][j]-1].getName().equals(Commons.want[k].pattern[0])) {
                                    if (j <= day - (Commons.want[k].pattern.length)) {
                                        int count = Commons.want[k].pattern.length;
                                        for (int l = 0; l < Commons.want[k].pattern.length; l++) {
                                            if (solution[i][j+l] != 0) {
                                                if (Commons.shiftxes[solution[i][j + l] - 1].getName().equals(Commons.want[k].pattern[l])) {
                                                    count--;
                                                }
                                            }
                                            else {
                                                if (Commons.want[k].pattern[l].equals("<Free>")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penalty8 = penalty8 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penalty8;
    }

    double penalty9 () {
        double penalty9 = 0;
        int day = (Commons.planningHorizon[(Commons.file-1)] * 7);
        int emp = Commons.emp.length;
        if (Commons.soft.getSc9() != 0) {
            for (int i = 0; i < emp; i++) {
                for (int j = 0; j < day; j++) {
                    for (int k = 0; k < Commons.unwant.length; k++) {
                        if (day % 7 == Commons.unwant[k].day) {
                            if (solution[i][j] != 0) {
                                if (Commons.shiftxes[solution[i][j]-1].getName().equals(Commons.unwant[k].pattern[0])) {
                                    if (j <= day - (Commons.unwant[k].pattern.length)) {
                                        int count = Commons.unwant[k].pattern.length;
                                        for (int l = 0; l < Commons.unwant[k].pattern.length; l++) {
                                            if (solution[i][j+l] != 0) {
                                                if (Commons.shiftxes[solution[i][j + l] - 1].getName().equals(Commons.unwant[k].pattern[l])) {
                                                    count--;
                                                }
                                            }
                                            else {
                                                if (Commons.unwant[k].pattern[l].equals("<Free>")) {
                                                    count--;
                                                }
                                            }
                                        }
                                        if (count == 0)
                                            penalty9 = penalty9 - 1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return penalty9;
    }


    public int countRF (int [] rf) {
        int index = -1000000;
        int llh = -1;
        LinkedList<Integer> rl = new LinkedList<Integer>();
        for (int i = 0; i < rf.length; i++) {
            if (rf[i] > index) {
                llh = i;
                index = rf[i];
                for (int j = 0; j < rf.length; j++) {
                    if (rf[j] == index)
                        rl.add(j);
                }
                if (!rl.isEmpty()) {
                    int x = -1;
                    x = (int) (Math.random() * 3);
                    while (!rl.contains(x)) {
                        x = (int) (Math.random() * 3);
                    }
                    rl.clear();
                }
            }
        }
        return llh;
    }

    public OptimationResult HC2(int iteration, int penaltyRecordRange) {
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

    public OptimationResult SA2(int initialTemp, double coolingRate, int iteration, int penaltyRecordRange) {
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

    public OptimationResult T2(int iteration, int tabuListLength, int penaltyRecordRange) {
        int day = Commons.planningHorizon[(Commons.file - 1)] * 7;
        int [][] newSolution = new int [Commons.emp.length][day];
        Commons.copyArray(solution, newSolution);
        double bestPenalty; double currPenalty;
        bestPenalty = currPenalty = countPenalty();
        int[][] bestSol = new int[newSolution.length][newSolution[0].length];
        LinkedList<Integer> tabuList = new LinkedList<>();
        LinkedList<Double> penalties = new LinkedList<>();
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

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
                if (countPenalty() <= currPenalty) {
                    currPenalty = countPenalty();
                    Commons.copyArray(solution, newSolution);
                    if (currPenalty <= bestPenalty) {
                        bestPenalty = currPenalty;
                        Commons.copyArray(solution, bestSol);
                        Commons.copyArray(solution, newSolution);
                    } else {
                        if(tabuList.size()==tabuListLength){
                            tabuList.pollLast();
                            tabuList.offerFirst(llh);
                        }else {
                            tabuList.offerFirst(llh);
                        }
                        currPenalty = countPenalty();
                        Commons.copyArray(solution, newSolution);
                    }
                } else {
                    if(tabuList.size()==tabuListLength){
                        tabuList.pollLast();
                        tabuList.offerFirst(llh);
                    }else {
                        tabuList.offerFirst(llh);
                    }
                    currPenalty = countPenalty();
                    Commons.copyArray(solution, newSolution);
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
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = countPenalty();
                penalties.push(penaltyTemp);
                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Commons.file);
    }

    public OptimationResult TSA2(int initialTemp, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange) {
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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

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
            if ((i+1)%penaltyRecordRange == 0){
                Double penaltyTemp = countPenalty();
                penalties.push(penaltyTemp);
//                System.out.println(penaltyTemp);
            }
        }
        long endTime = System.currentTimeMillis();
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Commons.file);
    }

    public OptimationResult TSAR2(int initialTemp, double coolingRate, int iteration, int tabuListLength, int penaltyRecordRange, double reHeatRate, int reHeatRange) {
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
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < iteration; i++) {
            int rand;
            do {
                rand = (int) (Math.random() * 3);
            }while (tabuList.contains(rand));

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
        return new OptimationResult(penalties, endTime-startTime, bestSol, bestPenalty, Commons.file);
    }
}