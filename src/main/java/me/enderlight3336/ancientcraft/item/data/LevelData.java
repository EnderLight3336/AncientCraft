package me.enderlight3336.ancientcraft.item.data;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class LevelData implements ItemData {
    private int exp;
    private int level;
    private final ItemStack item;
    private final Map<String, Integer> parts = new HashMap<>();

    public LevelData(ItemStack item) {
        exp = 0;
        level = 0;
        this.item = item;
    }

    public void setExp(int i) {
        this.exp = i;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    /**
     * @param i      how many exp will add to this item
     * @param expCap the exp that requires to level up
     * @return true if level up, false means not level up
     */
    public boolean addExp(int i, int expCap) {
        this.exp = this.exp + i;
        if (this.exp >= expCap) {
            this.exp = this.exp - expCap;
            this.level++;
            return true;
        }
        return false;
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    @Override
    public ItemStack getItemStack() {
        return item;
    }

    public void addPart(String id) {
        if (parts.containsKey(id))
            parts.replace(id, parts.get(id) + 1);
        else
            parts.put(id, 1);
    }

    public int removePart(String id) {
        return parts.remove(id);
    }

    /**
     * @return the Part's level, 0 means not exist
     */
    public int getPartLevel(String id) {
        return parts.getOrDefault(id, 0);
    }

    @Override
    public String toJsonString() {}
}
