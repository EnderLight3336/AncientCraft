package me.enderlight3336.ancientcraft.item.part;

import me.enderlight3336.ancientcraft.item.base.CommonTool;
import org.bukkit.Material;

public abstract class UniversalPart extends BasePart{
    public UniversalPart(String id, String name, Material material, int amount, String... lore) {
        super(id, name, material, amount, lore);
    }

    @Override
    public boolean canApply(CommonTool tool) {
        return true;
    }
}
