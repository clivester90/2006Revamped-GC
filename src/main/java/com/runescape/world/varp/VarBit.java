package com.runescape.world.varp;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;

public final class VarBit {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("varbit.dat"));
		int cacheSize = byteBuffer.readUnsignedWord();
		if (cache == null) {
			cache = new VarBit[cacheSize];
		}
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null) {
				cache[j] = new VarBit();
			}
			cache[j].readValues(byteBuffer);
			if (cache[j].aBoolean651) {
				Varp.cache[cache[j].setting].aBoolean713 = true;
			}
		}

		if (byteBuffer.currentOffset != byteBuffer.buffer.length) {
			System.out.println("varbit load mismatch");
		}
	}

	private void readValues(ByteBuffer byteBuffer) {
		do {
			int j = byteBuffer.readUnsignedByte();
			if (j == 0) {
				return;
			}
			if (j == 1) {
				setting = byteBuffer.readUnsignedWord();
				startbit = byteBuffer.readUnsignedByte();
				endbit = byteBuffer.readUnsignedByte();
			} else if (j == 10) {
				byteBuffer.readString();
			} else if (j == 2) {
				aBoolean651 = true;
			} else if (j == 3) {
				byteBuffer.readDWord();
			} else if (j == 4) {
				byteBuffer.readDWord();
			} else {
				System.out.println("Error unrecognised config code: " + j);
			}
		} while (true);
	}

	private VarBit() {
		aBoolean651 = false;
	}

	public static VarBit[] cache;
	public int setting;
	public int startbit;
	public int endbit;
	private boolean aBoolean651;
}
