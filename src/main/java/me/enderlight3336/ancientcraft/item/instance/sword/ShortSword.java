package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.Levelable;
import me.enderlight3336.ancientcraft.item.instance.Starable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShortSword extends DataItem<CommonData> implements Levelable, Starable {
    public ShortSword(JSONObject json) {
        super(json);
    }

    @Override
    public int genNewData(ItemStack item) {
        return data.put(new CommonData());
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }
}
