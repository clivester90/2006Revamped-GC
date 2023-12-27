package com.runescape.world;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 

public final class CollisionMap {

	public CollisionMap() {
		xOffset = 0;
		yOffset = 0;
		width = 104;
		height = 104;
		adjacencies = new int[width][height];
		reset();
	}

	public void reset() {
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (i == 0 || j == 0 || i == width - 1 || j == height - 1) {
					adjacencies[i][j] = 0xffffff;
				} else {
					adjacencies[i][j] = 0x1000000;
				}
			}

		}

	}

	public void addWall(int i, int j, int k, int l, boolean blocksProjectiles) {
		k -= xOffset;
		i -= yOffset;
		if (l == 0) {
			if (j == 0) {
				add(k, i, 128);
				add(k - 1, i, 8);
			}
			if (j == 1) {
				add(k, i, 2);
				add(k, i + 1, 32);
			}
			if (j == 2) {
				add(k, i, 8);
				add(k + 1, i, 128);
			}
			if (j == 3) {
				add(k, i, 32);
				add(k, i - 1, 2);
			}
		}
		if (l == 1 || l == 3) {
			if (j == 0) {
				add(k, i, 1);
				add(k - 1, i + 1, 16);
			}
			if (j == 1) {
				add(k, i, 4);
				add(k + 1, i + 1, 64);
			}
			if (j == 2) {
				add(k, i, 16);
				add(k + 1, i - 1, 1);
			}
			if (j == 3) {
				add(k, i, 64);
				add(k - 1, i - 1, 4);
			}
		}
		if (l == 2) {
			if (j == 0) {
				add(k, i, 130);
				add(k - 1, i, 8);
				add(k, i + 1, 32);
			}
			if (j == 1) {
				add(k, i, 10);
				add(k, i + 1, 32);
				add(k + 1, i, 128);
			}
			if (j == 2) {
				add(k, i, 40);
				add(k + 1, i, 128);
				add(k, i - 1, 2);
			}
			if (j == 3) {
				add(k, i, 160);
				add(k, i - 1, 2);
				add(k - 1, i, 8);
			}
		}
		if (blocksProjectiles) {
			if (l == 0) {
				if (j == 0) {
					add(k, i, 0x10000);
					add(k - 1, i, 4096);
				}
				if (j == 1) {
					add(k, i, 1024);
					add(k, i + 1, 16384);
				}
				if (j == 2) {
					add(k, i, 4096);
					add(k + 1, i, 0x10000);
				}
				if (j == 3) {
					add(k, i, 16384);
					add(k, i - 1, 1024);
				}
			}
			if (l == 1 || l == 3) {
				if (j == 0) {
					add(k, i, 512);
					add(k - 1, i + 1, 8192);
				}
				if (j == 1) {
					add(k, i, 2048);
					add(k + 1, i + 1, 32768);
				}
				if (j == 2) {
					add(k, i, 8192);
					add(k + 1, i - 1, 512);
				}
				if (j == 3) {
					add(k, i, 32768);
					add(k - 1, i - 1, 2048);
				}
			}
			if (l == 2) {
				if (j == 0) {
					add(k, i, 0x10400);
					add(k - 1, i, 4096);
					add(k, i + 1, 16384);
				}
				if (j == 1) {
					add(k, i, 5120);
					add(k, i + 1, 16384);
					add(k + 1, i, 0x10000);
				}
				if (j == 2) {
					add(k, i, 20480);
					add(k + 1, i, 0x10000);
					add(k, i - 1, 1024);
				}
				if (j == 3) {
					add(k, i, 0x14000);
					add(k, i - 1, 1024);
					add(k - 1, i, 4096);
				}
			}
		}
	}

	public void addObject(boolean flag, int j, int k, int l, int i1, int j1) {
		int k1 = 256;
		if (flag) {
			k1 += 0x20000;
		}
		l -= xOffset;
		i1 -= yOffset;
		if (j1 == 1 || j1 == 3) {
			int l1 = j;
			j = k;
			k = l1;
		}
		for (int i2 = l; i2 < l + j; i2++) {
			if (i2 >= 0 && i2 < width) {
				for (int j2 = i1; j2 < i1 + k; j2++) {
					if (j2 >= 0 && j2 < height) {
						add(i2, j2, k1);
					}
				}

			}
		}

	}

	public void setSolid(int i, int k) {
		k -= xOffset;
		i -= yOffset;
		adjacencies[k][i] |= 0x200000;
	}

	private void add(int i, int j, int k) {
		adjacencies[i][j] |= k;
	}

	public void removeWall(int i, int j, boolean flag, int k, int l) {
		k -= xOffset;
		l -= yOffset;
		if (j == 0) {
			if (i == 0) {
				remove(128, k, l);
				remove(8, k - 1, l);
			}
			if (i == 1) {
				remove(2, k, l);
				remove(32, k, l + 1);
			}
			if (i == 2) {
				remove(8, k, l);
				remove(128, k + 1, l);
			}
			if (i == 3) {
				remove(32, k, l);
				remove(2, k, l - 1);
			}
		}
		if (j == 1 || j == 3) {
			if (i == 0) {
				remove(1, k, l);
				remove(16, k - 1, l + 1);
			}
			if (i == 1) {
				remove(4, k, l);
				remove(64, k + 1, l + 1);
			}
			if (i == 2) {
				remove(16, k, l);
				remove(1, k + 1, l - 1);
			}
			if (i == 3) {
				remove(64, k, l);
				remove(4, k - 1, l - 1);
			}
		}
		if (j == 2) {
			if (i == 0) {
				remove(130, k, l);
				remove(8, k - 1, l);
				remove(32, k, l + 1);
			}
			if (i == 1) {
				remove(10, k, l);
				remove(32, k, l + 1);
				remove(128, k + 1, l);
			}
			if (i == 2) {
				remove(40, k, l);
				remove(128, k + 1, l);
				remove(2, k, l - 1);
			}
			if (i == 3) {
				remove(160, k, l);
				remove(2, k, l - 1);
				remove(8, k - 1, l);
			}
		}
		if (flag) {
			if (j == 0) {
				if (i == 0) {
					remove(0x10000, k, l);
					remove(4096, k - 1, l);
				}
				if (i == 1) {
					remove(1024, k, l);
					remove(16384, k, l + 1);
				}
				if (i == 2) {
					remove(4096, k, l);
					remove(0x10000, k + 1, l);
				}
				if (i == 3) {
					remove(16384, k, l);
					remove(1024, k, l - 1);
				}
			}
			if (j == 1 || j == 3) {
				if (i == 0) {
					remove(512, k, l);
					remove(8192, k - 1, l + 1);
				}
				if (i == 1) {
					remove(2048, k, l);
					remove(32768, k + 1, l + 1);
				}
				if (i == 2) {
					remove(8192, k, l);
					remove(512, k + 1, l - 1);
				}
				if (i == 3) {
					remove(32768, k, l);
					remove(2048, k - 1, l - 1);
				}
			}
			if (j == 2) {
				if (i == 0) {
					remove(0x10400, k, l);
					remove(4096, k - 1, l);
					remove(16384, k, l + 1);
				}
				if (i == 1) {
					remove(5120, k, l);
					remove(16384, k, l + 1);
					remove(0x10000, k + 1, l);
				}
				if (i == 2) {
					remove(20480, k, l);
					remove(0x10000, k + 1, l);
					remove(1024, k, l - 1);
				}
				if (i == 3) {
					remove(0x14000, k, l);
					remove(1024, k, l - 1);
					remove(4096, k - 1, l);
				}
			}
		}
	}

	public void removeObject(int i, int sizeX, int xPos, int yPos, int sizeY, boolean blocksProjectiles) {
		int j1 = 256;
		if (blocksProjectiles) {
			j1 += 0x20000;
		}
		xPos -= xOffset;
		yPos -= yOffset;
		if (i == 1 || i == 3) {
			int k1 = sizeX;
			sizeX = sizeY;
			sizeY = k1;
		}
		for (int l1 = xPos; l1 < xPos + sizeX; l1++) {
			if (l1 >= 0 && l1 < width) {
				for (int i2 = yPos; i2 < yPos + sizeY; i2++) {
					if (i2 >= 0 && i2 < height) {
						remove(j1, l1, i2);
					}
				}

			}
		}

	}

	private void remove(int i, int j, int k) {
		adjacencies[j][k] &= 0xffffff - i;
	}

	public void method218(int j, int k) {
		k -= xOffset;
		j -= yOffset;
		adjacencies[k][j] &= 0xdfffff;
	}

	public boolean atWall(int destX, int destY, int playerX, int playerY, int objectFace, int objectType) {
		if (playerX == destX && playerY == destY) {
			return true;
		}
		playerX -= xOffset;
		playerY -= yOffset;
		destX -= xOffset;
		destY -= yOffset;
		if (objectType == 0) {
			if (objectFace == 0) {
				if (playerX == destX - 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x1280120) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 0x1280102) == 0) {
					return true;
				}
			} else if (objectFace == 1) {
				if (playerX == destX && playerY == destY + 1) {
					return true;
				}
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280108) == 0) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280180) == 0) {
					return true;
				}
			} else if (objectFace == 2) {
				if (playerX == destX + 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x1280120) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 0x1280102) == 0) {
					return true;
				}
			} else if (objectFace == 3) {
				if (playerX == destX && playerY == destY - 1) {
					return true;
				}
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280108) == 0) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280180) == 0) {
					return true;
				}
			}
		}
		if (objectType == 2) {
			if (objectFace == 0) {
				if (playerX == destX - 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280180) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 0x1280102) == 0) {
					return true;
				}
			} else if (objectFace == 1) {
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280108) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 0x1280102) == 0) {
					return true;
				}
			} else if (objectFace == 2) {
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280108) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x1280120) == 0) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1) {
					return true;
				}
			} else if (objectFace == 3) {
				if (playerX == destX - 1 && playerY == destY) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x1280120) == 0) {
					return true;
				}
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x1280180) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1) {
					return true;
				}
			}
		}
		if (objectType == 9) {
			if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x20) == 0) {
				return true;
			}
			if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 2) == 0) {
				return true;
			}
			if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 8) == 0) {
				return true;
			}
			if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean atDecoration(int destX, int destY, int playerX, int playerY, int objectType, int objectFace) {
		if (playerX == destX && playerY == destY) {
			return true;
		}
		playerX -= xOffset;
		playerY -= yOffset;
		destX -= xOffset;
		destY -= yOffset;
		if (objectType == 6 || objectType == 7) {
			if (objectType == 7) {
				objectFace = objectFace + 2 & 3;
			}
			if (objectFace == 0) {
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x80) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 2) == 0) {
					return true;
				}
			} else if (objectFace == 1) {
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 8) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 2) == 0) {
					return true;
				}
			} else if (objectFace == 2) {
				if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 8) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x20) == 0) {
					return true;
				}
			} else if (objectFace == 3) {
				if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x80) == 0) {
					return true;
				}
				if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x20) == 0) {
					return true;
				}
			}
		}
		if (objectType == 8) {
			if (playerX == destX && playerY == destY + 1 && (adjacencies[playerX][playerY] & 0x20) == 0) {
				return true;
			}
			if (playerX == destX && playerY == destY - 1 && (adjacencies[playerX][playerY] & 2) == 0) {
				return true;
			}
			if (playerX == destX - 1 && playerY == destY && (adjacencies[playerX][playerY] & 8) == 0) {
				return true;
			}
			if (playerX == destX + 1 && playerY == destY && (adjacencies[playerX][playerY] & 0x80) == 0) {
				return true;
			}
		}
		return false;
	}

	public boolean atObject(int destY, int destX, int playerX, int playerY, int targetSizeX, int targetSizeY1, int objectRotation) {
		int l1 = destX + targetSizeY1 - 1;
		int i2 = destY + playerY - 1;
		if (playerX >= destX && playerX <= l1 && objectRotation >= destY && objectRotation <= i2) {
			return true;
		}
		return playerX == destX - 1 && objectRotation >= destY && objectRotation <= i2 && (adjacencies[playerX - xOffset][objectRotation - yOffset] & 8) == 0 && (targetSizeX & 8) == 0 || playerX == l1 + 1 && objectRotation >= destY && objectRotation <= i2 && (adjacencies[playerX - xOffset][objectRotation - yOffset] & 0x80) == 0 && (targetSizeX & 2) == 0 || objectRotation == destY - 1 && playerX >= destX && playerX <= l1 && (adjacencies[playerX - xOffset][objectRotation - yOffset] & 2) == 0 && (targetSizeX & 4) == 0 || objectRotation == i2 + 1 && playerX >= destX && playerX <= l1 && (adjacencies[playerX - xOffset][objectRotation - yOffset] & 0x20) == 0 && (targetSizeX & 1) == 0;
	}

	private final int xOffset;
	private final int yOffset;
	private final int width;
	private final int height;
	public final int[][] adjacencies;
}
