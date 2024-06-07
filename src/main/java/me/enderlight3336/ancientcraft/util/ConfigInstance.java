package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;

public final class ConfigInstance {
    private static String lang = null;
    private static String[] starInfo;
    private static Integer[] levelInfo;

    public static void init() {
        JSONObject json = FileUtil.getJSON("config.json");
        lang = json.getString("lang");
        starInfo = json.getJSONArray("starInfo").toArray(String.class);
        levelInfo = json.getJSONArray("level").toArray(Integer.class);
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
}