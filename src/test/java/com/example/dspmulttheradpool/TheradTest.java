package com.example.dspmulttheradpool;
import com.example.dspmulttheradpool.config.BspMultTheradPool;
import com.example.dspmulttheradpool.config.ThreadPoolConfig;
import com.example.dspmulttheradpool.therad.BspMultCallTest;
import com.example.dspmulttheradpool.therad.RunnableCallTest;
import com.example.dspmulttheradpool.therad.RunnableTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
@SpringBootTest
class TheradTest {
    @Autowired
    ThreadPoolConfig threadPoolConfig;
    @Test
    public void test() throws InterruptedException {
       BspMultTheradPool bspMultTheradTaskPool=new BspMultTheradPool(threadPoolConfig);
        bspMultTheradTaskPool.getThreadPoolExecutor().execute(new RunnableTest("hello"));
        Thread.sleep(5000);
    }
    @SneakyThrows
    @Test
    public void testRunnableCallTest()
    {
        BspMultTheradPool bspMultTheradTaskPool=new BspMultTheradPool(threadPoolConfig);
        Future future=bspMultTheradTaskPool.submit(new RunnableCallTest("hello"));
        System.out.println(future.get());
    }
    @Test
    public void bsp() throws ExecutionException, InterruptedException {
        BspMultTheradPool bspMultTheradTaskPool=new BspMultTheradPool(threadPoolConfig);
        int group=bspMultTheradTaskPool.getMaximumPoolSize()*2;//一个超步的运行任务是maxpoolsize的两倍速
        for(int i=0;i<1000;)
        {
            List<Future> list=new ArrayList<>();
            for(int j=i;j<i+group;j++)
            {
                list.add(bspMultTheradTaskPool.submit(new BspMultCallTest(j)));
            }
            i=i+group;
            int errorPoint=0;
            for(Future future:list)
            {
                System.out.println(future.get());
                if(!((Boolean) future.get()))
                {
                    errorPoint++;
                }
            }
            bspMultTheradTaskPool.updateCoreAndMaxPoolSize(errorPoint);
            group=bspMultTheradTaskPool.getMaximumPoolSize()*2;
        }
    }
}
