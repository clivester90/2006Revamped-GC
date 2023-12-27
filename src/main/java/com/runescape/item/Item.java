package com.runescape.item;

import com.runescape.world.model.Model;
import com.runescape.world.scene.SceneNode;

public class Item extends SceneNode {

	@Override
	public final Model getRotatedModel() {
		ItemDef itemDef = ItemDef.forID(ID);
		return itemDef.method201(anInt1559);
	}

	public Item() {
	}

	public int ID;
	public int x;
	public int y;
	public int anInt1559;
}
