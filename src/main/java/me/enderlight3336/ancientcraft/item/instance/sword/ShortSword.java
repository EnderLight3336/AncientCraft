package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.type.ISword;
import me.enderlight3336.ancientcraft.item.instance.type.ItemLevelAndPartable;
import me.enderlight3336.ancientcraft.util.AsyncDataSaver;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ShortSword extends DataItem<CommonData> implements ItemLevelAndPartable<CommonData>, ISword {
    public ShortSword(JSONObject json) {
        super(json);
    }

    @Override
    public AsyncDataSaver.Entry genNewData(ItemStack item) {
        CommonData data1 = new CommonData();
        return new AsyncDataSaver.Entry(data.put(data1), data1);
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }


    @Override
    public boolean checkType(String typeName) {
        return typeName.equals("sword") || super.checkType(typeName);
    }
}
