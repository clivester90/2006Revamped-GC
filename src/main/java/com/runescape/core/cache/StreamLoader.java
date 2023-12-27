package com.runescape.core.cache;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class StreamLoader {

	public StreamLoader(byte[] abyte0) {
		ByteBuffer byteBuffer = new ByteBuffer(abyte0);
		int i = byteBuffer.read3Bytes();
		int j = byteBuffer.read3Bytes();
		if (j != i) {
			byte[] abyte1 = new byte[i];
			BZip2InputStream.method225(abyte1, i, abyte0, j, 6);
			aByteArray726 = abyte1;
			byteBuffer = new ByteBuffer(aByteArray726);
			aBoolean732 = true;
		} else {
			aByteArray726 = abyte0;
			aBoolean732 = false;
		}
		dataSize = byteBuffer.readUnsignedWord();
		anIntArray728 = new int[dataSize];
		anIntArray729 = new int[dataSize];
		anIntArray730 = new int[dataSize];
		anIntArray731 = new int[dataSize];
		int k = byteBuffer.currentOffset + dataSize * 10;
		for (int l = 0; l < dataSize; l++) {
			anIntArray728[l] = byteBuffer.readDWord();
			anIntArray729[l] = byteBuffer.read3Bytes();
			anIntArray730[l] = byteBuffer.read3Bytes();
			anIntArray731[l] = k;
			k += anIntArray730[l];
		}
	}

	public byte[] getDataForName(String s) {
		byte[] abyte0 = null; // was a parameter
		int i = 0;
		s = s.toUpperCase();
		for (int j = 0; j < s.length(); j++) {
			i = i * 61 + s.charAt(j) - 32;
		}

		for (int k = 0; k < dataSize; k++) {
			if (anIntArray728[k] == i) {
				if (abyte0 == null) {
					abyte0 = new byte[anIntArray729[k]];
				}
				if (!aBoolean732) {
					BZip2InputStream.method225(abyte0, anIntArray729[k], aByteArray726, anIntArray730[k], anIntArray731[k]);
				} else {
					System.arraycopy(aByteArray726, anIntArray731[k], abyte0, 0, anIntArray729[k]);

				}
				return abyte0;
			}
		}

		return null;
	}

	private final byte[] aByteArray726;
	private final int dataSize;
	private final int[] anIntArray728;
	private final int[] anIntArray729;
	private final int[] anIntArray730;
	private final int[] anIntArray731;
	private final boolean aBoolean732;
}
