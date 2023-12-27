package com.runescape.world.object;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.core.node.Node;

public final class TemporaryObject extends Node {

	public TemporaryObject() {
		cycle = -1;
	}

	public int objectId;
	public int rotation;
	public int instancedHeight;
	public int cycle;
	public int plane;
	public int classType;
	public int x;
	public int y;
	public int locIndex;
	public int locRotation;
	public int locType;
	public int spawnCycle;
}
