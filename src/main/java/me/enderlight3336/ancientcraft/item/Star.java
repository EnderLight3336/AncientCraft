package me.enderlight3336.ancientcraft.item;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

import java.util.HashMap;
import java.util.Map;

public class Star {
    private final HashMap<Material, Integer> items = new HashMap<>(ConfigInstance.getMaxStar());
    private final HashMap<Attribute, Double> attributes = new HashMap<>(ConfigInstance.getMaxStar());

    public Star(JSONObject json) {
        json.getJSONArray("items").forEach(object ->
                items.put(Material.valueOf(((JSONObject) object).getString("material")),
                        ((JSONObject) object).getIntValue("amount")));
        json.getJSONArray("attributes").forEach(object ->
                attributes.put(Attribute.valueOf(((JSONObject) object).getString("name")),
                        ((JSONObject) object).getDouble("amount")));
    }

    public Map<Material, Integer> getItems() {
        return items;
    }

    public Map<Attribute, Double> getAttributes() {
        return attributes;
    }
}
