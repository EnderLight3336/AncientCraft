package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.util.DataList;
import me.enderlight3336.ancientcraft.util.DataSaver;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.function.Consumer;

public abstract class DataItem<T extends ItemData> extends ItemInstance implements Datable<T> {
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
        DataSaver.Entry entry = genNewData(item);
        ItemMeta im = item.getItemMeta();

        List<String> lore = im.getLore();
        lore.addAll(entry.getData().buildLore());
        im.setLore(lore);

        ItemUtil.setDataId(im, entry.getIndex());
        item.setItemMeta(im);
        DataSaver.pub(getId(), entry);
        return item;
    }

    @Override
    public DataList<T> getDataList() {
        return data;
    }

    @Override
    public void modifyItemData(ItemStack target, Consumer<T> consumer) {
        int index = ItemUtil.getDataId(target);
        T data = getDataList().get(index);
        consumer.accept(data);
        DataSaver.put(getId(), index, data);
    }
}
