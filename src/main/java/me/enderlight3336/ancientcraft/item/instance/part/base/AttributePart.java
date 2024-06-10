package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Locale;
import java.util.UUID;

public final class AttributePart extends BasePart {
    final Attribute attribute;
    final Double[] value;
    final UUID uuid;

    public AttributePart(JSONObject json) {
        super(json);

        attribute = Attribute.valueOf(json.getString("attribute").toUpperCase(Locale.ROOT));
        value = json.getJSONArray("value").toArray(Double.class);
        if (json.containsKey("uuid"))
            uuid = UUID.fromString(json.getString("uuid"));
        else
            uuid = new UUID(378526250564278601L, id.hashCode());//todo
    }

    @Override
    public boolean isMaxLevel(int partLevel) {
        return value.length == partLevel || value.length < partLevel;
    }

    @Override
    protected void unsafeApply(ItemStack target, int currentPartLevel) {
        ItemMeta im = target.getItemMeta();
        AttributeModifier at = new AttributeModifier(uuid, id + "part", value[currentPartLevel], AttributeModifier.Operation.ADD_NUMBER);
        im.removeAttributeModifier(attribute, at);
        im.addAttributeModifier(attribute, at);
        target.setItemMeta(im);
    }
}
