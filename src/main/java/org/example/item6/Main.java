package org.example.item6;

import org.example.utils.Timer;

public class Main {

    private static void badSum() {
        Long sum1 = 0L;
        Long sum2 = 0L;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum1 += i;
            sum2 += i;
        }
    }

    static void goodSum() {
        long sum1 = 0;
        long sum2 = 0;

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            sum1 += i;
            sum2 += i;
        }
    }

    static void main() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            Timer badTimer = new Timer("badTimer");
            badTimer.start();
            badSum();
            badTimer.end();
            badTimer.getDuration();
        });

        Thread t2 = new Thread(() -> {
            Timer goodTimer = new Timer("goodTimer");
            goodTimer.start();
            goodSum();
            goodTimer.end();
            goodTimer.getDuration();
        });

        /*
        goodTimer 0.001823791 seconds
        badTimer 5.586006375 seconds
        */
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("fin");
    }
}
