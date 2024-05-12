package me.enderlight3336.ancientcraft.item.base;

import org.bukkit.Material;

public class ItemRef {
    private final Material material;
    private final int amount;
    private String[] tags;
    public ItemRef(Material material, int amount) {
        this(material, amount, null);
    }
    public ItemRef(Material material, int amount, String... tags) {
        this.material = material;
        this.amount = amount;
        this.tags = tags;
    }
    public Material getMaterial() {
        return material;
    }

    public int getAmount() {
        return amount;
    }
}
