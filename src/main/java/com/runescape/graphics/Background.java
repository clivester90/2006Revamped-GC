package com.runescape.graphics;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.graphics.DrawingArea;

public final class Background extends DrawingArea {

	public Background(StreamLoader streamLoader, String s, int i) {
		ByteBuffer dataStream = new ByteBuffer(streamLoader.getDataForName(s + ".dat"));
		ByteBuffer infoStream = new ByteBuffer(streamLoader.getDataForName("index.dat"));
		infoStream.currentOffset = dataStream.readUnsignedWord();
		cropWidth = infoStream.readUnsignedWord();
		cropHeight = infoStream.readUnsignedWord();
		int j = infoStream.readUnsignedByte();
		palette = new int[j];
		for (int k = 0; k < j - 1; k++) {
			palette[k + 1] = infoStream.read3Bytes();
		}

		for (int l = 0; l < i; l++) {
			infoStream.currentOffset += 2;
			dataStream.currentOffset += infoStream.readUnsignedWord() * infoStream.readUnsignedWord();
			infoStream.currentOffset++;
		}

		offsetX = infoStream.readUnsignedByte();
		offsetY = infoStream.readUnsignedByte();
		myWidth = infoStream.readUnsignedWord();
		myHeight = infoStream.readUnsignedWord();
		int i1 = infoStream.readUnsignedByte();
		int j1 = myWidth * myHeight;
		data = new byte[j1];
		if (i1 == 0) {
			for (int k1 = 0; k1 < j1; k1++) {
				data[k1] = dataStream.readSignedByte();
			}

			return;
		}
		if (i1 == 1) {
			for (int l1 = 0; l1 < myWidth; l1++) {
				for (int i2 = 0; i2 < myHeight; i2++) {
					data[l1 + i2 * myWidth] = dataStream.readSignedByte();
				}

			}

		}
	}

	public void shrink() {
		cropWidth /= 2;
		cropHeight /= 2;
		byte[] abyte0 = new byte[cropWidth * cropHeight];
		int i = 0;
		for (int j = 0; j < myHeight; j++) {
			for (int k = 0; k < myWidth; k++) {
				abyte0[(k + offsetX >> 1) + (j + offsetY >> 1) * cropWidth] = data[i++];
			}

		}

		data = abyte0;
		myWidth = cropWidth;
		myHeight = cropHeight;
		offsetX = 0;
		offsetY = 0;
	}

	public void crop() {
		if (myWidth == cropWidth && myHeight == cropHeight) {
			return;
		}
		byte[] abyte0 = new byte[cropWidth * cropHeight];
		int i = 0;
		for (int j = 0; j < myHeight; j++) {
			for (int k = 0; k < myWidth; k++) {
				abyte0[k + offsetX + (j + offsetY) * cropWidth] = data[i++];
			}

		}

		data = abyte0;
		myWidth = cropWidth;
		myHeight = cropHeight;
		offsetX = 0;
		offsetY = 0;
	}

	public void flipHorizontally() {
		byte[] abyte0 = new byte[myWidth * myHeight];
		int j = 0;
		for (int k = 0; k < myHeight; k++) {
			for (int l = myWidth - 1; l >= 0; l--) {
				abyte0[j++] = data[l + k * myWidth];
			}

		}

		data = abyte0;
		offsetX = cropWidth - myWidth - offsetX;
	}

	public void flipVertically() {
		byte[] abyte0 = new byte[myWidth * myHeight];
		int i = 0;
		for (int j = myHeight - 1; j >= 0; j--) {
			for (int k = 0; k < myWidth; k++) {
				abyte0[i++] = data[k + j * myWidth];
			}

		}

		data = abyte0;
		offsetY = cropHeight - myHeight - offsetY;
	}

	public void translateRGB(int i, int j, int k) {
		for (int i1 = 0; i1 < palette.length; i1++) {
			int j1 = palette[i1] >> 16 & 0xff;
			j1 += i;
			if (j1 < 0) {
				j1 = 0;
			} else if (j1 > 255) {
				j1 = 255;
			}
			int k1 = palette[i1] >> 8 & 0xff;
			k1 += j;
			if (k1 < 0) {
				k1 = 0;
			} else if (k1 > 255) {
				k1 = 255;
			}
			int l1 = palette[i1] & 0xff;
			l1 += k;
			if (l1 < 0) {
				l1 = 0;
			} else if (l1 > 255) {
				l1 = 255;
			}
			palette[i1] = (j1 << 16) + (k1 << 8) + l1;
		}
	}

	public void drawBackground(int i, int k) {
		i += offsetX;
		k += offsetY;
		int l = i + k * DrawingArea.width;
		int i1 = 0;
		int backgroundHeight = myHeight;
		int backgroundWidth = myWidth;
		int l1 = DrawingArea.width - backgroundWidth;
		int i2 = 0;
		if (k < DrawingArea.topY) {
			int j2 = DrawingArea.topY - k;
			backgroundHeight -= j2;
			k = DrawingArea.topY;
			i1 += j2 * backgroundWidth;
			l += j2 * DrawingArea.width;
		}
		if (k + backgroundHeight > DrawingArea.bottomY) {
			backgroundHeight -= k + backgroundHeight - DrawingArea.bottomY;
		}
		if (i < DrawingArea.topX) {
			int k2 = DrawingArea.topX - i;
			backgroundWidth -= k2;
			i = DrawingArea.topX;
			i1 += k2;
			l += k2;
			i2 += k2;
			l1 += k2;
		}
		if (i + backgroundWidth > DrawingArea.bottomX) {
			int l2 = i + backgroundWidth - DrawingArea.bottomX;
			backgroundWidth -= l2;
			i2 += l2;
			l1 += l2;
		}
		if (!(backgroundWidth <= 0 || backgroundHeight <= 0)) {
			arraycopyIgnore0(backgroundHeight, DrawingArea.pixels, data, l1, l, backgroundWidth, i1, palette, i2);
		}
	}

	private void arraycopyIgnore0(int i, int[] ai, byte[] abyte0, int j, int k, int l, int i1, int[] ai1, int j1) {
		int k1 = -(l >> 2);
		l = -(l & 3);
		for (int l1 = -i; l1 < 0; l1++) {
			for (int i2 = k1; i2 < 0; i2++) {
				byte byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
				byte1 = abyte0[i1++];
				if (byte1 != 0) {
					ai[k++] = ai1[byte1 & 0xff];
				} else {
					k++;
				}
			}

			for (int j2 = l; j2 < 0; j2++) {
				byte byte2 = abyte0[i1++];
				if (byte2 != 0) {
					ai[k++] = ai1[byte2 & 0xff];
				} else {
					k++;
				}
			}

			k += j;
			i1 += j1;
		}

	}

	public byte[] data;
	public final int[] palette;
	public int myWidth;
	public int myHeight;
	public int offsetX;
	public int offsetY;
	public int cropWidth;
	private int cropHeight;
}
