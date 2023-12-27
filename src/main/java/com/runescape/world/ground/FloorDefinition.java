package com.runescape.world.ground;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class FloorDefinition {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("flo.dat"));
		int cacheSize = byteBuffer.readUnsignedWord();
		if (cache == null) {
			cache = new FloorDefinition[cacheSize];
		}
		for (int j = 0; j < cacheSize; j++) {
			if (cache[j] == null) {
				cache[j] = new FloorDefinition();
			}
			cache[j].readValues(byteBuffer);
		}
	}
	
	public static String getTodaysDate() {
		Calendar date = new GregorianCalendar();
		return date.get(Calendar.DAY_OF_MONTH) + "."+ (date.get(Calendar.MONTH) + 1) + "." + date.get(Calendar.YEAR);
	}


	private void readValues(ByteBuffer byteBuffer) {
		do {
			int i = byteBuffer.readUnsignedByte();
			if (i == 0) {
				return;
			} else if (i == 1) {
				rgb = byteBuffer.read3Bytes();
				Calendar date = new GregorianCalendar();
				if ((date.get(Calendar.MONTH) + 1) == 12) {
					rgb = 0xffffff;
				}
				rgbToHsl(rgb);
			} else if (i == 2) {
				texture = byteBuffer.readUnsignedByte();
			} else if (i == 3) {
			} else if (i == 5) {
				occlude = false;
			} else if (i == 6) {
				byteBuffer.readString();
			} else if (i == 7) {
				int j = hue;
				int k = saturation;
				int l = luminance;
				int i1 = anotherHue;
				int j1 = byteBuffer.read3Bytes();
				rgbToHsl(j1);
				hue = j;
				saturation = k;
				luminance = l;
				anotherHue = i1;
				blendHueMultiplier = i1;
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	private void rgbToHsl(int colour) {
		double d = (colour >> 16 & 0xff) / 256D;
		double d1 = (colour >> 8 & 0xff) / 256D;
		double d2 = (colour & 0xff) / 256D;
		double d3 = d;
		if (d1 < d3) {
			d3 = d1;
		}
		if (d2 < d3) {
			d3 = d2;
		}
		double d4 = d;
		if (d1 > d4) {
			d4 = d1;
		}
		if (d2 > d4) {
			d4 = d2;
		}
		double d5 = 0.0D;
		double d6 = 0.0D;
		double d7 = (d3 + d4) / 2D;
		if (d3 != d4) {
			if (d7 < 0.5D) {
				d6 = (d4 - d3) / (d4 + d3);
			}
			if (d7 >= 0.5D) {
				d6 = (d4 - d3) / (2D - d4 - d3);
			}
			if (d == d4) {
				d5 = (d1 - d2) / (d4 - d3);
			} else if (d1 == d4) {
				d5 = 2D + (d2 - d) / (d4 - d3);
			} else if (d2 == d4) {
				d5 = 4D + (d - d1) / (d4 - d3);
			}
		}
		d5 /= 6D;
		hue = (int) (d5 * 256D);
		saturation = (int) (d6 * 256D);
		luminance = (int) (d7 * 256D);
		if (saturation < 0) {
			saturation = 0;
		} else if (saturation > 255) {
			saturation = 255;
		}
		if (luminance < 0) {
			luminance = 0;
		} else if (luminance > 255) {
			luminance = 255;
		}
		if (d7 > 0.5D) {
			blendHueMultiplier = (int) ((1.0D - d7) * d6 * 512D);
		} else {
			blendHueMultiplier = (int) (d7 * d6 * 512D);
		}
		if (blendHueMultiplier < 1) {
			blendHueMultiplier = 1;
		}
		anotherHue = (int) (d5 * blendHueMultiplier);
		int k = hue + (int) (Math.random() * 16D) - 8;
		if (k < 0) {
			k = 0;
		} else if (k > 255) {
			k = 255;
		}
		int l = saturation + (int) (Math.random() * 48D) - 24;
		if (l < 0) {
			l = 0;
		} else if (l > 255) {
			l = 255;
		}
		int i1 = luminance + (int) (Math.random() * 48D) - 24;
		if (i1 < 0) {
			i1 = 0;
		} else if (i1 > 255) {
			i1 = 255;
		}
		hsl16 = hslToRgb(k, l, i1);
	}

	private int hslToRgb(int i, int j, int k) {
		if (k > 179) {
			j /= 2;
		}
		if (k > 192) {
			j /= 2;
		}
		if (k > 217) {
			j /= 2;
		}
		if (k > 243) {
			j /= 2;
		}
		return (i / 4 << 10) + (j / 32 << 7) + k / 2;
	}

	private FloorDefinition() {
		texture = -1;
		occlude = true;
	}

	public static FloorDefinition[] cache;
	public int rgb;
	public int texture;
	public boolean occlude;
	public int hue;
	public int saturation;
	public int luminance;
	public int anotherHue;
	public int blendHueMultiplier;
	public int hsl16;
	
}
