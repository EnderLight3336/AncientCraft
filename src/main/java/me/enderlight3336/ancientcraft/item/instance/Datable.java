package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.util.DataList;
import me.enderlight3336.ancientcraft.util.DataSaver;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface Datable<T extends ItemData> {
    DataSaver.Entry genNewData(ItemStack item);

    @NotNull T readData(JSONObject json);

    DataList<T> getDataList();

    void modifyItemData(ItemStack target, Consumer<T> consumer);


    default T getData(ItemStack item) {
        return this.getDataList().get(ItemUtil.getDataId(item));
    }
}
