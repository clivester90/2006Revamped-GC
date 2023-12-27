package com.runescape.world.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.world.model.frames.Frames;
import com.runescape.world.model.Model;
import com.runescape.animation.SpotAnim;

public final class SceneProjectile extends SceneNode {

	public void update(int i, int j, int k, int l) {
		if (!isMobile) {
			double d = l - x0;
			double d2 = j - y0;
			double d3 = Math.sqrt(d * d + d2 * d2);
			startX = x0 + d * arcScale / d3;
			startY = y0 + d2 * arcScale / d3;
			startElevation = z0;
		}
		double d1 = endCycle + 1 - i;
		velocityX = (l - startX) / d1;
		velocityY = (j - startY) / d1;
		velocity = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
		if (!isMobile) {
			velocityZ = -velocity * Math.tan(elevationPitch * 0.02454369D);
		}
		accelerationZ = 2D * (k - startElevation - velocityZ * d1) / (d1 * d1);
	}

	@Override
	public Model getRotatedModel() {
		Model model = spotanim.getModel();
		if (model == null) {
			return null;
		}
		int j = -1;
		if (spotanim.sequencesSequence != null) {
			j = spotanim.sequencesSequence.primary[anInt1593];
		}
		Model model_1 = new Model(true, Frames.method532(j), false, model);
		if (j != -1) {
			model_1.method469();
			model_1.method470(j);
			model_1.anIntArrayArray1658 = null;
			model_1.anIntArrayArray1657 = null;
		}
		if (spotanim.scale != 128 || spotanim.height != 128) {
			model_1.scaleModel(spotanim.scale, spotanim.scale, spotanim.height);
		}
		model_1.method474(pitch);
		model_1.method479(64 + spotanim.ambient, 850 + spotanim.contrast, -30, -50, -30, true);
		return model_1;
	}

	public SceneProjectile(int i, int j, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2) {
		isMobile = false;
		spotanim = SpotAnim.cache[l2];
		plane = k1;
		x0 = j2;
		y0 = i2;
		z0 = l1;
		anInt1571 = l;
		endCycle = i1;
		elevationPitch = i;
		arcScale = j1;
		target = k2;
		anInt1583 = j;
		isMobile = false;
	}

	public void update(int i) {
		isMobile = true;
		startX += velocityX * i;
		startY += velocityY * i;
		startElevation += velocityZ * i + 0.5D * accelerationZ * i * i;
		velocityZ += accelerationZ * i;
		yaw = (int) (Math.atan2(velocityX, velocityY) * 325.94900000000001D) + 1024 & 0x7ff;
		pitch = (int) (Math.atan2(velocityZ, velocity) * 325.94900000000001D) & 0x7ff;
		if (spotanim.sequencesSequence != null) {
			for (anInt1594 += i; anInt1594 > spotanim.sequencesSequence.getDuration(anInt1593);) {
				anInt1594 -= spotanim.sequencesSequence.getDuration(anInt1593) + 1;
				anInt1593++;
				if (anInt1593 >= spotanim.sequencesSequence.length) {
					anInt1593 = 0;
				}
			}
		}

	}

	public final int anInt1571;
	public final int endCycle;
	private double velocityX;
	private double velocityY;
	private double velocity;
	private double velocityZ;
	private double accelerationZ;
	private boolean isMobile;
	private final int x0;
	private final int y0;
	private final int z0;
	public final int anInt1583;
	public double startX;
	public double startY;
	public double startElevation;
	private final int elevationPitch;
	private final int arcScale;
	public final int target;
	private final SpotAnim spotanim;
	private int anInt1593;
	private int anInt1594;
	public int yaw;
	private int pitch;
	public final int plane;
}
