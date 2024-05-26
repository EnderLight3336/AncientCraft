package me.enderlight3336.ancientcraft.item.instance;

import me.enderlight3336.ancientcraft.util.ConfigInstance;

public interface Levelable {
    default boolean canLevelUp(int currentExp, int currentLevel) {
        return ConfigInstance.getNeedExp(currentLevel) <= currentExp;
    }
}
