package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.impl.sword.*;
import me.enderlight3336.ancientcraft.util.FileUtil;

public enum Items {
    DRAGONSWORD(new ItemInstance(getData("DragonSword"))),
    HEAVYSWORD(new HeavySword(getData("HeavySword"))),
    LONGSWORD(new LongSword(getData("LongSword"))),
    SHORTSWORD(new ShortSword(getData("ShortSword"))),
    SWORDOFHEAVEN(new SwordOfHeaven(getData("SwordOfHeaven"))),
    NANOMETERSWORD(new NanometerSword(getData("NanometerSword")));
    Items(ItemInstance instance) {
        this.instance = instance;
    }
    private final ItemInstance instance;

    public ItemInstance getInstance() {
        return instance;
    }
    public static JSONObject getData(String s) {
        return FileUtil.getJSON("item/" + s + ".json");
    }
}
