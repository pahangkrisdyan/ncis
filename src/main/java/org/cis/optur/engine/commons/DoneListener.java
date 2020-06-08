package org.apache.commons;

import java.io.IOException;

public interface DoneListener {
    void onDoneEvent(int[][] initSol) throws IOException, ClassNotFoundException;
}
