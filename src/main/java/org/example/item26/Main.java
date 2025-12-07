package org.example.item26;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Main {

    private static class Coin {
        private int value;

        public Coin(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Coin{" +
                    "value=" + value +
                    '}';
        }
    }

    private static class Stamp {
        private String name;

        public Stamp(String name) {
            this.name = name;
        }
    }

    static void main() {
        Collection stamps = new ArrayList<>();
        stamps.add(new Coin(500));
        Iterator iterator = stamps.iterator();
        while(iterator.hasNext()) {
            Object next = iterator.next();
            System.out.println(next);
            // ClassCastException이 발생하지만
            // 제네릭이 없어 컴파일 타임에 잡을 수가 없어진다
            try {
                Stamp stamp = (Stamp) next;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }

        // 컴파일은 되지만 오류가 발생
        List<String> strings = new ArrayList<>();
        unsafeAdd(strings , Integer.valueOf(42));
        try {
            String s = strings.get(0);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    private static void unsafeAdd(List list, Object o) {
        list.add(o);
    }
}
