package com.runescape.entity;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.world.model.Model;

public final class IdentityKit {

	public static void unpackConfig(StreamLoader streamLoader) {
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("idk.dat"));
		length = byteBuffer.readUnsignedWord();
		if (cache == null) {
			cache = new IdentityKit[length];
		}
		for (int j = 0; j < length; j++) {
			if (cache[j] == null) {
				cache[j] = new IdentityKit();
			}
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
				partId = byteBuffer.readUnsignedByte();
			} else if (i == 2) {
				int j = byteBuffer.readUnsignedByte();
				modelArray = new int[j];
				for (int k = 0; k < j; k++) {
					modelArray[k] = byteBuffer.readUnsignedWord();
				}

			} else if (i == 3) {
				disableDisplay = true;
			} else if (i >= 40 && i < 50) {
				oldColour[i - 40] = byteBuffer.readUnsignedWord();
			} else if (i >= 50 && i < 60) {
				newColour[i - 50] = byteBuffer.readUnsignedWord();
			} else if (i >= 60 && i < 70) {
				dialogueModel[i - 60] = byteBuffer.readUnsignedWord();
			} else {
				System.out.println("Error unrecognised config code: " + i);
			}
		} while (true);
	}

	public boolean method537() {
		if (modelArray == null) {
			return true;
		}
		boolean flag = true;
        for (int i : modelArray) {
            if (!Model.method463(i)) {
                flag = false;
            }
        }

		return flag;
	}

	public Model method538() {
		if (modelArray == null) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6s = new Model[modelArray.length];
		for (int i = 0; i < modelArray.length; i++) {
			aclass30_sub2_sub4_sub6s[i] = Model.method462(modelArray[i]);
		}

		Model model;
		if (aclass30_sub2_sub4_sub6s.length == 1) {
			model = aclass30_sub2_sub4_sub6s[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		}
		for (int j = 0; j < 6; j++) {
			if (oldColour[j] == 0) {
				break;
			}
			model.method476(oldColour[j], newColour[j]);
		}

		return model;
	}

	public boolean method539() {
		boolean flag1 = true;
		for (int i = 0; i < 5; i++) {
			if (dialogueModel[i] != -1 && !Model.method463(dialogueModel[i])) {
				flag1 = false;
			}
		}

		return flag1;
	}

	public Model method540() {
		Model[] aclass30_sub2_sub4_sub6s = new Model[5];
		int j = 0;
		for (int k = 0; k < 5; k++) {
			if (dialogueModel[k] != -1) {
				aclass30_sub2_sub4_sub6s[j++] = Model.method462(dialogueModel[k]);
			}
		}

		Model model = new Model(j, aclass30_sub2_sub4_sub6s);
		for (int l = 0; l < 6; l++) {
			if (oldColour[l] == 0) {
				break;
			}
			model.method476(oldColour[l], newColour[l]);
		}

		return model;
	}

	private IdentityKit() {
		partId = -1;
		oldColour = new int[6];
		newColour = new int[6];
		disableDisplay = false;
	}

	public static int length;
	public static IdentityKit[] cache;
	public int partId;
	private int[] modelArray;
	private final int[] oldColour;
	private final int[] newColour;
	private final int[] dialogueModel = {-1, -1, -1, -1, -1};
	public boolean disableDisplay;
}
