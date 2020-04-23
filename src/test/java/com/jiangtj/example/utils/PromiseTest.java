package com.jiangtj.example.utils;

import org.junit.jupiter.api.Test;

import java.util.Timer;
import java.util.TimerTask;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by MrTT (jiang.taojie@foxmail.com)
 * 2020/4/23.
 */
class PromiseTest {

    @Test
    void exampleV1() {
        String result = new Promise<>(5)
                .then(x -> x+2)
                .then(x -> x + "!")
                .block();
        assertEquals("7!", result);
    }

    @Test
    void exampleV2() {
        new PromiseV2("初始化")
                .then(s -> s + "ok")
                .then(check -> {
                    System.out.println(check);
                    assertEquals("初始化ok", check);
                    return check;
                })
                .pending((val,p) -> {
                    Timer timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            p.resolve(val + ",2s等待！");
                        }
                    }, 2000);
                })
                .then(check -> {
                    System.out.println(check);
                    assertEquals("初始化ok,2s等待！", check);
                    return check;
                });
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}