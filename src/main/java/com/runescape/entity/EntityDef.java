package com.runescape.entity;

import com.Game;
import com.runescape.core.node.ReferenceCache;
import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.world.model.Model;
import com.runescape.world.model.frames.Frames;
import com.runescape.world.varp.VarBit;

public class EntityDef {
	
	public static EntityDef forID(int i) {
		for (int j = 0; j < 20; j++) {
			if (cache[j].type == i) {
				return cache[j];
			}
		}

		anInt56 = (anInt56 + 1) % 20;
		EntityDef entityDef = cache[anInt56] = new EntityDef();
		byteBuffer.currentOffset = streamIndices[i];
		entityDef.type = i;
		entityDef.readValues(byteBuffer);
		switch(i) {
			case 2258:
				entityDef.actions = new String[5];
				entityDef.actions[0] = "Talk-to";
				entityDef.actions[2] = "Trade";
				entityDef.actions[3] = "Teleport";
				break;
		}
		return entityDef;
	}

	public Model method160() {
		if (childrenIDs != null) {
			EntityDef entityDef = method161();
			if (entityDef == null) {
				return null;
			} else {
				return entityDef.method160();
			}
		}
		if (anIntArray73 == null) {
			return null;
		}
		boolean flag1 = false;
        for (int value : anIntArray73) {
            if (!Model.method463(value)) {
                flag1 = true;
            }
        }

		if (flag1) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6s = new Model[anIntArray73.length];
		for (int j = 0; j < anIntArray73.length; j++) {
			aclass30_sub2_sub4_sub6s[j] = Model.method462(anIntArray73[j]);
		}

		Model model;
		if (aclass30_sub2_sub4_sub6s.length == 1) {
			model = aclass30_sub2_sub4_sub6s[0];
		} else {
			model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
		}
		if (anIntArray76 != null) {
			for (int k = 0; k < anIntArray76.length; k++) {
				model.method476(anIntArray76[k], anIntArray70[k]);
			}

		}
		return model;
	}

	public EntityDef method161() {
		int j = -1;
		if (anInt57 != -1) {
			VarBit varBit = VarBit.cache[anInt57];
			int k = varBit.setting;
			int l = varBit.startbit;
			int i1 = varBit.endbit;
			int j1 = Game.BIT_MASK[i1 - l];
			j = clientInstance.variousSettings[k] >> l & j1;
		} else if (anInt59 != -1) {
			j = clientInstance.variousSettings[anInt59];
		}
		if (j < 0 || j >= childrenIDs.length || childrenIDs[j] == -1) {
			return null;
		} else {
			return forID(childrenIDs[j]);
		}
	}

	public static void unpackConfig(StreamLoader streamLoader) {
		byteBuffer = new ByteBuffer(streamLoader.getDataForName("npc.dat"));
		ByteBuffer byteBuffer2 = new ByteBuffer(streamLoader.getDataForName("npc.idx"));
		int totalNPCs = byteBuffer2.readUnsignedWord();
		streamIndices = new int[totalNPCs];
		int i = 2;
		for (int j = 0; j < totalNPCs; j++) {
			streamIndices[j] = i;
			i += byteBuffer2.readUnsignedWord();
		}

		cache = new EntityDef[20];
		for (int k = 0; k < 20; k++) {
			cache[k] = new EntityDef();
		}

	}

	public static void nullLoader() {
		referenceCache = null;
		streamIndices = null;
		cache = null;
		byteBuffer = null;
	}

	public Model method164(int j, int k, int[] ai) {
		if (childrenIDs != null) {
			EntityDef entityDef = method161();
			if (entityDef == null) {
				return null;
			} else {
				return entityDef.method164(j, k, ai);
			}
		}
		Model model = (Model) referenceCache.insertFromCache(type);
		if (model == null) {
			boolean flag = false;
            for (int i : anIntArray94) {
                if (!Model.method463(i)) {
                    flag = true;
                }
            }

			if (flag) {
				return null;
			}
			Model[] aclass30_sub2_sub4_sub6s = new Model[anIntArray94.length];
			for (int j1 = 0; j1 < anIntArray94.length; j1++) {
				aclass30_sub2_sub4_sub6s[j1] = Model.method462(anIntArray94[j1]);
			}

			if (aclass30_sub2_sub4_sub6s.length == 1) {
				model = aclass30_sub2_sub4_sub6s[0];
			} else {
				model = new Model(aclass30_sub2_sub4_sub6s.length, aclass30_sub2_sub4_sub6s);
			}
			if (anIntArray76 != null) {
				for (int k1 = 0; k1 < anIntArray76.length; k1++) {
					model.method476(anIntArray76[k1], anIntArray70[k1]);
				}

			}
			model.method469();
			model.method479(64 + anInt85, 850 + anInt92, -30, -50, -30, true);
			referenceCache.removeFromCache(model, type);
		}
		Model model_1 = Model.aModel_1621;
		model_1.method464(model, Frames.method532(k) & Frames.method532(j));
		if (k != -1 && j != -1) {
			model_1.method471(ai, j, k);
		} else if (k != -1) {
			model_1.method470(k);
		}
		if (anInt91 != 128 || anInt86 != 128) {
			model_1.scaleModel(anInt91, anInt91, anInt86);
		}
		model_1.method466();
		model_1.anIntArrayArray1658 = null;
		model_1.anIntArrayArray1657 = null;
		if (aByte68 == 1) {
			model_1.aBoolean1659 = true;
		}
		return model_1;
	}

	private void readValues(ByteBuffer byteBuffer) {
		do {
			int i = byteBuffer.readUnsignedByte();
			if (i == 0) {
				return;
			}
			if (i == 1) {
				int j = byteBuffer.readUnsignedByte();
				anIntArray94 = new int[j];
				for (int j1 = 0; j1 < j; j1++) {
					anIntArray94[j1] = byteBuffer.readUnsignedWord();
				}

			} else if (i == 2) {
				name = byteBuffer.readString();
			} else if (i == 3) {
				description = byteBuffer.readBytes();
			} else if (i == 12) {
				aByte68 = byteBuffer.readSignedByte();
			} else if (i == 13) {
				anInt77 = byteBuffer.readUnsignedWord();
			} else if (i == 14) {
				anInt67 = byteBuffer.readUnsignedWord();
			} else if (i == 17) {
				anInt67 = byteBuffer.readUnsignedWord();
				anInt58 = byteBuffer.readUnsignedWord();
				anInt83 = byteBuffer.readUnsignedWord();
				anInt55 = byteBuffer.readUnsignedWord();
			} else if (i >= 30 && i < 40) {
				if (actions == null) {
					actions = new String[5];
				}
				actions[i - 30] = byteBuffer.readString();
				if (actions[i - 30].equalsIgnoreCase("hidden")) {
					actions[i - 30] = null;
				}
			} else if (i == 40) {
				int k = byteBuffer.readUnsignedByte();
				anIntArray76 = new int[k];
				anIntArray70 = new int[k];
				for (int k1 = 0; k1 < k; k1++) {
					anIntArray76[k1] = byteBuffer.readUnsignedWord();
					anIntArray70[k1] = byteBuffer.readUnsignedWord();
				}

			} else if (i == 60) {
				int l = byteBuffer.readUnsignedByte();
				anIntArray73 = new int[l];
				for (int l1 = 0; l1 < l; l1++) {
					anIntArray73[l1] = byteBuffer.readUnsignedWord();
				}

			} else if (i == 90) {
				byteBuffer.readUnsignedWord();
			} else if (i == 91) {
				byteBuffer.readUnsignedWord();
			} else if (i == 92) {
				byteBuffer.readUnsignedWord();
			} else if (i == 93) {
				aBoolean87 = false;
			} else if (i == 95) {
				combatLevel = byteBuffer.readUnsignedWord();
			} else if (i == 97) {
				anInt91 = byteBuffer.readUnsignedWord();
			} else if (i == 98) {
				anInt86 = byteBuffer.readUnsignedWord();
			} else if (i == 99) {
				hasRenderPriority = true;
			} else if (i == 100) {
				anInt85 = byteBuffer.readSignedByte();
			} else if (i == 101) {
				anInt92 = byteBuffer.readSignedByte() * 5;
			} else if (i == 102) {
				anInt75 = byteBuffer.readUnsignedWord();
			} else if (i == 103) {
				anInt79 = byteBuffer.readUnsignedWord();
			} else if (i == 106) {
				anInt57 = byteBuffer.readUnsignedWord();
				if (anInt57 == 65535) {
					anInt57 = -1;
				}
				anInt59 = byteBuffer.readUnsignedWord();
				if (anInt59 == 65535) {
					anInt59 = -1;
				}
				int i1 = byteBuffer.readUnsignedByte();
				childrenIDs = new int[i1 + 1];
				for (int i2 = 0; i2 <= i1; i2++) {
					childrenIDs[i2] = byteBuffer.readUnsignedWord();
					if (childrenIDs[i2] == 65535) {
						childrenIDs[i2] = -1;
					}
				}

			} else if (i == 107) {
				interactable = false;
			}
		} while (true);
	}

	private EntityDef() {
		anInt55 = -1;
		anInt57 = -1;
		anInt58 = -1;
		anInt59 = -1;
		combatLevel = -1;
		anInt67 = -1;
		aByte68 = 1;
		anInt75 = -1;
		anInt77 = -1;
		type = -1L;
		anInt79 = 32;
		anInt83 = -1;
		interactable = true;
		anInt86 = 128;
		aBoolean87 = true;
		anInt91 = 128;
		hasRenderPriority = false;
	}

	public int anInt55;
	private static int anInt56;
	private int anInt57;
	public int anInt58;
	private int anInt59;
	private static ByteBuffer byteBuffer;
	public int combatLevel;
	public String name;
	public String[] actions;
	public int anInt67;
	public byte aByte68;
	private int[] anIntArray70;
	private static int[] streamIndices;
	private int[] anIntArray73;
	public int anInt75;
	private int[] anIntArray76;
	public int anInt77;
	public long type;
	public int anInt79;
	private static EntityDef[] cache;
	public static Game clientInstance;
	public int anInt83;
	public boolean interactable;
	private int anInt85;
	private int anInt86;
	public boolean aBoolean87;
	public int[] childrenIDs;
	public byte[] description;
	private int anInt91;
	private int anInt92;
	public boolean hasRenderPriority;
	private int[] anIntArray94;
	public static ReferenceCache referenceCache = new ReferenceCache(30);

}
