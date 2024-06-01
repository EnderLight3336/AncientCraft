package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemInstance {
    protected final String id;
    protected final String name;
    protected final Material material;
    protected final List<String> lore = new ArrayList<>();
    protected final ItemStack originItem;

    public ItemInstance(String id, Material material, String name, List<String> lore) {
        this.id = id;
        this.name = name;
        this.material = material;

        this.lore.addAll(lore);
        this.originItem = new ItemStack(material);

        ItemMeta im = originItem.getItemMeta();
        im.setDisplayName(name);
        im.setLore(this.lore);

        originItem.setItemMeta(im);
    }

    public ItemInstance(JSONObject json) {
        this.id = json.getString("id");
        this.name = json.getString("name");
        this.material = Material.valueOf(json.getString("material"));
        this.lore.addAll(json.getJSONArray("lore").toJavaList(String.class));

        originItem = new ItemStack(this.material);
        ItemMeta im = originItem.getItemMeta();
        im.setDisplayName(this.name);
        ItemUtil.setId(im, id);
        im.setLore(this.lore);

        if (json.containsKey("baseAttributes")) {
            JSONObject attributes = json.getJSONObject("attributes");
            attributes.forEach((str, o) -> im.addAttributeModifier(Attribute.valueOf(str),
                    new AttributeModifier("ancientcraft", (double) o, AttributeModifier.Operation.ADD_NUMBER)));
        }

        if (im instanceof Damageable && json.containsKey("durability")) {
            ((Damageable) im).setMaxDamage(json.getInteger("durability"));
        }

        this.originItem.setItemMeta(im);

        ItemManager.regItem(this);
    }


    /**
     * called when prepare create {@link ItemStack}
     *
     * @return a copy of {@link ItemInstance#originItem}
     * @see #createItem()
     */
    public ItemStack createItem() {
        return originItem.clone();
    }

    /**
     * called when prepare create {@link ItemStack}
     *
     * @return a copy of {@link ItemInstance#originItem}
     * @throws RuntimeException if extends {@link DataItem}
     * @see ItemManager#createItem(String, int)
     * @see DataItem#createItem(int)
     */
    public ItemStack createItem(int amount) {
        ItemStack item = originItem.clone();
        item.setAmount(amount);
        return item;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }
}
