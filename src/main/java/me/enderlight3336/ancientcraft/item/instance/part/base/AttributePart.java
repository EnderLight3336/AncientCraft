package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import org.bukkit.attribute.Attribute;

public abstract class AttributePart extends BasePart {
    public AttributePart(JSONObject json) {
        super(json);
    }

    public void apply(ItemData ref, int targetLevel) {
        ItemManager.addAttribute(ref.getItemStack(), getAttribute(), getAmount(targetLevel));
    }

    public boolean canApply(ItemData ref) {
        return true;
    }

    public Attribute getAttribute() {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }

    public int getAmount(int targetLevel) {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }
}
