package org.example.item10;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {

    private static final class CaseInsensitiveString {
        private final String s;

        public CaseInsensitiveString(String s) {
            // 방어적 프로그래밍용
            // null 진입시 터트리는 의도를 분명히한다
            this.s = Objects.requireNonNull(s);
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof CaseInsensitiveString) {
                return s.equalsIgnoreCase(((CaseInsensitiveString) o).s);
            }
            if (o instanceof String) {
                return s.equalsIgnoreCase((String) o);
            }
            return false;
        }
    }

    private static class Point {
        private final int x;
        private final int y;
        // ..Composition
        private final Color color;

        public Point(int x, int y, Color color) {
            this.x = x;
            this.y = y;
            this.color = color;
        }

        @Override
        public boolean equals(Object o) {
            /* 해당 구현은 상속으로 인해 equals의 구현이 불가능하다
            if(! (o instanceof Point)) {
                return false;
            }
            Point p = (Point) o;
            return p.x == x && p.y == y;
             */
            // Composition을 통해 LSP를 만족시키는 equals를 구현한다
            // equals를 재정의할때는 <<반드시>> hashcode도 재정의해야 hash관련 API에서 문제가 생기지 않는다
            // 올바른 equals의 구현 방법은 다음을 반드시 지켜야 한다
            // 1. 연산자를 이용해 자기 자신의 참조인지 확인한다(성능 최적화용)
            // 2. instanceof 연산자로 입력이 올바른 타입인지 확인한다
            // 3. 입력을 온바른 타입으로 형변환 한다(2와 연관되어 반드시 성공한다)
            // 4. 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사한다
            if(o == this) {
                return true;
            }

            if(!(o instanceof Point)) {
                return false;
            }

            Point p = (Point) o;
            return p.x == this.x && p.y == this.y && p.color == this.color;
        }
    }

    // 상속은 equals에 대한 재정의가 <<불가능>>하다
    // 객체 지향에서 equals를 만족시키기 위한 BEST PRACTICE는 COMPOSITION이다
    /*
    private static class ColorPoint extends Point {
        private final Color color;

        public ColorPoint(int x, int y, Color color) {
            super(x, y);
            this.color = color;
        }

        // equals를 구현하지 않을 시 point의 equals가 사용되 색상을 무시하고 비교를 수행한다
        @Override
        public boolean equals(Object o) {
            if(!(o instanceof ColorPoint)) {
                return false;
            }
            return super.equals(o) && ((ColorPoint) o).color == color;
        }
    }
    */
    private enum Color {
        RED, GREEN
    }

    static void main() {
        List<CaseInsensitiveString> testList = new ArrayList<>();

        System.out.println("------Symmetry대칭성 위배------------");
        CaseInsensitiveString cis = new CaseInsensitiveString("Cis");
        String s = "cis";
        System.out.println(cis.equals(s));
        System.out.println(s.equals(cis));
        testList.add(cis);
        // contains 는 equals를 이용해 동일성을 판별한다
        System.out.println(testList.contains(s));
        // equals의 구현이 서로 달라 발생하는 문제이다
        System.out.println("----------------------------------");

        System.out.println("------Transitivity추이성 위배---------");

        System.out.println("----------------------------------");
    }
}
