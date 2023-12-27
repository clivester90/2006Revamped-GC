package com.runescape.world.varp;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;

public final class Varp {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("varp.dat"));
		anInt702 = 0;
		int cacheSize = byteBuffer.readUnsignedWord();
		if (cache == null) {
			cache = new Varp[cacheSize];
		}
		if (anIntArray703 == null) {
			anIntArray703 = new int[cacheSize];
		}
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null) {
				cache[j] = new Varp();
			}
			cache[j].readValues(byteBuffer, j);
		}
		if (byteBuffer.currentOffset != byteBuffer.buffer.length) {
			System.out.println("varptype load mismatch");
		}
	}

	private void readValues(ByteBuffer byteBuffer, int i) {
		do {
			int j = byteBuffer.readUnsignedByte();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				byteBuffer.readUnsignedByte();
			} else if (j == 2) {
				byteBuffer.readUnsignedByte();
			} else if (j == 3) {
				anIntArray703[anInt702++] = i;
			} else if (j == 4) {
			} else if (j == 5) {
				anInt709 = byteBuffer.readUnsignedWord();
			} else if (j == 6) {
			} else if (j == 7) {
				byteBuffer.readDWord();
			} else if (j == 8) {
				aBoolean713 = true;
			} else if (j == 10) {
				byteBuffer.readString();
			} else if (j == 11) {
				aBoolean713 = true;
			} else if (j == 12) {
				byteBuffer.readDWord();
			} else if (j == 13) {
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

	private Varp() {
		aBoolean713 = false;
	}

	public static Varp[] cache;
	private static int anInt702;
	private static int[] anIntArray703;
	public int anInt709;
	public boolean aBoolean713;

}
