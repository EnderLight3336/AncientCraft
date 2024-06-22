package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

public final class ItemUtil {
    public static double handleAttribute(Attribute attribute, Material material, double base) {
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
                if (material == Material.NETHERITE_HELMET ||
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
            case PLAYER_ENTITY_INTERACTION_RANGE -> {
                return base - 3;
            }
        }
        return base;
    }

    public static boolean hasId(@NotNull PersistentDataHolder p) {
        return p.getPersistentDataContainer().has(KeyManager.getIdKey());
    }

    /**
     * @return -1 means no data on this item
     */
    public static int getDataId(ItemStack item) {
        Integer i = item.getItemMeta().getPersistentDataContainer().get(KeyManager.getDataIdKey(), PersistentDataType.INTEGER);
        return i != null ? i : -1;
    }
    public static ItemStack createHead(String base64) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta im = (SkullMeta) item.getItemMeta();
        PlayerProfile profile = Bukkit.createPlayerProfile(ItemInstance.HEAD_UUID);
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URI("http://textures.minecraft.net/texture/" + base64).toURL());
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
        im.setOwnerProfile(profile);
        return item;
    }
}