package me.enderlight3336.ancientcraft.item.base;

import me.enderlight3336.ancientcraft.item.part.PartEnum;
import me.enderlight3336.ancientcraft.item.ref.BaseRef;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;

public class CommonTool extends ACItemStack implements Partable, Levelable {
    static BaseRef baseRef = new BaseRef();
    final Map<PartEnum, Integer> parts;
    int currentLevel;
    int maxSlot;
    int currentExp;

    public CommonTool(String id, String name, Material material, String... lore) {
        super(id, name, material, lore);
        this.parts = new HashMap<>();
    }

    @Override
    public void addExp(int exp) {
        int e = baseRef.handleLevelExp(currentLevel, currentExp + exp);
        if(e == -1) {
            currentExp = currentExp + exp;
        } else {
            currentExp = e;
            setLevel(currentLevel + 1);
        }
    }

    @Override
    public void setLevel(int targetLevel) {
        this.currentLevel = targetLevel;
        levelUp(currentLevel);
    }

    @Override
    public final int getLevel() {
        return currentLevel;
    }

    @Override
    public void levelUp(int targetLevel) {
        maxSlot++;
    }

    @Override
    public Map<PartEnum, Integer> getParts() {
        return parts;
    }

    @Override
    public Inventory getPartMenu() {//TODO
        return null;
    }

    @Override
    public final int getMaxPartSlot() {
        return maxSlot;
    }

    @Override
    public int removePart(PartEnum part) {
        return parts.remove(part);
    }
}
