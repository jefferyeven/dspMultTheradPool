package com.example.dspmulttheradpool.therad;

import lombok.SneakyThrows;

public class RunnableTest implements Runnable {

    private String taskName;

    public RunnableTest(final String taskName) {
        System.out.println(taskName);
        this.taskName = taskName;
    }

    @SneakyThrows
    @Override
    public void run() {
        Thread.sleep(2000);
        System.out.println("Inside "+taskName);
    }

}
