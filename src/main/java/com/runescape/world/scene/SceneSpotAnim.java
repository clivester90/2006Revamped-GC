package com.runescape.world.scene;// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)

import com.runescape.world.model.frames.Frames;
import com.runescape.world.model.Model;
import com.runescape.animation.SpotAnim;

public final class SceneSpotAnim extends SceneNode {

    public SceneSpotAnim(int i, int j, int l, int i1, int j1, int k1, int l1) {
        isFinished = false;
        aSpotAnim_1568 = SpotAnim.cache[i1];
        plane = i;
        x = l1;
        y = k1;
        z = j1;
        endCycle = j + l;
        isFinished = false;
    }

    @Override
    public Model getRotatedModel() {
        Model model = aSpotAnim_1568.getModel();
        if (model == null) {
            return null;
        }
        int   j       = aSpotAnim_1568.sequencesSequence.primary[anInt1569];
        Model model_1 = new Model(true, Frames.method532(j), false, model);
        if (!isFinished) {
            model_1.method469();
            model_1.method470(j);
            model_1.anIntArrayArray1658 = null;
            model_1.anIntArrayArray1657 = null;
        }
        if (aSpotAnim_1568.scale != 128 || aSpotAnim_1568.height != 128) {
            model_1.scaleModel(aSpotAnim_1568.scale, aSpotAnim_1568.scale, aSpotAnim_1568.height);
        }
        if (aSpotAnim_1568.anInt412 != 0) {
            if (aSpotAnim_1568.anInt412 == 90) {
                model_1.method473();
            }
            if (aSpotAnim_1568.anInt412 == 180) {
                model_1.method473();
                model_1.method473();
            }
            if (aSpotAnim_1568.anInt412 == 270) {
                model_1.method473();
                model_1.method473();
                model_1.method473();
            }
        }
        model_1.method479(64 + aSpotAnim_1568.ambient, 850 + aSpotAnim_1568.contrast, -30, -50, -30, true);
        return model_1;
    }

    public void method454(int i) {
        for (anInt1570 += i; anInt1570 > aSpotAnim_1568.sequencesSequence.getDuration(anInt1569); ) {
            anInt1570 -= aSpotAnim_1568.sequencesSequence.getDuration(anInt1569) + 1;
            anInt1569++;
            if (anInt1569 >= aSpotAnim_1568.sequencesSequence.length && (anInt1569 < 0 || anInt1569 >= aSpotAnim_1568.sequencesSequence.length)) {
                anInt1569 = 0;
                isFinished = true;
            }
        }

    }

    public final  int plane;
    public final  int x;
    public final  int y;
    public final  int z;
    public final  int endCycle;
    public        boolean isFinished;
    private final SpotAnim aSpotAnim_1568;
    private       int      anInt1569;
    private       int      anInt1570;
}
