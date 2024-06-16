package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import me.enderlight3336.ancientcraft.util.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.function.Consumer;

public abstract class DataItem<T extends ItemData> extends ItemInstance implements ItemDatable<T> {
    protected final DataList<T> data;

    public DataItem(JSONObject json) {
        super(json);

        data = new DataList<>(FileUtil.getDataFolder(getId()), this::readData);
    }

    /**
     * @throws IllegalArgumentException if amount isn't 1
     */
    @Override
    public final ItemStack createItem(int amount) {
        if (amount == 1)
            return createItem();
        throw new IllegalArgumentException("This item don't support set amount");
    }

    @Override
    public ItemStack createItem() {
        ItemStack item = originItem.clone();
        AsyncDataSaver.Entry entry = genNewData(item);
        ItemMeta im = item.getItemMeta();

        List<String> lore = im.getLore();
        lore.addAll(entry.getData().buildLore());
        im.setLore(lore);

        ItemUtil.setDataId(im, entry.getIndex());
        item.setItemMeta(im);
        AsyncDataSaver.pub(getId(), entry);
        return item;
    }

    @Override
    public ItemStack getPreviewItem() {
        ItemStack item = originItem.clone();
        ItemMeta im = item.getItemMeta();
        im.getPersistentDataContainer().set(KeyManager.getPreviewItemKey(), PersistentDataType.STRING, id);
        item.setItemMeta(im);
        return item;
    }

    @Override
    public final DataList<T> getDataList() {
        return data;
    }

    @Override
    public final void modifyItemData(ItemStack target, Consumer<T> consumer) {
        int index = ItemUtil.getDataId(target);
        T data = getDataList().get(index);
        consumer.accept(data);
        AsyncDataSaver.put(getId(), index, data);
    }

    @Override
    public final T getData(ItemStack item) {
        return data.get(ItemUtil.getDataId(item));
    }
}
