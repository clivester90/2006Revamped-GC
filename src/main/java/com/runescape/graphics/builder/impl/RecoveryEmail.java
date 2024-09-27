package com.runescape.graphics.builder.impl;

import com.runescape.graphics.builder.InterfaceBuilder;

public class RecoveryEmail extends InterfaceBuilder {
    public RecoveryEmail() {
        super(20100);
    }

    @Override
    public void build() {
        addBackgroundImage(nextInterface(), 300, 250, true);
        child(105, 40); // Background

        addHoverButton(nextInterface(), 14, 15, "Set recovery email", 0, 0xff981f, "Set Email");
        child(195, 249);

        addText(nextInterface(), "Choose Email Address", 0xff981f, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 50);//Title text

        addSprite(nextInterface(), 13);
        child(115, 175);//email entered background

        addText(nextInterface(), "In order to play Runescape, you must enter a\\nrecovery email which will be used to recover \\nyour account in case of a hijacking " +
                "or to \\nappeal a punishment.", 0xff981f, false, true, 52, defaultTextDrawingAreas, 1);
        child(130, 80);//Description text

        addText(nextInterface(), "Warning: If this is not a valid email, \\nyou will not be able to recover your account!", 0xff981f, true, true, 52, defaultTextDrawingAreas, 1);
        child(255, 205);//warning text

        addText(nextInterface(), "Recovery Email", 0xff981f, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 155);//Button text

        addText(nextInterface(), "", 0xffffff, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 176);//entered email text
    }

}
