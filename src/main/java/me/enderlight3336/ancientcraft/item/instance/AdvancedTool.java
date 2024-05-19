package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.Star;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public abstract class AdvancedTool extends CommonTool implements StarInstance {
    final Star[] star = new Star[ConfigInstance.getMaxStar()];
    public AdvancedTool(String id, String name, Material material, int[] i, List<ItemStack[]> list, List<String> lore) {
        super(id, material, name, i, lore);

    }

    public AdvancedTool(JSONObject json) {
        super(json);

        JSONArray array = json.getJSONArray("star");
        for(int i = 0; i < array.size(); i++) {
            star[i] = new Star(array.getJSONObject(i));
        }
    }
}
