package com.runescape.core.sound;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;

public final class Sounds {

	private Sounds() {
		aSoundSampleArray329 = new SoundSample[10];
	}

	public static void unpack(ByteBuffer byteBuffer) {
		aByteArray327 = new byte[0x6baa8];
		aByteBuffer_328 = new ByteBuffer(aByteArray327);
		SoundSample.method166();
		do {
			int j = byteBuffer.readUnsignedWord();
			if (j == 65535) {
				return;
			}
			aSoundsArray325s[j] = new Sounds();
			aSoundsArray325s[j].method242(byteBuffer);
			anIntArray326[j] = aSoundsArray325s[j].method243();
		} while (true);
	}

	public static ByteBuffer method241(int i, int j) {
		if (aSoundsArray325s[j] != null) {
			Sounds sounds = aSoundsArray325s[j];
			return sounds.method244(i);
		} else {
			return null;
		}
	}

	private void method242(ByteBuffer byteBuffer) {
		for (int i = 0; i < 10; i++) {
			int j = byteBuffer.readUnsignedByte();
			if (j != 0) {
				byteBuffer.currentOffset--;
				aSoundSampleArray329[i] = new SoundSample();
				aSoundSampleArray329[i].method169(byteBuffer);
			}
		}
		anInt330 = byteBuffer.readUnsignedWord();
		anInt331 = byteBuffer.readUnsignedWord();
	}

	private int method243() {
		int j = 0x98967f;
		for (int k = 0; k < 10; k++) {
			if (aSoundSampleArray329[k] != null && aSoundSampleArray329[k].remaining / 20 < j) {
				j = aSoundSampleArray329[k].remaining / 20;
			}
		}

		if (anInt330 < anInt331 && anInt330 / 20 < j) {
			j = anInt330 / 20;
		}
		if (j == 0x98967f || j == 0) {
			return 0;
		}
		for (int l = 0; l < 10; l++) {
			if (aSoundSampleArray329[l] != null) {
				aSoundSampleArray329[l].remaining -= j * 20;
			}
		}

		if (anInt330 < anInt331) {
			anInt330 -= j * 20;
			anInt331 -= j * 20;
		}
		return j;
	}

	private ByteBuffer method244(int i) {
		int k = method245(i);
		aByteBuffer_328.currentOffset = 0;
		aByteBuffer_328.writeDWord(0x52494646);
		aByteBuffer_328.method403(36 + k);
		aByteBuffer_328.writeDWord(0x57415645);
		aByteBuffer_328.writeDWord(0x666d7420);
		aByteBuffer_328.method403(16);
		aByteBuffer_328.method400(1);
		aByteBuffer_328.method400(1);
		aByteBuffer_328.method403(22050);
		aByteBuffer_328.method403(22050);
		aByteBuffer_328.method400(1);
		aByteBuffer_328.method400(8);
		aByteBuffer_328.writeDWord(0x64617461);
		aByteBuffer_328.method403(k);
		aByteBuffer_328.currentOffset += k;
		return aByteBuffer_328;
	}

	private int method245(int i) {
		int j = 0;
		for (int k = 0; k < 10; k++) {
			if (aSoundSampleArray329[k] != null && aSoundSampleArray329[k].position + aSoundSampleArray329[k].remaining > j) {
				j = aSoundSampleArray329[k].position + aSoundSampleArray329[k].remaining;
			}
		}

		if (j == 0) {
			return 0;
		}
		int l = 22050 * j / 1000;
		int i1 = 22050 * anInt330 / 1000;
		int j1 = 22050 * anInt331 / 1000;
		if (i1 < 0 || i1 > l || j1 < 0 || j1 > l || i1 >= j1) {
			i = 0;
		}
		int k1 = l + (j1 - i1) * (i - 1);
		for (int l1 = 44; l1 < k1 + 44; l1++) {
			aByteArray327[l1] = -128;
		}

		for (int i2 = 0; i2 < 10; i2++) {
			if (aSoundSampleArray329[i2] != null) {
				int j2 = aSoundSampleArray329[i2].position * 22050 / 1000;
				int i3 = aSoundSampleArray329[i2].remaining * 22050 / 1000;
				int[] ai = aSoundSampleArray329[i2].method167(j2, aSoundSampleArray329[i2].position);
				for (int l3 = 0; l3 < j2; l3++) {
					aByteArray327[l3 + i3 + 44] += (byte) (ai[l3] >> 8);
				}

			}
		}

		if (i > 1) {
			i1 += 44;
			j1 += 44;
			l += 44;
			int k2 = (k1 += 44) - l;
			for (int j3 = l - 1; j3 >= j1; j3--) {
				aByteArray327[j3 + k2] = aByteArray327[j3];
			}

			for (int k3 = 1; k3 < i; k3++) {
				int l2 = (j1 - i1) * k3;
				System.arraycopy(aByteArray327, i1, aByteArray327, i1 + l2, j1 - i1);

			}

			k1 -= 44;
		}
		return k1;
	}

	private static final Sounds[] aSoundsArray325s = new Sounds[5000];
	public static final int[] anIntArray326 = new int[5000];
	private static byte[] aByteArray327;
	private static ByteBuffer aByteBuffer_328;
	private final SoundSample[] aSoundSampleArray329;
	private int anInt330;
	private int anInt331;

}
