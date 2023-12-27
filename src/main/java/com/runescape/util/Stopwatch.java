package com.runescape.util;

public class Stopwatch {

    private long time = System.currentTimeMillis();

    protected Stopwatch() {
        time = 0;
    }

    public Stopwatch headStart(long startAt) {
        time = System.currentTimeMillis() - startAt;
        return this;
    }

    public Stopwatch reset(long i) {
        time = i;
        return this;
    }

    protected Stopwatch reset() {
        time = System.currentTimeMillis();
        return this;
    }

    protected long elapsed() {
        return System.currentTimeMillis() - time;
    }

    protected boolean elapsed(long time) {
        return elapsed() >= time;
    }

    public long getTime() {
        return time;
    }
}