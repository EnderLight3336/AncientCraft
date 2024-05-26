package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import org.jetbrains.annotations.NotNull;

public final class L18N {
    private static JSONObject lang;

    public static void init() {
        // lang = FileUtil.getJSON(ConfigInstance.getLang() + ".json");
    }

    @NotNull
    public static String get(@NotNull String s) {
        return lang.getString(s) == null ? s : lang.getString(s);
    }
}
