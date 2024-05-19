package me.enderlight3336.ancientcraft.item;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

import java.util.Objects;

public class Star {
    final RequireItem[] items;
    final AttributeData[] attributes;
    public Star(JSONObject json) {
        items = new RequireItem[json.getJSONArray("items").size()];
        attributes = new AttributeData[json.getJSONArray("attributes").size()];

        JSONArray array = json.getJSONArray("items");
        for(int i = 0; i < array.size(); i++) {
            items[i] = new RequireItem(array.getJSONObject(i));
        }
        array = json.getJSONArray("attributes");
        for(int i = 0; i < array.size(); i++) {
            attributes[i] = new AttributeData(array.getJSONObject(i));
        }
    }

    public final class RequireItem {
        final Material material;
        final int amount;
        public RequireItem(JSONObject json) {
            this.material = Material.valueOf(json.getString("material"));
            this.amount = json.getInteger("amount");
            Objects.requireNonNull(this.material);
        }
    }
    public final class AttributeData {
        final Attribute attribute;
        final double amount;
        public AttributeData(JSONObject json) {
            this.attribute = Attribute.valueOf(json.getString("name"));
            this.amount = json.getDouble("amount");
        }
    }
}
