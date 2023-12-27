package com.runescape.world.model.frames;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import com.runescape.core.cache.ByteBuffer;

public final class Frames {

	public static void method528(int i) {
		aFramesArray635 = new Frames[i + 1];
		aBooleanArray643 = new boolean[i + 1];
		for (int j = 0; j < i + 1; j++) {
			aBooleanArray643[j] = true;
		}

	}

	public static void load(byte[] abyte0) {
		ByteBuffer byteBuffer = new ByteBuffer(abyte0);
		byteBuffer.currentOffset = abyte0.length - 8;
		int i = byteBuffer.readUnsignedWord();
		int j = byteBuffer.readUnsignedWord();
		int k = byteBuffer.readUnsignedWord();
		int l = byteBuffer.readUnsignedWord();
		int i1 = 0;
		ByteBuffer byteBuffer_1 = new ByteBuffer(abyte0);
		byteBuffer_1.currentOffset = i1;
		i1 += i + 2;
		ByteBuffer byteBuffer_2 = new ByteBuffer(abyte0);
		byteBuffer_2.currentOffset = i1;
		i1 += j;
		ByteBuffer byteBuffer_3 = new ByteBuffer(abyte0);
		byteBuffer_3.currentOffset = i1;
		i1 += k;
		ByteBuffer byteBuffer_4 = new ByteBuffer(abyte0);
		byteBuffer_4.currentOffset = i1;
		i1 += l;
		ByteBuffer byteBuffer_5 = new ByteBuffer(abyte0);
		byteBuffer_5.currentOffset = i1;
		FramesBase framesBase = new FramesBase(byteBuffer_5);
		int k1 = byteBuffer_1.readUnsignedWord();
		int[] ai = new int[500];
		int[] ai1 = new int[500];
		int[] ai2 = new int[500];
		int[] ai3 = new int[500];
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = byteBuffer_1.readUnsignedWord();
			Frames frames = aFramesArray635[i2] = new Frames();
			frames.anInt636 = byteBuffer_4.readUnsignedByte();
			frames.aFramesBase_637 = framesBase;
			int j2 = byteBuffer_1.readUnsignedByte();
			int k2 = -1;
			int l2 = 0;
			for (int i3 = 0; i3 < j2; i3++) {
				int j3 = byteBuffer_2.readUnsignedByte();
				if (j3 > 0) {
					if (framesBase.opcode[i3] != 0) {
						for (int l3 = i3 - 1; l3 > k2; l3--) {
							if (framesBase.opcode[l3] != 0) {
								continue;
							}
							ai[l2] = l3;
							ai1[l2] = 0;
							ai2[l2] = 0;
							ai3[l2] = 0;
							l2++;
							break;
						}

					}
					ai[l2] = i3;
					char c = '\0';
					if (framesBase.opcode[i3] == 3) {
						c = '\200';
					}
					if ((j3 & 1) != 0) {
						ai1[l2] = byteBuffer_3.method421();
					} else {
						ai1[l2] = c;
					}
					if ((j3 & 2) != 0) {
						ai2[l2] = byteBuffer_3.method421();
					} else {
						ai2[l2] = c;
					}
					if ((j3 & 4) != 0) {
						ai3[l2] = byteBuffer_3.method421();
					} else {
						ai3[l2] = c;
					}
					k2 = i3;
					l2++;
					if (framesBase.opcode[i3] == 5) {
						aBooleanArray643[i2] = false;
					}
				}
			}

			frames.anInt638 = l2;
			frames.anIntArray639 = new int[l2];
			frames.anIntArray640 = new int[l2];
			frames.anIntArray641 = new int[l2];
			frames.anIntArray642 = new int[l2];
			for (int k3 = 0; k3 < l2; k3++) {
				frames.anIntArray639[k3] = ai[k3];
				frames.anIntArray640[k3] = ai1[k3];
				frames.anIntArray641[k3] = ai2[k3];
				frames.anIntArray642[k3] = ai3[k3];
			}

		}

	}

	public static void nullLoader() {
		aFramesArray635 = null;
	}

	public static Frames get(int j) {
		if (aFramesArray635 == null) {
			return null;
		} else {
			return aFramesArray635[j];
		}
	}

	public static boolean method532(int i) {
		return i == -1;
	}

	private Frames() {
	}

	private static Frames[] aFramesArray635;
	public int anInt636;
	public FramesBase aFramesBase_637;
	public int anInt638;
	public int[] anIntArray639;
	public int[] anIntArray640;
	public int[] anIntArray641;
	public int[] anIntArray642;
	private static boolean[] aBooleanArray643;

}
