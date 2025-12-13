package org.example.item34;

// enum은 JVM이 보장하는 싱글톤이다
// 사용처: 필요한 원소를 컴파일타임에 다 알 수 있는 상수 집합
public enum Planet {

    MERCURY(3.302e+23, 2.439e6),
    VENUS(4.869e+24, 6.052e6),
    EARTH(5.975e+24, 6.378e6),
    MARS(6.419e+23, 3.393e6),
    JUPITER(1.899e+27, 7.149e7),
    SATURN(5.685e+26, 6.027e7),
    URANUS(8.683e+25, 2.556e7),
    NEPTUNE(1.024e+26, 2.47e7);

    private final double mass;
    private final double radius;
    private final double surfaceGravity;

    private static final double G = 6.67300E-11;

    Planet(double mass, double radius) {
        this.mass = mass;
        this.radius = radius;
        this.surfaceGravity = G * mass / (radius * radius);
    }

    public double getMass() {
        return mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getSurfaceGravity() {
        return surfaceGravity;
    }

    public double surfaceWeight(double mass) {
        return mass * surfaceGravity; // F=ma
    }

    static void main() {
        double earthWeigh = 100;
        double mass = earthWeigh / Planet.EARTH.getSurfaceGravity();
        for (Planet p : Planet.values()) {
            System.out.printf("%s에서의 무게는 %f이다. %n", p, p.surfaceWeight(mass));
        }
    }
}
