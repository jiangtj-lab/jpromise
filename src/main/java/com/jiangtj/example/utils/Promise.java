package com.jiangtj.example.utils;

import java.util.function.Function;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2020/4/23.
 */
public class Promise<V> {

    private V val;

    public Promise(V val) {
        this.val = val;
    }

    public <P> Promise<P> then(Function<V,P> call) {
        return new Promise<>(call.apply(val));
    }

    public V block() {
        return val;
    }


}
