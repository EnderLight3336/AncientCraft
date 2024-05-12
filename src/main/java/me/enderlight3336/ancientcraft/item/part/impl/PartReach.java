package me.enderlight3336.ancientcraft.item.part.impl;

import me.enderlight3336.ancientcraft.item.base.CommonTool;
import me.enderlight3336.ancientcraft.item.part.BasePart;
import me.enderlight3336.ancientcraft.item.part.SwordPart;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public class PartReach extends SwordPart {
    public PartReach(int amount) {
        super("PART_REACH", "Reach Part", Material.GOLDEN_SWORD, amount, "百米大刀, 虽远必诛");
    }

    @Override
    public final void apply(CommonTool tool, int targetLevel) {//TODO: TEST
        tool.addAttribute(Attribute.PLAYER_ENTITY_INTERACTION_RANGE, 30);
        tool.addAttribute(Attribute.PLAYER_BLOCK_INTERACTION_RANGE, 30);
    }
}
