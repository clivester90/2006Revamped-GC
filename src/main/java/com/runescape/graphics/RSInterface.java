package com.runescape.graphics;

import com.*;
import com.runescape.core.cache.ByteBuffer;
import com.runescape.core.cache.StreamLoader;
import com.runescape.constants.ColourConstants;
import com.runescape.graphics.dropdown.DropdownMenu;
import com.runescape.graphics.dropdown.MenuItem;
import com.runescape.item.ItemDef;
import com.runescape.entity.EntityDef;
import com.runescape.graphics.sprite.Sprite;
import com.runescape.graphics.text.TextClass;
import com.runescape.graphics.text.TextDrawingArea;
import com.runescape.world.model.Model;
import com.runescape.world.model.frames.Frames;
import com.google.common.base.Preconditions;
import com.runescape.constants.ClientSettings;
import com.runescape.core.node.ReferenceCache;
import com.runescape.util.MiscUtils;

public class RSInterface {

    public void swapInventoryItems(int i, int j) {
        int k = inv[i];
        inv[i] = inv[j];
        inv[j] = k;
        k = invStackSizes[i];
        invStackSizes[i] = invStackSizes[j];
        invStackSizes[j] = k;
    }

    public static void unpack(StreamLoader streamLoader, TextDrawingArea[] textDrawingAreas, StreamLoader streamLoader_1) {
        try {
            aReferenceCache_238 = new ReferenceCache(50000);
            ByteBuffer byteBuffer = new ByteBuffer(streamLoader.getDataForName("data"));
            int i = -1;
            int j = byteBuffer.readUnsignedWord();
            interfaceCache = new RSInterface[200000];
            while (byteBuffer.currentOffset < byteBuffer.buffer.length) {
                int k = byteBuffer.readUnsignedWord();
                if (k == 65535) {
                    i = byteBuffer.readUnsignedWord();
                    k = byteBuffer.readUnsignedWord();
                }
                RSInterface rsInterface = interfaceCache[k] = new RSInterface();
                rsInterface.id = k;
                rsInterface.parentID = i;
                rsInterface.type = byteBuffer.readUnsignedByte();
                //if(rsInterface.type == 0)
                //	System.out.println(rsInterface.parentID+":"+k);
                rsInterface.atActionType = byteBuffer.readUnsignedByte();
                rsInterface.contentType = byteBuffer.readUnsignedWord();
                rsInterface.width = byteBuffer.readUnsignedWord();
                rsInterface.height = byteBuffer.readUnsignedWord();
                rsInterface.aByte254 = (byte) byteBuffer.readUnsignedByte();
                rsInterface.mOverInterToTrigger = byteBuffer.readUnsignedByte();
                if (rsInterface.mOverInterToTrigger != 0) {
                    rsInterface.mOverInterToTrigger = (rsInterface.mOverInterToTrigger - 1 << 8) + byteBuffer.readUnsignedByte();
                } else {
                    rsInterface.mOverInterToTrigger = -1;
                }
                int i1 = byteBuffer.readUnsignedByte();
                if (i1 > 0) {
                    rsInterface.anIntArray245 = new int[i1];
                    rsInterface.anIntArray212 = new int[i1];
                    for (int j1 = 0; j1 < i1; j1++) {
                        rsInterface.anIntArray245[j1] = byteBuffer.readUnsignedByte();
                        rsInterface.anIntArray212[j1] = byteBuffer.readUnsignedWord();
                    }

                }
                int k1 = byteBuffer.readUnsignedByte();
                if (k1 > 0) {
                    rsInterface.valueIndexArray = new int[k1][];
                    for (int l1 = 0; l1 < k1; l1++) {
                        int i3 = byteBuffer.readUnsignedWord();
                        rsInterface.valueIndexArray[l1] = new int[i3];
                        for (int l4 = 0; l4 < i3; l4++) {
                            rsInterface.valueIndexArray[l1][l4] = byteBuffer.readUnsignedWord();
                        }

                    }

                }
                if (rsInterface.type == 0) {
                    rsInterface.scrollMax = byteBuffer.readUnsignedWord();
                    rsInterface.isMouseoverTriggered = byteBuffer.readUnsignedByte() == 1;
                    int i2 = byteBuffer.readUnsignedWord();
                    rsInterface.children = new int[i2];
                    rsInterface.childX = new int[i2];
                    rsInterface.childY = new int[i2];
                    for (int j3 = 0; j3 < i2; j3++) {
                        rsInterface.children[j3] = byteBuffer.readUnsignedWord();
                        rsInterface.childX[j3] = byteBuffer.readSignedWord();
                        rsInterface.childY[j3] = byteBuffer.readSignedWord();
                    }

                }
                if (rsInterface.type == 1) {
                    byteBuffer.readUnsignedWord();
                    byteBuffer.readUnsignedByte();
                }
                if (rsInterface.type == 2) {
                    rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                    rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                    rsInterface.aBoolean259 = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.isInventoryInterface = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.usableItemInterface = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.aBoolean235 = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.invSpritePadX = byteBuffer.readUnsignedByte();
                    rsInterface.invSpritePadY = byteBuffer.readUnsignedByte();
                    rsInterface.spritesX = new int[20];
                    rsInterface.spritesY = new int[20];
                    rsInterface.sprites = new Sprite[20];
                    for (int j2 = 0; j2 < 20; j2++) {
                        int k3 = byteBuffer.readUnsignedByte();
                        if (k3 == 1) {
                            rsInterface.spritesX[j2] = byteBuffer.readSignedWord();
                            rsInterface.spritesY[j2] = byteBuffer.readSignedWord();
                            String s1 = byteBuffer.readString();
                            if (streamLoader_1 != null && !s1.isEmpty()) {
                                int i5 = s1.lastIndexOf(",");
                                rsInterface.sprites[j2] = method207(Integer.parseInt(s1.substring(i5 + 1)), streamLoader_1, s1.substring(0, i5));
                            }
                        }
                    }

                    rsInterface.actions = new String[5];
                    for (int l3 = 0; l3 < 5; l3++) {
                        rsInterface.actions[l3] = byteBuffer.readString();
                        if (rsInterface.actions[l3].isEmpty()) {
                            rsInterface.actions[l3] = null;
                        }
                    }
                    if (rsInterface.parentID == 3822) {
                        rsInterface.actions[2] = "Sell 10";
                        rsInterface.actions[3] = "Sell X";
                    }
                    if (rsInterface.id == 3900) {
                        rsInterface.actions[2] = "Buy 10";
                        rsInterface.actions[3] = "Buy X";
                    }
                }
                if (rsInterface.type == 3) {
                    rsInterface.aBoolean227 = byteBuffer.readUnsignedByte() == 1;
                }
                if (rsInterface.type == 4 || rsInterface.type == 1) {
                    rsInterface.centerText = byteBuffer.readUnsignedByte() == 1;
                    int k2 = byteBuffer.readUnsignedByte();
                    if (textDrawingAreas != null) {
                        rsInterface.textDrawingAreas = textDrawingAreas[k2];
                    }
                    rsInterface.textShadow = byteBuffer.readUnsignedByte() == 1;
                }
                if (rsInterface.type == 4) {
                    rsInterface.disabledText = byteBuffer.readString().replaceAll("RuneScape", ClientSettings.SERVER_NAME);
                    rsInterface.enabledText = byteBuffer.readString();
                }
                if (rsInterface.type == 1 || rsInterface.type == 3 || rsInterface.type == 4) {
                    rsInterface.textColour = byteBuffer.readDWord();
                }
                if (rsInterface.type == 3 || rsInterface.type == 4) {
                    rsInterface.anInt219 = byteBuffer.readDWord();
                    rsInterface.anInt216 = byteBuffer.readDWord();
                    rsInterface.anInt239 = byteBuffer.readDWord();
                }
                if (rsInterface.type == 5) {
                    String s = byteBuffer.readString();
                    if (streamLoader_1 != null && !s.isEmpty()) {
                        int i4 = s.lastIndexOf(",");
                        rsInterface.sprite1 = method207(Integer.parseInt(s.substring(i4 + 1)), streamLoader_1, s.substring(0, i4));
                    }
                    s = byteBuffer.readString();
                    if (streamLoader_1 != null && !s.isEmpty()) {
                        int j4 = s.lastIndexOf(",");
                        rsInterface.sprite2 = method207(Integer.parseInt(s.substring(j4 + 1)), streamLoader_1, s.substring(0, j4));
                    }
                }
                if (rsInterface.type == 6) {
                    int l = byteBuffer.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.modelTypeDisabled = 1;
                        rsInterface.mediaID = (l - 1 << 8) + byteBuffer.readUnsignedByte();
                    }
                    l = byteBuffer.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt255 = 1;
                        rsInterface.anInt256 = (l - 1 << 8) + byteBuffer.readUnsignedByte();
                    }
                    l = byteBuffer.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt257 = (l - 1 << 8) + byteBuffer.readUnsignedByte();
                    } else {
                        rsInterface.anInt257 = -1;
                    }
                    l = byteBuffer.readUnsignedByte();
                    if (l != 0) {
                        rsInterface.anInt258 = (l - 1 << 8) + byteBuffer.readUnsignedByte();
                    } else {
                        rsInterface.anInt258 = -1;
                    }
                    rsInterface.modelZoom = byteBuffer.readUnsignedWord();
                    rsInterface.modelRotationY = byteBuffer.readUnsignedWord();
                    rsInterface.modelRotationX = byteBuffer.readUnsignedWord();
                }
                if (rsInterface.type == 7) {
                    rsInterface.inv = new int[rsInterface.width * rsInterface.height];
                    rsInterface.invStackSizes = new int[rsInterface.width * rsInterface.height];
                    rsInterface.centerText = byteBuffer.readUnsignedByte() == 1;
                    int l2 = byteBuffer.readUnsignedByte();
                    if (textDrawingAreas != null) {
                        rsInterface.textDrawingAreas = textDrawingAreas[l2];
                    }
                    rsInterface.textShadow = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.textColour = byteBuffer.readDWord();
                    rsInterface.invSpritePadX = byteBuffer.readSignedWord();
                    rsInterface.invSpritePadY = byteBuffer.readSignedWord();
                    rsInterface.isInventoryInterface = byteBuffer.readUnsignedByte() == 1;
                    rsInterface.actions = new String[5];
                    for (int k4 = 0; k4 < 5; k4++) {
                        rsInterface.actions[k4] = byteBuffer.readString();
                        if (rsInterface.actions[k4].isEmpty()) {
                            rsInterface.actions[k4] = null;
                        }
                    }

                }
                if (rsInterface.atActionType == 2 || rsInterface.type == 2) {
                    rsInterface.selectedActionName = byteBuffer.readString();
                    rsInterface.spellName = byteBuffer.readString();
                    rsInterface.spellUsableOn = byteBuffer.readUnsignedWord();
                }
                if (rsInterface.type == 8) {
                    rsInterface.disabledText = byteBuffer.readString();
                }
                if (rsInterface.atActionType == 1 || rsInterface.atActionType == 4 || rsInterface.atActionType == 5 || rsInterface.atActionType == 6) {
                    rsInterface.tooltip = byteBuffer.readString();
                    if (rsInterface.tooltip.isEmpty()) {
                        if (rsInterface.atActionType == 1) {
                            rsInterface.tooltip = "Ok";
                        }
                        if (rsInterface.atActionType == 4) {
                            rsInterface.tooltip = "Select";
                        }
                        if (rsInterface.atActionType == 5) {
                            rsInterface.tooltip = "Select";
                        }
                        if (rsInterface.atActionType == 6) {
                            rsInterface.tooltip = "Continue";
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        printFreeIdRange(100);

        defaultTextDrawingAreas = textDrawingAreas;
        CustomInterfaces.loadInterfaces();

        aReferenceCache_238 = null;
    }

    public static TextDrawingArea[] defaultTextDrawingAreas;
    public static final int OPTION_OK = 1;
    public static final int TYPE_TEXT = 4;
    public static final int TYPE_SPRITE = 5;
    public static final int TYPE_NEW_HOVER_BUTTON = 9;
    public static final int WRAPPING_TEXT = 10;
    public static final int TYPE_DROPDOWN = 13;
    public static final int TYPE_KEYBINDS_DROPDOWN = 15;
    public static final int AT_ACTION_TYPE_OPTION_DROPDOWN = 7;

    private static void printFreeIdRange(int minimumFreeSlotsAvailable){
        for(int i = 0; i < interfaceCache.length; i++){
            int freeSlots = 0;
            while(interfaceCache.length > (i+1) && interfaceCache[i++] == null)
                freeSlots++;
            if(freeSlots >= minimumFreeSlotsAvailable)
                System.out.println("RANGE	["+i+", "+(i+freeSlots)+"]	(has "+freeSlots+" free slots)");
        }
    }

    public static RSInterface addInterface(int id) {
        if (interfaceCache[id] != null) {
            //System.err.println("Overwriting interface id: " + id);
        }
        RSInterface rsi = interfaceCache[id] = new RSInterface();
        rsi.id = id;
        rsi.parentID = id;
        rsi.width = 512;
        rsi.height = 334;
        return rsi;
    }

    public static void expandChildren(int amount, RSInterface i) {
        int writeIndex = i.children == null ? 0 : i.children.length;
        int[] newChildren = new int[writeIndex + amount];
        int[] newChildX = new int[writeIndex + amount];
        int[] newChildY = new int[writeIndex + amount];
        if (i.children != null) {
            System.arraycopy(i.children, 0, newChildren, 0, i.children.length);
            System.arraycopy(i.childX, 0, newChildX, 0, i.childX.length);
            System.arraycopy(i.childY, 0, newChildY, 0, i.childY.length);
        }
        i.children = newChildren;
        i.childX = newChildX;
        i.childY = newChildY;
    }


    public void child(int id, int interID, int x, int y) {
        children[id] = interID;
        childX[id] = x;
        childY[id] = y;
    }

    public void totalChildren(int t) {
        children = new int[t];
        childX = new int[t];
        childY = new int[t];
    }

    public static RSInterface get(int interfaceId) {
        Preconditions.checkArgument(interfaceId >= 0 && interfaceId < interfaceCache.length);
        Preconditions.checkArgument(interfaceCache[interfaceId] != null);
        return interfaceCache[interfaceId];
    }

    public RSInterface getChild(int childId) {
        return get(children[childId]);
    }

    public static void addBackgroundImage(int id, int width, int height, boolean divider) {
        RSInterface tab = interfaceCache[id] = new RSInterface();
        tab.id = id;
        tab.parentID = id;
        tab.type = TYPE_SPRITE;
        tab.atActionType = 0;
        tab.contentType = 0;
        tab.opacity = (byte) 0;
        tab.hoverType = 52;
        tab.sprite1 = tab.sprite2 = buildBackground(width, height, divider);
        tab.width = width;
        tab.height = height;
    }

    public int messageOffsetX;
    public int messageOffsetY;
    public int fontType;
    public int msgX, msgY;

    public boolean toggled = false;

    public static void addHoverButton(int id, int offSprite, int hoverSprite, String hoverTooltip, int font, int color, String buttonText) {
        RSInterface tab = addInterface(id);
        tab.tooltip = hoverTooltip;
        tab.atActionType = OPTION_OK;
        tab.type = TYPE_NEW_HOVER_BUTTON;
        tab.sprite1 = Game.spriteCache.lookup(offSprite);
        tab.sprite2 = Game.spriteCache.lookup(hoverSprite);
        tab.width = tab.sprite1.myWidth;
        tab.height = tab.sprite1.height;
        tab.messageOffsetX = tab.width / 2;
        tab.messageOffsetY = (tab.height / 2) + 5;
        tab.disabledText = buttonText;
        tab.textDrawingAreas = defaultTextDrawingAreas[font];
        tab.textColour = color;
        tab.centerText = true;
        tab.toggled = false;
    }

    public static void hoverButton(int id, int offSprite, int hoverSprite, String hoverTooltip, int font, int color, String buttonText, int msgX, int msgY) {
        RSInterface tab = addInterface(id);
        tab.tooltip = hoverTooltip;
        tab.atActionType = OPTION_OK;
        tab.type = TYPE_NEW_HOVER_BUTTON;
        tab.sprite1 = Game.spriteCache.lookup(offSprite);
        tab.sprite2 = Game.spriteCache.lookup(hoverSprite);
        tab.width = tab.sprite1.myWidth;
        tab.height = tab.sprite1.height;
        tab.messageOffsetX = msgX;
        tab.messageOffsetY = msgY;
        tab.disabledText = buttonText;
        tab.fontType = font;
        tab.textColour = color;
        tab.centerText = false;
        tab.toggled = false;
    }

    public static void addSmallCloseButton(int id) {
        addHoverButton(id, 1, 2, "Close", 0, 0, "");
    }

    public static void addLargeCloseButton(int id) {
        addHoverButton(id, 1, 2, "Close", 0, 0, "");
    }

    public static void addTitleText(int id, String text) {
        addText(id, text, ColourConstants.DEFAULT_TEXT_COLOR, true, true, -1, defaultTextDrawingAreas, 2);
    }

    /**
     * Adds newlines to a text for a certain TextDrawingArea so each line is never longer than width.
     * Param tda The textDrawing Area for the text, basically the font
     * Param text The text to convert to wrapped text
     * Param width The width above which wrapping is applied
     * Return The wrapped text
     */
    public static String getWrappedText(TextDrawingArea tda, String text, int width) {
        if (text.contains("\\n") || tda.getTextWidth(text) <= width) {
            return text;
        }
        int spaceWidth = tda.getTextWidth(" ");
        StringBuilder result = new StringBuilder(text.length());
        StringBuilder line = new StringBuilder();
        int lineLength = 0;
        int curIndex = 0;
        while (true) {
            int spaceIndex = text.indexOf(' ', curIndex);
            int newLength = lineLength;
            boolean last = false;
            String curWord;
            if (spaceIndex < 0) {
                last = true;
                curWord = text.substring(curIndex);
            } else {
                curWord = text.substring(curIndex, spaceIndex);
                newLength += spaceWidth;
            }
            curIndex = spaceIndex + 1;
            int w = tda.getTextWidth(curWord);
            newLength += w;
            if (newLength > width) {
                result.append(line);
                result.append("\\n");
                line = new StringBuilder(curWord);
                line.append(' ');
                lineLength = w;
            } else {
                line.append(curWord);
                line.append(' ');
                lineLength = newLength;
            }
            if (last) {
                break;
            }
        }
        result.append(line);
        return result.toString();
    }

    /**
     * Adds text with the specified properties. Automatically wraps text so it doesn't exceed width.
     * Only use for dynamic interfaces as there is some computation to check if wrapping is required.
     * If static text, use another addText method and pass the text into RSInterface.getWrappedText() firstly.
     * Param id The child id for the text
     * Param text The text message
     * Param tda The tdas available
     * Param idx The index of the tda to use
     * Param color The text color
     * Param center Whether the text is centered
     * Param shadow Whether the text has shadow
     * Param width The maximum width of each line before wrapping applies
     * Return
     */
    public static void addWrappingText(int id, String text, TextDrawingArea[] tda, int idx, int color, boolean center, boolean shadow, int width) {
        RSInterface tab = addInterface(id);
        tab.parentID = id;
        tab.id = id;
        tab.type = WRAPPING_TEXT;
        tab.atActionType = 0;
        tab.width = width;
        tab.height = 11;
        tab.contentType = 0;
        tab.opacity = 0;
        tab.hoverType = -1;
        tab.textColour = color;
        tab.centerText = center;
        tab.textShadow = shadow;
        tab.textDrawingAreas = tda[idx];
        tab.disabledText = getWrappedText(tab.textDrawingAreas, text, tab.width);
    }

    public static void dropdownMenu(int id, int width, int defaultOption, String[] options, MenuItem menuItem, TextDrawingArea[] tda, int idx, boolean centerText) {
        dropdownMenu(id, width, defaultOption, options, menuItem,
                new int[] { 0x0d0d0b, 0x464644, 0x473d32, 0x51483c, 0x787169 }, centerText, tda, idx);
    }

    public static void dropdownMenu(int id, int width, int defaultOption, String[] options, MenuItem menuItem, int[] dropdownColours, boolean centerText, TextDrawingArea[] tda, int idx) {
        RSInterface menu = addInterface(id);
        menu.type = TYPE_DROPDOWN;
        menu.textDrawingAreas = tda[idx];
        menu.dropdown = new DropdownMenu(width, false, defaultOption, options, menuItem);
        menu.atActionType = AT_ACTION_TYPE_OPTION_DROPDOWN;
        menu.dropdownColours = dropdownColours;
        menu.centerText = centerText;
    }

    public static void keybindingDropdown(int id, int width, int defaultOption, String[] options, MenuItem menuItem, boolean inverted) {
        RSInterface widget = addInterface(id);
        widget.type = TYPE_KEYBINDS_DROPDOWN;
        widget.dropdown = new DropdownMenu(width, true, defaultOption, options, menuItem);
        widget.atActionType = AT_ACTION_TYPE_OPTION_DROPDOWN;
        widget.inverted = inverted;
    }

    public static void addText(int id, String text, int colour, boolean center, boolean shadow, int mouseTrigger, TextDrawingArea[] tda, int fontSize) {
        RSInterface RSInterface = addInterface(id);
        RSInterface.parentID = id;
        RSInterface.id = id;
        RSInterface.type = TYPE_TEXT;
        RSInterface.atActionType = 0;
        RSInterface.width = 0;
        RSInterface.height = 0;
        RSInterface.contentType = 0;
        RSInterface.aByte254 = 0;
        RSInterface.mOverInterToTrigger = mouseTrigger;
        RSInterface.centerText = center;
        RSInterface.textShadow = shadow;
        RSInterface.textDrawingAreas = tda[fontSize];
        RSInterface.disabledText = text;
        RSInterface.enabledText = "";
        RSInterface.textColour = colour;
    }

    public static RSInterface addSprite(int i, int sprite) {
        RSInterface rsinterface = interfaceCache[i] = new RSInterface();
        rsinterface.id = i;
        rsinterface.parentID = i;
        rsinterface.type = TYPE_SPRITE;
        rsinterface.atActionType = 0;
        rsinterface.contentType = 0;
        rsinterface.width = Game.spriteCache.lookup(sprite).myWidth;
        rsinterface.height = Game.spriteCache.lookup(sprite).height;
        rsinterface.aByte254 = 0;
        rsinterface.mOverInterToTrigger = 52;
        rsinterface.sprite1 = Game.spriteCache.lookup(sprite);
        rsinterface.sprite2 = Game.spriteCache.lookup(sprite);
        return rsinterface;
    }

    public static Sprite buildBackground(int width, int height, boolean divider) {
        int[][] pixels = new int[height][width];

        // Background
        fillPixels(pixels, Game.autoBackgroundSprites[0], 0, 0, width, height);

        // Top border
        fillPixels(pixels, Game.autoBackgroundSprites[5], 25, 0, width - 25, 6);

        // Left border
        fillPixels(pixels, Game.autoBackgroundSprites[7], 0, 30, 6, height - 30);

        // Right border
        fillPixels(pixels, Game.autoBackgroundSprites[6], width - 6, 30, width, height - 30);

        // Bottom border
        fillPixels(pixels, Game.autoBackgroundSprites[8], 25, height - 6, width - 25, height);

        // Top left corner
        insertPixels(pixels, Game.autoBackgroundSprites[1], 0, 0, true);

        // Top right corner
        insertPixels(pixels, Game.autoBackgroundSprites[2], width - 25, 0, true);

        // Bottom left corner
        insertPixels(pixels, Game.autoBackgroundSprites[3], 0, height - 30, true);

        // Bottom right corner
        insertPixels(pixels, Game.autoBackgroundSprites[4], width - 25, height - 30, true);

        // Divider
        if (divider)
            fillPixels(pixels, Game.autoBackgroundSprites[5], 6, 29, width - 6, 35);

        return new Sprite(width, height, 0, 0, MiscUtils.d2Tod1(pixels));
    }

    public static void insertPixels(int[][] pixels, Sprite image, int x, int y, boolean ignoreTransparency) {
        int[][] imagePixels = MiscUtils.d1Tod2(image.pixels, image.myWidth);

        for (int j = y; j < y + image.height; j++) {
            for (int i = x; i < x + image.myWidth; i++) {
                if (ignoreTransparency && imagePixels[j - y][i - x] == 0)
                    continue;
                pixels[j][i] = imagePixels[j - y][i - x];
            }
        }
    }

    public static void fillPixels(int[][] pixels, Sprite image, int startX, int startY, int endX, int endY) {
        int[][] imagePixels = MiscUtils.d1Tod2(image.pixels, image.myWidth);

        for (int j = startY; j < endY; j++) {
            for (int i = startX; i < endX; i++) {
                pixels[j][i] = imagePixels[(j - startY) % image.height][(i - startX) % image.myWidth];
            }
        }
    }

    private Model method206(int i, int j) {
        ItemDef itemDefinition = null;
        if (type == 4) {
            itemDefinition = ItemDef.forID(id);
            lightness += itemDefinition.anInt196;
            shading += itemDefinition.anInt184;
        }
        Model model = (Model) A_REFERENCE_CACHE___264.insertFromCache((i << 16) + j);
        if (model != null)
            return model;
        if (i == 1)
            model = Model.method462(j);
        if (i == 2)
            model = EntityDef.forID(j).method160();
        if (i == 3)
            model = Game.myPlayer.method453();
        if (i == 4)
            model = ItemDef.forID(j).method202(50);
        if (i == 5)
            model = null;
        if (model != null)
            A_REFERENCE_CACHE___264.removeFromCache(model, (i << 16) + j);
        return model;
    }

    private static Sprite method207(int i, StreamLoader streamLoader, String s) {
        long l = (TextClass.method585(s) << 8) + i;
        Sprite sprite = (Sprite) aReferenceCache_238.insertFromCache(l);
        if (sprite != null) {
            return sprite;
        }
        try {
            sprite = new Sprite(streamLoader, s, i);
            aReferenceCache_238.removeFromCache(sprite, l);
        } catch (Exception _ex) {
            return null;
        }
        return sprite;
    }

    public static void discardInterface(int i) {
        if (i == -1)
            return;
        for (int j = 0; j < interfaceCache.length; j++)
            if (interfaceCache[j] != null
                    && interfaceCache[j].parentID == i
                    && interfaceCache[j].type != 2)
                interfaceCache[j] = null;

    }

    public static void method208(Model model, int id, int type) {
        A_REFERENCE_CACHE___264.unlinkAll();
        if (model != null && type != 4) {
            A_REFERENCE_CACHE___264.removeFromCache(model, (type << 16) + id);
        }
    }

    public Model method209(int j, int k, boolean flag) {
        lightness = 64;
        shading = 768;
        Model model;
        if (flag) {
            model = method206(anInt255, anInt256);
        } else {
            model = method206(modelTypeDisabled, mediaID);
        }
        if (model == null) {
            return null;
        }
        if (k == -1 && j == -1 && model.anIntArray1640 == null) {
            return model;
        }
        Model model_1 = new Model(true, Frames.method532(k) & Frames.method532(j), false, model);
        if (k != -1 || j != -1) {
            model_1.method469();
        }
        if (k != -1) {
            model_1.method470(k);
        }
        if (j != -1) {
            model_1.method470(j);
        }
        model_1.method479(lightness, shading, -50, -10, -50, true);
        return model_1;
    }

    public RSInterface() {
    }

    public String hoverText;
    public int opacity;
    public int hoverType;

    public Sprite sprite1;
    public Sprite sprite2;
    public int sequenceCycle;
    public Sprite[] sprites;
    public static RSInterface[] interfaceCache;
    public int[] anIntArray212;
    public int contentType;
    public int[] spritesX;
    public int anInt216;
    public int atActionType;
    public String spellName;
    public int anInt219;
    public int width;
    public String tooltip;
    public String selectedActionName;
    public boolean centerText;
    public int scrollPosition;
    public String[] actions;
    public int[][] valueIndexArray;
    public boolean aBoolean227;
    public String enabledText;
    public int mOverInterToTrigger;
    public int invSpritePadX;
    public int textColour;
    public int modelTypeDisabled;
    public int mediaID;
    public boolean aBoolean235;
    public int parentID;
    public int spellUsableOn;
    private static ReferenceCache aReferenceCache_238;
    public int anInt239;
    public int[] children;
    public int[] childX;
    public boolean usableItemInterface;
    public TextDrawingArea textDrawingAreas;
    public int invSpritePadY;
    public int[] anIntArray245;
    public int sequenceFrame;
    public int[] spritesY;
    public String disabledText;
    public boolean isInventoryInterface;
    public int id;
    public int[] invStackSizes;
    public int[] inv;
    public byte aByte254;
    private int anInt255;
    private int anInt256;
    public int anInt257;
    public int anInt258;
    public boolean aBoolean259;
    public int scrollMax;
    public int type;
    public int anInt263;
    private static final ReferenceCache A_REFERENCE_CACHE___264 = new ReferenceCache(30);
    public int anInt265;
    public boolean isMouseoverTriggered;
    public int height;
    public static int shading;
    public static int lightness;
    public boolean textShadow;
    public int modelZoom;
    public int modelRotationY;
    public int modelRotationX;
    public int[] childY;
    public DropdownMenu dropdown;
    public int[] dropdownColours;
    public boolean hovered = false;
    public RSInterface dropdownOpen;
    public int dropdownHover = -1;
    public boolean inverted;

}
