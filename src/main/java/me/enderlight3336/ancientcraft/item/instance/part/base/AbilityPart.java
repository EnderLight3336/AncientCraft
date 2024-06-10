package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.part.PartEventAcceptor;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbilityPart<T extends Event> extends BasePart implements PartEventAcceptor<T> {
    public AbilityPart(JSONObject object) {
        super(object);
    }


    @Override
    public <Ty extends LevelAndPartData> ApplyResult apply(@NotNull Ty data, ItemDatable<?> instance, ItemStack target) {
        ApplyResult result = super.apply(data, instance, target);
        if (result == ApplyResult.SUCCESS) {
            data.registerEventPart(this);
        }
        return result;
    }

    public abstract String[] getListenedEventNames();
}
