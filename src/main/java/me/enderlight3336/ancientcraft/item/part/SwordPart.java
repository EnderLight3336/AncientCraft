package me.enderlight3336.ancientcraft.item.part;

import me.enderlight3336.ancientcraft.item.base.CommonTool;
import me.enderlight3336.ancientcraft.item.sword.Sword;
import org.bukkit.Material;

public abstract class SwordPart extends BasePart{
    public SwordPart(String id, String name, Material material, int amount, String... lore) {
        super(id, name, material, amount, lore);
    }

    @Override
    public boolean canApply(CommonTool tool) {
        return tool instanceof Sword;
    }
}
