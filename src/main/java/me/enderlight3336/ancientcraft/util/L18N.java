package me.enderlight3336.ancientcraft.util;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class L18N {
    private static final Map<String, String> lang = new HashMap<>();
    public static void init() {
        String s =  ;
        lang.putAll(FileUtil.getJSON(s + ".json"));
    }
    @NotNull
    public static String get(@NotNull String s) {
        return lang.get(s) == null ? s : lang.get(s);
    }
    public enum LangType {

    }
}
