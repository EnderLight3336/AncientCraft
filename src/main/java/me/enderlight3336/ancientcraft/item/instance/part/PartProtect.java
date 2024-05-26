package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AttributePart;
import org.bukkit.attribute.Attribute;

public class PartProtect extends AttributePart {

    public PartProtect(JSONObject json) {
        super(json);
    }

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_ARMOR;
    }

    @Override
    public int getAmount(int targetLevel) {
        return 50;
    }
}
