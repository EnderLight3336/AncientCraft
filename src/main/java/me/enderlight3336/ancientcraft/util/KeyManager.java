package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.AncientCraft;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class KeyManager {
    private static final NamespacedKey idKey;
    private static final NamespacedKey crafterKey;

    static {
        idKey = new NamespacedKey(AncientCraft.getInstance(), "ac_id");
        crafterKey = new NamespacedKey(AncientCraft.getInstance(), "ac_crafter");
    }

    public static NamespacedKey getIdKey() {
        return idKey;
    }

    public static NamespacedKey getCrafterKey() {
        return crafterKey;
    }

    @Nullable
    public static String getId(@NotNull ItemMeta im) {
        return im.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
    }

    public static void setId(@NotNull PersistentDataHolder p, String id) {
        p.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, id);
    }

    public static boolean hasId(@NotNull PersistentDataHolder p) {
        return p.getPersistentDataContainer().has(idKey);
    }

    public static void setCrafter(@NotNull ItemStack item, UUID uuid) {
        ItemMeta im = item.getItemMeta();
        im.getPersistentDataContainer().set(crafterKey, UUIDDataTypeImpl.get(), uuid);
        item.setItemMeta(im);
    }

    public static UUID getCrafter(@NotNull PersistentDataHolder p) {
        return p.getPersistentDataContainer().get(crafterKey, UUIDDataTypeImpl.get());
    }
}
