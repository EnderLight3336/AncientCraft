package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.part.PartEventAcceptor;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class AbilityPart<T extends Event> extends BasePart implements PartEventAcceptor<T> {
    public AbilityPart(JSONObject object) {
        super(object);
    }


    @Override
    public <Ty extends LevelAndPartData> boolean safeApply(@NotNull Ty data, ItemStack target) {
        if(super.safeApply(data, target)) {
            data.registerEventPart(this);
            return true;
        }
        return false;
    }

    public abstract String[] getListenedEventNames();
}
