package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public final class EnchantmentPart extends BasePart {
    private final Enchantment enc;
    private final Integer[] value;
    public EnchantmentPart(JSONObject json) {
        super(json);

        this.enc = Registry.ENCHANTMENT.match(json.getString("enchantement"));
        value = json.getJSONArray("value").toArray(Integer.class);
    }

    @Override
    public boolean canApply(int current) {
        return current < value.length;
    }

    @Override
    public <T extends CommonData> void apply(T data, ItemStack target, int currentPartLevel) {
        super.apply(data, target, currentPartLevel);

        target.addUnsafeEnchantment(enc, value[currentPartLevel]);
    }
}