package com.runescape.entity;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.animation.Sequences;
import com.runescape.animation.SpotAnim;
import com.runescape.world.model.Model;
import com.runescape.world.model.frames.Frames;

public final class NPC extends Entity {

	private Model getBuiltModel() {
		if (super.anim >= 0 && super.sequenceDelayCycle == 0) {
			int k = Sequences.anims[super.anim].primary[super.anInt1527];
			int i1 = -1;
			if (super.anInt1517 >= 0 && super.anInt1517 != super.anInt1511) {
				i1 = Sequences.anims[super.anInt1517].primary[super.anInt1518];
			}
			return desc.method164(i1, k, Sequences.anims[super.anim].vertices);
		}
		int l = -1;
		if (super.anInt1517 >= 0) {
			l = Sequences.anims[super.anInt1517].primary[super.anInt1518];
		}
		return desc.method164(-1, l, null);
	}

	@Override
	public Model getRotatedModel() {
		if (desc == null) {
			return null;
		}
		Model model = getBuiltModel();
		if (model == null) {
			return null;
		}
		super.height = model.modelHeight;
		if (super.anInt1520 != -1 && super.anInt1521 != -1) {
			SpotAnim spotAnim = SpotAnim.cache[super.anInt1520];
			Model model_1 = spotAnim.getModel();
			if (model_1 != null) {
				int j = spotAnim.sequencesSequence.primary[super.anInt1521];
				Model model_2 = new Model(true, Frames.method532(j), false, model_1);
				model_2.method475(0, -super.anInt1524, 0);
				model_2.method469();
				model_2.method470(j);
				model_2.anIntArrayArray1658 = null;
				model_2.anIntArrayArray1657 = null;
				if (spotAnim.scale != 128 || spotAnim.height != 128) {
					model_2.scaleModel(spotAnim.scale, spotAnim.scale, spotAnim.height);
				}
				model_2.method479(64 + spotAnim.ambient, 850 + spotAnim.contrast, -30, -50, -30, true);
				Model[] aModel = {model, model_2};
				model = new Model(aModel);
			}
		}
		if (desc.aByte68 == 1) {
			model.aBoolean1659 = true;
		}
		return model;
	}

	@Override
	public boolean isVisible() {
		return desc != null;
	}

	public NPC() {
	}

	public EntityDef desc;
}
