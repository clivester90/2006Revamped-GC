package com.runescape.world.object;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.ondemand.OnDemandFetcher;
import com.runescape.graphics.Texture;
import com.runescape.world.model.Model;
import com.runescape.world.scene.SceneNode;
import com.runescape.world.scene.SceneObject;
import com.runescape.world.ChunkUtil;
import com.runescape.world.CollisionMap;
import com.runescape.world.Landscape;
import com.runescape.world.ground.FloorDefinition;

public final class ObjectManager {

	public ObjectManager(byte[][][] abyte0, int[][][] ai) {
		minPlane = 99;
		regionSizeX = 104;
		regionSizeY = 104;
		heightMap = ai;
		tileFlags = abyte0;
		underlays = new byte[4][regionSizeX][regionSizeY];
		overlays = new byte[4][regionSizeX][regionSizeY];
		overlayTypes = new byte[4][regionSizeX][regionSizeY];
		overlayOrientations = new byte[4][regionSizeX][regionSizeY];
		cullingMap = new int[4][regionSizeX + 1][regionSizeY + 1];
		shadowMap = new byte[4][regionSizeX + 1][regionSizeY + 1];
		lightMap = new int[regionSizeX + 1][regionSizeY + 1];
		blendedHue = new int[regionSizeY];
		blendedSaturation = new int[regionSizeY];
		blendedLightness = new int[regionSizeY];
		blendedHueDivisor = new int[regionSizeY];
		blendedDirection = new int[regionSizeY];
	}

	private static int getPerlinNoise(int i, int j) {
		int k = i + j * 57;
		k = k << 13 ^ k;
		int l = k * (k * k * 15731 + 0xc0ae5) + 0x5208dd0d & 0x7fffffff;
		return l >> 19 & 0xff;
	}

	public void createRegionScene(CollisionMap[] collisionMaps, Landscape landscape) {
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 104; k++) {
				for (int i1 = 0; i1 < 104; i1++) {
					if ((tileFlags[j][k][i1] & 1) == 1) {
						int k1 = j;
						if ((tileFlags[1][k][i1] & 2) == 2) {
							k1--;
						}
						if (k1 >= 0) {
							collisionMaps[k1].setSolid(i1, k);
						}
					}
				}

			}

		}
		anInt123 += (int) (Math.random() * 5D) - 2;
		if (anInt123 < -8) {
			anInt123 = -8;
		}
		if (anInt123 > 8) {
			anInt123 = 8;
		}
		anInt133 += (int) (Math.random() * 5D) - 2;
		if (anInt133 < -16) {
			anInt133 = -16;
		}
		if (anInt133 > 16) {
			anInt133 = 16;
		}
		for (int l = 0; l < 4; l++) {
			byte[][] abyte0 = shadowMap[l];
			byte byte0 = 96;
			char c = '\u0300';
			byte byte1 = -50;
			byte byte2 = -10;
			byte byte3 = -50;
			int j3 = (int) Math.sqrt(byte1 * byte1 + byte2 * byte2 + byte3 * byte3);
			int l3 = c * j3 >> 8;
			for (int j4 = 1; j4 < regionSizeY - 1; j4++) {
				for (int j5 = 1; j5 < regionSizeX - 1; j5++) {
					int k6 = heightMap[l][j5 + 1][j4] - heightMap[l][j5 - 1][j4];
					int l7 = heightMap[l][j5][j4 + 1] - heightMap[l][j5][j4 - 1];
					int j9 = (int) Math.sqrt(k6 * k6 + 0x10000 + l7 * l7);
					int k12 = (k6 << 8) / j9;
					int l13 = 0x10000 / j9;
					int j15 = (l7 << 8) / j9;
					int j16 = byte0 + (byte1 * k12 + byte2 * l13 + byte3 * j15) / l3;
					int j17 = (abyte0[j5 - 1][j4] >> 2) + (abyte0[j5 + 1][j4] >> 3) + (abyte0[j5][j4 - 1] >> 2) + (abyte0[j5][j4 + 1] >> 3) + (abyte0[j5][j4] >> 1);
					lightMap[j5][j4] = j16 - j17;
				}

			}

			for (int z = 0; z < regionSizeY; z++) {
				blendedHue[z] = 0;
				blendedSaturation[z] = 0;
				blendedLightness[z] = 0;
				blendedHueDivisor[z] = 0;
				blendedDirection[z] = 0;
			}

			for (int x = -5; x < regionSizeX + 5; x++) {
				for (int z = 0; z < regionSizeY; z++) {
					int k9 = x + 5;
					if (k9 >= 0 && k9 < regionSizeX) {
						int l12 = underlays[l][k9][z] & 0xff;
						if (l12 > 0) {
							FloorDefinition floorDefinition = FloorDefinition.cache[l12 - 1];
							blendedHue[z] += floorDefinition.anotherHue;
							blendedSaturation[z] += floorDefinition.saturation;
							blendedLightness[z] += floorDefinition.luminance;
							blendedHueDivisor[z] += floorDefinition.blendHueMultiplier;
							blendedDirection[z]++;
						}
					}
					int i13 = x - 5;
					if (i13 >= 0 && i13 < regionSizeX) {
						int i14 = underlays[l][i13][z] & 0xff;
						if (i14 > 0) {
							FloorDefinition floorDefinition_1 = FloorDefinition.cache[i14 - 1];
							blendedHue[z] -= floorDefinition_1.anotherHue;
							blendedSaturation[z] -= floorDefinition_1.saturation;
							blendedLightness[z] -= floorDefinition_1.luminance;
							blendedHueDivisor[z] -= floorDefinition_1.blendHueMultiplier;
							blendedDirection[z]--;
						}
					}
				}

				if (x >= 1 && x < regionSizeX - 1) {
					int l9 = 0;
					int j13 = 0;
					int j14 = 0;
					int k15 = 0;
					int k16 = 0;
					for (int k17 = -5; k17 < regionSizeY + 5; k17++) {
						int j18 = k17 + 5;
						if (j18 >= 0 && j18 < regionSizeY) {
							l9 += blendedHue[j18];
							j13 += blendedSaturation[j18];
							j14 += blendedLightness[j18];
							k15 += blendedHueDivisor[j18];
							k16 += blendedDirection[j18];
						}
						int k18 = k17 - 5;
						if (k18 >= 0 && k18 < regionSizeY) {
							l9 -= blendedHue[k18];
							j13 -= blendedSaturation[k18];
							j14 -= blendedLightness[k18];
							k15 -= blendedHueDivisor[k18];
							k16 -= blendedDirection[k18];
						}
						if (k17 >= 1 && k17 < regionSizeY - 1 && (!lowMem || (tileFlags[0][x][k17] & 2) != 0 || (tileFlags[l][x][k17] & 0x10) == 0 && getVisiblePlane(k17, l, x) == buildPlane)) {
							if (l < minPlane) {
								minPlane = l;
							}
							int l18 = underlays[l][x][k17] & 0xff;
							int i19 = overlays[l][x][k17] & 0xff;
							if (l18 > 0 || i19 > 0) {
								int j19 = heightMap[l][x][k17];
								int k19 = heightMap[l][x + 1][k17];
								int l19 = heightMap[l][x + 1][k17 + 1];
								int i20 = heightMap[l][x][k17 + 1];
								int j20 = lightMap[x][k17];
								int k20 = lightMap[x + 1][k17];
								int l20 = lightMap[x + 1][k17 + 1];
								int i21 = lightMap[x][k17 + 1];
								int j21 = -1;
								int k21 = -1;
								if (l18 > 0) {
									int l21 = l9 * 256 / k15;
									int j22 = j13 / k16;
									int l22 = j14 / k16;
									j21 = getTrimmedHSL(l21, j22, l22);
									l21 = l21 + anInt123 & 0xff;
									l22 += anInt133;
									if (l22 < 0) {
										l22 = 0;
									} else if (l22 > 255) {
										l22 = 255;
									}
									k21 = getTrimmedHSL(l21, j22, l22);
								}
								if (l > 0) {
									boolean flag = true;
									if (l18 == 0 && overlayTypes[l][x][k17] != 0) {
										flag = false;
									}
									if (i19 > 0 && !FloorDefinition.cache[i19 - 1].occlude) {
										flag = false;
									}
									if (flag && j19 == k19 && j19 == l19 && j19 == i20) {
										cullingMap[l][x][k17] |= 0x924;
									}
								}
								int i22 = 0;
								if (j21 != -1) {
									i22 = Texture.palette[adjustHSLLightness(k21, 96)];
								}
								if (i19 == 0) {
									landscape.addTile(l, x, k17, 0, 0, -1, j19, k19, l19, i20, adjustHSLLightness(j21, j20), adjustHSLLightness(j21, k20), adjustHSLLightness(j21, l20), adjustHSLLightness(j21, i21), 0, 0, 0, 0, i22, 0);
								} else {
									int k22 = overlayTypes[l][x][k17] + 1;
									byte byte4 = overlayOrientations[l][x][k17];
									FloorDefinition floorDefinition_2 = FloorDefinition.cache[i19 - 1];
									int i23 = floorDefinition_2.texture;
									int j23;
									int k23;
									if (i23 >= 0) {
										k23 = Texture.getAverageTextureRGB(i23);
										j23 = -1;
									} else if (floorDefinition_2.rgb == 0xff00ff) {
										/*j23 = -2;
										i23 = -1;
										k23 = com.client.graphics.Texture.anIntArray1482[method185(flo_2.anInt399, 96)];*/
										k23 = 0;
										j23 = -2;
										i23 = -1;
									} else if(floorDefinition_2.rgb == 0x333333) {
										k23 = Texture.palette[getRGB(floorDefinition_2.hsl16, 96)];
										j23 = -2;
										i23 = -1;
									} else {
										j23 = getTrimmedHSL(floorDefinition_2.hue, floorDefinition_2.saturation, floorDefinition_2.luminance);
										k23 = Texture.palette[getRGB(floorDefinition_2.hsl16, 96)];
									}
									landscape.addTile(l, x, k17, k22, byte4, i23, j19, k19, l19, i20, adjustHSLLightness(j21, j20), adjustHSLLightness(j21, k20), adjustHSLLightness(j21, l20), adjustHSLLightness(j21, i21), getRGB(j23, j20), getRGB(j23, k20), getRGB(j23, l20), getRGB(j23, i21), i22, k23);
								}
							}
						}
					}

				}
			}

			for (int j8 = 1; j8 < regionSizeY - 1; j8++) {
				for (int i10 = 1; i10 < regionSizeX - 1; i10++) {
					landscape.setTopPlane(l, i10, j8, getVisiblePlane(j8, l, i10));
				}

			}

		}

		landscape.method305(-10, -50, -50);
		for (int j1 = 0; j1 < regionSizeX; j1++) {
			for (int l1 = 0; l1 < regionSizeY; l1++) {
				if ((tileFlags[1][j1][l1] & 2) == 2) {
					landscape.addBridge(l1, j1);
				}
			}

		}

		int i2 = 1;
		int j2 = 2;
		int k2 = 4;
		for (int l2 = 0; l2 < 4; l2++) {
			if (l2 > 0) {
				i2 <<= 3;
				j2 <<= 3;
				k2 <<= 3;
			}
			for (int i3 = 0; i3 <= l2; i3++) {
				for (int k3 = 0; k3 <= regionSizeY; k3++) {
					for (int i4 = 0; i4 <= regionSizeX; i4++) {
						if ((cullingMap[i3][i4][k3] & i2) != 0) {
							int k4 = k3;
							int l5 = k3;
							int i7 = i3;
							int k8 = i3;
							for (; k4 > 0 && (cullingMap[i3][i4][k4 - 1] & i2) != 0; k4--) {
                            }
							for (; l5 < regionSizeY && (cullingMap[i3][i4][l5 + 1] & i2) != 0; l5++) {
                            }
							label0 : for (; i7 > 0; i7--) {
								for (int j10 = k4; j10 <= l5; j10++) {
									if ((cullingMap[i7 - 1][i4][j10] & i2) == 0) {
										break label0;
									}
								}

							}

							label1 : for (; k8 < l2; k8++) {
								for (int k10 = k4; k10 <= l5; k10++) {
									if ((cullingMap[k8 + 1][i4][k10] & i2) == 0) {
										break label1;
									}
								}

							}

							int l10 = (k8 + 1 - i7) * (l5 - k4 + 1);
							if (l10 >= 8) {
								char c1 = '\360';
								int k14 = heightMap[k8][i4][k4] - c1;
								int l15 = heightMap[i7][i4][k4];
								Landscape.createNewSceneCluster(l2, i4 * 128, l15, i4 * 128, l5 * 128 + 128, k14, k4 * 128, 1);
								for (int l16 = i7; l16 <= k8; l16++) {
									for (int l17 = k4; l17 <= l5; l17++) {
										cullingMap[l16][i4][l17] &= ~i2;
									}

								}

							}
						}
						if ((cullingMap[i3][i4][k3] & j2) != 0) {
							int l4 = i4;
							int i6 = i4;
							int j7 = i3;
							int l8 = i3;
							for (; l4 > 0 && (cullingMap[i3][l4 - 1][k3] & j2) != 0; l4--) {
                            }
							for (; i6 < regionSizeX && (cullingMap[i3][i6 + 1][k3] & j2) != 0; i6++) {
                            }
							label2 : for (; j7 > 0; j7--) {
								for (int i11 = l4; i11 <= i6; i11++) {
									if ((cullingMap[j7 - 1][i11][k3] & j2) == 0) {
										break label2;
									}
								}

							}

							label3 : for (; l8 < l2; l8++) {
								for (int j11 = l4; j11 <= i6; j11++) {
									if ((cullingMap[l8 + 1][j11][k3] & j2) == 0) {
										break label3;
									}
								}

							}

							int k11 = (l8 + 1 - j7) * (i6 - l4 + 1);
							if (k11 >= 8) {
								char c2 = '\360';
								int l14 = heightMap[l8][l4][k3] - c2;
								int i16 = heightMap[j7][l4][k3];
								Landscape.createNewSceneCluster(l2, l4 * 128, i16, i6 * 128 + 128, k3 * 128, l14, k3 * 128, 2);
								for (int i17 = j7; i17 <= l8; i17++) {
									for (int i18 = l4; i18 <= i6; i18++) {
										cullingMap[i17][i18][k3] &= ~j2;
									}

								}

							}
						}
						if ((cullingMap[i3][i4][k3] & k2) != 0) {
							int i5 = i4;
							int j6 = i4;
							int k7 = k3;
							int i9 = k3;
							for (; k7 > 0 && (cullingMap[i3][i4][k7 - 1] & k2) != 0; k7--) {
                            }
							for (; i9 < regionSizeY && (cullingMap[i3][i4][i9 + 1] & k2) != 0; i9++) {
                            }
							label4 : for (; i5 > 0; i5--) {
								for (int l11 = k7; l11 <= i9; l11++) {
									if ((cullingMap[i3][i5 - 1][l11] & k2) == 0) {
										break label4;
									}
								}

							}

							label5 : for (; j6 < regionSizeX; j6++) {
								for (int i12 = k7; i12 <= i9; i12++) {
									if ((cullingMap[i3][j6 + 1][i12] & k2) == 0) {
										break label5;
									}
								}

							}

							if ((j6 - i5 + 1) * (i9 - k7 + 1) >= 4) {
								int j12 = heightMap[i3][i5][k7];
								Landscape.createNewSceneCluster(l2, i5 * 128, j12, j6 * 128 + 128, i9 * 128 + 128, j12, k7 * 128, 4);
								for (int k13 = i5; k13 <= j6; k13++) {
									for (int i15 = k7; i15 <= i9; i15++) {
										cullingMap[i3][k13][i15] &= ~k2;
									}

								}

							}
						}
					}

				}

			}

		}

	}

	private static int getHeightNoise(int i, int j) {
		int k = getNoise(i + 45365, j + 0x16713, 4) - 128 + (getNoise(i + 10294, j + 37821, 2) - 128 >> 1) + (getNoise(i, j, 1) - 128 >> 2);
		k = (int) (k * 0.29999999999999999D) + 35;
		if (k < 10) {
			k = 10;
		} else if (k > 60) {
			k = 60;
		}
		return k;
	}

	public static void evaluateObjects(ByteBuffer byteBuffer, OnDemandFetcher demandFetcher) {
		label0 : {
			int i = -1;
			do {
				int j = byteBuffer.method422();
				if (j == 0) {
					break label0;
				}
				i += j;
				ObjectDefinition objectDef = ObjectDefinition.forID(i);
				objectDef.requestModels(demandFetcher);
				do {
					int k = byteBuffer.method422();
					if (k == 0) {
						break;
					}
					byteBuffer.readUnsignedByte();
				} while (true);
			} while (true);
		}
	}

	public void sewEdges(int i, int j, int l, int i1) {
		for (int j1 = i; j1 <= i + j; j1++) {
			for (int k1 = i1; k1 <= i1 + l; k1++) {
				if (k1 >= 0 && k1 < regionSizeX && j1 >= 0 && j1 < regionSizeY) {
					shadowMap[0][k1][j1] = 127;
					if (k1 == i1 && k1 > 0) {
						heightMap[0][k1][j1] = heightMap[0][k1 - 1][j1];
					}
					if (k1 == i1 + l && k1 < regionSizeX - 1) {
						heightMap[0][k1][j1] = heightMap[0][k1 + 1][j1];
					}
					if (j1 == i && j1 > 0) {
						heightMap[0][k1][j1] = heightMap[0][k1][j1 - 1];
					}
					if (j1 == i + j && j1 < regionSizeY - 1) {
						heightMap[0][k1][j1] = heightMap[0][k1][j1 + 1];
					}
				}
			}

		}
	}

	private void addObject(int i, Landscape landscape, CollisionMap class11, int j, int k, int l, int i1, int j1) {
		if (lowMem && (tileFlags[0][l][i] & 2) == 0) {
			if ((tileFlags[k][l][i] & 0x10) != 0) {
				return;
			}
			if (getVisiblePlane(i, k, l) != buildPlane) {
				return;
			}
		}
		if (k < minPlane) {
			minPlane = k;
		}
		int k1 = heightMap[k][l][i];
		int l1 = heightMap[k][l + 1][i];
		int i2 = heightMap[k][l + 1][i + 1];
		int j2 = heightMap[k][l][i + 1];
		int k2 = k1 + l1 + i2 + j2 >> 2;
		ObjectDefinition class46 = ObjectDefinition.forID(i1);
		int l2 = l + (i << 7) + (i1 << 14) + 0x40000000;
		if (!class46.hasActions) {
			l2 += 0x80000000;
		}
		byte byte0 = (byte) ((j1 << 6) + j);
		if (j == 22) {
			if (lowMem && !class46.hasActions && !class46.isDecoration) {
				return;
			}
			Object obj;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj = class46.getAdjustedModel(22, j1, k1, l1, i2, j2, -1);
			} else {
				obj = new SceneObject(i1, j1, 22, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addGroundDecoration(k, k2, i, ((SceneNode) obj), byte0, l2, l);
			if (class46.blocksWalk && class46.hasActions && class11 != null) {
				class11.setSolid(i, l);
			}
			return;
		}
		if (j == 10 || j == 11) {
			Object obj1;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj1 = class46.getAdjustedModel(10, j1, k1, l1, i2, j2, -1);
			} else {
				obj1 = new SceneObject(i1, j1, 10, l1, i2, k1, j2, class46.animationID, true);
			}
			if (obj1 != null) {
				int i5 = 0;
				if (j == 11) {
					i5 += 256;
				}
				int j4;
				int l4;
				if (j1 == 1 || j1 == 3) {
					j4 = class46.sizeY;
					l4 = class46.sizeX;
				} else {
					j4 = class46.sizeX;
					l4 = class46.sizeY;
				}
				if (landscape.add(l2, byte0, k2, l4, ((SceneNode) obj1), j4, k, i5, i, l) && class46.castsShadow) {
					Model model;
					if (obj1 instanceof Model) {
						model = (Model) obj1;
					} else {
						model = class46.getAdjustedModel(10, j1, k1, l1, i2, j2, -1);
					}
					if (model != null) {
						for (int j5 = 0; j5 <= j4; j5++) {
							for (int k5 = 0; k5 <= l4; k5++) {
								int l5 = model.maxVertexDistanceXZPlane / 4;
								if (l5 > 30) {
									l5 = 30;
								}
								if (l5 > shadowMap[k][l + j5][i + k5]) {
									shadowMap[k][l + j5][i + k5] = (byte) l5;
								}
							}

						}

					}
				}
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, l, i, j1);
			}
			return;
		}
		if (j >= 12) {
			Object obj2;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj2 = class46.getAdjustedModel(j, j1, k1, l1, i2, j2, -1);
			} else {
				obj2 = new SceneObject(i1, j1, j, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.add(l2, byte0, k2, 1, ((SceneNode) obj2), 1, k, 0, i, l);
			if (j >= 12 && j <= 17 && j != 13 && k > 0) {
				cullingMap[k][l][i] |= 0x924;
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, l, i, j1);
			}
			return;
		}
		if (j == 0) {
			Object obj3;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj3 = class46.getAdjustedModel(0, j1, k1, l1, i2, j2, -1);
			} else {
				obj3 = new SceneObject(i1, j1, 0, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWall(WALL_ROOT_FLAGS[j1], ((SceneNode) obj3), l2, i, byte0, l, null, k2, 0, k);
			if (j1 == 0) {
				if (class46.castsShadow) {
					shadowMap[k][l][i] = 50;
					shadowMap[k][l][i + 1] = 50;
				}
				if (class46.isSolid) {
					cullingMap[k][l][i] |= 0x249;
				}
			} else if (j1 == 1) {
				if (class46.castsShadow) {
					shadowMap[k][l][i + 1] = 50;
					shadowMap[k][l + 1][i + 1] = 50;
				}
				if (class46.isSolid) {
					cullingMap[k][l][i + 1] |= 0x492;
				}
			} else if (j1 == 2) {
				if (class46.castsShadow) {
					shadowMap[k][l + 1][i] = 50;
					shadowMap[k][l + 1][i + 1] = 50;
				}
				if (class46.isSolid) {
					cullingMap[k][l + 1][i] |= 0x249;
				}
			} else if (j1 == 3) {
				if (class46.castsShadow) {
					shadowMap[k][l][i] = 50;
					shadowMap[k][l + 1][i] = 50;
				}
				if (class46.isSolid) {
					cullingMap[k][l][i] |= 0x492;
				}
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addWall(i, j1, l, j, class46.blocksProjectiles);
			}
			if (class46.wallWidth != 16) {
				landscape.setWallDecorationPadding(i, class46.wallWidth, l, k);
			}
			return;
		}
		if (j == 1) {
			Object obj4;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj4 = class46.getAdjustedModel(1, j1, k1, l1, i2, j2, -1);
			} else {
				obj4 = new SceneObject(i1, j1, 1, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWall(WALL_EXT_FLAGS[j1], ((SceneNode) obj4), l2, i, byte0, l, null, k2, 0, k);
			if (class46.castsShadow) {
				if (j1 == 0) {
					shadowMap[k][l][i + 1] = 50;
				} else if (j1 == 1) {
					shadowMap[k][l + 1][i + 1] = 50;
				} else if (j1 == 2) {
					shadowMap[k][l + 1][i] = 50;
				} else if (j1 == 3) {
					shadowMap[k][l][i] = 50;
				}
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addWall(i, j1, l, j, class46.blocksProjectiles);
			}
			return;
		}
		if (j == 2) {
			int i3 = j1 + 1 & 3;
			Object obj11;
			Object obj12;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj11 = class46.getAdjustedModel(2, 4 + j1, k1, l1, i2, j2, -1);
				obj12 = class46.getAdjustedModel(2, i3, k1, l1, i2, j2, -1);
			} else {
				obj11 = new SceneObject(i1, 4 + j1, 2, l1, i2, k1, j2, class46.animationID, true);
				obj12 = new SceneObject(i1, i3, 2, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWall(WALL_ROOT_FLAGS[j1], ((SceneNode) obj11), l2, i, byte0, l, ((SceneNode) obj12), k2, WALL_ROOT_FLAGS[i3], k);
			if (class46.isSolid) {
				if (j1 == 0) {
					cullingMap[k][l][i] |= 0x249;
					cullingMap[k][l][i + 1] |= 0x492;
				} else if (j1 == 1) {
					cullingMap[k][l][i + 1] |= 0x492;
					cullingMap[k][l + 1][i] |= 0x249;
				} else if (j1 == 2) {
					cullingMap[k][l + 1][i] |= 0x249;
					cullingMap[k][l][i] |= 0x492;
				} else if (j1 == 3) {
					cullingMap[k][l][i] |= 0x492;
					cullingMap[k][l][i] |= 0x249;
				}
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addWall(i, j1, l, j, class46.blocksProjectiles);
			}
			if (class46.wallWidth != 16) {
				landscape.setWallDecorationPadding(i, class46.wallWidth, l, k);
			}
			return;
		}
		if (j == 3) {
			Object obj5;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj5 = class46.getAdjustedModel(3, j1, k1, l1, i2, j2, -1);
			} else {
				obj5 = new SceneObject(i1, j1, 3, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWall(WALL_EXT_FLAGS[j1], ((SceneNode) obj5), l2, i, byte0, l, null, k2, 0, k);
			if (class46.castsShadow) {
				if (j1 == 0) {
					shadowMap[k][l][i + 1] = 50;
				} else if (j1 == 1) {
					shadowMap[k][l + 1][i + 1] = 50;
				} else if (j1 == 2) {
					shadowMap[k][l + 1][i] = 50;
				} else if (j1 == 3) {
					shadowMap[k][l][i] = 50;
				}
			}
			if (class46.blocksWalk && class11 != null) {
				class11.addWall(i, j1, l, j, class46.blocksProjectiles);
			}
			return;
		}
		if (j == 9) {
			Object obj6;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj6 = class46.getAdjustedModel(j, j1, k1, l1, i2, j2, -1);
			} else {
				obj6 = new SceneObject(i1, j1, j, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.add(l2, byte0, k2, 1, ((SceneNode) obj6), 1, k, 0, i, l);
			if (class46.blocksWalk && class11 != null) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, l, i, j1);
			}
			return;
		}
		if (class46.adjustsToTerrain) {
			if (j1 == 1) {
				int j3 = j2;
				j2 = i2;
				i2 = l1;
				l1 = k1;
				k1 = j3;
			} else if (j1 == 2) {
				int k3 = j2;
				j2 = l1;
				l1 = k3;
				k3 = i2;
				i2 = k1;
				k1 = k3;
			} else if (j1 == 3) {
				int l3 = j2;
				j2 = k1;
				k1 = l1;
				l1 = i2;
				i2 = l3;
			}
		}
		if (j == 4) {
			Object obj7;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj7 = class46.getAdjustedModel(4, 0, k1, l1, i2, j2, -1);
			} else {
				obj7 = new SceneObject(i1, 0, 4, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWallDecoration(l2, i, j1 * 512, k, 0, k2, ((SceneNode) obj7), l, byte0, 0, WALL_ROOT_FLAGS[j1]);
			return;
		}
		if (j == 5) {
			int i4 = 16;
			int k4 = landscape.getWallUID(k, l, i);
			if (k4 > 0) {
				i4 = ObjectDefinition.forID(k4 >> 14 & 0x7fff).wallWidth;
			}
			Object obj13;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj13 = class46.getAdjustedModel(4, 0, k1, l1, i2, j2, -1);
			} else {
				obj13 = new SceneObject(i1, 0, 4, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWallDecoration(l2, i, j1 * 512, k, ROT_X_DELTA[j1] * i4, k2, ((SceneNode) obj13), l, byte0, ROT_Y_DELTA[j1] * i4, WALL_ROOT_FLAGS[j1]);
			return;
		}
		if (j == 6) {
			Object obj8;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj8 = class46.getAdjustedModel(4, 0, k1, l1, i2, j2, -1);
			} else {
				obj8 = new SceneObject(i1, 0, 4, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWallDecoration(l2, i, j1, k, 0, k2, ((SceneNode) obj8), l, byte0, 0, 256);
			return;
		}
		if (j == 7) {
			Object obj9;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj9 = class46.getAdjustedModel(4, 0, k1, l1, i2, j2, -1);
			} else {
				obj9 = new SceneObject(i1, 0, 4, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWallDecoration(l2, i, j1, k, 0, k2, ((SceneNode) obj9), l, byte0, 0, 512);
			return;
		}
		if (j == 8) {
			Object obj10;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj10 = class46.getAdjustedModel(4, 0, k1, l1, i2, j2, -1);
			} else {
				obj10 = new SceneObject(i1, 0, 4, l1, i2, k1, j2, class46.animationID, true);
			}
			landscape.addWallDecoration(l2, i, j1, k, 0, k2, ((SceneNode) obj10), l, byte0, 0, 768);
		}
	}

	private static int getNoise(int i, int j, int k) {
		int l = i / k;
		int i1 = i & k - 1;
		int j1 = j / k;
		int k1 = j & k - 1;
		int l1 = getNoise2D(l, j1);
		int i2 = getNoise2D(l + 1, j1);
		int j2 = getNoise2D(l, j1 + 1);
		int k2 = getNoise2D(l + 1, j1 + 1);
		int l2 = getLerpedCosine(l1, i2, i1, k);
		int i3 = getLerpedCosine(j2, k2, i1, k);
		return getLerpedCosine(l2, i3, k1, k);
	}

	private int getTrimmedHSL(int i, int j, int k) {
		if (k > 179) {
			j /= 2;
		}
		if (k > 192) {
			j /= 2;
		}
		if (k > 217) {
			j /= 2;
		}
		if (k > 243) {
			j /= 2;
		}
		return (i / 4 << 10) + (j / 32 << 7) + k / 2;
	}

	public static boolean objectIsValid(int id, int j) {
		ObjectDefinition class46 = ObjectDefinition.forID(id);
		if (j == 11) {
			j = 10;
		}
		if (j >= 5 && j <= 8) {
			j = 4;
		}
		return class46.method577(j);
	}

	public void loadLand1(int i, int j, CollisionMap[] aclass11, int l, int i1, byte[] abyte0, int j1, int k1, int l1) {
		for (int i2 = 0; i2 < 8; i2++) {
			for (int j2 = 0; j2 < 8; j2++) {
				if (l + i2 > 0 && l + i2 < 103 && l1 + j2 > 0 && l1 + j2 < 103) {
					aclass11[k1].adjacencies[l + i2][l1 + j2] &= 0xfeffffff;
				}
			}

		}
		ByteBuffer byteBuffer = new ByteBuffer(abyte0);
		for (int l2 = 0; l2 < 4; l2++) {
			for (int i3 = 0; i3 < 64; i3++) {
				for (int j3 = 0; j3 < 64; j3++) {
					if (l2 == i && i3 >= i1 && i3 < i1 + 8 && j3 >= j1 && j3 < j1 + 8) {
						loadLand3(l1 + ChunkUtil.getLandOffsetY(j3 & 7, j, i3 & 7), 0, byteBuffer, l + ChunkUtil.getLandOffsetX(j, j3 & 7, i3 & 7), k1, j, 0);
					} else {
						loadLand3(-1, 0, byteBuffer, -1, 0, 0, 0);
					}
				}

			}

		}

	}

	public void loadLand2(byte[] abyte0, int i, int j, int k, int l, CollisionMap[] collisionMaps) {
		for (int i1 = 0; i1 < 4; i1++) {
			for (int j1 = 0; j1 < 64; j1++) {
				for (int k1 = 0; k1 < 64; k1++) {
					if (j + j1 > 0 && j + j1 < 103 && i + k1 > 0 && i + k1 < 103) {
						collisionMaps[i1].adjacencies[j + j1][i + k1] &= 0xfeffffff;
					}
				}

			}

		}

		ByteBuffer byteBuffer = new ByteBuffer(abyte0);
		for (int l1 = 0; l1 < 4; l1++) {
			for (int i2 = 0; i2 < 64; i2++) {
				for (int j2 = 0; j2 < 64; j2++) {
					loadLand3(j2 + i, l, byteBuffer, i2 + j, l1, 0, k);
				}

			}

		}
	}

	private void loadLand3(int i, int j, ByteBuffer byteBuffer, int k, int l, int i1, int k1) {
		if (k >= 0 && k < 104 && i >= 0 && i < 104) {
			tileFlags[l][k][i] = 0;
			do {
				int l1 = byteBuffer.readUnsignedByte();
				if (l1 == 0) {
					if (l == 0) {
						heightMap[0][k][i] = -getHeightNoise(0xe3b7b + k + k1, 0x87cce + i + j) * 8;
						return;
					} else {
						heightMap[l][k][i] = heightMap[l - 1][k][i] - 240;
						return;
					}
				}
				if (l1 == 1) {
					int j2 = byteBuffer.readUnsignedByte();
					if (j2 == 1) {
						j2 = 0;
					}
					if (l == 0) {
						heightMap[0][k][i] = -j2 * 8;
						return;
					} else {
						heightMap[l][k][i] = heightMap[l - 1][k][i] - j2 * 8;
						return;
					}
				}
				if (l1 <= 49) {
					overlays[l][k][i] = byteBuffer.readSignedByte();
					overlayTypes[l][k][i] = (byte) ((l1 - 2) / 4);
					overlayOrientations[l][k][i] = (byte) (l1 - 2 + i1 & 3);
				} else if (l1 <= 81) {
					tileFlags[l][k][i] = (byte) (l1 - 49);
				} else {
					underlays[l][k][i] = (byte) (l1 - 81);
				}
			} while (true);
		}
		do {
			int i2 = byteBuffer.readUnsignedByte();
			if (i2 == 0) {
				break;
			}
			if (i2 == 1) {
				byteBuffer.readUnsignedByte();
				return;
			}
			if (i2 <= 49) {
				byteBuffer.readUnsignedByte();
			}
		} while (true);
	}

	private int getVisiblePlane(int i, int j, int k) {
		if ((tileFlags[j][k][i] & 8) != 0) {
			return 0;
		}
		if (j > 0 && (tileFlags[1][k][i] & 2) != 0) {
			return j - 1;
		} else {
			return j;
		}
	}

	public void loadObjects(CollisionMap[] aclass11, Landscape landscape, int i, int j, int k, int l, byte[] abyte0, int i1, int j1, int k1) {
		label0 : {
			ByteBuffer byteBuffer = new ByteBuffer(abyte0);
			int l1 = -1;
			do {
				int i2 = byteBuffer.method422();
				if (i2 == 0) {
					break label0;
				}
				l1 += i2;
				int j2 = 0;
				do {
					int k2 = byteBuffer.method422();
					if (k2 == 0) {
						break;
					}
					j2 += k2 - 1;
					int l2 = j2 & 0x3f;
					int i3 = j2 >> 6 & 0x3f;
					int j3 = j2 >> 12;
					int k3 = byteBuffer.readUnsignedByte();
					int l3 = k3 >> 2;
					int i4 = k3 & 3;
					if (j3 == i && i3 >= i1 && i3 < i1 + 8 && l2 >= k && l2 < k + 8) {
						ObjectDefinition class46 = ObjectDefinition.forID(l1);
						int j4 = j + ChunkUtil.getObjectOffsetX(j1, class46.sizeY, i3 & 7, l2 & 7, class46.sizeX);
						int k4 = k1 + ChunkUtil.getObjectOffsetY(l2 & 7, class46.sizeY, j1, class46.sizeX, i3 & 7);
						if (j4 > 0 && k4 > 0 && j4 < 103 && k4 < 103) {
							int l4 = j3;
							if ((tileFlags[1][j4][k4] & 2) == 2) {
								l4--;
							}
							CollisionMap class11 = null;
							if (l4 >= 0) {
								class11 = aclass11[l4];
							}
							addObject(k4, landscape, class11, l3, l, j4, l1, i4 + j1 & 3);
						}
					}
				} while (true);
			} while (true);
		}
	}

	private static int getLerpedCosine(int i, int j, int k, int l) {
		int i1 = 0x10000 - Texture.cos[k * 1024 / l] >> 1;
		return (i * (0x10000 - i1) >> 16) + (j * i1 >> 16);
	}

	private int getRGB(int i, int j) {
		if (i == -2) {
			return 0xbc614e;
		}
		if (i == -1) {
			if (j < 0) {
				j = 0;
			} else if (j > 127) {
				j = 127;
			}
			j = 127 - j;
			return j;
		}
		j = j * (i & 0x7f) / 128;
		if (j < 2) {
			j = 2;
		} else if (j > 126) {
			j = 126;
		}
		return (i & 0xff80) + j;
	}

	private static int getNoise2D(int i, int j) {
		int k = getPerlinNoise(i - 1, j - 1) + getPerlinNoise(i + 1, j - 1) + getPerlinNoise(i - 1, j + 1) + getPerlinNoise(i + 1, j + 1);
		int l = getPerlinNoise(i - 1, j) + getPerlinNoise(i + 1, j) + getPerlinNoise(i, j - 1) + getPerlinNoise(i, j + 1);
		int i1 = getPerlinNoise(i, j);
		return k / 16 + l / 8 + i1 / 4;
	}

	private static int adjustHSLLightness(int i, int j) {
		if (i == -1) {
			return 0xbc614e;
		}
		j = j * (i & 0x7f) / 128;
		if (j < 2) {
			j = 2;
		} else if (j > 126) {
			j = 126;
		}
		return (i & 0xff80) + j;
	}

	public static void addObject(Landscape landscape, int i, int j, int k, int l, CollisionMap class11, int[][][] ai, int i1, int j1, int k1) {
		int l1 = ai[l][i1][j];
		int i2 = ai[l][i1 + 1][j];
		int j2 = ai[l][i1 + 1][j + 1];
		int k2 = ai[l][i1][j + 1];
		int l2 = l1 + i2 + j2 + k2 >> 2;
		ObjectDefinition class46 = ObjectDefinition.forID(j1);
		int i3 = i1 + (j << 7) + (j1 << 14) + 0x40000000;
		if (!class46.hasActions) {
			i3 += 0x80000000;
		}
		byte byte1 = (byte) ((i << 6) + k);
		if (k == 22) {
			Object obj;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj = class46.getAdjustedModel(22, i, l1, i2, j2, k2, -1);
			} else {
				obj = new SceneObject(j1, i, 22, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addGroundDecoration(k1, l2, j, ((SceneNode) obj), byte1, i3, i1);
			if (class46.blocksWalk && class46.hasActions) {
				class11.setSolid(j, i1);
			}
			return;
		}
		if (k == 10 || k == 11) {
			Object obj1;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj1 = class46.getAdjustedModel(10, i, l1, i2, j2, k2, -1);
			} else {
				obj1 = new SceneObject(j1, i, 10, i2, j2, l1, k2, class46.animationID, true);
			}
			if (obj1 != null) {
				int j5 = 0;
				if (k == 11) {
					j5 += 256;
				}
				int k4;
				int i5;
				if (i == 1 || i == 3) {
					k4 = class46.sizeY;
					i5 = class46.sizeX;
				} else {
					k4 = class46.sizeX;
					i5 = class46.sizeY;
				}
				landscape.add(i3, byte1, l2, i5, ((SceneNode) obj1), k4, k1, j5, j, i1);
			}
			if (class46.blocksWalk) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, i1, j, i);
			}
			return;
		}
		if (k >= 12) {
			Object obj2;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj2 = class46.getAdjustedModel(k, i, l1, i2, j2, k2, -1);
			} else {
				obj2 = new SceneObject(j1, i, k, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.add(i3, byte1, l2, 1, ((SceneNode) obj2), 1, k1, 0, j, i1);
			if (class46.blocksWalk) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, i1, j, i);
			}
			return;
		}
		if (k == 0) {
			Object obj3;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj3 = class46.getAdjustedModel(0, i, l1, i2, j2, k2, -1);
			} else {
				obj3 = new SceneObject(j1, i, 0, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWall(WALL_ROOT_FLAGS[i], ((SceneNode) obj3), i3, j, byte1, i1, null, l2, 0, k1);
			if (class46.blocksWalk) {
				class11.addWall(j, i, i1, k, class46.blocksProjectiles);
			}
			return;
		}
		if (k == 1) {
			Object obj4;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj4 = class46.getAdjustedModel(1, i, l1, i2, j2, k2, -1);
			} else {
				obj4 = new SceneObject(j1, i, 1, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWall(WALL_EXT_FLAGS[i], ((SceneNode) obj4), i3, j, byte1, i1, null, l2, 0, k1);
			if (class46.blocksWalk) {
				class11.addWall(j, i, i1, k, class46.blocksProjectiles);
			}
			return;
		}
		if (k == 2) {
			int j3 = i + 1 & 3;
			Object obj11;
			Object obj12;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj11 = class46.getAdjustedModel(2, 4 + i, l1, i2, j2, k2, -1);
				obj12 = class46.getAdjustedModel(2, j3, l1, i2, j2, k2, -1);
			} else {
				obj11 = new SceneObject(j1, 4 + i, 2, i2, j2, l1, k2, class46.animationID, true);
				obj12 = new SceneObject(j1, j3, 2, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWall(WALL_ROOT_FLAGS[i], ((SceneNode) obj11), i3, j, byte1, i1, ((SceneNode) obj12), l2, WALL_ROOT_FLAGS[j3], k1);
			if (class46.blocksWalk) {
				class11.addWall(j, i, i1, k, class46.blocksProjectiles);
			}
			return;
		}
		if (k == 3) {
			Object obj5;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj5 = class46.getAdjustedModel(3, i, l1, i2, j2, k2, -1);
			} else {
				obj5 = new SceneObject(j1, i, 3, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWall(WALL_EXT_FLAGS[i], ((SceneNode) obj5), i3, j, byte1, i1, null, l2, 0, k1);
			if (class46.blocksWalk) {
				class11.addWall(j, i, i1, k, class46.blocksProjectiles);
			}
			return;
		}
		if (k == 9) {
			Object obj6;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj6 = class46.getAdjustedModel(k, i, l1, i2, j2, k2, -1);
			} else {
				obj6 = new SceneObject(j1, i, k, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.add(i3, byte1, l2, 1, ((SceneNode) obj6), 1, k1, 0, j, i1);
			if (class46.blocksWalk) {
				class11.addObject(class46.blocksProjectiles, class46.sizeX, class46.sizeY, i1, j, i);
			}
			return;
		}
		if (class46.adjustsToTerrain) {
			if (i == 1) {
				int k3 = k2;
				k2 = j2;
				j2 = i2;
				i2 = l1;
				l1 = k3;
			} else if (i == 2) {
				int l3 = k2;
				k2 = i2;
				i2 = l3;
				l3 = j2;
				j2 = l1;
				l1 = l3;
			} else if (i == 3) {
				int i4 = k2;
				k2 = l1;
				l1 = i2;
				i2 = j2;
				j2 = i4;
			}
		}
		if (k == 4) {
			Object obj7;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj7 = class46.getAdjustedModel(4, 0, l1, i2, j2, k2, -1);
			} else {
				obj7 = new SceneObject(j1, 0, 4, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWallDecoration(i3, j, i * 512, k1, 0, l2, ((SceneNode) obj7), i1, byte1, 0, WALL_ROOT_FLAGS[i]);
			return;
		}
		if (k == 5) {
			int j4 = 16;
			int l4 = landscape.getWallUID(k1, i1, j);
			if (l4 > 0) {
				j4 = ObjectDefinition.forID(l4 >> 14 & 0x7fff).wallWidth;
			}
			Object obj13;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj13 = class46.getAdjustedModel(4, 0, l1, i2, j2, k2, -1);
			} else {
				obj13 = new SceneObject(j1, 0, 4, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWallDecoration(i3, j, i * 512, k1, ROT_X_DELTA[i] * j4, l2, ((SceneNode) obj13), i1, byte1, ROT_Y_DELTA[i] * j4, WALL_ROOT_FLAGS[i]);
			return;
		}
		if (k == 6) {
			Object obj8;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj8 = class46.getAdjustedModel(4, 0, l1, i2, j2, k2, -1);
			} else {
				obj8 = new SceneObject(j1, 0, 4, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWallDecoration(i3, j, i, k1, 0, l2, ((SceneNode) obj8), i1, byte1, 0, 256);
			return;
		}
		if (k == 7) {
			Object obj9;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj9 = class46.getAdjustedModel(4, 0, l1, i2, j2, k2, -1);
			} else {
				obj9 = new SceneObject(j1, 0, 4, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWallDecoration(i3, j, i, k1, 0, l2, ((SceneNode) obj9), i1, byte1, 0, 512);
			return;
		}
		if (k == 8) {
			Object obj10;
			if (class46.animationID == -1 && class46.childrenIDs == null) {
				obj10 = class46.getAdjustedModel(4, 0, l1, i2, j2, k2, -1);
			} else {
				obj10 = new SceneObject(j1, 0, 4, i2, j2, l1, k2, class46.animationID, true);
			}
			landscape.addWallDecoration(i3, j, i, k1, 0, l2, ((SceneNode) obj10), i1, byte1, 0, 768);
		}
	}

	public static boolean allObjectsLoaded(int i, byte[] is, int i_250_) {
		boolean bool = true;
		ByteBuffer byteBuffer = new ByteBuffer(is);
		int i_252_ = -1;
		for (;;) {
			int i_253_ = byteBuffer.method422();
			if (i_253_ == 0) {
				break;
			}
			i_252_ += i_253_;
			int i_254_ = 0;
			boolean bool_255_ = false;
			for (;;) {
				if (bool_255_) {
					int i_256_ = byteBuffer.method422();
					if (i_256_ == 0) {
						break;
					}
					byteBuffer.readUnsignedByte();
				} else {
					int i_257_ = byteBuffer.method422();
					if (i_257_ == 0) {
						break;
					}
					i_254_ += i_257_ - 1;
					int i_258_ = i_254_ & 0x3f;
					int i_259_ = i_254_ >> 6 & 0x3f;
					int i_260_ = byteBuffer.readUnsignedByte() >> 2;
					int i_261_ = i_259_ + i;
					int i_262_ = i_258_ + i_250_;
					if (i_261_ > 0 && i_262_ > 0 && i_261_ < 103 && i_262_ < 103) {
						ObjectDefinition class46 = ObjectDefinition.forID(i_252_);
						if (i_260_ != 22 || !lowMem || class46.hasActions || class46.isDecoration) {
							bool &= class46.method579();
							bool_255_ = true;
						}
					}
				}
			}
		}
		return bool;
	}

	public void loadObjects(int i, CollisionMap[] aclass11, int j, Landscape landscape, byte[] abyte0) {
		label0 : {
			ByteBuffer byteBuffer = new ByteBuffer(abyte0);
			int l = -1;
			do {
				int i1 = byteBuffer.method422();
				if (i1 == 0) {
					break label0;
				}
				l += i1;
				int j1 = 0;
				do {
					int k1 = byteBuffer.method422();
					if (k1 == 0) {
						break;
					}
					j1 += k1 - 1;
					int l1 = j1 & 0x3f;
					int i2 = j1 >> 6 & 0x3f;
					int j2 = j1 >> 12;
					int k2 = byteBuffer.readUnsignedByte();
					int l2 = k2 >> 2;
					int i3 = k2 & 3;
					int j3 = i2 + i;
					int k3 = l1 + j;
					if (j3 > 0 && k3 > 0 && j3 < 103 && k3 < 103) {
						int l3 = j2;
						if ((tileFlags[1][j3][k3] & 2) == 2) {
							l3--;
						}
						CollisionMap class11 = null;
						if (l3 >= 0) {
							class11 = aclass11[l3];
						}
						addObject(k3, landscape, class11, l2, j2, j3, l, i3);
					}
				} while (true);
			} while (true);
		}
	}

	private static int anInt123 = (int) (Math.random() * 17D) - 8;
	private final int[] blendedHue;
	private final int[] blendedSaturation;
	private final int[] blendedLightness;
	private final int[] blendedHueDivisor;
	private final int[] blendedDirection;
	private final int[][][] heightMap;
	private final byte[][][] overlays;
	public static int buildPlane;
	private static int anInt133 = (int) (Math.random() * 33D) - 16;
	private final byte[][][] shadowMap;
	private final int[][][] cullingMap;
	private final byte[][][] overlayTypes;
	private static final int[] ROT_X_DELTA = {1, 0, -1, 0};
	private final int[][] lightMap;
	private static final int[] WALL_EXT_FLAGS = {16, 32, 64, 128};
	private final byte[][][] underlays;
	private static final int[] ROT_Y_DELTA = {0, -1, 0, 1};
	public static int minPlane = 99;
	private final int regionSizeX;
	private final int regionSizeY;
	private final byte[][][] overlayOrientations;
	private final byte[][][] tileFlags;
	public static boolean lowMem = true;
	private static final int[] WALL_ROOT_FLAGS = {1, 2, 4, 8};

}
