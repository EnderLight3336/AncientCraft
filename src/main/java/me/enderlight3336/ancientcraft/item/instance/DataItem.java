package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.util.DataList;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public abstract class DataItem<T extends ItemData> extends ItemInstance implements Datable <T> {
    protected final DataList<T> data;
    public DataItem(JSONObject json) {
        super(json);

        data = new DataList<>(FileUtil.getDataFolder(getId()), this::readData);
    }


    /**
     * @throws RuntimeException don't support set amount
     */
    @Override
    public final ItemStack createItem(int amount) {
        throw new RuntimeException("This item don't support set amount");
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = originItem.clone();
        ItemMeta im = item.getItemMeta();
        ItemUtil.setDataId(im, genNewData(item));
        item.setItemMeta(im);
        return item;
    }

    public DataList<T> getDataList() {
        return data;
    }
}
