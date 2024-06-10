package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Locale;

public final class EnchantmentPart extends BasePart {
    private final Enchantment enc;
    private final Integer[] value;

    public EnchantmentPart(JSONObject json) {
        super(json);

        this.enc = Registry.ENCHANTMENT.get(NamespacedKey.minecraft(json.getString("enchantment").toLowerCase(Locale.ROOT)));
        value = json.getJSONArray("value").toArray(Integer.class);
    }

    @Override
    public boolean isMaxLevel(int partLevel) {
        return value.length == partLevel || value.length < partLevel;
    }

    @Override
    protected void unsafeApply(ItemStack target, int currentPartLevel) {
        target.addUnsafeEnchantment(enc, value[currentPartLevel]);
    }
}