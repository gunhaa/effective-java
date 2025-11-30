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

        한개만 더할경우
        goodTimer 0.001995208 seconds
        badTimer 2.934069959 seconds

        두개씩 더할 경우
        goodTimer 0.001823791 seconds
        badTimer 5.586006375 seconds

        goodTime의 실행시간이 거의 비슷한 이유

        - goodSum() 메서드 내부에는 최적화를 방해하는 요소가 거의 없기 때문에, JIT 컴파일러는 루프를 실행할 때 반복되는 비효율적인 부분을 제거 한다
        - 두 개의 독립적인 합산(sum1 += i;와 sum2 += i;)은 서로 간섭하지 않는 독립적인 연산으로 간주된다
        - JIT 컴파일러는 이 두 연산을 병렬로 처리할 수 있는 CPU의 벡터 레지스터나 명령어 파이프라인에 효과적으로 배치하여, 두 개의 연산이 거의 한 번의 사이클에 가깝게 동시에 처리되도록 최적화 한다
        - 이로 인해 sum1 += i;만 있을 때나, sum1 += i;와 sum2 += i;가 같이 있을 때나 전체 루프 실행 시간은 거의 차이가 나지 않게 된다
        */
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("fin");
    }
}
