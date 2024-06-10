package me.enderlight3336.ancientcraft.item.instance.type;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.util.AsyncDataSaver;
import me.enderlight3336.ancientcraft.util.DataList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface ItemDatable<T extends ItemData> {
    AsyncDataSaver.Entry genNewData(ItemStack item);

    @NotNull T readData(JSONObject json);

    DataList<T> getDataList();

    void modifyItemData(ItemStack target, Consumer<T> consumer);

    String getId();

    T getData(ItemStack item);

    boolean checkType(String typeName);
}
