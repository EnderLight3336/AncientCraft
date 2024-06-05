package me.enderlight3336.ancientcraft.item.instance;

import me.enderlight3336.ancientcraft.item.data.ItemData;

import java.util.List;

public interface Datable<T extends ItemData> {
    int genNewData(ItemStack item);
    @NotNull T readData(JSONObject json);
    DataList<T> getDataList();
    
    default T getData(ItemStack item) {
        return getDataList().get(ItemUtil.getDataId(item));
    }
}
