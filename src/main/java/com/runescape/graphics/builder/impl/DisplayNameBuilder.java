package com.runescape.graphics.builder.impl;

import com.runescape.graphics.builder.InterfaceBuilder;

public class DisplayNameBuilder extends InterfaceBuilder {
    public DisplayNameBuilder() {
        super(20125);
    }

    @Override
    public void build() {

        addBackgroundImage(nextInterface(), 300, 250, true);
        child(105, 40); // Background

        addText(nextInterface(), "Choose Display Name", 0xff981f, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 50);//Title text

        addText(nextInterface(), "In order to play Runescape, you must select a", 0xff981f, false, true, 52, defaultTextDrawingAreas, 1);
        child(131, 80);//Description text
        addText(nextInterface(), "unique display name for your character.", 0xff981f, false, true, 52, defaultTextDrawingAreas, 1);
        child(138, 92);//Description text 2

        addText(nextInterface(), "This cannot yet be changed at a later stage.", 0xff981f, false, true, 52, defaultTextDrawingAreas, 1);
        child(131, 115);//Change later text

        addText(nextInterface(), "Display Name", 0xff981f, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 155);//Text above input field

        addSprite(nextInterface(), 13);
        child(115, 175);//name background

        addText(nextInterface(), "Available!", 0xff981f, true, true, 52, defaultTextDrawingAreas, 1);
        child(255, 199);//Available text

        addText(nextInterface(), "Please enter a name to see whether it is available.", 0xff981f, false, true, 52, defaultTextDrawingAreas, 1);
        child(115, 224);//Text below text box

        addHoverButton(nextInterface(), 8, 9, "Look Up Name",0, 0, "");
        child(130, 249);

        addHoverButton(nextInterface(), 10, 11, "Set Name",0, 0, "");
        child(260, 249);

        addText(nextInterface(), "", 0xffffff, true, true, 52, defaultTextDrawingAreas, 2);
        child(255, 178);//Name text in the box
        
    }
}
