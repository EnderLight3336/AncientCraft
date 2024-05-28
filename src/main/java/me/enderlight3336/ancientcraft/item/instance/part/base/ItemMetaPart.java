package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelData;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemMetaPart extends BasePart {

    public ItemMetaPart(JSONObject json) {
        super(json);
    }

    @Override
    public <T extends LevelData> void accept(T data) {
        ItemMeta im = data.getItemStack().getItemMeta();
        acceptMeta(im, data.getPartLevel(getId()));
        data.getItemStack().setItemMeta(im);

        super.accept(data);
    }
    public abstract void acceptMeta(ItemMeta meta, int current);
}
