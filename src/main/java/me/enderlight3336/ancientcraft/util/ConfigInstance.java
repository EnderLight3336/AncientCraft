package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;

public final class ConfigInstance {
    private static String lang = null;
    private static String[] starInfo;
    private static Integer[] levelInfo;
    private static boolean beta;
    private static int slotPerLevel;
    private static final Map<String, Integer> expMap = new HashMap<>();

    public static void init() {
        init(FileUtil.getJSON("config.json"));
    }

    public static void init(JSONObject json) {
        lang = json.getString("lang");
        starInfo = json.getJSONArray("starInfo").toArray(String.class);
        levelInfo = json.getJSONArray("level").toArray(Integer.class);
        beta = json.getBooleanValue("beta", false);
        slotPerLevel = json.getIntValue("slotPerLevel", 2);
        json.getJSONObject("swordExp").forEach((s, o) -> expMap.put(s, Integer.parseInt(o.toString())));
    }

    public static String getLang() {
        return lang;
    }

    public static int getMaxStar() {
        return starInfo.length;
    }

    public static int getMaxLevel() {
        return levelInfo.length;
    }

    public static String getStarInfo(int currentStar) {
        return starInfo[currentStar - 1];
    }

    public static int getNeedExp(int currentLevel) {
        return levelInfo[currentLevel];
    }

    public static boolean isBeta() {
        return beta;
    }

    public static int getSlotPerLevel() {
        return slotPerLevel;
    }

    public static int getExp(String mob) {
        Integer exp = expMap.get(mob);
        return exp != null ? exp : expMap.get("default");
    }

    public static Map<String, Integer> getExpMap() {
        return expMap;
    }
}