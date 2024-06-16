package me.enderlight3336.ancientcraft.brain;

import com.alibaba.fastjson2.JSONObject;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public final class Brain {
    private final int level;
    private final Map<Material, Integer> bag;
    public Brain() {
        this.level = 0;
        bag = new HashMap<>();
    }
    public Brain(JSONObject json) {
        this.level = json.getIntValue("level");
        JSONObject bagJson = json.getJSONObject("bag");
        this.bag = new HashMap<>(bagJson.size());
        bagJson.forEach((s, o) -> bag.put(Material.valueOf(s), Integer.parseInt((String) o)));
    }
    public int getLevel() {
        return this.level;
    }

    public int getBagItemAmount(Material material) {
        return bag.getOrDefault(material, 0);
    }
}
