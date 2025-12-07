package org.example.item33;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// 타입 안전 이종 콘테이너를 고려하라
public class Main {
    private static class Favorites {
        private Map<Class<?>, Object> favorites = new HashMap<>();

        public <T> void putFavorite(Class<T> type, T instance) {
            favorites.put(Objects.requireNonNull(type), instance);
        }

        public <T> T getFavorite(Class<T> type) {
            return type.cast(favorites.get(type));
        }
    }

    static void main() {
        Favorites f = new Favorites();

        f.putFavorite(String.class, "Python");
        f.putFavorite(Integer.class, 0xcafebabe);
        f.putFavorite(Class.class, Favorites.class);
        f.putFavorite(String.class, "Java");

        String favoriteString = f.getFavorite(String.class);
        Integer favoriteInteger = f.getFavorite(Integer.class);
        Class<?> favoriteClass = f.getFavorite(Class.class);

        System.out.printf("%s %x %s%n", favoriteString, favoriteInteger, favoriteClass.getName());
    }
}
