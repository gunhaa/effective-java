package org.example.item14;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// 컬렉션을 이용한 순서가 필요하다면
// Comparable 인터페이스를 구현하는 것이 가장 좋은 방법이다
public class Main {

    private static class NotComparableImplObj {
        private int item1;
        private int item2;

        public NotComparableImplObj(int item1, int item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        @Override
        public String toString() {
            return "NotComparableImplObj{" +
                    "item1=" + item1 +
                    ", item2=" + item2 +
                    '}';
        }
    }

    private static class ComparableImplObj implements Comparable<ComparableImplObj> {
        private int item1;
        private int item2;

        public ComparableImplObj(int item1, int item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

        @Override
        public String toString() {
            return "ComparableImplObj{" +
                    "item1=" + item1 +
                    ", item2=" + item2 +
                    '}';
        }

        /* compare 메서드의 규약
        1. 순서를 위해 이 객체를 주어진 객체와 비교한다.
        2. 이 객체가 주어진 객체보다 작은 경우 음의 정수를 반환해야 하고, 같은 경우에는 0, 큰 경우 양의 정수를 반환한다.
          2-1. 값의 차이로 비교하지 않는다(소수점 문제, 정수 오버플로우)
        3. 만약 해당 객체와 비교할 수 없는 타입이 주어진다면, ClassCastException을 던져야 한다.
        4. 기본형의 비교에는 박싱 타입의 compareTo를 사용한다
        */
        @Override
        public int compareTo(ComparableImplObj o) {
            // 인자의 순서가 중요하다, this가 앞으로 가야 예상한 오름차순 결과가 나온다
            return Integer.compare(this.item2, o.item2);
        }
    }

    private static class ComparatorImplClazz implements Comparable<ComparatorImplClazz> {

        private int item1;
        private int item2;
        private int item3;

        public int getItem1() {
            return item1;
        }

        public int getItem2() {
            return item2;
        }

        public int getItem3() {
            return item3;
        }

        public ComparatorImplClazz(int item1, int item2, int item3) {
            this.item1 = item1;
            this.item2 = item2;
            this.item3 = item3;
        }

        @Override
        public String toString() {
            return "ComparatorImplClazz{" +
                    "item1=" + item1 +
                    ", item2=" + item2 +
                    ", item3=" + item3 +
                    '}';
        }

        // item 3, 2, 1 순으로 비교하는 로직
        // java8 이후는 Comparator를 사용하는것이 가장 직관적이고 베스트 프락티스이다
        @Override
        public int compareTo(ComparatorImplClazz o) {
            return Comparator
                    .comparingInt(ComparatorImplClazz::getItem3)
                    .thenComparing(ComparatorImplClazz::getItem2)
                    .thenComparing(ComparatorImplClazz::getItem1)
                    // 인자의 순서가 중요하다, this가 앞으로 가야 예상한 오름차순 결과가 나온다
                    .compare(this, o);
        }
    }

    static void main() {
        List<Integer> primitiveList = new ArrayList<>();
        primitiveList.add(3);
        primitiveList.add(2);
        primitiveList.add(1);
        System.out.println("primitiveList = " + primitiveList);
        Collections.sort(primitiveList);
        System.out.println("primitiveList = " + primitiveList);

        List<NotComparableImplObj> cantSortedList = new ArrayList<>();
        cantSortedList.add(new NotComparableImplObj(1,3));
        cantSortedList.add(new NotComparableImplObj(2,2));
        cantSortedList.add(new NotComparableImplObj(3,1));
        System.out.println("cantSortedList = " + cantSortedList);
        // comparable 인터페이스가 구현 안되어 sort가 불가능하다
        // Collections.sort(cantSortedList);

        List<ComparableImplObj> sortableList = new ArrayList<>();
        sortableList.add(new ComparableImplObj(1,3));
        sortableList.add(new ComparableImplObj(2,2));
        sortableList.add(new ComparableImplObj(3,1));
        System.out.println("sortableList = " + sortableList);
        Collections.sort(sortableList);
        System.out.println("sortableList = " + sortableList);

        List<ComparatorImplClazz> comparatorImplClazzList = new ArrayList<>();
        comparatorImplClazzList.add(new ComparatorImplClazz(3,3,1));
        comparatorImplClazzList.add(new ComparatorImplClazz(2,2,1));
        comparatorImplClazzList.add(new ComparatorImplClazz(1,1,1));
        comparatorImplClazzList.add(new ComparatorImplClazz(1,2,2));
        comparatorImplClazzList.add(new ComparatorImplClazz(1,1,2));
        System.out.println("comparatorImplClazzList = " + comparatorImplClazzList);
        Collections.sort(comparatorImplClazzList);
        System.out.println("comparatorImplClazzList = " + comparatorImplClazzList);
    }
}
