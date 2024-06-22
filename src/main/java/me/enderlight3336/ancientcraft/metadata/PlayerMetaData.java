package me.enderlight3336.ancientcraft.metadata;

import com.alibaba.fastjson2.JSONObject;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import me.enderlight3336.ancientcraft.AncientCraft;
import org.bukkit.Material;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class PlayerMetaData {
    private final Object2IntOpenHashMap<Material> sack;
    public PlayerMetaData() {
        sack = new Object2IntOpenHashMap<>();
    }
    public PlayerMetaData(JSONObject json) {
        JSONObject sackJSON = json.getJSONObject("sack");
        sack = new Object2IntOpenHashMap<>(sackJSON.size());
    }
}
