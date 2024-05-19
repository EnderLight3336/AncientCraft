package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;

import java.io.FileNotFoundException;

public final class ConfigInstance {
    private static String lang = null;
    private static int maxStar = 0;
    private static int maxLevel = 10;
    public static void init() throws FileNotFoundException {
        JSONObject json = FileUtil.getJSON("config.json");
        lang = json.getString("lang");
        maxStar = json.getInteger("maxstar");
        maxLevel = json.getInteger("maxlevel");
    }

    public static String getLang() {
        return lang;
    }

    public static int getMaxStar() {
        return maxStar;
    }

    public static int getMaxLevel() {
        return maxLevel;
    }
}