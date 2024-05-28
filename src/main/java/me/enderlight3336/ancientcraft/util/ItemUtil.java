package me.enderlight3336.ancientcraft.util;

public final class ItemUtil {
    public static double handleAttribute(double base, Material material, Attribute attribute) {
        switch (attribute) {
            case GENERIC_ARMOR -> {}
            case GENERIC_ARMOR_TOUGHNESS -> {}
            case GENERIC_ATTACK_DAMAGE -> {
                switch (material) {
                    case WOODEN_SWORD -> {
                        return base - 4;
                    }
                    case GOLDEN_SWORD -> {
                        return base - 4;
                    }
                    case STONE_SWORD -> {
                        return base - 5;
                    }
                }
            }
            case GENERIC_ATTACK_SPEED -> {}
        }
    }
}