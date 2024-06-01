package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class AttributePart extends BasePart {
    final Attribute attribute;
    final Double[] value;
    public AttributePart(JSONObject json) {
        super(json);

        attribute = Attribute.valueOf(json.getString("attribute"));
        value = (Double[]) json.getJSONArray("value").toArray();
    }

    @Override
    public boolean canApply(int current) {
        return current < value.length;
    }

    @Override
    public <T extends CommonData> void apply(T data, ItemStack target, int currentPartLevel) {
        super.apply(data, target, currentPartLevel);

        ItemMeta im = target.getItemMeta();
        if(im.getAttributeModifiers(g))
    }
    public class PartAttributeModifier extends AttributeModifier {
        public PartAttributeModifier(double amount, @NotNull AttributeModifier.Operation operation) {
            super(uuid, id + "ATMO", amount, operation);
        }
    }
}
