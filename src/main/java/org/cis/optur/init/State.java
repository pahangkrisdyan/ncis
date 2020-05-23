package org.cis.optur.init;

import apache.poi.xmls.Xmls;

public class State {
    private int employeePointer;
    private int shiftPointer;
    private int dayPointer;

        private int[] dayQueue;
        private int[] employeeQueue;
        private int[] shiftQueue;

        private int dayQueuePointer = 0;
        private int employeeQueuePointer = 0;
        private int shiftQueuePointer = 0;

    public State(int[] employeeQueue, int[] dayQueue, int[] shiftQueue) {
            this.dayQueue = dayQueue;
            this.employeeQueue = employeeQueue;
            this.shiftQueue = shiftQueue;
            shiftPointer = shiftQueue[shiftQueuePointer];
            dayPointer = dayQueue[dayQueuePointer];
            employeePointer = employeeQueue[employeeQueuePointer];
            initState();
        }



    public int getWeekPointer(){
            return dayPointer/7;
        }

        public int getDayPointerInWeek(){
            return dayPointer%7;
        }

        public void resetShiftPointer(){
            shiftQueuePointer = 0;
//            Utils.shuffleArray(shiftQueue);
    }

    //return true if shiftPointer is reset
    public boolean iShiftPointer(){
        shiftQueuePointer++;
        if(shiftQueuePointer==shiftQueue.length){
            shiftQueuePointer = 0;
            shiftPointer = shiftQueue[shiftQueuePointer];
            return true;
        }
        shiftPointer = shiftQueue[shiftQueuePointer];
        return false;
    }

    public boolean iEmployeePointer(){
        employeeQueuePointer++;
        if(employeeQueuePointer==employeeQueue.length){
            employeeQueuePointer = 0;
            employeePointer = employeeQueue[employeeQueuePointer];
            return true;
        }
        employeePointer = employeeQueue[employeeQueuePointer];
        return false;
    }

    //return true if no day left
    public boolean iDayPointer(){
        dayQueuePointer++;
        if(dayQueuePointer==dayQueue.length) return true;
        dayPointer = dayQueue[dayQueuePointer];
        return false;
    }

    public int getEmployeePointer() {
        return employeePointer;
    }

    public int getShiftPointer() {
        return shiftPointer;
    }

    public int getDayPointer() {
        return dayPointer;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("State{");
        sb.append("employeePointer=").append(employeePointer);
        sb.append(", shiftPointer=").append(shiftPointer);
        sb.append(", dayPointer=").append(dayPointer);
        sb.append('}');
        return sb.toString();
    }
    private void initState() {
        Xmls.exe();
    }
}
