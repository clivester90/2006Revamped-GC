package com.runescape.core.sound;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;

public class SoundSample {

	public static void method166() {
		noise = new int[32768];
		for (int i = 0; i < 32768; i++) {
			if (Math.random() > 0.5D) {
				noise[i] = 1;
			} else {
				noise[i] = -1;
			}
		}

		sine = new int[32768];
		for (int j = 0; j < 32768; j++) {
			sine[j] = (int) (Math.sin(j / 5215.1903000000002D) * 16384D);
		}

		encoded = new int[0x35d54];
	}

	public int[] method167(int i, int j) {
		for (int k = 0; k < i; k++) {
			encoded[k] = 0;
		}

		if (j < 10) {
			return encoded;
		}
		double d = i / (j + 0.0D);
		aSoundEnvelope_98.resetValues();
		aSoundEnvelope_99.resetValues();
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		if (aSoundEnvelope_100 != null) {
			aSoundEnvelope_100.resetValues();
			aSoundEnvelope_101.resetValues();
			l = (int) ((aSoundEnvelope_100.anInt539 - aSoundEnvelope_100.anInt538) * 32.768000000000001D / d);
			i1 = (int) (aSoundEnvelope_100.anInt538 * 32.768000000000001D / d);
		}
		int k1 = 0;
		int l1 = 0;
		int i2 = 0;
		if (aSoundEnvelope_102 != null) {
			aSoundEnvelope_102.resetValues();
			aSoundEnvelope_103.resetValues();
			k1 = (int) ((aSoundEnvelope_102.anInt539 - aSoundEnvelope_102.anInt538) * 32.768000000000001D / d);
			l1 = (int) (aSoundEnvelope_102.anInt538 * 32.768000000000001D / d);
		}
		for (int j2 = 0; j2 < 5; j2++) {
			if (anIntArray106[j2] != 0) {
				anIntArray118[j2] = 0;
				anIntArray119[j2] = (int) (anIntArray108[j2] * d);
				anIntArray120[j2] = (anIntArray106[j2] << 14) / 100;
				anIntArray121[j2] = (int) ((aSoundEnvelope_98.anInt539 - aSoundEnvelope_98.anInt538) * 32.768000000000001D * Math.pow(1.0057929410678534D, anIntArray107[j2]) / d);
				anIntArray122[j2] = (int) (aSoundEnvelope_98.anInt538 * 32.768000000000001D / d);
			}
		}

		for (int k2 = 0; k2 < i; k2++) {
			int l2 = aSoundEnvelope_98.method328(i);
			int j4 = aSoundEnvelope_99.method328(i);
			if (aSoundEnvelope_100 != null) {
				int j5 = aSoundEnvelope_100.method328(i);
				int j6 = aSoundEnvelope_101.method328(i);
				l2 += method168(j6, j1, aSoundEnvelope_100.anInt540) >> 1;
				j1 += (j5 * l >> 16) + i1;
			}
			if (aSoundEnvelope_102 != null) {
				int k5 = aSoundEnvelope_102.method328(i);
				int k6 = aSoundEnvelope_103.method328(i);
				j4 = j4 * ((method168(k6, i2, aSoundEnvelope_102.anInt540) >> 1) + 32768) >> 15;
				i2 += (k5 * k1 >> 16) + l1;
			}
			for (int l5 = 0; l5 < 5; l5++) {
				if (anIntArray106[l5] != 0) {
					int l6 = k2 + anIntArray119[l5];
					if (l6 < i) {
						encoded[l6] += method168(j4 * anIntArray120[l5] >> 15, anIntArray118[l5], aSoundEnvelope_98.anInt540);
						anIntArray118[l5] += (l2 * anIntArray121[l5] >> 16) + anIntArray122[l5];
					}
				}
			}

		}

		if (aSoundEnvelope_104 != null) {
			aSoundEnvelope_104.resetValues();
			aSoundEnvelope_105.resetValues();
			int i3 = 0;
			boolean flag1 = true;
			for (int i7 = 0; i7 < i; i7++) {
				int k7 = aSoundEnvelope_104.method328(i);
				int i8 = aSoundEnvelope_105.method328(i);
				int k4;
				if (flag1) {
					k4 = aSoundEnvelope_104.anInt538 + ((aSoundEnvelope_104.anInt539 - aSoundEnvelope_104.anInt538) * k7 >> 8);
				} else {
					k4 = aSoundEnvelope_104.anInt538 + ((aSoundEnvelope_104.anInt539 - aSoundEnvelope_104.anInt538) * i8 >> 8);
				}
				if ((i3 += 256) >= k4) {
					i3 = 0;
					flag1 = !flag1;
				}
				if (flag1) {
					encoded[i7] = 0;
				}
			}

		}
		if (anInt109 > 0 && anInt110 > 0) {
			int j3 = (int) (anInt109 * d);
			for (int l4 = j3; l4 < i; l4++) {
				encoded[l4] += encoded[l4 - j3] * anInt110 / 100;
			}

		}
		if (soundFilter.anIntArray665[0] > 0 || soundFilter.anIntArray665[1] > 0) {
			soundEnvelope.resetValues();
			int k3 = soundEnvelope.method328(i + 1);
			int i5 = soundFilter.method544(0, k3 / 65536F);
			int i6 = soundFilter.method544(1, k3 / 65536F);
			if (i >= i5 + i6) {
				int j7 = 0;
				int l7 = i6;
				if (l7 > i - i5) {
					l7 = i - i5;
				}
				for (; j7 < l7; j7++) {
					int j8 = (int) ((long) encoded[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
					for (int k8 = 0; k8 < i5; k8++) {
						j8 += (int) ((long) encoded[j7 + i5 - 1 - k8] * (long) SoundFilter.anIntArrayArray670[0][k8] >> 16);
					}

					for (int j9 = 0; j9 < j7; j9++) {
						j8 -= (int) ((long) encoded[j7 - 1 - j9] * (long) SoundFilter.anIntArrayArray670[1][j9] >> 16);
					}

					encoded[j7] = j8;
					k3 = soundEnvelope.method328(i + 1);
				}

				char c = '\200';
				l7 = c;
				do {
					if (l7 > i - i5) {
						l7 = i - i5;
					}
					for (; j7 < l7; j7++) {
						int l8 = (int) ((long) encoded[j7 + i5] * (long) SoundFilter.anInt672 >> 16);
						for (int k9 = 0; k9 < i5; k9++) {
							l8 += (int) ((long) encoded[j7 + i5 - 1 - k9] * (long) SoundFilter.anIntArrayArray670[0][k9] >> 16);
						}

						for (int i10 = 0; i10 < i6; i10++) {
							l8 -= (int) ((long) encoded[j7 - 1 - i10] * (long) SoundFilter.anIntArrayArray670[1][i10] >> 16);
						}

						encoded[j7] = l8;
						k3 = soundEnvelope.method328(i + 1);
					}

					if (j7 >= i - i5) {
						break;
					}
					i5 = soundFilter.method544(0, k3 / 65536F);
					i6 = soundFilter.method544(1, k3 / 65536F);
					l7 += c;
				} while (true);
				for (; j7 < i; j7++) {
					int i9 = 0;
					for (int l9 = j7 + i5 - i; l9 < i5; l9++) {
						i9 += (int) ((long) encoded[j7 + i5 - 1 - l9] * (long) SoundFilter.anIntArrayArray670[0][l9] >> 16);
					}

					for (int j10 = 0; j10 < i6; j10++) {
						i9 -= (int) ((long) encoded[j7 - 1 - j10] * (long) SoundFilter.anIntArrayArray670[1][j10] >> 16);
					}

					encoded[j7] = i9;
					soundEnvelope.method328(i + 1);
				}

			}
		}
		for (int i4 = 0; i4 < i; i4++) {
			if (encoded[i4] < -32768) {
				encoded[i4] = -32768;
			}
			if (encoded[i4] > 32767) {
				encoded[i4] = 32767;
			}
		}

		return encoded;
	}

	private int method168(int i, int k, int l) {
		if (l == 1) {
			if ((k & 0x7fff) < 16384) {
				return i;
			} else {
				return -i;
			}
		}
		if (l == 2) {
			return sine[k & 0x7fff] * i >> 14;
		}
		if (l == 3) {
			return ((k & 0x7fff) * i >> 14) - i;
		}
		if (l == 4) {
			return noise[k / 2607 & 0x7fff] * i;
		} else {
			return 0;
		}
	}

	public void method169(ByteBuffer byteBuffer) {
		aSoundEnvelope_98 = new SoundEnvelope();
		aSoundEnvelope_98.method325(byteBuffer);
		aSoundEnvelope_99 = new SoundEnvelope();
		aSoundEnvelope_99.method325(byteBuffer);
		int i = byteBuffer.readUnsignedByte();
		if (i != 0) {
			byteBuffer.currentOffset--;
			aSoundEnvelope_100 = new SoundEnvelope();
			aSoundEnvelope_100.method325(byteBuffer);
			aSoundEnvelope_101 = new SoundEnvelope();
			aSoundEnvelope_101.method325(byteBuffer);
		}
		i = byteBuffer.readUnsignedByte();
		if (i != 0) {
			byteBuffer.currentOffset--;
			aSoundEnvelope_102 = new SoundEnvelope();
			aSoundEnvelope_102.method325(byteBuffer);
			aSoundEnvelope_103 = new SoundEnvelope();
			aSoundEnvelope_103.method325(byteBuffer);
		}
		i = byteBuffer.readUnsignedByte();
		if (i != 0) {
			byteBuffer.currentOffset--;
			aSoundEnvelope_104 = new SoundEnvelope();
			aSoundEnvelope_104.method325(byteBuffer);
			aSoundEnvelope_105 = new SoundEnvelope();
			aSoundEnvelope_105.method325(byteBuffer);
		}
		for (int j = 0; j < 10; j++) {
			int k = byteBuffer.method422();
			if (k == 0) {
				break;
			}
			anIntArray106[j] = k;
			anIntArray107[j] = byteBuffer.method421();
			anIntArray108[j] = byteBuffer.method422();
		}

		anInt109 = byteBuffer.method422();
		anInt110 = byteBuffer.method422();
		position = byteBuffer.readUnsignedWord();
		remaining = byteBuffer.readUnsignedWord();
		soundFilter = new SoundFilter();
		soundEnvelope = new SoundEnvelope();
		soundFilter.method545(byteBuffer, soundEnvelope);
	}

	public SoundSample() {
		anIntArray106 = new int[5];
		anIntArray107 = new int[5];
		anIntArray108 = new int[5];
		anInt110 = 100;
		position = 500;
	}

	private SoundEnvelope aSoundEnvelope_98;
	private SoundEnvelope aSoundEnvelope_99;
	private SoundEnvelope aSoundEnvelope_100;
	private SoundEnvelope aSoundEnvelope_101;
	private SoundEnvelope aSoundEnvelope_102;
	private SoundEnvelope aSoundEnvelope_103;
	private SoundEnvelope aSoundEnvelope_104;
	private SoundEnvelope aSoundEnvelope_105;
	private final int[] anIntArray106;
	private final int[] anIntArray107;
	private final int[] anIntArray108;
	private int anInt109;
	private int anInt110;
	private SoundFilter soundFilter;
	private SoundEnvelope soundEnvelope;
	public int position;
	public int remaining;
	private static int[] encoded;
	private static int[] noise;
	private static int[] sine;
	private static final int[] anIntArray118 = new int[5];
	private static final int[] anIntArray119 = new int[5];
	private static final int[] anIntArray120 = new int[5];
	private static final int[] anIntArray121 = new int[5];
	private static final int[] anIntArray122 = new int[5];

}
