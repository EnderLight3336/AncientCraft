package me.enderlight3336.ancientcraft.item.part;

import me.enderlight3336.ancientcraft.item.CPickaxe;
import me.enderlight3336.ancientcraft.item.base.CommonTool;
import org.bukkit.Material;

public abstract class PickaxePart extends BasePart{
    public PickaxePart(String id, String name, Material material, int amount, String... lore) {
        super(id, name, material, amount, lore);
    }

    @Override
    public boolean canApply(CommonTool tool) {
        return tool instanceof CPickaxe;
    }
}
