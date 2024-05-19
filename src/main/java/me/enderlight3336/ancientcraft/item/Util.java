package me.enderlight3336.ancientcraft.item;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.ref.ItemRef;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class Util {
    private static List<ItemRef> refs = new ArrayList<>(50);
    private static final NamespacedKey idKey;
    private static final NamespacedKey dataKey;
    static {
        idKey = new NamespacedKey(AncientCraft.getInstance(), "ac_id");
        dataKey = new NamespacedKey(AncientCraft.getInstance(), "ac_data");
    }
    public static void init() {}
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
    public static ItemStack createItem(ItemInstance instance) {
        ItemStack item = instance.getItem();
        ItemMeta im = item.getItemMeta();
        setInternalId(im);
        item.setItemMeta(im);
        return item;
    }
    public static ItemStack createItem(ItemInstance instance, int amount) {
        ItemStack item = instance.getItem(amount);
        ItemMeta im = item.getItemMeta();
        setInternalId(im);
        item.setItemMeta(im);
        return item;
    }
    public static ItemRef getRef(ItemStack item) {
        return refs.get(getInternalId(item.getItemMeta()));
    }

    @Nullable
    public static String getId(@NotNull ItemMeta im) {
        return im.getPersistentDataContainer().get(idKey, PersistentDataType.STRING);
    }

    public static void setId(@NotNull ItemMeta im, String id) {
        im.getPersistentDataContainer().set(idKey, PersistentDataType.STRING, id);
    }

    public static int getInternalId(@NotNull ItemMeta im) {
        return im.getPersistentDataContainer().get(dataKey, PersistentDataType.INTEGER);
    }

    public static void setInternalId(@NotNull ItemMeta im) {
        im.getPersistentDataContainer().set(dataKey, PersistentDataType.INTEGER, refs.size());
    }
}
