package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public final class ConfigInstance {
    private static String lang = null;
    private static String[] starInfo;
    private static Integer[] levelupExp;
    private static boolean beta;
    private static int slotPerLevel;
    private static final Map<String, Integer> swordExpMap = new HashMap<>();
    private static final Map<String, Integer> pickaxeExpMap = new HashMap<>();
    private static final Map<String, Integer> hoeExpMap = new HashMap<>();

    public static void init() {
        init(FileUtil.getJSON("config.json"));
    }

    public static void init(JSONObject json) {
        lang = json.getString("lang");
        starInfo = json.getJSONArray("starInfo").toArray(String.class);
        levelupExp = json.getJSONArray("level").toArray(Integer.class);
        beta = json.getBooleanValue("beta", false);
        slotPerLevel = json.getIntValue("slotPerLevel", 2);
        json.getJSONObject("swordExp").forEach((s, o) -> swordExpMap.put(s, Integer.parseInt(o.toString())));
        json.getJSONObject("pickaxeExp").forEach((s, o) -> pickaxeExpMap.put(s, Integer.parseInt(o.toString())));
        json.getJSONObject("hoeExp").forEach((s, o) -> hoeExpMap.put(s, Integer.parseInt(o.toString())));
    }

    public static String getLang() {
        return lang;
    }

    public static int getMaxStar() {
        return starInfo.length;
    }

    public static int getMaxLevel() {
        return levelupExp.length;
    }

    public static String getStarInfo(int currentStar) {
        return starInfo[currentStar - 1];
    }

    public static int getNeedExp(int currentLevel) {
        return currentLevel < levelupExp.length - 1 ? levelupExp[currentLevel] : 0;
    }

    public static boolean isBeta() {
        return beta;
    }

    public static int getSlotPerLevel() {
        return slotPerLevel;
    }

    public static int getSwordExp(String mob) {
        Integer exp = swordExpMap.get(mob);
        return exp != null ? exp : swordExpMap.get("default");
    }

    public static Map<String, Integer> getSwordExpMap() {
        return swordExpMap;
    }

    public static int getPickaxeExp(Material brokeBlock) {
        Integer exp = pickaxeExpMap.get(brokeBlock.getKey().getKey());
        return exp != null ? exp : 0;
    }

    public static boolean isPickaxeExpTarget(Material material) {
        return pickaxeExpMap.containsKey(material.getKey().getKey());
    }

    public static int getHoeExp(Material material) {
        Integer ret = hoeExpMap.get(material.getKey().getKey());
        return ret != null ? ret : 0;
    }
}