package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class DataItem extends ItemInstance {
    public DataItem(JSONObject json) {
        super(json);
    }

    @Override
    public abstract @NotNull ItemData genData(ItemStack item);

    /**
     * @throws RuntimeException don't support set amount
     */
    @Override
    public final ItemStack createItem(int amount) {
        throw new RuntimeException("This item don't support set amount");
    }
    //public abstract List<String> buildLore();
}
