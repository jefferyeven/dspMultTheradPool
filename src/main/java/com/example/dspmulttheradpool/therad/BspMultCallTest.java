package com.example.dspmulttheradpool.therad;

import java.util.concurrent.Callable;

public class BspMultCallTest implements Callable<Boolean> {
    private int taskName=0;
    public BspMultCallTest(Integer taskName) {
        this.taskName=taskName;
    }

    @Override
    public Boolean call() throws Exception {
        Thread.sleep(2000);
        System.out.println("thread"+taskName);
        return taskName % 66 != 0;
    }
}
