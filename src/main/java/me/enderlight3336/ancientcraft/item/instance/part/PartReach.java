package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.instance.part.base.AttributePart;
import org.bukkit.attribute.Attribute;

public class PartReach extends AttributePart {
    public PartReach(JSONObject json) {
        super(json);
    }

    @Override
    public final void apply(ItemData tool, int targetLevel) {//TODO: TEST
        ItemManager.addAttribute(tool.getItemStack(), Attribute.PLAYER_ENTITY_INTERACTION_RANGE, 30);
        ItemManager.addAttribute(tool.getItemStack(), Attribute.PLAYER_BLOCK_INTERACTION_RANGE, 30);
    }
}
