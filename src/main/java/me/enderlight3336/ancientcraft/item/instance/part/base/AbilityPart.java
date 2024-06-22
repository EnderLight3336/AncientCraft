package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.part.PartEventAcceptor;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public abstract class AbilityPart extends BasePart {
    public AbilityPart(JSONObject object) {
        super(object);
    }


    @Override
    public <D extends LevelAndPartData> ApplyResult apply(@NotNull D data, ItemDatable<?> instance, ItemStack target) {
        ApplyResult result = super.apply(data, instance, target);
        if (result == ApplyResult.SUCCESS) {
            data.registerEventPart(getEventHandlers());
        }
        return result;
    }

    @Override
    protected void unsafeApply(ItemStack target, int currentPartLevel) {
    }

    public abstract Map<String, PartEventAcceptor<?>> getEventHandlers();
}
