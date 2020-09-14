package dhlk.proxy.dhlk_proxy.config;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {

    private String name;
    private int count;

    public MyThreadFactory(String name) {
        this.name = name;
        count = 0;
    }

    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread t = new Thread(r);
        t.setName(this.name+"-"+count);
        count++;
        return t;
    }
}
