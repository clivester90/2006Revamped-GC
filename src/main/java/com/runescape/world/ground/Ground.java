package com.runescape.world.ground;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.item.ItemPile;
import com.runescape.core.node.Node;
import com.runescape.world.object.GameObject;
import com.runescape.world.wall.WallDecoration;
import com.runescape.world.wall.WallLoc;

public final class Ground extends Node {

	public Ground(int i, int j, int k) {
		objects = new GameObject[5];
		objectFlags = new int[5];
		plane = z = i;
		x = j;
		y = k;
	}

	public int z;
	public final int x;
	public final int y;
	public final int plane;
	public TileUnderlay underlay;
	public ShapedTile overlay;
	public WallLoc obj1;
	public WallDecoration wallDecoration;
	public GroundDecoration groundDecoration;
	public ItemPile itemPile;
	public int objectCount;
	public final GameObject[] objects;
	public final int[] objectFlags;
	public int anInt1320;
	public int anInt1321;
	public boolean aBoolean1322;
	public boolean aBoolean1323;
	public boolean aBoolean1324;
	public int anInt1325;
	public int anInt1326;
	public int anInt1327;
	public int anInt1328;
	public Ground ground;
}
