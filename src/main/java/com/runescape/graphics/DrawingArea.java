package com.runescape.graphics;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.node.NodeSub;

public class DrawingArea extends NodeSub {

	public static void initDrawingArea(int i, int j, int[] ai) {
		pixels = ai;
		width = j;
		height = i;
		setDrawingArea(i, 0, j, 0);
	}

	public static void defaultDrawingAreaSize() {
		topX = 0;
		topY = 0;
		bottomX = width;
		bottomY = height;
		centerX = bottomX - 1;
		centerY = bottomX / 2;
	}

	public static void setDrawingArea(int i, int j, int k, int l) {
		if (j < 0) {
			j = 0;
		}
		if (l < 0) {
			l = 0;
		}
		if (k > width) {
			k = width;
		}
		if (i > height) {
			i = height;
		}
		topX = j;
		topY = l;
		bottomX = k;
		bottomY = i;
		centerX = bottomX - 1;
		centerY = bottomX / 2;
		anInt1387 = bottomY / 2;
	}

	public static void setAllPixelsToZero() {
		int i = width * height;
		for (int j = 0; j < i; j++) {
			pixels[j] = 0;
		}
	}

	public static void drawBox(int topX, int topY, int width, int height, int rgbColour) {
		if (topX < DrawingArea.topX) {
			width -= DrawingArea.topX - topX;
			topX = DrawingArea.topX;
		}
		if (topY < DrawingArea.topY) {
			height -= DrawingArea.topY - topY;
			topY = DrawingArea.topY;
		}
		if (topX + width > bottomX)
			width = bottomX - topX;
		if (topY + height > bottomY)
			height = bottomY - topY;
		int leftOver = DrawingArea.width - width;
		int pixelIndex = topX + topY * DrawingArea.width;
		for (int rowIndex = 0; rowIndex < height; rowIndex++) {
			for (int columnIndex = 0; columnIndex < width; columnIndex++)
				pixels[pixelIndex++] = rgbColour;
			pixelIndex += leftOver;
		}
	}

	public static void drawBoxOutline(int leftX, int topY, int width, int height, int rgbColour){
		drawHorizontalLine2(leftX, topY, width, rgbColour);
		drawHorizontalLine2(leftX, (topY + height) - 1, width, rgbColour);
		drawVerticalLine2(leftX, topY, height, rgbColour);
		drawVerticalLine2((leftX + width) - 1, topY, height, rgbColour);
	}

	private static void drawVerticalLine2(int xPosition, int yPosition, int height, int rgbColour){
		if(xPosition < topX || xPosition >= bottomX)
			return;
		if(yPosition < topY){
			height -= topY - yPosition;
			yPosition = topY;
		}
		if(yPosition + height > bottomY)
			height = bottomY - yPosition;
		int pixelIndex = xPosition + yPosition * width;
		for(int rowIndex = 0; rowIndex < height; rowIndex++)
			pixels[pixelIndex + rowIndex * width] = rgbColour;
	}

	private static void drawHorizontalLine2(int xPosition, int yPosition, int width, int rgbColour){
		if(yPosition < topY || yPosition >= bottomY)
			return;
		if(xPosition < topX){
			width -= topX - xPosition;
			xPosition = topX;
		}
		if(xPosition + width > bottomX)
			width = bottomX - xPosition;
		int pixelIndex = xPosition + yPosition * DrawingArea.width;
		for(int i = 0; i < width; i++)
			pixels[pixelIndex + i] = rgbColour;
	}

	public static void method335(int i, int j, int k, int l, int i1, int k1) {
		if (k1 < topX) {
			k -= topX - k1;
			k1 = topX;
		}
		if (j < topY) {
			l -= topY - j;
			j = topY;
		}
		if (k1 + k > bottomX) {
			k = bottomX - k1;
		}
		if (j + l > bottomY) {
			l = bottomY - j;
		}
		int l1 = 256 - i1;
		int i2 = (i >> 16 & 0xff) * i1;
		int j2 = (i >> 8 & 0xff) * i1;
		int k2 = (i & 0xff) * i1;
		int k3 = width - k;
		int l3 = k1 + j * width;
		for (int i4 = 0; i4 < l; i4++) {
			for (int j4 = -k; j4 < 0; j4++) {
				int l2 = (pixels[l3] >> 16 & 0xff) * l1;
				int i3 = (pixels[l3] >> 8 & 0xff) * l1;
				int j3 = (pixels[l3] & 0xff) * l1;
				int k4 = (i2 + l2 >> 8 << 16) + (j2 + i3 >> 8 << 8) + (k2 + j3 >> 8);
				pixels[l3++] = k4;
			}

			l3 += k3;
		}
	}

	public static void method336(int i, int j, int l, int i1, int k) {
		if (k < topX) {
			i1 -= topX - k;
			k = topX;
		}
		if (j < topY) {
			i -= topY - j;
			j = topY;
		}
		if (k + i1 > bottomX) {
			i1 = bottomX - k;
		}
		if (j + i > bottomY) {
			i = bottomY - j;
		}
		int k1 = width - i1;
		int l1 = k + j * width;
		for (int i2 = -i; i2 < 0; i2++) {
			for (int j2 = -i1; j2 < 0; j2++) {
				pixels[l1++] = l;
			}

			l1 += k1;
		}

	}

	public static void drawPixels(int drawHeight, int yPosition, int xPositon, int color, int drawWidth) {
		if (xPositon < topX) {
			drawWidth -= topX - xPositon;
			xPositon = topX;
		}
		if (yPosition < topY) {
			drawHeight -= topY - yPosition;
			yPosition = topY;
		}
		if (xPositon + drawWidth > bottomX)
			drawWidth = bottomX - xPositon;
		if (yPosition + drawHeight > bottomY)
			drawHeight = bottomY - yPosition;
		int k1 = width - drawWidth;
		int l1 = xPositon + yPosition * width;
		for (int i2 = -drawHeight; i2 < 0; i2++) {
			for (int j2 = -drawWidth; j2 < 0; j2++)
				pixels[l1++] = color;

			l1 += k1;
		}

	}

	public static void fillPixels(int i1, int k, int l, int i, int j) {
		method339(i1, l, j, i);
		method339(i1 + k - 1, l, j, i);
		drawVerticalLineNew(i1, l, k, i);
		drawVerticalLineNew(i1, l, k, i + j - 1);
	}

	public static void drawAlphaBox(int x, int y, int lineWidth, int lineHeight, int color, int alpha) {// drawAlphaHorizontalLine
		if (y < topY) {
			if (y > (topY - lineHeight)) {
				lineHeight -= (topY - y);
				y += (topY - y);
			} else {
				return;
			}
		}
		if (y + lineHeight > bottomY) {
			lineHeight -= y + lineHeight - bottomY;
		}
		//if (y >= bottomY - lineHeight)
		//return;
		if (x < topX) {
			lineWidth -= topX - x;
			x = topX;
		}
		if (x + lineWidth > bottomX)
			lineWidth = bottomX - x;
		for(int yOff = 0; yOff < lineHeight; yOff++) {
			int i3 = x + (y + (yOff)) * width;
			for (int j3 = 0; j3 < lineWidth; j3++) {
				//int alpha2 = (lineWidth-j3) / (lineWidth/alpha);
				int j1 = 256 - alpha;//alpha2 is for gradient
				int k1 = (color >> 16 & 0xff) * alpha;
				int l1 = (color >> 8 & 0xff) * alpha;
				int i2 = (color & 0xff) * alpha;
				int j2 = (pixels[i3] >> 16 & 0xff) * j1;
				int k2 = (pixels[i3] >> 8 & 0xff) * j1;
				int l2 = (pixels[i3] & 0xff) * j1;
				int k3 = ((k1 + j2 >> 8) << 16) + ((l1 + k2 >> 8) << 8)
						+ (i2 + l2 >> 8);
				pixels[i3++] = k3;
			}
		}
	}

	public static void method338(int i, int j, int k, int l, int i1, int j1) {
		drawHorizontalLineNew(l, i1, i, k, j1);
		drawHorizontalLineNew(l, i1, i + j - 1, k, j1);
		if (j >= 3) {
			drawVerticalLineNew(l, j1, k, i + 1, j - 2);
			drawVerticalLineNew(l, j1 + i1 - 1, k, i + 1, j - 2);
		}
	}

	public static void method339(int i, int j, int k, int l) {
		if (i < topY || i >= bottomY) {
			return;
		}
		if (l < topX) {
			k -= topX - l;
			l = topX;
		}
		if (l + k > bottomX) {
			k = bottomX - l;
		}
		int i1 = l + i * width;
		for (int j1 = 0; j1 < k; j1++) {
			pixels[i1 + j1] = j;
		}

	}

	private static void drawHorizontalLineNew(int i, int j, int k, int l, int i1) {
		if (k < topY || k >= bottomY) {
			return;
		}
		if (i1 < topX) {
			j -= topX - i1;
			i1 = topX;
		}
		if (i1 + j > bottomX) {
			j = bottomX - i1;
		}
		int j1 = 256 - l;
		int k1 = (i >> 16 & 0xff) * l;
		int l1 = (i >> 8 & 0xff) * l;
		int i2 = (i & 0xff) * l;
		int i3 = i1 + k * width;
		for (int j3 = 0; j3 < j; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3++] = k3;
		}

	}

	public static void drawVerticalLineNew(int lineHeight, int j, int linewidth, int lineXPosition) {
		if (lineXPosition < topX || lineXPosition >= bottomX) {
			return;
		}
		if (lineHeight < topY) {
			linewidth -= topY - lineHeight;
			lineHeight = topY;
		}
		if (lineHeight + linewidth > bottomY) {
			linewidth = bottomY - lineHeight;
		}
		int j1 = lineXPosition + lineHeight * width;
		for (int k1 = 0; k1 < linewidth; k1++) {
			pixels[j1 + k1 * width] = j;
		}

	}

	private static void drawVerticalLineNew(int i, int j, int k, int l, int i1) {
		if (j < topX || j >= bottomX) {
			return;
		}
		if (l < topY) {
			i1 -= topY - l;
			l = topY;
		}
		if (l + i1 > bottomY) {
			i1 = bottomY - l;
		}
		int j1 = 256 - k;
		int k1 = (i >> 16 & 0xff) * k;
		int l1 = (i >> 8 & 0xff) * k;
		int i2 = (i & 0xff) * k;
		int i3 = j + l * width;
		for (int j3 = 0; j3 < i1; j3++) {
			int j2 = (pixels[i3] >> 16 & 0xff) * j1;
			int k2 = (pixels[i3] >> 8 & 0xff) * j1;
			int l2 = (pixels[i3] & 0xff) * j1;
			int k3 = (k1 + j2 >> 8 << 16) + (l1 + k2 >> 8 << 8) + (i2 + l2 >> 8);
			pixels[i3] = k3;
			i3 += width;
		}

	}

	public DrawingArea() {
	}

	public static int[] pixels;
	public static int width;
	public static int height;
	public static int topY;
	public static int bottomY;
	public static int topX;
	public static int bottomX;
	public static int centerX;
	public static int centerY;
	public static int anInt1387;

}
