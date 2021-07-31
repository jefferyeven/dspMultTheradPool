package com.example.dspmulttheradpool.config;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j
public class BspMultTheradPool {
    private final ThreadPoolExecutor threadPoolExecutor ;
    private final ThreadPoolConfig poolConfig;
    public ThreadPoolExecutor getThreadPoolExecutor() {
        return threadPoolExecutor;
    }

    public BspMultTheradPool(ThreadPoolConfig poolConfig) {
        this.poolConfig=poolConfig;
        this.threadPoolExecutor=new ThreadPoolExecutor(poolConfig.getInitCorePoolSize(),poolConfig.getInitMaxPoolSize(),
                poolConfig.getInitKeepAliveSeconds(),TimeUnit.SECONDS,new LinkedBlockingQueue<>(poolConfig.getInitQueueCapacity())
                , new ThreadPoolExecutor.CallerRunsPolicy());
    }
    public void updateCoreAndMaxPoolSize(int errorCount)
    {
        if(errorCount==0||errorCount==1)
        {
            return;
        }
        int maxPoolSize=threadPoolExecutor.getMaximumPoolSize();
        maxPoolSize=errorCount>0?maxPoolSize-errorCount/2:maxPoolSize+poolConfig.getAddPoolSize();
        if(maxPoolSize>poolConfig.getMaxSetMaxPoolSize())
        {
            return;
        }
        if(maxPoolSize<poolConfig.getMinSetMaxPoolSize())
        {
            maxPoolSize=poolConfig.getMinSetMaxPoolSize();
        }
        //设定最大连接池
        setMaximumPoolSize(maxPoolSize);
        int corePoolSize=maxPoolSize/2;
        if(corePoolSize>poolConfig.getMaxSetCorePoolSize())
        {
            return;
        }
        if(corePoolSize<poolConfig.getMinSetCorePoolSize())
        {
            corePoolSize=poolConfig.getMinSetCorePoolSize();
        }
        setCorePoolSize(corePoolSize);
    }


    public void setCorePoolSize(int corePoolSize){
        try{
            threadPoolExecutor.setCorePoolSize(corePoolSize);
            log.info("设置核心线程数成功,设置之后的核心线程数： {}",threadPoolExecutor.getCorePoolSize());

        }catch (Exception e){
            //设置失败
            e.printStackTrace();
        }
    }

    public int getCorePoolSize()
    {
        return threadPoolExecutor.getCorePoolSize();
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        try{
            threadPoolExecutor.setMaximumPoolSize(maximumPoolSize);
            log.info("设置核心线程数成功,设置之后的最大线程数： {}",threadPoolExecutor.getMaximumPoolSize());
        }catch (Exception e){
            //设置失败
            e.printStackTrace();

        }
    }

    public int getMaximumPoolSize()
    {
        return threadPoolExecutor.getMaximumPoolSize();
    }

    public void execute(Runnable task) {
        threadPoolExecutor.execute(task);
    }


    public Future<?> submit(Runnable task) {
        return threadPoolExecutor.submit(task);
    }


    public <T> Future<T> submit(Callable<T> task) {
        return this.threadPoolExecutor.submit(task);
    }
}
