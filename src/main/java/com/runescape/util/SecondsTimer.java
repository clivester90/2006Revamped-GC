package com.runescape.util;

public class SecondsTimer extends Stopwatch {
	private int seconds;

	public SecondsTimer(int seconds) {
		start(seconds);
	}

	public SecondsTimer() {}

	public void start(int seconds) {
		this.seconds = seconds;
		reset();
	}

	public void stop() {
		seconds = 0;
	}

	public boolean finished() {
		return elapsed(seconds * 1000);
	}

	public int secondsRemaining() {
		return seconds - secondsElapsed();
	}

	private int secondsElapsed() {
		return (int) elapsed() / 1000;
	}
}
