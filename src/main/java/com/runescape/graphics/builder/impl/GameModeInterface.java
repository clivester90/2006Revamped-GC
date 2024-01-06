package com.runescape.graphics.builder.impl;

import com.runescape.graphics.builder.InterfaceBuilder;

import static com.runescape.constants.ColourConstants.DEFAULT_TEXT_COLOR;

public class GameModeInterface extends InterfaceBuilder {
    public GameModeInterface() {
        super(20_000);
    }

    @Override
    public void build() {
        addBackgroundImage(nextInterface(), 450, 300, true);
        child(25, 20);

        addTitleText(nextInterface(), "Game Mode Selection");
        child(250, 30);

        addSmallCloseButton(nextInterface());
        child(447, 29);

        addWrappingText(nextInterface(), "Testing wrapped text on a 2006 client for game mode interfaces part one.", defaultTextDrawingAreas, 0, DEFAULT_TEXT_COLOR, false, true, 150);
        child(40, 80);

    }
}
