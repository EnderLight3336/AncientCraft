package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.Util;
import me.enderlight3336.ancientcraft.item.ref.ItemRef;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public abstract class AttributePart extends BasePart {
    public AttributePart(JSONObject json) {
        super(json);
    }

    public void apply(ItemRef ref, int targetLevel) {
        Util.addAttribute(ref.getItemStack(), getAttribute(), getAmount(targetLevel));
    }
    public boolean canApply(ItemRef ref) {
        return true;
    }
    public Attribute getAttribute() {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }
    public int getAmount(int targetLevel) {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }
}
