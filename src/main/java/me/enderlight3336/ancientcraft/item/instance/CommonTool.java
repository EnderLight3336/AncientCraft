package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.Material;
import org.jetbrains.annotations.Range;

import java.util.ArrayList;
import java.util.List;

public class CommonTool extends ItemInstance {
    final int[] requireExp;

    public CommonTool(String id, Material material, String name ,int[] i, List<String> lore) {
        super(id, material, name, lore);
        this.requireExp = i;
    }

    public CommonTool(JSONObject json) {
        super(json);
        this.requireExp = new int[10];//todo
        requireExp[0] = 1000;
        requireExp[1] = 2000;
        requireExp[2] = 3000;
        requireExp[3] = 4000;
        requireExp[4] = 5000;
        requireExp[5] = 6000;
        requireExp[6] = 7000;
        requireExp[7] = 8000;
        requireExp[8] = 9000;
        requireExp[9] = 10000;
    }
    public int getRequiredExp(@Range(from = 0, to = Integer.MAX_VALUE) int currentLevel) {
        return requireExp[currentLevel];
    }
}
