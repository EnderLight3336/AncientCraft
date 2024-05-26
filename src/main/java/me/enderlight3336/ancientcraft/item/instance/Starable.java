package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.Star;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public interface Starable {
    Star[] star = new Star[ConfigInstance.getMaxStar()];

    default boolean prepareAddStar(@NotNull Player player, int currentStar) {
        ItemStack[] stack = player.getInventory().getStorageContents();
        Map<Material, Integer> items = new HashMap<>(star[currentStar].getItems());
        for (ItemStack current : stack) {
            if (items.containsKey(current.getType())) {
                int amount = current.getAmount();
                int target = items.get(current.getType());
                if (amount >= target) {
                    current.setAmount(amount - target);
                    items.remove(current.getType());
                } else {
                    current.setAmount(0);
                    items.replace(current.getType(), target - amount);
                }
            }
        }
        if (items.isEmpty()) {
            player.getInventory().setStorageContents(stack);
            return true;
        }
        return false;
    }

    default void set(JSONObject json) {
        JSONArray array = json.getJSONArray("star");
        for (int i = 0; i < array.size(); i++) {
            star[i] = new Star(array.getJSONObject(i));
        }
    }
}
