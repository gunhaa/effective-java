package org.example.utils;

public class Timer {

    private long startTime;
    private long endTime;
    private String name;

    public Timer(String name) {
        this.name = name;
    }

    public void start() {
        this.startTime = System.nanoTime();
    }

    public void end() {
        this.endTime = System.nanoTime();
    }

    public void getDuration() {
        double durationSeconds = ((double) (this.endTime - this.startTime)) / 1_000_000_000.0;
        System.out.println(this.name + " " + durationSeconds+" seconds");
    }
}
