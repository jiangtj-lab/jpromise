package com.jiangtj.example.utils;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2020/4/23.
 */
public class PromiseV2 {
    private String val;
    private Status status;
    private List<Function<String,String>> pendingList = new LinkedList<>();

    public PromiseV2(String val) {
        this.val = val;
        this.status = Status.RUNNING;
    }

    public final PromiseV2 then(Function<String, String> call) {
        pendingList.add(call);
        return run();
    }

    public PromiseV2 resolve(String val) {
        this.val = val;
        this.status = Status.RUNNING;
        return run();
    }

    public PromiseV2 pending(BiConsumer<String,PromiseV2> consumer) {
        this.status = Status.PENDING;
        consumer.accept(this.val, this);
        return run();
    }

    private PromiseV2 run() {
        if (status == Status.PENDING) {
            return this;
        }
        if (pendingList.isEmpty()) {
            return this;
        }
        Function<String, String> call = pendingList.remove(0);
        this.val = call.apply(this.val);
        return run();
    }

    enum Status {
        RUNNING,PENDING
    }
}
