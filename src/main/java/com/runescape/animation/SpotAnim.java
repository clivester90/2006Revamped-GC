package com.runescape.animation;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.node.ReferenceCache;
import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.world.model.Model;

public final class SpotAnim {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("spotanim.dat"));
		int length = byteBuffer.readUnsignedWord();
		if (cache == null) {
			cache = new SpotAnim[length];
		}
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new SpotAnim();
			}
			cache[j].anInt404 = j;
			cache[j].readValues(byteBuffer);
		}

	}

	private void readValues(ByteBuffer byteBuffer) {
		do {
			int i = byteBuffer.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				anInt405 = byteBuffer.readUnsignedWord();
			} else if (i == 2) {
				anInt406 = byteBuffer.readUnsignedWord();
				if (Sequences.anims != null) {
					sequencesSequence = Sequences.anims[anInt406];
				}
			} else if (i == 4) {
				scale = byteBuffer.readUnsignedWord();
			} else if (i == 5) {
				height = byteBuffer.readUnsignedWord();
			} else if (i == 6) {
				anInt412 = byteBuffer.readUnsignedWord();
			} else if (i == 7) {
				ambient = byteBuffer.readUnsignedByte();
			} else if (i == 8) {
				contrast = byteBuffer.readUnsignedByte();
			} else if (i >= 40 && i < 50) {
				anIntArray408[i - 40] = byteBuffer.readUnsignedWord();
			} else if (i >= 50 && i < 60) {
				anIntArray409[i - 50] = byteBuffer.readUnsignedWord();
			} else {
				System.out.println("Error unrecognised spotanim config code: " + i);
			}
		} while (true);
	}

	public Model getModel() {
		Model model = (Model) aReferenceCache_415.insertFromCache(anInt404);
		if (model != null) {
			return model;
		}
		model = Model.method462(anInt405);
		if (model == null) {
			return null;
		}
		for (int i = 0; i < 6; i++) {
			if (anIntArray408[0] != 0) {
				model.method476(anIntArray408[i], anIntArray409[i]);
			}
		}

		aReferenceCache_415.removeFromCache(model, anInt404);
		return model;
	}

	private SpotAnim() {
		anInt406 = -1;
		anIntArray408 = new int[6];
		anIntArray409 = new int[6];
		scale = 128;
		height = 128;
	}

	public static SpotAnim[] cache;
	private int anInt404;
	private int anInt405;
	private int anInt406;
	public Sequences sequencesSequence;
	private final int[] anIntArray408;
	private final int[] anIntArray409;
	public int scale;
	public int height;
	public int anInt412;
	public int ambient;
	public int contrast;
	public static ReferenceCache aReferenceCache_415 = new ReferenceCache(30);

}
