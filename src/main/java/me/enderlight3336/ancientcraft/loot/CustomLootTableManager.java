package me.enderlight3336.ancientcraft.loot;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.loot.calculate.CommonCalc;
import me.enderlight3336.ancientcraft.loot.calculate.RollCalc;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public final class CustomLootTableManager {
    private static final Map<EntityType, CustomMobLootTable> lootMap = new HashMap<>();
    public static final Random RANDOM = new Random();

    @Nullable
    public static CustomMobLootTable getLootTable(EntityType type) {
        return lootMap.get(type);
    }

    public static void put(ItemInstance instance, JSONArray array) {
        int index = 0;
        while (index < array.size()) {
            JSONObject json = array.getJSONObject(index);
            EntityType type = EntityType.valueOf(json.getString("entityType").toUpperCase(Locale.ROOT));
            index++;
            CustomMobLootTable lootTable = lootMap.get(type);
            if (lootTable == null) {
                lootTable = new CustomMobLootTable();
                lootMap.put(type, lootTable);
            }
            String lootType = json.getString("lootType");
            switch (lootType) {
                case "immutable" -> {
                    float chance = json.getFloatValue("chance");
                    lootTable.put(instance,
                            new RollCalc(new float[]{chance, 1 - chance}, new int[]{json.getIntValue("amount"), 0}));
                }
                case "roll" -> {
                }
                case "common" ->
                        lootTable.put(instance, new CommonCalc(json.getIntValue("amount"), json.getFloatValue("chance"),
                                json.getFloatValue("chancePerLooting"), json.getFloatValue("multiple")));
                default ->
                        throw new IllegalStateException("Can't find any type for custom loot table!Ensure your meta file is right!");
            }
        }
    }
}
