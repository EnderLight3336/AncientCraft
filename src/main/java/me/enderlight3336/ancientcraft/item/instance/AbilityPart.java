package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.BasePart;
import org.bukkit.event.Event;

public abstract class AbilityPart <T extends Event> extends BasePart {
    public AbilityPart(JSONObject object) {
        super(object);
    }
    public abstract void accept(T event);
}
