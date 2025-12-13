package org.example.item39;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Annotation {

    // @Test 어노테이션은 마커 아노테이션으로, 아무 매개 변수 없이 단순히 대상을 마킹하는 마커 어노테이션이다
    private static class Sample1 {
        // 성공 해야한다
        @Test
        public static void m1() {
        }

        public static void m2() {
        }

        // 실패해야 한다
        @Test
        public static void m3() {
            throw new RuntimeException("fail");
        }

        public static void m4() {
        }

        // 실패, static method가 아님
        @Test
        public void m5() {
        }

        @Test
        public static void m6() {
        }

        // 실패해야 한다
        @Test
        public static void m7() {
            throw new RuntimeException("fail");
        }

        public static void m8() {
        }
    }

    private static class Sample2 {
        @ExceptionTest(ArithmeticException.class)
        public static void m1() { // 성공 해야한다
            int i = 0;
            i = i / i;
        }

        @ExceptionTest(ArithmeticException.class)
        public static void m2() {
            int[] a = new int[0];
            int i = a[1]; // 실패해야한다(예외 종류 예측실패)
        }

        @ExceptionTest(ArithmeticException.class)
        public static void m3() {
            // 실패해야한다(예외 발생하지 않음)
        }
    }

    static void main() throws Exception {
        int tests = 0;
        int passed = 0;
        Class<?> testClass = Class.forName("org.example.item39.Annotation$Sample1");
        for (Method m : testClass.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Test.class)) {
                tests++;
                try {
                    m.invoke(null);
                    passed++;
                } catch (InvocationTargetException wrappedExc) {
                    Throwable exc = wrappedExc.getCause();
                    System.out.println(m + "실패: " + exc);
                } catch (Exception e) {
                    System.out.println("잘못 사용한 @Test: " + m);
                }
            }
        }
        System.out.printf("성공: %d, 실패: %d\n", passed, tests - passed);
        System.out.println("=================================================");


        Class<?> testClass2 = Class.forName("org.example.item39.Annotation$Sample2");

        tests = 0;
        passed = 0;
        for (Method m : testClass2.getDeclaredMethods()) {
            if (m.isAnnotationPresent(ExceptionTest.class)) {
                tests++;
                try {
                    m.invoke(null);
                    System.out.printf("테스트 %s 실패: 예외를 던지지 않음 \n", m);
                } catch (InvocationTargetException wrappedEx) {
                    Throwable exc = wrappedEx.getCause();
                    Class<? extends Throwable> excType = m.getAnnotation(ExceptionTest.class).value();
                    if (excType.isInstance(exc)) {
                        passed++;
                    } else {
                        System.out.printf("테스트 %s 실패: 기대한 예외 %s, 발생한 예외 %s\n", m, excType.getName(), exc);
                    }
                } catch (Exception e) {
                    System.out.println("잘못 사용한 @ExceptionTest: " + m);
                }
            }
        }
        System.out.printf("성공: %d, 실패: %d\n", passed, tests - passed);
    }

}
