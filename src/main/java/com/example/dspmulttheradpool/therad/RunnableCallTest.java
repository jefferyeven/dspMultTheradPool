package com.example.dspmulttheradpool.therad;

import lombok.SneakyThrows;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.concurrent.*;

public class RunnableCallTest implements Callable {
    private String taskName;

    public RunnableCallTest(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public String call() throws Exception {
        Thread.sleep(500);
        return taskName;
    }
}
