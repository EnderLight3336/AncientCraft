package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.Util;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemInstance {
    private final String id;
    private final String name;
    private final Material material;
    private final List<String> lore = new ArrayList<>();
    private final ItemStack build;
    public ItemInstance(String id, Material material, String name, List<String> lore) {
        this.id = id;
        this.name = name;
        this.material = material;

        this.lore.addAll(lore);
        this.build = new ItemStack(material);

        ItemMeta im = build.getItemMeta();
        im.setDisplayName(name);
        im.setLore(this.lore);

        build.setItemMeta(im);
    }
    public ItemInstance(JSONObject json) {
        this.id = json.getString("id");
        this.name = json.getString("name");
        this.material = Material.valueOf(json.getString("material"));
        this.lore.addAll(json.getJSONArray("lore").toJavaList(String.class));

        build = new ItemStack(this.material);
        ItemMeta im = build.getItemMeta();
        im.setDisplayName(this.name);
        Util.setId(im, id);
        im.setLore(this.lore);

        this.build.setItemMeta(im);
    }

    public ItemStack getItem() {
        return getItem(1);
    }

    public ItemStack getItem(int amount) {
        ItemStack item = build.clone();
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
