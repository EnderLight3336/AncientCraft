package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;

import java.util.HashMap;
import java.util.Map;

public class ItemDataReader {
    private final Map<JSONObject, String> map = new HashMap<>();
    public ItemDataReader() {
        AncientCraft.getInstance().getDataFolder()
    }
}
