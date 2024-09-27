package com.runescape.graphics;

import com.runescape.graphics.builder.impl.DisplayNameBuilder;
import com.runescape.graphics.builder.impl.GameModeInterface;
import com.runescape.graphics.builder.impl.RecoveryEmail;

public class CustomInterfaces {

    private CustomInterfaces() {}

    public static void loadInterfaces() {
        new GameModeInterface().build();
        new RecoveryEmail().build();
        new DisplayNameBuilder().build();
    }

}
