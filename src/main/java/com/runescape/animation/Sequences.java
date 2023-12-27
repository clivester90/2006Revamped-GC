package com.runescape.animation;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.world.model.frames.Frames;

public final class Sequences {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("seq.dat"));
		int length = byteBuffer.readUnsignedWord();
		if (anims == null) {
			anims = new Sequences[length];
		}
		for (int j = 0; j < length; j++) {
			if (anims[j] == null) {
				anims[j] = new Sequences();
			}
			anims[j].readValues(byteBuffer);
		}
	}

	public int getDuration(int i) {
		int j = duration[i];
		if (j == 0) {
			Frames frames = Frames.get(primary[i]);
			if (frames != null) {
				j = duration[i] = frames.anInt636;
			}
		}
		if (j == 0) {
			j = 1;
		}
		return j;
	}

	private void readValues(ByteBuffer byteBuffer) {
		do {
			int i = byteBuffer.readUnsignedByte();
			if (i == 0) {
				break;
			}
			if (i == 1) {
				length = byteBuffer.readUnsignedByte();
				primary = new int[length];
				secondary = new int[length];
				duration = new int[length];
				for (int j = 0; j < length; j++) {
					primary[j] = byteBuffer.readUnsignedWord();
					secondary[j] = byteBuffer.readUnsignedWord();
					if (secondary[j] == 65535) {
						secondary[j] = -1;
					}
					duration[j] = byteBuffer.readUnsignedWord();
				}

			} else if (i == 2) {
				padding = byteBuffer.readUnsignedWord();
			} else if (i == 3) {
				int k = byteBuffer.readUnsignedByte();
				vertices = new int[k + 1];
				for (int l = 0; l < k; l++) {
					vertices[l] = byteBuffer.readUnsignedByte();
				}

				vertices[k] = 0x98967f;
			} else if (i == 4) {
				allowsRotation = true;
			} else if (i == 5) {
				priority1 = byteBuffer.readUnsignedByte();
			} else if (i == 6) {
				shield = byteBuffer.readUnsignedWord();
			} else if (i == 7) {
				weapon = byteBuffer.readUnsignedWord();
			} else if (i == 8) {
				resetCycle = byteBuffer.readUnsignedByte();
			} else if (i == 9) {
				runFlag = byteBuffer.readUnsignedByte();
			} else if (i == 10) {
				walkFlag = byteBuffer.readUnsignedByte();
			} else if (i == 11) {
				delayType = byteBuffer.readUnsignedByte();
			} else if (i == 12) {
				byteBuffer.readDWord();
			} else {
				System.out.println("Error unrecognised seq config code: " + i);
			}
		} while (true);
		if (length == 0) {
			length = 1;
			primary = new int[1];
			primary[0] = -1;
			secondary = new int[1];
			secondary[0] = -1;
			duration = new int[1];
			duration[0] = -1;
		}
		if (runFlag == -1) {
			if (vertices != null) {
				runFlag = 2;
			} else {
				runFlag = 0;
			}
		}
		if (walkFlag == -1) {
			if (vertices != null) {
				walkFlag = 2;
				return;
			}
			walkFlag = 0;
		}
	}

	private Sequences() {
		padding = -1;
		allowsRotation = false;
		priority1 = 5;
		shield = -1;
		weapon = -1;
		resetCycle = 99;
		runFlag = -1;
		walkFlag = -1;
		delayType = 2;
	}

	public static Sequences[] anims;
	public int length;
	public int[] primary;
	public int[] secondary;
	private int[] duration;
	public int padding;
	public int[] vertices;
	public boolean allowsRotation;
	public int priority1;
	public int shield;
	public int weapon;
	public int resetCycle;
	public int runFlag;
	public int walkFlag;
	public int delayType;
	public static int anInt367;
}
