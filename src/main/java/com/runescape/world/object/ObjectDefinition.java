package com.runescape.world.object;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.*;
import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.world.model.Model;
import com.runescape.world.model.frames.Frames;
import com.runescape.core.node.ReferenceCache;
import com.runescape.core.ondemand.OnDemandFetcher;
import com.runescape.world.varp.VarBit;

public final class ObjectDefinition {

	public static ObjectDefinition forID(int i) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == i) {
				return cache[j];
			}
		}

		cacheIndex = (cacheIndex + 1) % 20;
		ObjectDefinition class46 = cache[cacheIndex];
		byteBuffer.currentOffset = streamIndices[i];
		class46.type = i;
		class46.setDefaults();
		class46.readValues(byteBuffer);
		if (i == 6) {
			class46.actions[1] = "Load";
			class46.actions[2] = "Pick-up";
		}
		switch (i) {

		}
		return class46;
	}

	private void setDefaults() {
		models = null;
		modelTypes = null;
		name = null;
		description = null;
		modifiedModelColors = null;
		originalModelColors = null;
		sizeX = 1;
		sizeY = 1;
		blocksWalk = true;
		blocksProjectiles = true;
		hasActions = false;
		adjustsToTerrain = false;
		isFlatShaded = false;
		isSolid = false;
		animationID = -1;
		wallWidth = 16;
		brightness = 0;
		contrast = 0;
		actions = null;
		minimapFunction = -1;
		anInt758 = -1;
		rotateLeft = false;
		castsShadow = true;
		scaleX = 128;
		scaleY = 128;
		anInt740 = 128;
		rotationFlags = 0;
		offsetX = 0;
		offsetY = 0;
		offsetZ = 0;
		isDecoration = false;
		ghost = false;
		holdsItemPiles = -1;
		varbit = -1;
		setting = -1;
		childrenIDs = null;
	}

	public void requestModels(OnDemandFetcher class42_sub1) {
		if (models == null) {
			return;
		}
		for (int element : models) {
			class42_sub1.method560(element & 0xffff, 0);
		}
	}

	public static void nullLoader() {
		referenceCache1 = null;
		referenceCache2 = null;
		streamIndices = null;
		cache = null;
		byteBuffer = null;
	}

	public static void unpackConfig(StreamLoader streamLoader) {
		ObjectDefinition.byteBuffer = new ByteBuffer(streamLoader.getDataForName("loc.dat"));
		ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("loc.idx"));
		int totalObjects = byteBuffer.readUnsignedWord();
		streamIndices = new int[totalObjects];
		int i = 2;
		for (int j = 0; j < totalObjects; j++) {
			streamIndices[j] = i;
			i += byteBuffer.readUnsignedWord();
		}

		cache = new ObjectDefinition[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new ObjectDefinition();
		}

	}

	public boolean method577(int i) {
		if (modelTypes == null) {
			if (models == null) {
				return true;
			}
			if (i != 10) {
				return true;
			}
			boolean flag1 = true;
			for (int element : models) {
				flag1 &= Model.method463(element & 0xffff);
			}

			return flag1;
		}
		for (int j = 0; j < modelTypes.length; j++) {
			if (modelTypes[j] == i) {
				return Model.method463(models[j] & 0xffff);
			}
		}

		return true;
	}

	public Model getAdjustedModel(int i, int j, int k, int l, int i1, int j1, int k1) {
		Model model = method581(i, k1, j);
		if (model == null) {
			return null;
		}
		if (adjustsToTerrain || isFlatShaded) {
			model = new Model(adjustsToTerrain, isFlatShaded, model);
		}
		if (adjustsToTerrain) {
			int l1 = (k + l + i1 + j1) / 4;
			for (int i2 = 0; i2 < model.anInt1626; i2++) {
				int j2 = model.vertexX[i2];
				int k2 = model.vertexZ[i2];
				int l2 = k + (l - k) * (j2 + 64) / 128;
				int i3 = j1 + (i1 - j1) * (j2 + 64) / 128;
				int j3 = l2 + (i3 - l2) * (k2 + 64) / 128;
				model.vertexY[i2] += j3 - l1;
			}

			model.method467();
		}
		return model;
	}

	public boolean method579() {
		if (models == null) {
			return true;
		}
		boolean flag1 = true;
		for (int element : models) {
			flag1 &= Model.method463(element & 0xffff);
		}
		return flag1;
	}

	public ObjectDefinition method580() {
		int i = -1;
		if (varbit != -1) {
			VarBit varBit = VarBit.cache[varbit];
			int j = varBit.setting;
			int k = varBit.startbit;
			int l = varBit.endbit;
			int i1 = Game.BIT_MASK[l - k];
			i = clientInstance.variousSettings[j] >> k & i1;
		} else if (setting != -1) {
			i = clientInstance.variousSettings[setting];
		}
		if (i < 0 || i >= childrenIDs.length || childrenIDs[i] == -1) {
			return null;
		} else {
			return forID(childrenIDs[i]);
		}
	}

	private Model method581(int j, int k, int l) {
		Model model = null;
		long l1;
		if (modelTypes == null) {
			if (j != 10) {
				return null;
			}
			l1 = (type << 6) + l + ((long) (k + 1) << 32);
			Model model_1 = (Model) referenceCache2.insertFromCache(l1);
			if (model_1 != null) {
				return model_1;
			}
			if (models == null) {
				return null;
			}
			boolean flag1 = rotateLeft ^ l > 3;
			int k1 = models.length;
			for (int i2 = 0; i2 < k1; i2++) {
				int l2 = models[i2];
				if (flag1) {
					l2 += 0x10000;
				}
				model = (Model) referenceCache1.insertFromCache(l2);
				if (model == null) {
					model = Model.method462(l2 & 0xffff);
					if (model == null) {
						return null;
					}
					if (flag1) {
						model.method477();
					}
					referenceCache1.removeFromCache(model, l2);
				}
				if (k1 > 1) {
					aModelArray741s[i2] = model;
				}
			}

			if (k1 > 1) {
				model = new Model(k1, aModelArray741s);
			}
		} else {
			int i1 = -1;
			for (int j1 = 0; j1 < modelTypes.length; j1++) {
				if (modelTypes[j1] != j) {
					continue;
				}
				i1 = j1;
				break;
			}

			if (i1 == -1) {
				return null;
			}
			l1 = (type << 6) + (i1 << 3) + l + ((long) (k + 1) << 32);
			Model model_2 = (Model) referenceCache2.insertFromCache(l1);
			if (model_2 != null) {
				return model_2;
			}
			int j2 = models[i1];
			boolean flag3 = rotateLeft ^ l > 3;
			if (flag3) {
				j2 += 0x10000;
			}
			model = (Model) referenceCache1.insertFromCache(j2);
			if (model == null) {
				model = Model.method462(j2 & 0xffff);
				if (model == null) {
					return null;
				}
				if (flag3) {
					model.method477();
				}
				referenceCache1.removeFromCache(model, j2);
			}
		}
		boolean flag;
		flag = scaleX != 128 || scaleY != 128 || anInt740 != 128;
		boolean flag2;
		flag2 = offsetX != 0 || offsetY != 0 || offsetZ != 0;
		Model model_3 = new Model(modifiedModelColors == null, Frames.method532(k), l == 0 && k == -1 && !flag && !flag2, model);
		if (k != -1) {
			model_3.method469();
			model_3.method470(k);
			model_3.anIntArrayArray1658 = null;
			model_3.anIntArrayArray1657 = null;
		}
		while (l-- > 0) {
			model_3.method473();
		}
		if (modifiedModelColors != null) {
			for (int k2 = 0; k2 < modifiedModelColors.length; k2++) {
				model_3.method476(modifiedModelColors[k2], originalModelColors[k2]);
			}

		}
		if (flag) {
			model_3.scaleModel(scaleX, anInt740, scaleY);
		}
		if (flag2) {
			model_3.method475(offsetX, offsetY, offsetZ);
		}
		model_3.method479(64 + brightness, 768 + contrast * 5, -50, -10, -50, !isFlatShaded);
		if (holdsItemPiles == 1) {
			model_3.anInt1654 = model_3.modelHeight;
		}
		referenceCache2.removeFromCache(model_3, l1);
		return model_3;
	}

	private void readValues(ByteBuffer byteBuffer) {
		int i = -1;
		label0 : do {
			int j;
			do {
				j = byteBuffer.readUnsignedByte();
				if (j == 0) {
					break label0;
				}
				if (j == 1) {
					int k = byteBuffer.readUnsignedByte();
					if (k > 0) {
						if (models == null || lowMem) {
							modelTypes = new int[k];
							models = new int[k];
							for (int k1 = 0; k1 < k; k1++) {
								models[k1] = byteBuffer.readUnsignedWord();
								modelTypes[k1] = byteBuffer.readUnsignedByte();
							}

						} else {
							byteBuffer.currentOffset += k * 3;
						}
					}
				} else if (j == 2) {
					name = byteBuffer.readString();
				} else if (j == 3) {
					description = byteBuffer.readBytes();
				} else if (j == 5) {
					int l = byteBuffer.readUnsignedByte();
					if (l > 0) {
						if (models == null || lowMem) {
							modelTypes = null;
							models = new int[l];
							for (int l1 = 0; l1 < l; l1++) {
								models[l1] = byteBuffer.readUnsignedWord();
							}

						} else {
							byteBuffer.currentOffset += l * 2;
						}
					}
				} else if (j == 14) {
					sizeX = byteBuffer.readUnsignedByte();
				} else if (j == 15) {
					sizeY = byteBuffer.readUnsignedByte();
				} else if (j == 17) {
					blocksWalk = false;
				} else if (j == 18) {
					blocksProjectiles = false;
				} else if (j == 19) {
					i = byteBuffer.readUnsignedByte();
					if (i == 1) {
						hasActions = true;
					}
				} else if (j == 21) {
					adjustsToTerrain = true;
				} else if (j == 22) {
					isFlatShaded = true;
				} else if (j == 23) {
					isSolid = true;
				} else if (j == 24) {
					animationID = byteBuffer.readUnsignedWord();
					if (animationID == 65535) {
						animationID = -1;
					}
				} else if (j == 28) {
					wallWidth = byteBuffer.readUnsignedByte();
				} else if (j == 29) {
					brightness = byteBuffer.readSignedByte();
				} else if (j == 39) {
					contrast = byteBuffer.readSignedByte();
				} else if (j >= 30 && j < 39) {
					if (actions == null) {
						actions = new String[5];
					}
					actions[j - 30] = byteBuffer.readString();
					if (actions[j - 30].equalsIgnoreCase("hidden")) {
						actions[j - 30] = null;
					}
				} else if (j == 40) {
					int i1 = byteBuffer.readUnsignedByte();
					modifiedModelColors = new int[i1];
					originalModelColors = new int[i1];
					for (int i2 = 0; i2 < i1; i2++) {
						modifiedModelColors[i2] = byteBuffer.readUnsignedWord();
						originalModelColors[i2] = byteBuffer.readUnsignedWord();
					}

				} else if (j == 60) {
					minimapFunction = byteBuffer.readUnsignedWord();
				} else if (j == 62) {
					rotateLeft = true;
				} else if (j == 64) {
					castsShadow = false;
				} else if (j == 65) {
					scaleX = byteBuffer.readUnsignedWord();
				} else if (j == 66) {
					scaleY = byteBuffer.readUnsignedWord();
				} else if (j == 67) {
					anInt740 = byteBuffer.readUnsignedWord();
				} else if (j == 68) {
					anInt758 = byteBuffer.readUnsignedWord();
				} else if (j == 69) {
					rotationFlags = byteBuffer.readUnsignedByte();
				} else if (j == 70) {
					offsetX = byteBuffer.readSignedWord();
				} else if (j == 71) {
					offsetY = byteBuffer.readSignedWord();
				} else if (j == 72) {
					offsetZ = byteBuffer.readSignedWord();
				} else if (j == 73) {
					isDecoration = true;
				} else if (j == 74) {
					ghost = true;
				} else {
					if (j != 75) {
						continue;
					}
					holdsItemPiles = byteBuffer.readUnsignedByte();
				}
				continue label0;
			} while (j != 77);
			varbit = byteBuffer.readUnsignedWord();
			if (varbit == 65535) {
				varbit = -1;
			}
			setting = byteBuffer.readUnsignedWord();
			if (setting == 65535) {
				setting = -1;
			}
			int j1 = byteBuffer.readUnsignedByte();
			childrenIDs = new int[j1 + 1];
			for (int j2 = 0; j2 <= j1; j2++) {
				childrenIDs[j2] = byteBuffer.readUnsignedWord();
				if (childrenIDs[j2] == 65535) {
					childrenIDs[j2] = -1;
				}
			}

		} while (true);
		if (i == -1) {
			hasActions = models != null && (modelTypes == null || modelTypes[0] == 10);
			if (actions != null) {
				hasActions = true;
			}
		}
		if (ghost) {
			blocksWalk = false;
			blocksProjectiles = false;
		}
		if (holdsItemPiles == -1) {
			holdsItemPiles = blocksWalk ? 1 : 0;
		}
	}

	private ObjectDefinition() {
		type = -1;
	}

	public boolean isDecoration;
	private byte brightness;
	private int offsetX;
	public String name;
	private int anInt740;
	private static final Model[] aModelArray741s = new Model[4];
	private byte contrast;
	public int sizeX;
	private int offsetY;
	public int minimapFunction;
	private int[] originalModelColors;
	private int scaleX;
	public int setting;
	private boolean rotateLeft;
	public static boolean lowMem;
	private static ByteBuffer byteBuffer;
	public int type;
	private static int[] streamIndices;
	public boolean blocksProjectiles;
	public int anInt758;
	public int[] childrenIDs;
	private int holdsItemPiles;
	public int sizeY;
	public boolean adjustsToTerrain;
	public boolean isSolid;
	public static Game clientInstance;
	private boolean ghost;
	public boolean blocksWalk;
	public int rotationFlags;
	private boolean isFlatShaded;
	private static int cacheIndex;
	private int scaleY;
	private int[] models;
	public int varbit;
	public int wallWidth;
	private int[] modelTypes;
	public byte[] description;
	public boolean hasActions;
	public boolean castsShadow;
	public static ReferenceCache referenceCache2 = new ReferenceCache(30);
	public int animationID;
	private static ObjectDefinition[] cache;
	private int offsetZ;
	private int[] modifiedModelColors;
	public static ReferenceCache referenceCache1 = new ReferenceCache(500);
	public String[] actions;

}
