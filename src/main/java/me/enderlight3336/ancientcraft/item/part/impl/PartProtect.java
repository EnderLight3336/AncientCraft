package me.enderlight3336.ancientcraft.item.part.impl;

import me.enderlight3336.ancientcraft.item.part.BasePart;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public class PartProtect extends BasePart {
    public PartProtect(int amount) {
        super("PART_PROTECT", "保护", Material.IRON_CHESTPLATE, amount, "TEST");
    }

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_ARMOR;
    }

    @Override
    public int getAmount(int targetLevel) {
        return 50;
    }
}
