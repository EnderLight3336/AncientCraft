package me.enderlight3336.ancientcraft.item.part.impl;

import me.enderlight3336.ancientcraft.item.part.BasePart;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public class PartToughness extends BasePart {
    public PartToughness(int amount) {
        super("PART_BEDROCK", "BEDROCK?", Material.BEDROCK, amount, "你是不是开无击退了?");
    }

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_ARMOR_TOUGHNESS;
    }

    @Override
    public int getAmount(int targetLevel) {
        return 10;
    }
}
