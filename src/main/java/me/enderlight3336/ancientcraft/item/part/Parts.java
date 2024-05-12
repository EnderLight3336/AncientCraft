package me.enderlight3336.ancientcraft.item.part;

import me.enderlight3336.ancientcraft.item.part.impl.PartSuckBlood;

import java.util.HashMap;
import java.util.Map;

public final class Parts {
    private static Map<PartEnum, BasePart> parts = new HashMap<>();
    public static void init() {
        parts.put(PartEnum.SUCK_BLOOD, new PartSuckBlood("吸血", "suckblood"));
    }
}
