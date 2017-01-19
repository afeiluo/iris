package com.afeiluo.log;

public class TestTrace {

    /**
     * @param args
     */
    public static void main(String[] args) {
        OperationTrace trace = new OperationTrace("TestTrace", new DefaultTracerDriver());
        for (int i = 0; i < 10; i++) {
            System.out.println("do something....");
        }
        trace.commit();

    }
}
