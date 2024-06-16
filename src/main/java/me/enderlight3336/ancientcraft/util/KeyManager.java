package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.AncientCraft;
import org.bukkit.NamespacedKey;

public final class KeyManager {
    private static final NamespacedKey idKey = new NamespacedKey(AncientCraft.getInstance(), "ac_id");
    private static final NamespacedKey dataIdKey = new NamespacedKey(AncientCraft.getInstance(), "ac_dataid");
    private static final NamespacedKey starKey = new NamespacedKey(AncientCraft.getInstance(), "ac_star");
    private static final NamespacedKey previewItemKey = new NamespacedKey(AncientCraft.getInstance(), "preview_item");

    public static NamespacedKey getIdKey() {
        return idKey;
    }

    public static NamespacedKey getDataIdKey() {
        return dataIdKey;
    }

    public static NamespacedKey getStarKey() {
        return starKey;
    }

    public static NamespacedKey getPreviewItemKey() {
        return previewItemKey;
    }

    public static NamespacedKey genRecipeKey(String type, int index, String id) {
        return new NamespacedKey(AncientCraft.getInstance(),
                type + "_" + index + "_" + id);
    }
}
