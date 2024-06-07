package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.Levelable;
import me.enderlight3336.ancientcraft.item.instance.Starable;
import me.enderlight3336.ancientcraft.util.DataSaver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShortSword extends DataItem<CommonData> implements Levelable, Starable, ISword {
    public ShortSword(JSONObject json) {
        super(json);
    }

    @Override
    public DataSaver.Entry genNewData(ItemStack item) {
        CommonData data1 = new CommonData();
        return new DataSaver.Entry(data.put(data1), data1);
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }
}
