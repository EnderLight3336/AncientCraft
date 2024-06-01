package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import org.bukkit.inventory.ItemStack;

public abstract class BasePart extends ItemInstance {
    public BasePart(JSONObject json) {
        super(json);
    }

    /**
     * write data to item <br>
     * must be call after handling !!!!
     */
    public <T extends CommonData> void apply(T data, ItemStack target, int currentPartLevel) {
        data.addPart(getId());
    }

    public abstract boolean canApply(int current);
}
