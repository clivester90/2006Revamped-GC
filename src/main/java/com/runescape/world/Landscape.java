package com.runescape.world;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

import com.runescape.item.ItemPile;
import com.runescape.graphics.DrawingArea;
import com.runescape.graphics.Texture;
import com.runescape.core.node.Deque;
import com.runescape.world.ground.Ground;
import com.runescape.world.ground.GroundDecoration;
import com.runescape.world.ground.ShapedTile;
import com.runescape.world.ground.TileUnderlay;
import com.runescape.world.model.Model;
import com.runescape.world.model.Vertex;
import com.runescape.world.scene.SceneCluster;
import com.runescape.world.scene.SceneNode;
import com.runescape.world.object.GameObject;
import com.runescape.world.wall.WallDecoration;
import com.runescape.world.wall.WallLoc;

public final class Landscape {

	public Landscape(int[][][] ai) {
		int i = 104;// was parameter
		int j = 104;// was parameter
		int k = 4;// was parameter
		aBoolean434 = true;
		obj5Cache = new GameObject[5000];
		anIntArray486 = new int[10000];
		anIntArray487 = new int[10000];
		sizeZ = k;
		sizeX = j;
		sizeY = i;
		groundArray = new Ground[k][j][i];
		cycleMap = new int[k][j + 1][i + 1];
		heightMap = ai;
		initToNull();
	}

	public static void nullLoader() {
		aClass28Array462 = null;
		sceneClusterCounts = null;
		sceneClusters = null;
		tileDeque = null;
		TileVisibilityMaps = null;
		cullMap = null;
	}

	public void initToNull() {
		for (int j = 0; j < sizeZ; j++) {
			for (int k = 0; k < sizeX; k++) {
				for (int i1 = 0; i1 < sizeY; i1++) {
					groundArray[j][k][i1] = null;
				}

			}

		}
		for (int l = 0; l < cullingClusterPlaneCount; l++) {
			for (int j1 = 0; j1 < sceneClusterCounts[l]; j1++) {
				sceneClusters[l][j1] = null;
			}

			sceneClusterCounts[l] = 0;
		}

		for (int k1 = 0; k1 < obj5CacheCurrPos; k1++) {
			obj5Cache[k1] = null;
		}

		obj5CacheCurrPos = 0;
		for (int l1 = 0; l1 < aClass28Array462.length; l1++) {
			aClass28Array462[l1] = null;
		}

	}

	public void setPlane(int i) {
		anInt442 = i;
		for (int k = 0; k < sizeX; k++) {
			for (int l = 0; l < sizeY; l++) {
				if (groundArray[i][k][l] == null) {
					groundArray[i][k][l] = new Ground(i, k, l);
				}
			}

		}

	}

	public void addBridge(int i, int j) {
		Ground class30_sub3 = groundArray[0][j][i];
		for (int l = 0; l < 3; l++) {
			Ground class30_sub3_1 = groundArray[l][j][i] = groundArray[l + 1][j][i];
			if (class30_sub3_1 != null) {
				class30_sub3_1.z--;
				for (int j1 = 0; j1 < class30_sub3_1.objectCount; j1++) {
					GameObject class28 = class30_sub3_1.objects[j1];
					if ((class28.uid >> 29 & 3) == 2 && class28.localX0 == j && class28.localY0 == i) {
						class28.plane--;
					}
				}

			}
		}
		if (groundArray[0][j][i] == null) {
			groundArray[0][j][i] = new Ground(0, j, i);
		}
		groundArray[0][j][i].ground = class30_sub3;
		groundArray[3][j][i] = null;
	}

	public static void createNewSceneCluster(int i, int j, int k, int l, int i1, int j1, int l1, int i2) {
		SceneCluster sceneCluster = new SceneCluster();
		sceneCluster.localMinX = j / 128;
		sceneCluster.localMaxX = l / 128;
		sceneCluster.localMinY = l1 / 128;
		sceneCluster.localMaxY = i1 / 128;
		sceneCluster.type = i2;
		sceneCluster.minX = j;
		sceneCluster.maxX = l;
		sceneCluster.minY = l1;
		sceneCluster.maxY = i1;
		sceneCluster.maxZ = j1;
		sceneCluster.minZ = k;
		sceneClusters[i][sceneClusterCounts[i]++] = sceneCluster;
	}

	public void setTopPlane(int i, int j, int k, int l) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 != null) {
			groundArray[i][j][k].anInt1321 = l;
		}
	}

	public void addTile(int i, int j, int k, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2, int l2, int i3, int j3, int k3, int l3, int i4, int j4, int k4, int l4) {
		if (l == 0) {
			TileUnderlay tileUnderlay = new TileUnderlay(k2, l2, i3, j3, -1, k4, false);
			for (int i5 = i; i5 >= 0; i5--) {
				if (groundArray[i5][j][k] == null) {
					groundArray[i5][j][k] = new Ground(i5, j, k);
				}
			}

			groundArray[i][j][k].underlay = tileUnderlay;
			return;
		}
		if (l == 1) {
			TileUnderlay tileUnderlay_1 = new TileUnderlay(k3, l3, i4, j4, j1, l4, k1 == l1 && k1 == i2 && k1 == j2);
			for (int j5 = i; j5 >= 0; j5--) {
				if (groundArray[j5][j][k] == null) {
					groundArray[j5][j][k] = new Ground(j5, j, k);
				}
			}

			groundArray[i][j][k].underlay = tileUnderlay_1;
			return;
		}
		ShapedTile shapedTile = new ShapedTile(k, k3, j3, i2, j1, i4, i1, k2, k4, i3, j2, l1, k1, l, j4, l3, l2, j, l4);
		for (int k5 = i; k5 >= 0; k5--) {
			if (groundArray[k5][j][k] == null) {
				groundArray[k5][j][k] = new Ground(k5, j, k);
			}
		}

		groundArray[i][j][k].overlay = shapedTile;
	}

	public void addGroundDecoration(int i, int j, int k, SceneNode class30_sub2_sub4, byte byte0, int i1, int j1) {
		if (class30_sub2_sub4 == null) {
			return;
		}
		GroundDecoration class49 = new GroundDecoration();
		class49.node = class30_sub2_sub4;
		class49.x = j1 * 128 + 64;
		class49.y = k * 128 + 64;
		class49.z = j;
		class49.uid = i1;
		class49.arrangement = byte0;
		if (groundArray[i][j1][k] == null) {
			groundArray[i][j1][k] = new Ground(i, j1, k);
		}
		groundArray[i][j1][k].groundDecoration = class49;
	}

	public void addItemPile(int i, int j, SceneNode class30_sub2_sub4, int k, SceneNode class30_sub2_sub4_1, SceneNode class30_sub2_sub4_2, int l, int i1) {
		ItemPile itemPile = new ItemPile();
		itemPile.top = class30_sub2_sub4_2;
		itemPile.x = i * 128 + 64;
		itemPile.y = i1 * 128 + 64;
		itemPile.z = k;
		itemPile.uid = j;
		itemPile.bottom = class30_sub2_sub4;
		itemPile.middle = class30_sub2_sub4_1;
		int j1 = 0;
		Ground class30_sub3 = groundArray[l][i][i1];
		if (class30_sub3 != null) {
			for (int k1 = 0; k1 < class30_sub3.objectCount; k1++) {
				if (class30_sub3.objects[k1].node instanceof Model) {
					int l1 = ((Model) class30_sub3.objects[k1].node).anInt1654;
					if (l1 > j1) {
						j1 = l1;
					}
				}
			}

		}
		itemPile.offsetZ = j1;
		if (groundArray[l][i][i1] == null) {
			groundArray[l][i][i1] = new Ground(l, i, i1);
		}
		groundArray[l][i][i1].itemPile = itemPile;
	}

	public void addWall(int i, SceneNode class30_sub2_sub4, int j, int k, byte byte0, int l, SceneNode class30_sub2_sub4_1, int i1, int j1, int k1) {
		if (class30_sub2_sub4 == null && class30_sub2_sub4_1 == null) {
			return;
		}
		WallLoc wallLoc = new WallLoc();
		wallLoc.uid = j;
		wallLoc.arrangement = byte0;
		wallLoc.x = l * 128 + 64;
		wallLoc.z = k * 128 + 64;
		wallLoc.y = i1;
		wallLoc.root = class30_sub2_sub4;
		wallLoc.extension = class30_sub2_sub4_1;
		wallLoc.orientation = i;
		wallLoc.orientation1 = j1;
		for (int l1 = k1; l1 >= 0; l1--) {
			if (groundArray[l1][l][k] == null) {
				groundArray[l1][l][k] = new Ground(l1, l, k);
			}
		}

		groundArray[k1][l][k].obj1 = wallLoc;
	}

	public void addWallDecoration(int i, int j, int k, int i1, int j1, int k1, SceneNode class30_sub2_sub4, int l1, byte byte0, int i2, int j2) {
		if (class30_sub2_sub4 == null) {
			return;
		}
		WallDecoration class26 = new WallDecoration();
		class26.uid = i;
		class26.arrangement = byte0;
		class26.x = l1 * 128 + 64 + j1;
		class26.y = j * 128 + 64 + i2;
		class26.z = k1;
		class26.node = class30_sub2_sub4;
		class26.flags = j2;
		class26.rotation = k;
		for (int k2 = i1; k2 >= 0; k2--) {
			if (groundArray[k2][l1][j] == null) {
				groundArray[k2][l1][j] = new Ground(k2, l1, j);
			}
		}

		groundArray[i1][l1][j].wallDecoration = class26;
	}

	public boolean add(int i, byte byte0, int j, int k, SceneNode class30_sub2_sub4, int l, int i1, int j1, int k1, int l1) {
		if (class30_sub2_sub4 == null) {
			return true;
		} else {
			int i2 = l1 * 128 + 64 * l;
			int j2 = k1 * 128 + 64 * k;
			return add(i1, l1, k1, l, k, i2, j2, j, class30_sub2_sub4, j1, false, i, byte0);
		}
	}

	public boolean add(int i, int j, int k, int l, int i1, int j1, int k1, SceneNode class30_sub2_sub4, boolean flag) {
		if (class30_sub2_sub4 == null) {
			return true;
		}
		int l1 = k1 - j1;
		int i2 = i1 - j1;
		int j2 = k1 + j1;
		int k2 = i1 + j1;
		if (flag) {
			if (j > 640 && j < 1408) {
				k2 += 128;
			}
			if (j > 1152 && j < 1920) {
				j2 += 128;
			}
			if (j > 1664 || j < 384) {
				i2 -= 128;
			}
			if (j > 128 && j < 896) {
				l1 -= 128;
			}
		}
		l1 /= 128;
		i2 /= 128;
		j2 /= 128;
		k2 /= 128;
		return add(i, l1, i2, j2 - l1 + 1, k2 - i2 + 1, k1, i1, k, class30_sub2_sub4, j, true, l, (byte) 0);
	}

	public boolean add(int j, int k, SceneNode class30_sub2_sub4, int l, int i1, int j1, int k1, int l1, int i2, int j2, int k2) {
		return class30_sub2_sub4 == null || add(j, l1, k2, i2 - l1 + 1, i1 - k2 + 1, j1, k, k1, class30_sub2_sub4, l, true, j2, (byte) 0);
	}

	private boolean add(int i, int j, int k, int l, int i1, int j1, int k1, int l1, SceneNode class30_sub2_sub4, int i2, boolean flag, int j2, byte byte0) {
		for (int k2 = j; k2 < j + l; k2++) {
			for (int l2 = k; l2 < k + i1; l2++) {
				if (k2 < 0 || l2 < 0 || k2 >= sizeX || l2 >= sizeY) {
					return false;
				}
				Ground class30_sub3 = groundArray[i][k2][l2];
				if (class30_sub3 != null && class30_sub3.objectCount >= 5) {
					return false;
				}
			}

		}

		GameObject class28 = new GameObject();
		class28.uid = j2;
		class28.arrangement = byte0;
		class28.plane = i;
		class28.x = j1;
		class28.y = k1;
		class28.z = l1;
		class28.node = class30_sub2_sub4;
		class28.rotation = i2;
		class28.localX0 = j;
		class28.localY0 = k;
		class28.localX1 = j + l - 1;
		class28.localY1 = k + i1 - 1;
		for (int i3 = j; i3 < j + l; i3++) {
			for (int j3 = k; j3 < k + i1; j3++) {
				int k3 = 0;
				if (i3 > j) {
					k3++;
				}
				if (i3 < j + l - 1) {
					k3 += 4;
				}
				if (j3 > k) {
					k3 += 8;
				}
				if (j3 < k + i1 - 1) {
					k3 += 2;
				}
				for (int l3 = i; l3 >= 0; l3--) {
					if (groundArray[l3][i3][j3] == null) {
						groundArray[l3][i3][j3] = new Ground(l3, i3, j3);
					}
				}

				Ground class30_sub3_1 = groundArray[i][i3][j3];
				class30_sub3_1.objects[class30_sub3_1.objectCount] = class28;
				class30_sub3_1.objectFlags[class30_sub3_1.objectCount] = k3;
				class30_sub3_1.anInt1320 |= k3;
				class30_sub3_1.objectCount++;
			}

		}

		if (flag) {
			obj5Cache[obj5CacheCurrPos++] = class28;
		}
		return true;
	}

	public void clearObj5Cache() {
		for (int i = 0; i < obj5CacheCurrPos; i++) {
			GameObject gameObject = obj5Cache[i];
			remove(gameObject);
			obj5Cache[i] = null;
		}

		obj5CacheCurrPos = 0;
	}

	private void remove(GameObject class28) {
		for (int j = class28.localX0; j <= class28.localX1; j++) {
			for (int k = class28.localY0; k <= class28.localY1; k++) {
				Ground class30_sub3 = groundArray[class28.plane][j][k];
				if (class30_sub3 != null) {
					for (int l = 0; l < class30_sub3.objectCount; l++) {
						if (class30_sub3.objects[l] != class28) {
							continue;
						}
						class30_sub3.objectCount--;
						for (int i1 = l; i1 < class30_sub3.objectCount; i1++) {
							class30_sub3.objects[i1] = class30_sub3.objects[i1 + 1];
							class30_sub3.objectFlags[i1] = class30_sub3.objectFlags[i1 + 1];
						}

						class30_sub3.objects[class30_sub3.objectCount] = null;
						break;
					}

					class30_sub3.anInt1320 = 0;
					for (int j1 = 0; j1 < class30_sub3.objectCount; j1++) {
						class30_sub3.anInt1320 |= class30_sub3.objectFlags[j1];
					}

				}
			}

		}

	}

	public void setWallDecorationPadding(int i, int k, int l, int i1) {
		Ground class30_sub3 = groundArray[i1][l][i];
		if (class30_sub3 == null) {
			return;
		}
		WallDecoration class26 = class30_sub3.wallDecoration;
		if (class26 != null) {
			int j1 = l * 128 + 64;
			int k1 = i * 128 + 64;
			class26.x = j1 + (class26.x - j1) * k / 16;
			class26.y = k1 + (class26.y - k1) * k / 16;
		}
	}

	public void removeWall(int i, int j, int k, byte byte0) {
		Ground class30_sub3 = groundArray[j][i][k];
		if (byte0 != -119) {
			aBoolean434 = !aBoolean434;
		}
		if (class30_sub3 != null) {
			class30_sub3.obj1 = null;
		}
	}

	public void removeWallDecoration(int j, int k, int l) {
		Ground class30_sub3 = groundArray[k][l][j];
		if (class30_sub3 != null) {
			class30_sub3.wallDecoration = null;
		}
	}

	public void removeObjects(int i, int k, int l) {
		Ground class30_sub3 = groundArray[i][k][l];
		if (class30_sub3 == null) {
			return;
		}
		for (int j1 = 0; j1 < class30_sub3.objectCount; j1++) {
			GameObject class28 = class30_sub3.objects[j1];
			if ((class28.uid >> 29 & 3) == 2 && class28.localX0 == k && class28.localY0 == l) {
				remove(class28);
				return;
			}
		}

	}

	public void removeGroundDecoration(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][k][j];
		if (class30_sub3 == null) {
			return;
		}
		class30_sub3.groundDecoration = null;
	}

	public void removeItemPile(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 != null) {
			class30_sub3.itemPile = null;
		}
	}

	public WallLoc method296(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 == null) {
			return null;
		} else {
			return class30_sub3.obj1;
		}
	}

	public WallDecoration method297(int i, int k, int l) {
		Ground class30_sub3 = groundArray[l][i][k];
		if (class30_sub3 == null) {
			return null;
		} else {
			return class30_sub3.wallDecoration;
		}
	}

	public GameObject method298(int i, int j, int k) {
		Ground class30_sub3 = groundArray[k][i][j];
		if (class30_sub3 == null) {
			return null;
		}
		for (int l = 0; l < class30_sub3.objectCount; l++) {
			GameObject class28 = class30_sub3.objects[l];
			if ((class28.uid >> 29 & 3) == 2 && class28.localX0 == i && class28.localY0 == j) {
				return class28;
			}
		}
		return null;
	}

	public GroundDecoration method299(int i, int j, int k) {
		Ground class30_sub3 = groundArray[k][j][i];
		if (class30_sub3 == null || class30_sub3.groundDecoration == null) {
			return null;
		} else {
			return class30_sub3.groundDecoration;
		}
	}

	public int getWallUID(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 == null || class30_sub3.obj1 == null) {
			return 0;
		} else {
			return class30_sub3.obj1.uid;
		}
	}

	public int getWallDecorationUID(int i, int j, int l) {
		Ground class30_sub3 = groundArray[i][j][l];
		if (class30_sub3 == null || class30_sub3.wallDecoration == null) {
			return 0;
		} else {
			return class30_sub3.wallDecoration.uid;
		}
	}

	public int getObjectUID(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 == null) {
			return 0;
		}
		for (int l = 0; l < class30_sub3.objectCount; l++) {
			GameObject class28 = class30_sub3.objects[l];
			if ((class28.uid >> 29 & 3) == 2 && class28.localX0 == j && class28.localY0 == k) {
				return class28.uid;
			}
		}

		return 0;
	}

	public int getGroundDecorationUID(int i, int j, int k) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 == null || class30_sub3.groundDecoration == null) {
			return 0;
		} else {
			return class30_sub3.groundDecoration.uid;
		}
	}

	public int getArrangement(int i, int j, int k, int l) {
		Ground class30_sub3 = groundArray[i][j][k];
		if (class30_sub3 == null) {
			return -1;
		}
		if (class30_sub3.obj1 != null && class30_sub3.obj1.uid == l) {
			return class30_sub3.obj1.arrangement & 0xff;
		}
		if (class30_sub3.wallDecoration != null && class30_sub3.wallDecoration.uid == l) {
			return class30_sub3.wallDecoration.arrangement & 0xff;
		}
		if (class30_sub3.groundDecoration != null && class30_sub3.groundDecoration.uid == l) {
			return class30_sub3.groundDecoration.arrangement & 0xff;
		}
		for (int i1 = 0; i1 < class30_sub3.objectCount; i1++) {
			if (class30_sub3.objects[i1].uid == l) {
				return class30_sub3.objects[i1].arrangement & 0xff;
			}
		}

		return -1;
	}

	public void method305(int i, int k, int i1) {
		int j = 64;// was parameter
		int l = 768;// was parameter
		int j1 = (int) Math.sqrt(k * k + i * i + i1 * i1);
		int k1 = l * j1 >> 8;
		for (int l1 = 0; l1 < sizeZ; l1++) {
			for (int i2 = 0; i2 < sizeX; i2++) {
				for (int j2 = 0; j2 < sizeY; j2++) {
					Ground class30_sub3 = groundArray[l1][i2][j2];
					if (class30_sub3 != null) {
						WallLoc class10 = class30_sub3.obj1;
						if (class10 != null && class10.root != null && class10.root.normals != null) {
							method307(l1, 1, 1, i2, j2, (Model) class10.root);
							if (class10.extension != null && class10.extension.normals != null) {
								method307(l1, 1, 1, i2, j2, (Model) class10.extension);
								method308((Model) class10.root, (Model) class10.extension, 0, 0, 0, false);
								((Model) class10.extension).method480(j, k1, k, i, i1);
							}
							((Model) class10.root).method480(j, k1, k, i, i1);
						}
						for (int k2 = 0; k2 < class30_sub3.objectCount; k2++) {
							GameObject class28 = class30_sub3.objects[k2];
							if (class28 != null && class28.node != null && class28.node.normals != null) {
								method307(l1, class28.localX1 - class28.localX0 + 1, class28.localY1 - class28.localY0 + 1, i2, j2, (Model) class28.node);
								((Model) class28.node).method480(j, k1, k, i, i1);
							}
						}

						GroundDecoration class49 = class30_sub3.groundDecoration;
						if (class49 != null && class49.node.normals != null) {
							method306(i2, l1, (Model) class49.node, j2);
							((Model) class49.node).method480(j, k1, k, i, i1);
						}
					}
				}

			}

		}

	}

	private void method306(int i, int j, Model model, int k) {
		if (i < sizeX) {
			Ground class30_sub3 = groundArray[j][i + 1][k];
			if (class30_sub3 != null && class30_sub3.groundDecoration != null && class30_sub3.groundDecoration.node.normals != null) {
				method308(model, (Model) class30_sub3.groundDecoration.node, 128, 0, 0, true);
			}
		}
		if (k < sizeX) {
			Ground class30_sub3_1 = groundArray[j][i][k + 1];
			if (class30_sub3_1 != null && class30_sub3_1.groundDecoration != null && class30_sub3_1.groundDecoration.node.normals != null) {
				method308(model, (Model) class30_sub3_1.groundDecoration.node, 0, 0, 128, true);
			}
		}
		if (i < sizeX && k < sizeY) {
			Ground class30_sub3_2 = groundArray[j][i + 1][k + 1];
			if (class30_sub3_2 != null && class30_sub3_2.groundDecoration != null && class30_sub3_2.groundDecoration.node.normals != null) {
				method308(model, (Model) class30_sub3_2.groundDecoration.node, 128, 0, 128, true);
			}
		}
		if (i < sizeX && k > 0) {
			Ground class30_sub3_3 = groundArray[j][i + 1][k - 1];
			if (class30_sub3_3 != null && class30_sub3_3.groundDecoration != null && class30_sub3_3.groundDecoration.node.normals != null) {
				method308(model, (Model) class30_sub3_3.groundDecoration.node, 128, 0, -128, true);
			}
		}
	}

	private void method307(int i, int j, int k, int l, int i1, Model model) {
		boolean flag = true;
		int j1 = l;
		int k1 = l + j;
		int l1 = i1 - 1;
		int i2 = i1 + k;
		for (int j2 = i; j2 <= i + 1; j2++) {
			if (j2 != sizeZ) {
				for (int k2 = j1; k2 <= k1; k2++) {
					if (k2 >= 0 && k2 < sizeX) {
						for (int l2 = l1; l2 <= i2; l2++) {
							if (l2 >= 0 && l2 < sizeY && (!flag || k2 >= k1 || l2 >= i2 || l2 < i1 && k2 != l)) {
								Ground class30_sub3 = groundArray[j2][k2][l2];
								if (class30_sub3 != null) {
									int i3 = (heightMap[j2][k2][l2] + heightMap[j2][k2 + 1][l2] + heightMap[j2][k2][l2 + 1] + heightMap[j2][k2 + 1][l2 + 1]) / 4 - (heightMap[i][l][i1] + heightMap[i][l + 1][i1] + heightMap[i][l][i1 + 1] + heightMap[i][l + 1][i1 + 1]) / 4;
									WallLoc class10 = class30_sub3.obj1;
									if (class10 != null && class10.root != null && class10.root.normals != null) {
										method308(model, (Model) class10.root, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
									}
									if (class10 != null && class10.extension != null && class10.extension.normals != null) {
										method308(model, (Model) class10.extension, (k2 - l) * 128 + (1 - j) * 64, i3, (l2 - i1) * 128 + (1 - k) * 64, flag);
									}
									for (int j3 = 0; j3 < class30_sub3.objectCount; j3++) {
										GameObject class28 = class30_sub3.objects[j3];
										if (class28 != null && class28.node != null && class28.node.normals != null) {
											int k3 = class28.localX1 - class28.localX0 + 1;
											int l3 = class28.localY1 - class28.localY0 + 1;
											method308(model, (Model) class28.node, (class28.localX0 - l) * 128 + (k3 - j) * 64, i3, (class28.localY0 - i1) * 128 + (l3 - k) * 64, flag);
										}
									}

								}
							}
						}

					}
				}

				j1--;
				flag = false;
			}
		}

	}

	private void method308(Model model, Model model_1, int i, int j, int k, boolean flag) {
		anInt488++;
		int l = 0;
		int[] ai = model_1.vertexX;
		int i1 = model_1.anInt1626;
		for (int j1 = 0; j1 < model.anInt1626; j1++) {
			Vertex vertex = model.normals[j1];
			Vertex vertex_1 = model.aVertexArray1660[j1];
			if (vertex_1.w != 0) {
				int i2 = model.vertexY[j1] - j;
				if (i2 <= model_1.anInt1651) {
					int j2 = model.vertexX[j1] - i;
					if (j2 >= model_1.anInt1646 && j2 <= model_1.anInt1647) {
						int k2 = model.vertexZ[j1] - k;
						if (k2 >= model_1.anInt1649 && k2 <= model_1.anInt1648) {
							for (int l2 = 0; l2 < i1; l2++) {
								Vertex vertex_2 = model_1.normals[l2];
								Vertex vertex_3 = model_1.aVertexArray1660[l2];
								if (j2 == ai[l2] && k2 == model_1.vertexZ[l2] && i2 == model_1.vertexY[l2] && vertex_3.w != 0) {
									vertex.x += vertex_3.x;
									vertex.y += vertex_3.y;
									vertex.z += vertex_3.z;
									vertex.w += vertex_3.w;
									vertex_2.x += vertex_1.x;
									vertex_2.y += vertex_1.y;
									vertex_2.z += vertex_1.z;
									vertex_2.w += vertex_1.w;
									l++;
									anIntArray486[j1] = anInt488;
									anIntArray487[l2] = anInt488;
								}
							}

						}
					}
				}
			}
		}

		if (l < 3 || !flag) {
			return;
		}
		for (int k1 = 0; k1 < model.anInt1630; k1++) {
			if (anIntArray486[model.anIntArray1631[k1]] == anInt488 && anIntArray486[model.anIntArray1632[k1]] == anInt488 && anIntArray486[model.anIntArray1633[k1]] == anInt488) {
				model.anIntArray1637[k1] = -1;
			}
		}

		for (int l1 = 0; l1 < model_1.anInt1630; l1++) {
			if (anIntArray487[model_1.anIntArray1631[l1]] == anInt488 && anIntArray487[model_1.anIntArray1632[l1]] == anInt488 && anIntArray487[model_1.anIntArray1633[l1]] == anInt488) {
				model_1.anIntArray1637[l1] = -1;
			}
		}

	}

	public void drawMinimapTile(int[] ai, int i, int k, int l, int i1) {
		int j = 512;// was parameter
		Ground class30_sub3 = groundArray[k][l][i1];
		if (class30_sub3 == null) {
			return;
		}
		TileUnderlay tileUnderlay = class30_sub3.underlay;
		if (tileUnderlay != null) {
			int j1 = tileUnderlay.rgb;
			if (j1 == 0) {
				return;
			}
			for (int k1 = 0; k1 < 4; k1++) {
				ai[i] = j1;
				ai[i + 1] = j1;
				ai[i + 2] = j1;
				ai[i + 3] = j1;
				i += j;
			}

			return;
		}
		ShapedTile shapedTile = class30_sub3.overlay;
		if (shapedTile == null) {
			return;
		}
		int l1 = shapedTile.anInt684;
		int i2 = shapedTile.anInt685;
		int j2 = shapedTile.anInt686;
		int k2 = shapedTile.anInt687;
		int[] ai1 = MINIMAP_OVERLAY_MASK[l1];
		int[] ai2 = MINIMAP_ROTATION_MASK[i2];
		int l2 = 0;
		if (j2 != 0) {
			for (int i3 = 0; i3 < 4; i3++) {
				ai[i] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 1] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 2] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				ai[i + 3] = ai1[ai2[l2++]] != 0 ? k2 : j2;
				i += j;
			}

			return;
		}
		for (int j3 = 0; j3 < 4; j3++) {
			if (ai1[ai2[l2++]] != 0) {
				ai[i] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 1] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 2] = k2;
			}
			if (ai1[ai2[l2++]] != 0) {
				ai[i + 3] = k2;
			}
			i += j;
		}

	}

	public static void method310(int i, int j, int k, int l, int[] ai) {
		viewportLeft = 0;
		viewportTop = 0;
		screenWidth = k;
		screenHeight = l;
		screenCenterX = k / 2;
		screenCenterY = l / 2;
		boolean[][][][] aflag = new boolean[9][32][53][53];
		for (int i1 = 128; i1 <= 384; i1 += 32) {
			for (int j1 = 0; j1 < 2048; j1 += 64) {
				camUpDownY = Model.modelIntArray1[i1];
				camUpDownX = Model.modelIntArray2[i1];
				camLeftRightY = Model.modelIntArray1[j1];
				camLeftRightX = Model.modelIntArray2[j1];
				int l1 = (i1 - 128) / 32;
				int j2 = j1 / 64;
				for (int l2 = -26; l2 <= 26; l2++) {
					for (int j3 = -26; j3 <= 26; j3++) {
						int k3 = l2 * 128;
						int i4 = j3 * 128;
						boolean flag2 = false;
						for (int k4 = -i; k4 <= j; k4 += 128) {
							if (!method311(ai[l1] + k4, i4, k3)) {
								continue;
							}
							flag2 = true;
							break;
						}

						aflag[l1][j2][l2 + 25 + 1][j3 + 25 + 1] = flag2;
					}

				}

			}

		}

		for (int k1 = 0; k1 < 8; k1++) {
			for (int i2 = 0; i2 < 32; i2++) {
				for (int k2 = -25; k2 < 25; k2++) {
					for (int i3 = -25; i3 < 25; i3++) {
						boolean flag1 = false;
						label0 : for (int l3 = -1; l3 <= 1; l3++) {
							for (int j4 = -1; j4 <= 1; j4++) {
								if (aflag[k1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else if (aflag[k1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else if (aflag[k1 + 1][i2][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
									flag1 = true;
								} else {
									if (!aflag[k1 + 1][(i2 + 1) % 31][k2 + l3 + 25 + 1][i3 + j4 + 25 + 1]) {
										continue;
									}
									flag1 = true;
								}
								break label0;
							}

						}

						TileVisibilityMaps[k1][i2][k2 + 25][i3 + 25] = flag1;
					}

				}

			}

		}

	}

	private static boolean method311(int i, int j, int k) {
		int l = j * camLeftRightY + k * camLeftRightX >> 16;
		int i1 = j * camLeftRightX - k * camLeftRightY >> 16;
		int j1 = i * camUpDownY + i1 * camUpDownX >> 16;
		int k1 = i * camUpDownX - i1 * camUpDownY >> 16;
		if (j1 < 50 || j1 > 3500) {
			return false;
		}
		int l1 = screenCenterX + (l << 9) / j1;
		int i2 = screenCenterY + (k1 << 9) / j1;
		return l1 >= viewportLeft && l1 <= screenWidth && i2 >= viewportTop && i2 <= screenHeight;
	}

	public void method312(int i, int j) {
		inputRequested = true;
		clickScreenX = j;
		clickScreenY = i;
		clickLocalX = -1;
		clickedTileY = -1;
	}

	public void method313(int i, int j, int k, int l, int i1, int j1) {
		if (i < 0) {
			i = 0;
		} else if (i >= sizeX * 128) {
			i = sizeX * 128 - 1;
		}
		if (j < 0) {
			j = 0;
		} else if (j >= sizeY * 128) {
			j = sizeY * 128 - 1;
		}
		cycle++;
		camUpDownY = Model.modelIntArray1[j1];
		camUpDownX = Model.modelIntArray2[j1];
		camLeftRightY = Model.modelIntArray1[k];
		camLeftRightX = Model.modelIntArray2[k];
		cullMap = TileVisibilityMaps[(j1 - 128) / 32][k / 64];
		cameraX = i;
		cameraY = l;
		cameraZ = j;
		cameraLocalX = i / 128;
		cameraLocalY = j / 128;
		topPlane = i1;
		minVisibleX = cameraLocalX - 25;
		if (minVisibleX < 0) {
			minVisibleX = 0;
		}
		minVisibleY = cameraLocalY - 25;
		if (minVisibleY < 0) {
			minVisibleY = 0;
		}
		maxVisibleX = cameraLocalX + 25;
		if (maxVisibleX > sizeX) {
			maxVisibleX = sizeX;
		}
		maxVisibleY = cameraLocalY + 25;
		if (maxVisibleY > sizeY) {
			maxVisibleY = sizeY;
		}
		method319();
		anInt446 = 0;
		for (int k1 = anInt442; k1 < sizeZ; k1++) {
			Ground[][] aclass30_sub3 = groundArray[k1];
			for (int i2 = minVisibleX; i2 < maxVisibleX; i2++) {
				for (int k2 = minVisibleY; k2 < maxVisibleY; k2++) {
					Ground class30_sub3 = aclass30_sub3[i2][k2];
					if (class30_sub3 != null) {
						if (class30_sub3.anInt1321 > i1 || !cullMap[i2 - cameraLocalX + 25][k2 - cameraLocalY + 25] && heightMap[k1][i2][k2] - l < 2000) {
							class30_sub3.aBoolean1322 = false;
							class30_sub3.aBoolean1323 = false;
							class30_sub3.anInt1325 = 0;
						} else {
							class30_sub3.aBoolean1322 = true;
							class30_sub3.aBoolean1323 = true;
							class30_sub3.aBoolean1324 = class30_sub3.objectCount > 0;
							anInt446++;
						}
					}
				}

			}

		}

		for (int l1 = anInt442; l1 < sizeZ; l1++) {
			Ground[][] aclass30_sub3_1 = groundArray[l1];
			for (int l2 = -25; l2 <= 0; l2++) {
				int i3 = cameraLocalX + l2;
				int k3 = cameraLocalX - l2;
				if (i3 >= minVisibleX || k3 < maxVisibleX) {
					for (int i4 = -25; i4 <= 0; i4++) {
						int k4 = cameraLocalY + i4;
						int i5 = cameraLocalY - i4;
						if (i3 >= minVisibleX) {
							if (k4 >= minVisibleY) {
								Ground class30_sub3_1 = aclass30_sub3_1[i3][k4];
								if (class30_sub3_1 != null && class30_sub3_1.aBoolean1322) {
									method314(class30_sub3_1, true);
								}
							}
							if (i5 < maxVisibleY) {
								Ground class30_sub3_2 = aclass30_sub3_1[i3][i5];
								if (class30_sub3_2 != null && class30_sub3_2.aBoolean1322) {
									method314(class30_sub3_2, true);
								}
							}
						}
						if (k3 < maxVisibleX) {
							if (k4 >= minVisibleY) {
								Ground class30_sub3_3 = aclass30_sub3_1[k3][k4];
								if (class30_sub3_3 != null && class30_sub3_3.aBoolean1322) {
									method314(class30_sub3_3, true);
								}
							}
							if (i5 < maxVisibleY) {
								Ground class30_sub3_4 = aclass30_sub3_1[k3][i5];
								if (class30_sub3_4 != null && class30_sub3_4.aBoolean1322) {
									method314(class30_sub3_4, true);
								}
							}
						}
						if (anInt446 == 0) {
							inputRequested = false;
							return;
						}
					}

				}
			}

		}

		for (int j2 = anInt442; j2 < sizeZ; j2++) {
			Ground[][] aclass30_sub3_2 = groundArray[j2];
			for (int j3 = -25; j3 <= 0; j3++) {
				int l3 = cameraLocalX + j3;
				int j4 = cameraLocalX - j3;
				if (l3 >= minVisibleX || j4 < maxVisibleX) {
					for (int l4 = -25; l4 <= 0; l4++) {
						int j5 = cameraLocalY + l4;
						int k5 = cameraLocalY - l4;
						if (l3 >= minVisibleX) {
							if (j5 >= minVisibleY) {
								Ground class30_sub3_5 = aclass30_sub3_2[l3][j5];
								if (class30_sub3_5 != null && class30_sub3_5.aBoolean1322) {
									method314(class30_sub3_5, false);
								}
							}
							if (k5 < maxVisibleY) {
								Ground class30_sub3_6 = aclass30_sub3_2[l3][k5];
								if (class30_sub3_6 != null && class30_sub3_6.aBoolean1322) {
									method314(class30_sub3_6, false);
								}
							}
						}
						if (j4 < maxVisibleX) {
							if (j5 >= minVisibleY) {
								Ground class30_sub3_7 = aclass30_sub3_2[j4][j5];
								if (class30_sub3_7 != null && class30_sub3_7.aBoolean1322) {
									method314(class30_sub3_7, false);
								}
							}
							if (k5 < maxVisibleY) {
								Ground class30_sub3_8 = aclass30_sub3_2[j4][k5];
								if (class30_sub3_8 != null && class30_sub3_8.aBoolean1322) {
									method314(class30_sub3_8, false);
								}
							}
						}
						if (anInt446 == 0) {
							inputRequested = false;
							return;
						}
					}

				}
			}

		}

		inputRequested = false;
	}

	private void method314(Ground class30_sub3, boolean flag) {
		tileDeque.insertHead(class30_sub3);
		do {
			Ground class30_sub3_1;
			do {
				class30_sub3_1 = (Ground) tileDeque.popHead();
				if (class30_sub3_1 == null) {
					return;
				}
			} while (!class30_sub3_1.aBoolean1323);
			int i = class30_sub3_1.x;
			int j = class30_sub3_1.y;
			int k = class30_sub3_1.z;
			int l = class30_sub3_1.plane;
			Ground[][] aclass30_sub3 = groundArray[k];
			if (class30_sub3_1.aBoolean1322) {
				if (flag) {
					if (k > 0) {
						Ground class30_sub3_2 = groundArray[k - 1][i][j];
						if (class30_sub3_2 != null && class30_sub3_2.aBoolean1323) {
							continue;
						}
					}
					if (i <= cameraLocalX && i > minVisibleX) {
						Ground class30_sub3_3 = aclass30_sub3[i - 1][j];
						if (class30_sub3_3 != null && class30_sub3_3.aBoolean1323 && (class30_sub3_3.aBoolean1322 || (class30_sub3_1.anInt1320 & 1) == 0)) {
							continue;
						}
					}
					if (i >= cameraLocalX && i < maxVisibleX - 1) {
						Ground class30_sub3_4 = aclass30_sub3[i + 1][j];
						if (class30_sub3_4 != null && class30_sub3_4.aBoolean1323 && (class30_sub3_4.aBoolean1322 || (class30_sub3_1.anInt1320 & 4) == 0)) {
							continue;
						}
					}
					if (j <= cameraLocalY && j > minVisibleY) {
						Ground class30_sub3_5 = aclass30_sub3[i][j - 1];
						if (class30_sub3_5 != null && class30_sub3_5.aBoolean1323 && (class30_sub3_5.aBoolean1322 || (class30_sub3_1.anInt1320 & 8) == 0)) {
							continue;
						}
					}
					if (j >= cameraLocalY && j < maxVisibleY - 1) {
						Ground class30_sub3_6 = aclass30_sub3[i][j + 1];
						if (class30_sub3_6 != null && class30_sub3_6.aBoolean1323 && (class30_sub3_6.aBoolean1322 || (class30_sub3_1.anInt1320 & 2) == 0)) {
							continue;
						}
					}
				} else {
					flag = true;
				}
				class30_sub3_1.aBoolean1322 = false;
				if (class30_sub3_1.ground != null) {
					Ground class30_sub3_7 = class30_sub3_1.ground;
					if (class30_sub3_7.underlay != null) {
						if (!method320(0, i, j)) {
							method315(class30_sub3_7.underlay, 0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i, j);
						}
					} else if (class30_sub3_7.overlay != null && !method320(0, i, j)) {
						drawOverlayTile(i, camUpDownY, camLeftRightY, class30_sub3_7.overlay, camUpDownX, j, camLeftRightX);
					}
					WallLoc class10 = class30_sub3_7.obj1;
					if (class10 != null) {
						class10.root.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10.x - cameraX, class10.y - cameraY, class10.z - cameraZ, class10.uid);
					}
					for (int i2 = 0; i2 < class30_sub3_7.objectCount; i2++) {
						GameObject class28 = class30_sub3_7.objects[i2];
						if (class28 != null) {
							class28.node.draw(class28.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class28.x - cameraX, class28.z - cameraY, class28.y - cameraZ, class28.uid);
						}
					}

				}
				boolean flag1 = false;
				if (class30_sub3_1.underlay != null) {
					if (!method320(l, i, j)) {
						flag1 = true;
						method315(class30_sub3_1.underlay, l, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i, j);
					}
				} else if (class30_sub3_1.overlay != null && !method320(l, i, j)) {
					flag1 = true;
					drawOverlayTile(i, camUpDownY, camLeftRightY, class30_sub3_1.overlay, camUpDownX, j, camLeftRightX);
				}
				int j1 = 0;
				int j2 = 0;
				WallLoc class10_3 = class30_sub3_1.obj1;
				WallDecoration class26_1 = class30_sub3_1.wallDecoration;
				if (class10_3 != null || class26_1 != null) {
					if (cameraLocalX == i) {
						j1++;
					} else if (cameraLocalX < i) {
						j1 += 2;
					}
					if (cameraLocalY == j) {
						j1 += 3;
					} else if (cameraLocalY > j) {
						j1 += 6;
					}
					j2 = anIntArray478[j1];
					class30_sub3_1.anInt1328 = anIntArray480[j1];
				}
				if (class10_3 != null) {
					if ((class10_3.orientation & anIntArray479[j1]) != 0) {
						if (class10_3.orientation == 16) {
							class30_sub3_1.anInt1325 = 3;
							class30_sub3_1.anInt1326 = anIntArray481[j1];
							class30_sub3_1.anInt1327 = 3 - class30_sub3_1.anInt1326;
						} else if (class10_3.orientation == 32) {
							class30_sub3_1.anInt1325 = 6;
							class30_sub3_1.anInt1326 = anIntArray482[j1];
							class30_sub3_1.anInt1327 = 6 - class30_sub3_1.anInt1326;
						} else if (class10_3.orientation == 64) {
							class30_sub3_1.anInt1325 = 12;
							class30_sub3_1.anInt1326 = anIntArray483[j1];
							class30_sub3_1.anInt1327 = 12 - class30_sub3_1.anInt1326;
						} else {
							class30_sub3_1.anInt1325 = 9;
							class30_sub3_1.anInt1326 = anIntArray484[j1];
							class30_sub3_1.anInt1327 = 9 - class30_sub3_1.anInt1326;
						}
					} else {
						class30_sub3_1.anInt1325 = 0;
					}
					if ((class10_3.orientation & j2) != 0 && !method321(l, i, j, class10_3.orientation)) {
						class10_3.root.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10_3.x - cameraX, class10_3.y - cameraY, class10_3.z - cameraZ, class10_3.uid);
					}
					if ((class10_3.orientation1 & j2) != 0 && !method321(l, i, j, class10_3.orientation1)) {
						class10_3.extension.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10_3.x - cameraX, class10_3.y - cameraY, class10_3.z - cameraZ, class10_3.uid);
					}
				}
				if (class26_1 != null && !method322(l, i, j, class26_1.node.modelHeight)) {
					if ((class26_1.flags & j2) != 0) {
						class26_1.node.draw(class26_1.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class26_1.x - cameraX, class26_1.z - cameraY, class26_1.y - cameraZ, class26_1.uid);
					} else if ((class26_1.flags & 0x300) != 0) {
						int j4 = class26_1.x - cameraX;
						int l5 = class26_1.z - cameraY;
						int k6 = class26_1.y - cameraZ;
						int i8 = class26_1.rotation;
						int k9;
						if (i8 == 1 || i8 == 2) {
							k9 = -j4;
						} else {
							k9 = j4;
						}
						int k10;
						if (i8 == 2 || i8 == 3) {
							k10 = -k6;
						} else {
							k10 = k6;
						}
						if ((class26_1.flags & 0x100) != 0 && k10 < k9) {
							int i11 = j4 + anIntArray463[i8];
							int k11 = k6 + anIntArray464[i8];
							class26_1.node.draw(i8 * 512 + 256, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i11, l5, k11, class26_1.uid);
						}
						if ((class26_1.flags & 0x200) != 0 && k10 > k9) {
							int j11 = j4 + anIntArray465[i8];
							int l11 = k6 + anIntArray466[i8];
							class26_1.node.draw(i8 * 512 + 1280 & 0x7ff, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, j11, l5, l11, class26_1.uid);
						}
					}
				}
				if (flag1) {
					GroundDecoration class49 = class30_sub3_1.groundDecoration;
					if (class49 != null) {
						class49.node.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class49.x - cameraX, class49.z - cameraY, class49.y - cameraZ, class49.uid);
					}
					ItemPile itemPile_1 = class30_sub3_1.itemPile;
					if (itemPile_1 != null && itemPile_1.offsetZ == 0) {
						if (itemPile_1.bottom != null) {
							itemPile_1.bottom.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile_1.x - cameraX, itemPile_1.z - cameraY, itemPile_1.y - cameraZ, itemPile_1.uid);
						}
						if (itemPile_1.middle != null) {
							itemPile_1.middle.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile_1.x - cameraX, itemPile_1.z - cameraY, itemPile_1.y - cameraZ, itemPile_1.uid);
						}
						if (itemPile_1.top != null) {
							itemPile_1.top.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile_1.x - cameraX, itemPile_1.z - cameraY, itemPile_1.y - cameraZ, itemPile_1.uid);
						}
					}
				}
				int k4 = class30_sub3_1.anInt1320;
				if (k4 != 0) {
					if (i < cameraLocalX && (k4 & 4) != 0) {
						Ground class30_sub3_17 = aclass30_sub3[i + 1][j];
						if (class30_sub3_17 != null && class30_sub3_17.aBoolean1323) {
							tileDeque.insertHead(class30_sub3_17);
						}
					}
					if (j < cameraLocalY && (k4 & 2) != 0) {
						Ground class30_sub3_18 = aclass30_sub3[i][j + 1];
						if (class30_sub3_18 != null && class30_sub3_18.aBoolean1323) {
							tileDeque.insertHead(class30_sub3_18);
						}
					}
					if (i > cameraLocalX && (k4 & 1) != 0) {
						Ground class30_sub3_19 = aclass30_sub3[i - 1][j];
						if (class30_sub3_19 != null && class30_sub3_19.aBoolean1323) {
							tileDeque.insertHead(class30_sub3_19);
						}
					}
					if (j > cameraLocalY && (k4 & 8) != 0) {
						Ground class30_sub3_20 = aclass30_sub3[i][j - 1];
						if (class30_sub3_20 != null && class30_sub3_20.aBoolean1323) {
							tileDeque.insertHead(class30_sub3_20);
						}
					}
				}
			}
			if (class30_sub3_1.anInt1325 != 0) {
				boolean flag2 = true;
				for (int k1 = 0; k1 < class30_sub3_1.objectCount; k1++) {
					if (class30_sub3_1.objects[k1].cycle == cycle || (class30_sub3_1.objectFlags[k1] & class30_sub3_1.anInt1325) != class30_sub3_1.anInt1326) {
						continue;
					}
					flag2 = false;
					break;
				}

				if (flag2) {
					WallLoc class10_1 = class30_sub3_1.obj1;
					if (!method321(l, i, j, class10_1.orientation)) {
						class10_1.root.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10_1.x - cameraX, class10_1.y - cameraY, class10_1.z - cameraZ, class10_1.uid);
					}
					class30_sub3_1.anInt1325 = 0;
				}
			}
			if (class30_sub3_1.aBoolean1324) {
				try {
					int i1 = class30_sub3_1.objectCount;
					class30_sub3_1.aBoolean1324 = false;
					int l1 = 0;
					label0 : for (int k2 = 0; k2 < i1; k2++) {
						GameObject class28_1 = class30_sub3_1.objects[k2];
						if (class28_1.cycle == cycle) {
							continue;
						}
						for (int k3 = class28_1.localX0; k3 <= class28_1.localX1; k3++) {
							for (int l4 = class28_1.localY0; l4 <= class28_1.localY1; l4++) {
								Ground class30_sub3_21 = aclass30_sub3[k3][l4];
								if (class30_sub3_21.aBoolean1322) {
									class30_sub3_1.aBoolean1324 = true;
								} else {
									if (class30_sub3_21.anInt1325 == 0) {
										continue;
									}
									int l6 = 0;
									if (k3 > class28_1.localX0) {
										l6++;
									}
									if (k3 < class28_1.localX1) {
										l6 += 4;
									}
									if (l4 > class28_1.localY0) {
										l6 += 8;
									}
									if (l4 < class28_1.localY1) {
										l6 += 2;
									}
									if ((l6 & class30_sub3_21.anInt1325) != class30_sub3_1.anInt1327) {
										continue;
									}
									class30_sub3_1.aBoolean1324 = true;
								}
								continue label0;
							}

						}

						aClass28Array462[l1++] = class28_1;
						int i5 = cameraLocalX - class28_1.localX0;
						int i6 = class28_1.localX1 - cameraLocalX;
						if (i6 > i5) {
							i5 = i6;
						}
						int i7 = cameraLocalY - class28_1.localY0;
						int j8 = class28_1.localY1 - cameraLocalY;
						if (j8 > i7) {
							class28_1.distanceFromCamera = i5 + j8;
						} else {
							class28_1.distanceFromCamera = i5 + i7;
						}
					}

					while (l1 > 0) {
						int i3 = -50;
						int l3 = -1;
						for (int j5 = 0; j5 < l1; j5++) {
							GameObject class28_2 = aClass28Array462[j5];
							if (class28_2.cycle != cycle) {
								if (class28_2.distanceFromCamera > i3) {
									i3 = class28_2.distanceFromCamera;
									l3 = j5;
								} else if (class28_2.distanceFromCamera == i3) {
									int j7 = class28_2.x - cameraX;
									int k8 = class28_2.y - cameraZ;
									int l9 = aClass28Array462[l3].x - cameraX;
									int l10 = aClass28Array462[l3].y - cameraZ;
									if (j7 * j7 + k8 * k8 > l9 * l9 + l10 * l10) {
										l3 = j5;
									}
								}
							}
						}

						if (l3 == -1) {
							break;
						}
						GameObject class28_3 = aClass28Array462[l3];
						class28_3.cycle = cycle;
						if (!method323(l, class28_3.localX0, class28_3.localX1, class28_3.localY0, class28_3.localY1, class28_3.node.modelHeight)) {
							class28_3.node.draw(class28_3.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class28_3.x - cameraX, class28_3.z - cameraY, class28_3.y - cameraZ, class28_3.uid);
						}
						for (int k7 = class28_3.localX0; k7 <= class28_3.localX1; k7++) {
							for (int l8 = class28_3.localY0; l8 <= class28_3.localY1; l8++) {
								Ground class30_sub3_22 = aclass30_sub3[k7][l8];
								if (class30_sub3_22.anInt1325 != 0) {
									tileDeque.insertHead(class30_sub3_22);
								} else if ((k7 != i || l8 != j) && class30_sub3_22.aBoolean1323) {
									tileDeque.insertHead(class30_sub3_22);
								}
							}

						}

					}
					if (class30_sub3_1.aBoolean1324) {
						continue;
					}
				} catch (Exception _ex) {
					class30_sub3_1.aBoolean1324 = false;
				}
			}
			if (!class30_sub3_1.aBoolean1323 || class30_sub3_1.anInt1325 != 0) {
				continue;
			}
			if (i <= cameraLocalX && i > minVisibleX) {
				Ground class30_sub3_8 = aclass30_sub3[i - 1][j];
				if (class30_sub3_8 != null && class30_sub3_8.aBoolean1323) {
					continue;
				}
			}
			if (i >= cameraLocalX && i < maxVisibleX - 1) {
				Ground class30_sub3_9 = aclass30_sub3[i + 1][j];
				if (class30_sub3_9 != null && class30_sub3_9.aBoolean1323) {
					continue;
				}
			}
			if (j <= cameraLocalY && j > minVisibleY) {
				Ground class30_sub3_10 = aclass30_sub3[i][j - 1];
				if (class30_sub3_10 != null && class30_sub3_10.aBoolean1323) {
					continue;
				}
			}
			if (j >= cameraLocalY && j < maxVisibleY - 1) {
				Ground class30_sub3_11 = aclass30_sub3[i][j + 1];
				if (class30_sub3_11 != null && class30_sub3_11.aBoolean1323) {
					continue;
				}
			}
			class30_sub3_1.aBoolean1323 = false;
			anInt446--;
			ItemPile itemPile = class30_sub3_1.itemPile;
			if (itemPile != null && itemPile.offsetZ != 0) {
				if (itemPile.bottom != null) {
					itemPile.bottom.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile.x - cameraX, itemPile.z - cameraY - itemPile.offsetZ, itemPile.y - cameraZ, itemPile.uid);
				}
				if (itemPile.middle != null) {
					itemPile.middle.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile.x - cameraX, itemPile.z - cameraY - itemPile.offsetZ, itemPile.y - cameraZ, itemPile.uid);
				}
				if (itemPile.top != null) {
					itemPile.top.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, itemPile.x - cameraX, itemPile.z - cameraY - itemPile.offsetZ, itemPile.y - cameraZ, itemPile.uid);
				}
			}
			if (class30_sub3_1.anInt1328 != 0) {
				WallDecoration class26 = class30_sub3_1.wallDecoration;
				if (class26 != null && !method322(l, i, j, class26.node.modelHeight)) {
					if ((class26.flags & class30_sub3_1.anInt1328) != 0) {
						class26.node.draw(class26.rotation, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class26.x - cameraX, class26.z - cameraY, class26.y - cameraZ, class26.uid);
					} else if ((class26.flags & 0x300) != 0) {
						int l2 = class26.x - cameraX;
						int j3 = class26.z - cameraY;
						int i4 = class26.y - cameraZ;
						int k5 = class26.rotation;
						int j6;
						if (k5 == 1 || k5 == 2) {
							j6 = -l2;
						} else {
							j6 = l2;
						}
						int l7;
						if (k5 == 2 || k5 == 3) {
							l7 = -i4;
						} else {
							l7 = i4;
						}
						if ((class26.flags & 0x100) != 0 && l7 >= j6) {
							int i9 = l2 + anIntArray463[k5];
							int i10 = i4 + anIntArray464[k5];
							class26.node.draw(k5 * 512 + 256, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, i9, j3, i10, class26.uid);
						}
						if ((class26.flags & 0x200) != 0 && l7 <= j6) {
							int j9 = l2 + anIntArray465[k5];
							int j10 = i4 + anIntArray466[k5];
							class26.node.draw(k5 * 512 + 1280 & 0x7ff, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, j9, j3, j10, class26.uid);
						}
					}
				}
				WallLoc class10_2 = class30_sub3_1.obj1;
				if (class10_2 != null) {
					if ((class10_2.orientation1 & class30_sub3_1.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation1)) {
						class10_2.extension.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10_2.x - cameraX, class10_2.y - cameraY, class10_2.z - cameraZ, class10_2.uid);
					}
					if ((class10_2.orientation & class30_sub3_1.anInt1328) != 0 && !method321(l, i, j, class10_2.orientation)) {
						class10_2.root.draw(0, camUpDownY, camUpDownX, camLeftRightY, camLeftRightX, class10_2.x - cameraX, class10_2.y - cameraY, class10_2.z - cameraZ, class10_2.uid);
					}
				}
			}
			if (k < sizeZ - 1) {
				Ground class30_sub3_12 = groundArray[k + 1][i][j];
				if (class30_sub3_12 != null && class30_sub3_12.aBoolean1323) {
					tileDeque.insertHead(class30_sub3_12);
				}
			}
			if (i < cameraLocalX) {
				Ground class30_sub3_13 = aclass30_sub3[i + 1][j];
				if (class30_sub3_13 != null && class30_sub3_13.aBoolean1323) {
					tileDeque.insertHead(class30_sub3_13);
				}
			}
			if (j < cameraLocalY) {
				Ground class30_sub3_14 = aclass30_sub3[i][j + 1];
				if (class30_sub3_14 != null && class30_sub3_14.aBoolean1323) {
					tileDeque.insertHead(class30_sub3_14);
				}
			}
			if (i > cameraLocalX) {
				Ground class30_sub3_15 = aclass30_sub3[i - 1][j];
				if (class30_sub3_15 != null && class30_sub3_15.aBoolean1323) {
					tileDeque.insertHead(class30_sub3_15);
				}
			}
			if (j > cameraLocalY) {
				Ground class30_sub3_16 = aclass30_sub3[i][j - 1];
				if (class30_sub3_16 != null && class30_sub3_16.aBoolean1323) {
					tileDeque.insertHead(class30_sub3_16);
				}
			}
		} while (true);
	}

	private void method315(TileUnderlay tileUnderlay, int i, int j, int k, int l, int i1, int j1, int k1) {
		int l1;
		int i2 = l1 = (j1 << 7) - cameraX;
		int j2;
		int k2 = j2 = (k1 << 7) - cameraZ;
		int l2;
		int i3 = l2 = i2 + 128;
		int j3;
		int k3 = j3 = k2 + 128;
		int l3 = heightMap[i][j1][k1] - cameraY;
		int i4 = heightMap[i][j1 + 1][k1] - cameraY;
		int j4 = heightMap[i][j1 + 1][k1 + 1] - cameraY;
		int k4 = heightMap[i][j1][k1 + 1] - cameraY;
		int l4 = k2 * l + i2 * i1 >> 16;
		k2 = k2 * i1 - i2 * l >> 16;
		i2 = l4;
		l4 = l3 * k - k2 * j >> 16;
		k2 = l3 * j + k2 * k >> 16;
		l3 = l4;
		if (k2 < 50) {
			return;
		}
		l4 = j2 * l + i3 * i1 >> 16;
		j2 = j2 * i1 - i3 * l >> 16;
		i3 = l4;
		l4 = i4 * k - j2 * j >> 16;
		j2 = i4 * j + j2 * k >> 16;
		i4 = l4;
		if (j2 < 50) {
			return;
		}
		l4 = k3 * l + l2 * i1 >> 16;
		k3 = k3 * i1 - l2 * l >> 16;
		l2 = l4;
		l4 = j4 * k - k3 * j >> 16;
		k3 = j4 * j + k3 * k >> 16;
		j4 = l4;
		if (k3 < 50) {
			return;
		}
		l4 = j3 * l + l1 * i1 >> 16;
		j3 = j3 * i1 - l1 * l >> 16;
		l1 = l4;
		l4 = k4 * k - j3 * j >> 16;
		j3 = k4 * j + j3 * k >> 16;
		k4 = l4;
		if (j3 < 50) {
			return;
		}
		int i5 = Texture.textureInt1 + (i2 << 9) / k2;
		int j5 = Texture.textureInt2 + (l3 << 9) / k2;
		int k5 = Texture.textureInt1 + (i3 << 9) / j2;
		int l5 = Texture.textureInt2 + (i4 << 9) / j2;
		int i6 = Texture.textureInt1 + (l2 << 9) / k3;
		int j6 = Texture.textureInt2 + (j4 << 9) / k3;
		int k6 = Texture.textureInt1 + (l1 << 9) / j3;
		int l6 = Texture.textureInt2 + (k4 << 9) / j3;
		Texture.anInt1465 = 0;
		if ((i6 - k6) * (l5 - l6) - (j6 - l6) * (k5 - k6) > 0) {
			Texture.checkBounds = i6 < 0 || k6 < 0 || k5 < 0 || i6 > DrawingArea.centerX || k6 > DrawingArea.centerX || k5 > DrawingArea.centerX;
			if (inputRequested && method318(clickScreenX, clickScreenY, j6, l6, l5, i6, k6, k5)) {
				clickLocalX = j1;
				clickedTileY = k1;
			}
			if (tileUnderlay.texture == -1) {
				if (tileUnderlay.nwHSL != 0xbc614e) {
					Texture.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, tileUnderlay.nwHSL, tileUnderlay.seHSL, tileUnderlay.neHSL);
				}
			} else if (!lowMem) {
				if (tileUnderlay.isFlatShaded) {
					Texture.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, tileUnderlay.nwHSL, tileUnderlay.seHSL, tileUnderlay.neHSL, i2, i3, l1, l3, i4, k4, k2, j2, j3, tileUnderlay.texture);
				} else {
					Texture.drawMaterializedTriangle(j6, l6, l5, i6, k6, k5, tileUnderlay.nwHSL, tileUnderlay.seHSL, tileUnderlay.neHSL, l2, l1, i3, j4, k4, i4, k3, j3, j2, tileUnderlay.texture);
				}
			} else {
				int i7 = TEXTURE_HSL[tileUnderlay.texture];
				Texture.drawGouraudTriangle(j6, l6, l5, i6, k6, k5, method317(i7, tileUnderlay.nwHSL), method317(i7, tileUnderlay.seHSL), method317(i7, tileUnderlay.neHSL));
			}
		}
		if ((i5 - k5) * (l6 - l5) - (j5 - l5) * (k6 - k5) > 0) {
			Texture.checkBounds = i5 < 0 || k5 < 0 || k6 < 0 || i5 > DrawingArea.centerX || k5 > DrawingArea.centerX || k6 > DrawingArea.centerX;
			if (inputRequested && method318(clickScreenX, clickScreenY, j5, l5, l6, i5, k5, k6)) {
				clickLocalX = j1;
				clickedTileY = k1;
			}
			if (tileUnderlay.texture == -1) {
				if (tileUnderlay.swHSL != 0xbc614e) {
					Texture.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, tileUnderlay.swHSL, tileUnderlay.neHSL, tileUnderlay.seHSL);
				}
			} else {
				if (!lowMem) {
					Texture.drawMaterializedTriangle(j5, l5, l6, i5, k5, k6, tileUnderlay.swHSL, tileUnderlay.neHSL, tileUnderlay.seHSL, i2, i3, l1, l3, i4, k4, k2, j2, j3, tileUnderlay.texture);
					return;
				}
				int j7 = TEXTURE_HSL[tileUnderlay.texture];
				Texture.drawGouraudTriangle(j5, l5, l6, i5, k5, k6, method317(j7, tileUnderlay.swHSL), method317(j7, tileUnderlay.neHSL), method317(j7, tileUnderlay.seHSL));
			}
		}
	}

	private void drawOverlayTile(int i, int j, int k, ShapedTile shapedTile, int l, int i1, int j1) {
		int k1 = shapedTile.triangleX.length;
		for (int l1 = 0; l1 < k1; l1++) {
			int i2 = shapedTile.triangleX[l1] - cameraX;
			int k2 = shapedTile.triangleY[l1] - cameraY;
			int i3 = shapedTile.triangleZ[l1] - cameraZ;
			int k3 = i3 * k + i2 * j1 >> 16;
			i3 = i3 * j1 - i2 * k >> 16;
			i2 = k3;
			k3 = k2 * l - i3 * j >> 16;
			i3 = k2 * j + i3 * l >> 16;
			k2 = k3;
			if (i3 < 50) {
				return;
			}
			if (shapedTile.triangleTexture != null) {
				ShapedTile.tmpTriangleX[l1] = i2;
				ShapedTile.tmpTriangleY[l1] = k2;
				ShapedTile.tmpTriangleZ[l1] = i3;
			}
			ShapedTile.tmpScreenX[l1] = Texture.textureInt1 + (i2 << 9) / i3;
			ShapedTile.tmpScreenY[l1] = Texture.textureInt2 + (k2 << 9) / i3;
		}

		Texture.anInt1465 = 0;
		k1 = shapedTile.vertexX.length;
		for (int j2 = 0; j2 < k1; j2++) {
			int l2 = shapedTile.vertexX[j2];
			int j3 = shapedTile.vertexY[j2];
			int l3 = shapedTile.vertexZ[j2];
			int i4 = ShapedTile.tmpScreenX[l2];
			int j4 = ShapedTile.tmpScreenX[j3];
			int k4 = ShapedTile.tmpScreenX[l3];
			int l4 = ShapedTile.tmpScreenY[l2];
			int i5 = ShapedTile.tmpScreenY[j3];
			int j5 = ShapedTile.tmpScreenY[l3];
			if ((i4 - j4) * (j5 - i5) - (l4 - i5) * (k4 - j4) > 0) {
				Texture.checkBounds = i4 < 0 || j4 < 0 || k4 < 0 || i4 > DrawingArea.centerX || j4 > DrawingArea.centerX || k4 > DrawingArea.centerX;
				if (inputRequested && method318(clickScreenX, clickScreenY, l4, i5, j5, i4, j4, k4)) {
					clickLocalX = i;
					clickedTileY = i1;
				}
				if (shapedTile.triangleTexture == null || shapedTile.triangleTexture[j2] == -1) {
					if (shapedTile.vertexColorA[j2] != 0xbc614e) {
						Texture.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, shapedTile.vertexColorA[j2], shapedTile.vertexColorB[j2], shapedTile.vertexColorC[j2]);
					}
				} else if (!lowMem) {
					if (shapedTile.ignoreUV) {
						Texture.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, shapedTile.vertexColorA[j2], shapedTile.vertexColorB[j2], shapedTile.vertexColorC[j2], ShapedTile.tmpTriangleX[0], ShapedTile.tmpTriangleX[1], ShapedTile.tmpTriangleX[3], ShapedTile.tmpTriangleY[0], ShapedTile.tmpTriangleY[1], ShapedTile.tmpTriangleY[3], ShapedTile.tmpTriangleZ[0], ShapedTile.tmpTriangleZ[1], ShapedTile.tmpTriangleZ[3], shapedTile.triangleTexture[j2]);
					} else {
						Texture.drawMaterializedTriangle(l4, i5, j5, i4, j4, k4, shapedTile.vertexColorA[j2], shapedTile.vertexColorB[j2], shapedTile.vertexColorC[j2], ShapedTile.tmpTriangleX[l2], ShapedTile.tmpTriangleX[j3], ShapedTile.tmpTriangleX[l3], ShapedTile.tmpTriangleY[l2], ShapedTile.tmpTriangleY[j3], ShapedTile.tmpTriangleY[l3], ShapedTile.tmpTriangleZ[l2], ShapedTile.tmpTriangleZ[j3], ShapedTile.tmpTriangleZ[l3], shapedTile.triangleTexture[j2]);
					}
				} else {
					int k5 = TEXTURE_HSL[shapedTile.triangleTexture[j2]];
					Texture.drawGouraudTriangle(l4, i5, j5, i4, j4, k4, method317(k5, shapedTile.vertexColorA[j2]), method317(k5, shapedTile.vertexColorB[j2]), method317(k5, shapedTile.vertexColorC[j2]));
				}
			}
		}

	}

	private int method317(int j, int k) {
		k = 127 - k;
		k = k * (j & 0x7f) / 160;
		if (k < 2) {
			k = 2;
		} else if (k > 126) {
			k = 126;
		}
		return (j & 0xff80) + k;
	}

	private boolean method318(int i, int j, int k, int l, int i1, int j1, int k1, int l1) {
		if (j < k && j < l && j < i1) {
			return false;
		}
		if (j > k && j > l && j > i1) {
			return false;
		}
		if (i < j1 && i < k1 && i < l1) {
			return false;
		}
		if (i > j1 && i > k1 && i > l1) {
			return false;
		}
		int i2 = (j - k) * (k1 - j1) - (i - j1) * (l - k);
		int j2 = (j - i1) * (j1 - l1) - (i - l1) * (k - i1);
		int k2 = (j - l) * (l1 - k1) - (i - k1) * (i1 - l);
		return i2 * k2 > 0 && k2 * j2 > 0;
	}

	private void method319() {
		int j = sceneClusterCounts[topPlane];
		SceneCluster[] aclass47 = sceneClusters[topPlane];
		anInt475 = 0;
		for (int k = 0; k < j; k++) {
			SceneCluster sceneCluster = aclass47[k];
			if (sceneCluster.type == 1) {
				int l = sceneCluster.localMinX - cameraLocalX + 25;
				if (l < 0 || l > 50) {
					continue;
				}
				int k1 = sceneCluster.localMinY - cameraLocalY + 25;
				if (k1 < 0) {
					k1 = 0;
				}
				int j2 = sceneCluster.localMaxY - cameraLocalY + 25;
				if (j2 > 50) {
					j2 = 50;
				}
				boolean flag = false;
				while (k1 <= j2) {
					if (cullMap[l][k1++]) {
						flag = true;
						break;
					}
				}
				if (!flag) {
					continue;
				}
				int j3 = cameraX - sceneCluster.minX;
				if (j3 > 32) {
					sceneCluster.mode = 1;
				} else {
					if (j3 >= -32) {
						continue;
					}
					sceneCluster.mode = 2;
					j3 = -j3;
				}
				sceneCluster.y0 = (sceneCluster.minY - cameraZ << 8) / j3;
				sceneCluster.y1 = (sceneCluster.maxY - cameraZ << 8) / j3;
				sceneCluster.z1 = (sceneCluster.maxZ - cameraY << 8) / j3;
				sceneCluster.z0 = (sceneCluster.minZ - cameraY << 8) / j3;
				A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
				continue;
			}
			if (sceneCluster.type == 2) {
				int i1 = sceneCluster.localMinY - cameraLocalY + 25;
				if (i1 < 0 || i1 > 50) {
					continue;
				}
				int l1 = sceneCluster.localMinX - cameraLocalX + 25;
				if (l1 < 0) {
					l1 = 0;
				}
				int k2 = sceneCluster.localMaxX - cameraLocalX + 25;
				if (k2 > 50) {
					k2 = 50;
				}
				boolean flag1 = false;
				while (l1 <= k2) {
					if (cullMap[l1++][i1]) {
						flag1 = true;
						break;
					}
				}
				if (!flag1) {
					continue;
				}
				int k3 = cameraZ - sceneCluster.minY;
				if (k3 > 32) {
					sceneCluster.mode = 3;
				} else {
					if (k3 >= -32) {
						continue;
					}
					sceneCluster.mode = 4;
					k3 = -k3;
				}
				sceneCluster.x0 = (sceneCluster.minX - cameraX << 8) / k3;
				sceneCluster.x1 = (sceneCluster.maxX - cameraX << 8) / k3;
				sceneCluster.z1 = (sceneCluster.maxZ - cameraY << 8) / k3;
				sceneCluster.z0 = (sceneCluster.minZ - cameraY << 8) / k3;
				A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
			} else if (sceneCluster.type == 4) {
				int j1 = sceneCluster.maxZ - cameraY;
				if (j1 > 128) {
					int i2 = sceneCluster.localMinY - cameraLocalY + 25;
					if (i2 < 0) {
						i2 = 0;
					}
					int l2 = sceneCluster.localMaxY - cameraLocalY + 25;
					if (l2 > 50) {
						l2 = 50;
					}
					if (i2 <= l2) {
						int i3 = sceneCluster.localMinX - cameraLocalX + 25;
						if (i3 < 0) {
							i3 = 0;
						}
						int l3 = sceneCluster.localMaxX - cameraLocalX + 25;
						if (l3 > 50) {
							l3 = 50;
						}
						boolean flag2 = false;
						label0 : for (int i4 = i3; i4 <= l3; i4++) {
							for (int j4 = i2; j4 <= l2; j4++) {
								if (!cullMap[i4][j4]) {
									continue;
								}
								flag2 = true;
								break label0;
							}

						}

						if (flag2) {
							sceneCluster.mode = 5;
							sceneCluster.x0 = (sceneCluster.minX - cameraX << 8) / j1;
							sceneCluster.x1 = (sceneCluster.maxX - cameraX << 8) / j1;
							sceneCluster.y0 = (sceneCluster.minY - cameraZ << 8) / j1;
							sceneCluster.y1 = (sceneCluster.maxY - cameraZ << 8) / j1;
							A_SCENE_CLUSTER_ARRAY_476[anInt475++] = sceneCluster;
						}
					}
				}
			}
		}

	}

	private boolean method320(int i, int j, int k) {
		int l = cycleMap[i][j][k];
		if (l == -cycle) {
			return false;
		}
		if (l == cycle) {
			return true;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		if (method324(i1 + 1, heightMap[i][j][k], j1 + 1) && method324(i1 + 128 - 1, heightMap[i][j + 1][k], j1 + 1) && method324(i1 + 128 - 1, heightMap[i][j + 1][k + 1], j1 + 128 - 1) && method324(i1 + 1, heightMap[i][j][k + 1], j1 + 128 - 1)) {
			cycleMap[i][j][k] = cycle;
			return true;
		} else {
			cycleMap[i][j][k] = -cycle;
			return false;
		}
	}

	private boolean method321(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		int k1 = heightMap[i][j][k] - 1;
		int l1 = k1 - 120;
		int i2 = k1 - 230;
		int j2 = k1 - 238;
		if (l < 16) {
			if (l == 1) {
				if (i1 > cameraX) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1, i2, j1) && method324(i1, i2, j1 + 128);
			}
			if (l == 2) {
				if (j1 < cameraZ) {
					if (!method324(i1, k1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1 + 128)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1, i2, j1 + 128) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 4) {
				if (i1 < cameraX) {
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1 + 128)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1 + 128)) {
						return false;
					}
				}
				return method324(i1 + 128, i2, j1) && method324(i1 + 128, i2, j1 + 128);
			}
			if (l == 8) {
				if (j1 > cameraZ) {
					if (!method324(i1, k1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, k1, j1)) {
						return false;
					}
				}
				if (i > 0) {
					if (!method324(i1, l1, j1)) {
						return false;
					}
					if (!method324(i1 + 128, l1, j1)) {
						return false;
					}
				}
				return method324(i1, i2, j1) && method324(i1 + 128, i2, j1);
			}
		}
		if (!method324(i1 + 64, j2, j1 + 64)) {
			return false;
		}
		if (l == 16) {
			return method324(i1, i2, j1 + 128);
		}
		if (l == 32) {
			return method324(i1 + 128, i2, j1 + 128);
		}
		if (l == 64) {
			return method324(i1 + 128, i2, j1);
		}
		if (l == 128) {
			return method324(i1, i2, j1);
		} else {
			System.out.println("Warning unsupported wall type");
			return true;
		}
	}

	private boolean method322(int i, int j, int k, int l) {
		if (!method320(i, j, k)) {
			return false;
		}
		int i1 = j << 7;
		int j1 = k << 7;
		return method324(i1 + 1, heightMap[i][j][k] - l, j1 + 1) && method324(i1 + 128 - 1, heightMap[i][j + 1][k] - l, j1 + 1) && method324(i1 + 128 - 1, heightMap[i][j + 1][k + 1] - l, j1 + 128 - 1) && method324(i1 + 1, heightMap[i][j][k + 1] - l, j1 + 128 - 1);
	}

	private boolean method323(int i, int j, int k, int l, int i1, int j1) {
		if (j == k && l == i1) {
			if (!method320(i, j, l)) {
				return false;
			}
			int k1 = j << 7;
			int i2 = l << 7;
			return method324(k1 + 1, heightMap[i][j][l] - j1, i2 + 1) && method324(k1 + 128 - 1, heightMap[i][j + 1][l] - j1, i2 + 1) && method324(k1 + 128 - 1, heightMap[i][j + 1][l + 1] - j1, i2 + 128 - 1) && method324(k1 + 1, heightMap[i][j][l + 1] - j1, i2 + 128 - 1);
		}
		for (int l1 = j; l1 <= k; l1++) {
			for (int j2 = l; j2 <= i1; j2++) {
				if (cycleMap[i][l1][j2] == -cycle) {
					return false;
				}
			}

		}

		int k2 = (j << 7) + 1;
		int l2 = (l << 7) + 2;
		int i3 = heightMap[i][j][l] - j1;
		if (!method324(k2, i3, l2)) {
			return false;
		}
		int j3 = (k << 7) - 1;
		if (!method324(j3, i3, l2)) {
			return false;
		}
		int k3 = (i1 << 7) - 1;
		return method324(k2, i3, k3) && method324(j3, i3, k3);
	}

	private boolean method324(int i, int j, int k) {
		for (int l = 0; l < anInt475; l++) {
			SceneCluster sceneCluster = A_SCENE_CLUSTER_ARRAY_476[l];
			if (sceneCluster.mode == 1) {
				int i1 = sceneCluster.minX - i;
				if (i1 > 0) {
					int j2 = sceneCluster.minY + (sceneCluster.y0 * i1 >> 8);
					int k3 = sceneCluster.maxY + (sceneCluster.y1 * i1 >> 8);
					int l4 = sceneCluster.maxZ + (sceneCluster.z1 * i1 >> 8);
					int i6 = sceneCluster.minZ + (sceneCluster.z0 * i1 >> 8);
					if (k >= j2 && k <= k3 && j >= l4 && j <= i6) {
						return true;
					}
				}
			} else if (sceneCluster.mode == 2) {
				int j1 = i - sceneCluster.minX;
				if (j1 > 0) {
					int k2 = sceneCluster.minY + (sceneCluster.y0 * j1 >> 8);
					int l3 = sceneCluster.maxY + (sceneCluster.y1 * j1 >> 8);
					int i5 = sceneCluster.maxZ + (sceneCluster.z1 * j1 >> 8);
					int j6 = sceneCluster.minZ + (sceneCluster.z0 * j1 >> 8);
					if (k >= k2 && k <= l3 && j >= i5 && j <= j6) {
						return true;
					}
				}
			} else if (sceneCluster.mode == 3) {
				int k1 = sceneCluster.minY - k;
				if (k1 > 0) {
					int l2 = sceneCluster.minX + (sceneCluster.x0 * k1 >> 8);
					int i4 = sceneCluster.maxX + (sceneCluster.x1 * k1 >> 8);
					int j5 = sceneCluster.maxZ + (sceneCluster.z1 * k1 >> 8);
					int k6 = sceneCluster.minZ + (sceneCluster.z0 * k1 >> 8);
					if (i >= l2 && i <= i4 && j >= j5 && j <= k6) {
						return true;
					}
				}
			} else if (sceneCluster.mode == 4) {
				int l1 = k - sceneCluster.minY;
				if (l1 > 0) {
					int i3 = sceneCluster.minX + (sceneCluster.x0 * l1 >> 8);
					int j4 = sceneCluster.maxX + (sceneCluster.x1 * l1 >> 8);
					int k5 = sceneCluster.maxZ + (sceneCluster.z1 * l1 >> 8);
					int l6 = sceneCluster.minZ + (sceneCluster.z0 * l1 >> 8);
					if (i >= i3 && i <= j4 && j >= k5 && j <= l6) {
						return true;
					}
				}
			} else if (sceneCluster.mode == 5) {
				int i2 = j - sceneCluster.maxZ;
				if (i2 > 0) {
					int j3 = sceneCluster.minX + (sceneCluster.x0 * i2 >> 8);
					int k4 = sceneCluster.maxX + (sceneCluster.x1 * i2 >> 8);
					int l5 = sceneCluster.minY + (sceneCluster.y0 * i2 >> 8);
					int i7 = sceneCluster.maxY + (sceneCluster.y1 * i2 >> 8);
					if (i >= j3 && i <= k4 && k >= l5 && k <= i7) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private boolean aBoolean434;
	public static boolean lowMem = true;
	private final int sizeZ;
	private final int sizeX;
	private final int sizeY;
	private final int[][][] heightMap;
	private final Ground[][][] groundArray;
	private int anInt442;
	private int obj5CacheCurrPos;
	private final GameObject[] obj5Cache;
	private final int[][][] cycleMap;
	private static int anInt446;
	private static int topPlane;
	private static int cycle;
	private static int minVisibleX;
	private static int maxVisibleX;
	private static int minVisibleY;
	private static int maxVisibleY;
	private static int cameraLocalX;
	private static int cameraLocalY;
	private static int cameraX;
	private static int cameraY;
	private static int cameraZ;
	private static int camUpDownY;
	private static int camUpDownX;
	private static int camLeftRightY;
	private static int camLeftRightX;
	private static GameObject[] aClass28Array462 = new GameObject[100];
	private static final int[] anIntArray463 = {53, -53, -53, 53};
	private static final int[] anIntArray464 = {-53, -53, 53, 53};
	private static final int[] anIntArray465 = {-45, 45, 45, -45};
	private static final int[] anIntArray466 = {45, 45, -45, -45};
	private static boolean inputRequested;
	private static int clickScreenX;
	private static int clickScreenY;
	public static int clickLocalX = -1;
	public static int clickedTileY = -1;
	private static final int cullingClusterPlaneCount;
	private static int[] sceneClusterCounts;
	private static SceneCluster[][] sceneClusters;
	private static int anInt475;
	private static final SceneCluster[] A_SCENE_CLUSTER_ARRAY_476 = new SceneCluster[500];
	private static Deque tileDeque = new Deque();
	private static final int[] anIntArray478 = {19, 55, 38, 155, 255, 110, 137, 205, 76};
	private static final int[] anIntArray479 = {160, 192, 80, 96, 0, 144, 80, 48, 160};
	private static final int[] anIntArray480 = {76, 8, 137, 4, 0, 1, 38, 2, 19};
	private static final int[] anIntArray481 = {0, 0, 2, 0, 0, 2, 1, 1, 0};
	private static final int[] anIntArray482 = {2, 0, 0, 2, 0, 0, 0, 4, 4};
	private static final int[] anIntArray483 = {0, 4, 4, 8, 0, 0, 8, 0, 0};
	private static final int[] anIntArray484 = {1, 1, 0, 0, 0, 8, 0, 0, 8};
	private static final int[] TEXTURE_HSL = {41, 39248, 41, 4643, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 43086, 41, 41, 41, 41, 41, 41, 41, 8602, 41, 28992, 41, 41, 41, 41, 41, 5056, 41, 41, 41, 7079, 41, 41, 41, 41, 41, 41, 41, 41, 41, 41, 3131, 41, 41, 41};
	private final int[] anIntArray486;
	private final int[] anIntArray487;
	private int anInt488;
	private final int[][] MINIMAP_OVERLAY_MASK = {new int[16], {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1}, {0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1}, {1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0}, {1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1, 1}, {1, 1, 1, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 1, 1}};
	private final int[][] MINIMAP_ROTATION_MASK = {{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}, {12, 8, 4, 0, 13, 9, 5, 1, 14, 10, 6, 2, 15, 11, 7, 3}, {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, {3, 7, 11, 15, 2, 6, 10, 14, 1, 5, 9, 13, 0, 4, 8, 12}};
	private static boolean[][][][] TileVisibilityMaps = new boolean[8][32][51][51];
	private static boolean[][] cullMap;
	private static int screenCenterX;
	private static int screenCenterY;
	private static int viewportLeft;
	private static int viewportTop;
	private static int screenWidth;
	private static int screenHeight;

	static {
		cullingClusterPlaneCount = 4;
		sceneClusterCounts = new int[cullingClusterPlaneCount];
		sceneClusters = new SceneCluster[cullingClusterPlaneCount][500];
	}
}
