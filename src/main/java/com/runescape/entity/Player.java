package com.runescape.entity;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.Game;
import com.runescape.core.node.ReferenceCache;
import com.runescape.graphics.text.TextClass;
import com.runescape.animation.Sequences;
import com.runescape.animation.SpotAnim;
import com.runescape.core.cache.ByteBuffer;
import com.runescape.item.ItemDef;
import com.runescape.world.model.Model;
import com.runescape.world.model.frames.Frames;

public final class Player extends Entity {

	@Override
	public Model getRotatedModel() {
		if (!visible) {
			return null;
		}
		Model model = method452();
		if (model == null) {
			return null;
		}
		super.height = model.modelHeight;
		model.aBoolean1659 = true;
		if (aBoolean1699) {
			return model;
		}
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
			Model model_2 = spotAnim.getModel();
			if (model_2 != null) {
				Model model_3 = new Model(true, Frames.method532(super.anInt1521), false, model_2);
				model_3.method475(0, -super.anInt1524, 0);
				model_3.method469();
				model_3.method470(spotAnim.sequencesSequence.primary[super.anInt1521]);
				model_3.anIntArrayArray1658 = null;
				model_3.anIntArrayArray1657 = null;
				if (spotAnim.scale != 128 || spotAnim.height != 128) {
					model_3.scaleModel(spotAnim.scale, spotAnim.scale, spotAnim.height);
				}
				model_3.method479(64 + spotAnim.ambient, 850 + spotAnim.contrast, -30, -50, -30, true);
				Model[] aclass30_sub2_sub4_sub6_1s = {model, model_3};
				model = new Model(aclass30_sub2_sub4_sub6_1s);
			}
		}
		if (aModel_1714 != null) {
			if (Game.loopCycle >= anInt1708) {
				aModel_1714 = null;
			}
			if (Game.loopCycle >= objectStartCycle && Game.loopCycle < anInt1708) {
				Model model_1 = aModel_1714;
				model_1.method475(anInt1711 - super.x, anInt1712 - z, anInt1713 - super.y);
				if (super.turnDirection == 512) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
				}
				Model[] aclass30_sub2_sub4_sub6s = {model, model_1};
				model = new Model(aclass30_sub2_sub4_sub6s);
				if (super.turnDirection == 512) {
					model_1.method473();
				} else if (super.turnDirection == 1024) {
					model_1.method473();
					model_1.method473();
				} else if (super.turnDirection == 1536) {
					model_1.method473();
					model_1.method473();
					model_1.method473();
				}
				model_1.method475(super.x - anInt1711, z - anInt1712, super.y - anInt1713);
			}
		}
		model.aBoolean1659 = true;
		return model;
	}

	public void updatePlayer(ByteBuffer byteBuffer) {
		byteBuffer.currentOffset = 0;
		anInt1702 = byteBuffer.readUnsignedByte();
		headIcon = byteBuffer.readUnsignedByte();
		skullIcon = byteBuffer.readUnsignedByte();
		desc = null;
		team = 0;
		for (int j = 0; j < 12; j++) {
			int k = byteBuffer.readUnsignedByte();
			if (k == 0) {
				equipment[j] = 0;
				continue;
			}
			int i1 = byteBuffer.readUnsignedByte();
			equipment[j] = (k << 8) + i1;
			if (j == 0 && equipment[0] == 65535) {
				desc = EntityDef.forID(byteBuffer.readUnsignedWord());
				break;
			}
			if (equipment[j] >= 512 && equipment[j] - 512 < ItemDef.totalItems) {
				int l1 = ItemDef.forID(equipment[j] - 512).team;
				if (l1 != 0) {
					team = l1;
				}
			}
		}

		for (int l = 0; l < 5; l++) {
			int j1 = byteBuffer.readUnsignedByte();
			if (j1 < 0 || j1 >= Game.anIntArrayArray1003[l].length) {
				j1 = 0;
			}
			anIntArray1700[l] = j1;
		}

		super.anInt1511 = byteBuffer.readUnsignedWord();
		if (super.anInt1511 == 65535) {
			super.anInt1511 = -1;
		}
		super.anInt1512 = byteBuffer.readUnsignedWord();
		if (super.anInt1512 == 65535) {
			super.anInt1512 = -1;
		}
		super.anInt1554 = byteBuffer.readUnsignedWord();
		if (super.anInt1554 == 65535) {
			super.anInt1554 = -1;
		}
		super.anInt1555 = byteBuffer.readUnsignedWord();
		if (super.anInt1555 == 65535) {
			super.anInt1555 = -1;
		}
		super.anInt1556 = byteBuffer.readUnsignedWord();
		if (super.anInt1556 == 65535) {
			super.anInt1556 = -1;
		}
		super.anInt1557 = byteBuffer.readUnsignedWord();
		if (super.anInt1557 == 65535) {
			super.anInt1557 = -1;
		}
		super.anInt1505 = byteBuffer.readUnsignedWord();
		if (super.anInt1505 == 65535) {
			super.anInt1505 = -1;
		}
		name = TextClass.fixName(TextClass.nameForLong(byteBuffer.readQWord()));
		combatLevel = byteBuffer.readUnsignedByte();
		skill = byteBuffer.readUnsignedWord();
		visible = true;
		aLong1718 = 0L;
		for (int k1 = 0; k1 < 12; k1++) {
			aLong1718 <<= 4;
			if (equipment[k1] >= 256) {
				aLong1718 += equipment[k1] - 256;
			}
		}

		if (equipment[0] >= 256) {
			aLong1718 += equipment[0] - 256 >> 4;
		}
		if (equipment[1] >= 256) {
			aLong1718 += equipment[1] - 256 >> 8;
		}
		for (int i2 = 0; i2 < 5; i2++) {
			aLong1718 <<= 3;
			aLong1718 += anIntArray1700[i2];
		}

		aLong1718 <<= 1;
		aLong1718 += anInt1702;
	}

	private Model method452() {
		if (desc != null) {
			int j = -1;
			if (super.anim >= 0 && super.sequenceDelayCycle == 0) {
				j = Sequences.anims[super.anim].primary[super.anInt1527];
			} else if (super.anInt1517 >= 0) {
				j = Sequences.anims[super.anInt1517].primary[super.anInt1518];
			}
			Model model = desc.method164(-1, j, null);
			return model;
		}
		long l = aLong1718;
		int k = -1;
		int i1 = -1;
		int j1 = -1;
		int k1 = -1;
		if (super.anim >= 0 && super.sequenceDelayCycle == 0) {
			Sequences sequences = Sequences.anims[super.anim];
			k = sequences.primary[super.anInt1527];
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = Sequences.anims[super.anInt1517].primary[super.anInt1518];
			}
			if (sequences.shield >= 0) {
				j1 = sequences.shield;
				l += j1 - equipment[5] << 40;
			}
			if (sequences.weapon >= 0) {
				k1 = sequences.weapon;
				l += k1 - equipment[3] << 48;
			}
		} else if (super.anInt1517 >= 0) {
			k = Sequences.anims[super.anInt1517].primary[super.anInt1518];
		}
		Model model_1 = (Model) referenceCache.insertFromCache(l);
		if (model_1 == null) {
			boolean flag = false;
			for (int i2 = 0; i2 < 12; i2++) {
				int k2 = equipment[i2];
				if (k1 >= 0 && i2 == 3) {
					k2 = k1;
				}
				if (j1 >= 0 && i2 == 5) {
					k2 = j1;
				}
				if (k2 >= 256 && k2 < 512 && !IdentityKit.cache[k2 - 256].method537()) {
					flag = true;
				}
				if (k2 >= 512 && !ItemDef.forID(k2 - 512).method195(anInt1702)) {
					flag = true;
				}
			}

			if (flag) {
				if (aLong1697 != -1L) {
					model_1 = (Model) referenceCache.insertFromCache(aLong1697);
				}
				if (model_1 == null) {
					return null;
				}
			}
		}
		if (model_1 == null) {
			Model[] aclass30_sub2_sub4_sub6s = new Model[12];
			int j2 = 0;
			for (int l2 = 0; l2 < 12; l2++) {
				int i3 = equipment[l2];
				if (k1 >= 0 && l2 == 3) {
					i3 = k1;
				}
				if (j1 >= 0 && l2 == 5) {
					i3 = j1;
				}
				if (i3 >= 256 && i3 < 512) {
					Model model_3 = IdentityKit.cache[i3 - 256].method538();
					if (model_3 != null) {
						aclass30_sub2_sub4_sub6s[j2++] = model_3;
					}
				}
				if (i3 >= 512) {
					Model model_4 = ItemDef.forID(i3 - 512).method196(anInt1702);
					if (model_4 != null) {
						aclass30_sub2_sub4_sub6s[j2++] = model_4;
					}
				}
			}

			model_1 = new Model(j2, aclass30_sub2_sub4_sub6s);
			for (int j3 = 0; j3 < 5; j3++) {
				if (anIntArray1700[j3] != 0) {
					model_1.method476(Game.anIntArrayArray1003[j3][0], Game.anIntArrayArray1003[j3][anIntArray1700[j3]]);
					if (j3 == 1) {
						model_1.method476(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j3]]);
					}
				}
			}

			model_1.method469();
			model_1.method479(64, 850, -30, -50, -30, true);
			referenceCache.removeFromCache(model_1, l);
			aLong1697 = l;
		}
		if (aBoolean1699) {
			return model_1;
		}
		Model model_2 = Model.aModel_1621;
		model_2.method464(model_1, Frames.method532(k) & Frames.method532(i1));
		if (k != -1 && i1 != -1) {
			model_2.method471(Sequences.anims[super.anim].vertices, i1, k);
		} else if (k != -1) {
			model_2.method470(k);
		}
		model_2.method466();
		model_2.anIntArrayArray1658 = null;
		model_2.anIntArrayArray1657 = null;
		return model_2;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	public int privelage;
	public Model method453() {
		if (!visible) {
			return null;
		}
		if (desc != null) {
			return desc.method160();
		}
		boolean flag = false;
		for (int i = 0; i < 12; i++) {
			int j = equipment[i];
			if (j >= 256 && j < 512 && !IdentityKit.cache[j - 256].method539()) {
				flag = true;
			}
			if (j >= 512 && !ItemDef.forID(j - 512).method192(anInt1702)) {
				flag = true;
			}
		}

		if (flag) {
			return null;
		}
		Model[] aclass30_sub2_sub4_sub6s = new Model[12];
		int k = 0;
		for (int l = 0; l < 12; l++) {
			int i1 = equipment[l];
			if (i1 >= 256 && i1 < 512) {
				Model model_1 = IdentityKit.cache[i1 - 256].method540();
				if (model_1 != null) {
					aclass30_sub2_sub4_sub6s[k++] = model_1;
				}
			}
			if (i1 >= 512) {
				Model model_2 = ItemDef.forID(i1 - 512).method194(anInt1702);
				if (model_2 != null) {
					aclass30_sub2_sub4_sub6s[k++] = model_2;
				}
			}
		}

		Model model = new Model(k, aclass30_sub2_sub4_sub6s);
		for (int j1 = 0; j1 < 5; j1++) {
			if (anIntArray1700[j1] != 0) {
				model.method476(Game.anIntArrayArray1003[j1][0], Game.anIntArrayArray1003[j1][anIntArray1700[j1]]);
				if (j1 == 1) {
					model.method476(Game.anIntArray1204[0], Game.anIntArray1204[anIntArray1700[j1]]);
				}
			}
		}

		return model;
	}

	public Player() {
		aLong1697 = -1L;
		aBoolean1699 = false;
		anIntArray1700 = new int[5];
		visible = false;
		equipment = new int[12];
	}

	private long aLong1697;
	public EntityDef desc;
	public boolean aBoolean1699;
	public final int[] anIntArray1700;
	public int team;
	private int anInt1702;
	public String name;
	public static ReferenceCache referenceCache = new ReferenceCache(260);
	public int combatLevel;
	public int headIcon;
	public int skullIcon;
	public int hintIcon;
	public int objectStartCycle;
	public int anInt1708;
	public int z;
	public boolean visible;
	public int anInt1711;
	public int anInt1712;
	public int anInt1713;
	public Model aModel_1714;
	public final int[] equipment;
	private long aLong1718;
	public int anInt1719;
	public int anInt1720;
	public int anInt1721;
	public int anInt1722;
	public int skill;

}
