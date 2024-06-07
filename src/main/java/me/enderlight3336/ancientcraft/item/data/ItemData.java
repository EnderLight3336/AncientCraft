package me.enderlight3336.ancientcraft.item.data;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.function.Consumer;

/**
 * Do not suggest modify data that given by {@link me.enderlight3336.ancientcraft.util.DataList#get(int)}
 * or {@link me.enderlight3336.ancientcraft.item.instance.Datable#getData(ItemStack)}
 * <p>
 * If needed, please call {@link me.enderlight3336.ancientcraft.item.ItemManager#modifyItemData(ItemStack, ItemInstance, Consumer)}
 * or {@link me.enderlight3336.ancientcraft.item.instance.Datable#modifyItemData(ItemStack, Consumer)}
 * <p>
 * Because method modifyItemData will save your change
 */
public interface ItemData {
    String toJsonString();

    List<String> buildLore();
    List<String> rebuildLore(List<String> origin);
}
