package me.enderlight3336.ancientcraft.item.base;

import me.enderlight3336.ancientcraft.util.ItemNBTUtil;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.List;

public class ACItemStack extends ItemStack {
    private final String id;
    public ACItemStack(String id, String name, Material material, String... lore) {
        this(id, name, material, 1, lore);
    }
    public ACItemStack(String id, String name, Material material, int amount, String... lore) {
        super(material, amount);

        this.id = id;
        getMeta().setDisplayName(name);
        getMeta().setLore(List.of(lore));
        ItemNBTUtil.setId(getMeta(), id);
    }
    public ItemMeta getMeta() {
        try {
            Field field = super.getClass().getField("meta");
            field.setAccessible(true);
            return (ItemMeta) field.get(ItemMeta.class);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public String getId() {
        return id;
    }
    public void addAttribute(Attribute attribute, double amount) {
        try {
            this.getMeta().addAttributeModifier(attribute,
                    new AttributeModifier("ac_internal", amount, AttributeModifier.Operation.ADD_NUMBER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
