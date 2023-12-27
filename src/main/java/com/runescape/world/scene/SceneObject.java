package com.runescape.world.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.animation.Sequences;
import com.Game;
import com.runescape.world.model.Model;
import com.runescape.world.object.ObjectDefinition;
import com.runescape.world.varp.VarBit;

public final class SceneObject extends SceneNode {

	@Override
	public Model getRotatedModel() {
		int j = -1;
		if (sequence != null) {
			int k = Game.loopCycle - cycle;
			if (k > 100 && sequence.padding > 0) {
				k = 100;
			}
			while (k > sequence.getDuration(sequenceFrame)) {
				k -= sequence.getDuration(sequenceFrame);
				sequenceFrame++;
				if (sequenceFrame < sequence.length) {
					continue;
				}
				sequenceFrame -= sequence.padding;
				if (sequenceFrame >= 0 && sequenceFrame < sequence.length) {
					continue;
				}
				sequence = null;
				break;
			}
			cycle = Game.loopCycle - k;
			if (sequence != null) {
				j = sequence.primary[sequenceFrame];
			}
		}
		ObjectDefinition class46;
		if (overrides != null) {
			class46 = method457();
		} else {
			class46 = ObjectDefinition.forID(index);
		}
		if (class46 == null) {
			return null;
		} else {
			return class46.getAdjustedModel(type, rotation, swZ, seZ, neZ, nwZ, j);
		}
	}

	private ObjectDefinition method457() {
		int i = -1;
		if (varbit != -1) {
			VarBit varBit = VarBit.cache[varbit];
			int k = varBit.setting;
			int l = varBit.startbit;
			int i1 = varBit.endbit;
			int j1 = Game.BIT_MASK[i1 - l];
			i = clientInstance.variousSettings[k] >> l & j1;
		} else if (setting != -1) {
			i = clientInstance.variousSettings[setting];
		}
		if (i < 0 || i >= overrides.length || overrides[i] == -1) {
			return null;
		} else {
			return ObjectDefinition.forID(overrides[i]);
		}
	}

	public SceneObject(int i, int j, int k, int l, int i1, int j1, int k1, int l1, boolean flag) {
		index = i;
		type = k;
		rotation = j;
		swZ = j1;
		seZ = l;
		neZ = i1;
		nwZ = k1;
		if (l1 != -1) {
			sequence = Sequences.anims[l1];
			sequenceFrame = 0;
			cycle = Game.loopCycle;
			if (flag && sequence.padding != -1) {
				sequenceFrame = (int) (Math.random() * sequence.length);
				cycle -= (int) (Math.random() * sequence.getDuration(sequenceFrame));
			}
		}
		ObjectDefinition class46 = ObjectDefinition.forID(index);
		varbit = class46.varbit;
		setting = class46.setting;
		overrides = class46.childrenIDs;
	}

	private int sequenceFrame;
	private final int[] overrides;
	private final int varbit;
	private final int setting;
	private final int swZ;
	private final int seZ;
	private final int neZ;
	private final int nwZ;
	private Sequences sequence;
	private int cycle;
	public static Game clientInstance;
	private final int index;
	private final int type;
	private final int rotation;
}
