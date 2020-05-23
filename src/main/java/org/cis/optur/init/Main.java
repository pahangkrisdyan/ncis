package org.cis.optur.init;

import org.cis.optur.compile.Optur;
import org.cis.optur.compile.model.Employee;
import org.cis.optur.compile.model.ManpowerPlan;
import org.cis.optur.compile.model.Shift;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Employee[] employees = {};
        Shift[] shifts = {};
        ManpowerPlan[] manpowerPlans = {};
        int[][] mPNM = {};
        try {
            Optur optur = new Optur("E:\\SEMESTER 8\\DATA\\OpTur (1)\\OpTur7.xls");

            Object[] employeesRaw = optur.getEmployeeRepository().toArray();
            employees = new Employee[employeesRaw.length];
            for (int i = 0; i<employees.length; i++){
                employees[i] = (Employee) employeesRaw[i];
            }

            Object[] shiftsRaw = optur.getShiftRepository().toArray();
            shifts = new Shift[shiftsRaw.length];
            for (int i = 0; i<shifts.length; i++){
                shifts[i] = (Shift) shiftsRaw[i];
            }

            Object[] manpowerPlansRaw = optur.getManpowerPlanRepository().toArray();
            manpowerPlans = new ManpowerPlan[manpowerPlansRaw.length];
            for (int i = 0; i<manpowerPlans.length; i++){
                manpowerPlans[i] = (ManpowerPlan) manpowerPlansRaw[i];
            }

            mPNM = new int[manpowerPlans.length][7];

            for (int i=0; i<mPNM.length; i++){
                for (int j=0; j<7; j++){
                    switch (j){
                        case 0: mPNM[i][j] = manpowerPlans[i].getMonday(); break;
                        case 1: mPNM[i][j] = manpowerPlans[i].getTuesday(); break;
                        case 2: mPNM[i][j] = manpowerPlans[i].getWednesday(); break;
                        case 3: mPNM[i][j] = manpowerPlans[i].getThurdsay(); break;
                        case 4: mPNM[i][j] = manpowerPlans[i].getFriday(); break;
                        case 5: mPNM[i][j] = manpowerPlans[i].getSaturday(); break;
                        case 6: mPNM[i][j] = manpowerPlans[i].getSunday(); break;
                    }
                }
            }
        }catch (Exception e){
            Engine2 engine2 = new Engine2(employees, shifts, mPNM, 42);
            engine2.run();
        }
    }
}
