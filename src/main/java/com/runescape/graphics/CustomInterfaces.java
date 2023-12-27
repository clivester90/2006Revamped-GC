package com.runescape.graphics;

import com.runescape.graphics.builder.impl.GameModeInterface;

public class CustomInterfaces {

    private CustomInterfaces() {}

    public static void loadInterfaces() {
        new GameModeInterface().build();
    }

}
