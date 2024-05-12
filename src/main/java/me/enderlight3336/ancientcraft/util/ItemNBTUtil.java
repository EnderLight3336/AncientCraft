package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.AncientCraft;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemNBTUtil {
    private static final NamespacedKey idKey;
    private static final NamespacedKey dataKey;
    static {
        idKey = new NamespacedKey(AncientCraft.getInstance(), "ac_id");
        dataKey = new NamespacedKey(AncientCraft.getInstance(), "ac_data");
    }
    public static void init() {}
    @Nullable
    public static String getId(@NotNull ItemMeta im) {
        return im.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
    }
    public static void setId(@NotNull ItemMeta im, String id) {
        im.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, id);
    }
}
