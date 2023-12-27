package com.runescape.world.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.world.model.Model;
import com.runescape.core.node.NodeSub;
import com.runescape.world.model.Vertex;

public class SceneNode extends NodeSub {

	public void draw(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2) {
		Model model = getRotatedModel();
		if (model != null) {
			modelHeight = model.modelHeight;
			model.draw(i, j, k, l, i1, j1, k1, l1, i2);
		}
	}

	public Model getRotatedModel() {
		return null;
	}

	public SceneNode() {
		modelHeight = 1000;
	}

	public Vertex[] normals;
	public int modelHeight;
}
