package me.enderlight3336.ancientcraft.item.instance.impl.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.Util;
import me.enderlight3336.ancientcraft.item.instance.AbilityPart;
import me.enderlight3336.ancientcraft.item.instance.AttributePart;
import me.enderlight3336.ancientcraft.item.instance.CommonTool;
import me.enderlight3336.ancientcraft.item.ref.ItemRef;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public class PartReach extends AttributePart {
    public PartReach(JSONObject json) {
        super(json);
    }

    @Override
    public final void apply(ItemRef tool, int targetLevel) {//TODO: TEST
        Util.addAttribute(tool.getItemStack(), Attribute.PLAYER_ENTITY_INTERACTION_RANGE, 30);
        Util.addAttribute(tool.getItemStack(), Attribute.PLAYER_BLOCK_INTERACTION_RANGE, 30);
    }
}
