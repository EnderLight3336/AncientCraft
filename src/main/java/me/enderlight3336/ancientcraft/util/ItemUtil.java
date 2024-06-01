package me.enderlight3336.ancientcraft.util;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ItemUtil {
    public static double handleAttribute(double base, Material material, Attribute attribute) {
        switch (attribute) {
            case GENERIC_ARMOR -> {
                switch (material) {
                    case LEATHER_HELMET, LEATHER_BOOTS, GOLDEN_BOOTS, CHAINMAIL_BOOTS -> {
                        return base - 1;
                    }
                    case TURTLE_HELMET, LEATHER_LEGGINGS, GOLDEN_HELMET, CHAINMAIL_HELMET, IRON_HELMET, IRON_BOOTS -> {
                        return base - 2;
                    }
                    case LEATHER_CHESTPLATE, GOLDEN_LEGGINGS, DIAMOND_HELMET, DIAMOND_BOOTS, NETHERITE_HELMET, NETHERITE_BOOTS -> {
                        return base - 3;
                    }
                    case CHAINMAIL_LEGGINGS -> {
                        return base - 4;
                    }
                    case GOLDEN_CHESTPLATE, CHAINMAIL_CHESTPLATE, IRON_LEGGINGS -> {
                        return base - 5;
                    }
                    case IRON_CHESTPLATE, DIAMOND_LEGGINGS, NETHERITE_LEGGINGS -> {
                        return base - 6;
                    }
                    case DIAMOND_CHESTPLATE, NETHERITE_CHESTPLATE -> {
                        return base - 8;
                    }
                }
            }
            case GENERIC_ARMOR_TOUGHNESS -> {
                switch (material) {
                    case DIAMOND_HELMET, DIAMOND_CHESTPLATE, DIAMOND_LEGGINGS, DIAMOND_BOOTS -> {
                        return base - 2;
                    }
                    case NETHERITE_HELMET, NETHERITE_CHESTPLATE, NETHERITE_LEGGINGS, NETHERITE_BOOTS -> {
                        return base - 3;
                    }
                }
            }
            case GENERIC_KNOCKBACK_RESISTANCE -> {
                if(material == Material.NETHERITE_HELMET ||
                material == Material.NETHERITE_CHESTPLATE ||
                material == Material.NETHERITE_LEGGINGS ||
                material == Material.NETHERITE_BOOTS)
                    return base - 10;//todo: uncheck
            }
            case GENERIC_ATTACK_DAMAGE -> {
                switch (material) {
                    case WOODEN_SWORD, GOLDEN_SWORD -> {
                        return base - 4;
                    }
                    case STONE_SWORD -> {
                        return base - 5;
                    }
                    case IRON_SWORD -> {
                        return base - 6;
                    }
                    case DIAMOND_SWORD -> {
                        return base - 7;
                    }
                    case NETHERITE_SWORD -> {
                        return base - 8;
                    }
                }
            }
            case GENERIC_ATTACK_SPEED -> {
                switch (material) {
                    case DIAMOND_SWORD, IRON_SWORD, NETHERITE_SWORD, GOLDEN_SWORD, STONE_SWORD, WOODEN_SWORD -> {
                        return base - 1.6;
                    }
                }
            }
        }
        return base;
    }

    @Nullable
    public static String getId(@NotNull ItemMeta im) {
        return im.getPersistentDataContainer().get(KeyManager.getIdKey(), PersistentDataType.STRING);
    }

    public static void setId(@NotNull PersistentDataHolder p, String id) {
        p.getPersistentDataContainer().set(KeyManager.getIdKey(), PersistentDataType.STRING, id);
    }

    public static boolean hasId(@NotNull PersistentDataHolder p) {
        return p.getPersistentDataContainer().has(KeyManager.getIdKey());
    }

    public static void setDataId(ItemMeta im, int i) {
        im.getPersistentDataContainer().set(KeyManager.getDataIdKey(), PersistentDataType.INTEGER, i);
    }

    public static int getDataId(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().get(KeyManager.getDataIdKey(), PersistentDataType.INTEGER);
    }
}