package me.enderlight3336.ancientcraft.item;

import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.base.ItemMetaPart;
import me.enderlight3336.ancientcraft.item.instance.sword.DragonSword;
import me.enderlight3336.ancientcraft.item.instance.sword.HeavySword;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ItemManager {
    private static final Map<String, ItemInstance> registeredItem = new HashMap<>();
    private static final Map<DataItem, Map<String, List<ItemData>>> data = new HashMap<>();

    public static void addAttribute(ItemStack item, Attribute attribute, double amount) {
        try {
            ItemMeta im = item.getItemMeta();
            im.addAttributeModifier(attribute,
                    new AttributeModifier("ac_internal", amount, AttributeModifier.Operation.ADD_NUMBER));
            item.setItemMeta(im);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ItemStack createItem(String id, Player player) {
        return registeredItem.get(id).createItem(player);
    }

    public static ItemStack createItem(String id, int amount) {
        return registeredItem.get(id).createItem(amount);
    }

    public static boolean isACItem(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().has(KeyManager.getIdKey());
    }

    public static void regItem(ItemInstance item) {
        registeredItem.put(item.getId(), item);
    }

    public static boolean isAvailableId(String id) {
        return registeredItem.containsKey(id);
    }

    public static ItemInstance getItemInstance(ItemStack item) {
        return registeredItem.get(KeyManager.getId(item.getItemMeta()));
    }

    /**
     * @return the data's index
     */
    public int putData(ItemData data) {
        return -1;//todo
    }

    public static void init() {
        new DragonSword(FileUtil.getJSON("/meta/DragonSword.json"));
        new HeavySword(FileUtil.getJSON("/meta/HeavySword.json"));
        new ItemMetaPart(FileUtil.getJSON("/meta/PartSharpness.json"), (itemMeta, integer) ->)
    }
}
