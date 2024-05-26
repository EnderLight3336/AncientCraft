package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelData;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.function.BiConsumer;

public class ItemMetaPart extends BasePart {
    private final BiConsumer<ItemMeta, Integer> func;

    public ItemMetaPart(JSONObject json, BiConsumer<ItemMeta, Integer> action) {
        super(json);

        this.func = action;
    }

    @Override
    public <T extends LevelData> void accept(T data) {
        ItemMeta im = data.getItemStack().getItemMeta();
        func.accept(im, data.containsPart(getId()));
        data.getItemStack().setItemMeta(im);

        super.accept(data);
    }
}
