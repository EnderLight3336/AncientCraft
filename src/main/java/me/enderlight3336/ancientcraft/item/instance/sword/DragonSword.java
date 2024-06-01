package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.Levelable;
import me.enderlight3336.ancientcraft.item.instance.Starable;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DragonSword extends DataItem <CommonData> implements CustomDamageHandler, Starable, Levelable {
    public DragonSword(JSONObject json) {
        super(json);

        set(json);
    }

    @Override
    public int genNewData(ItemStack item) {
        return data.put(new CommonData());
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }

    @Override
    public void accept(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof EnderDragon) {
            event.setDamage(event.getDamage() * 2);
        }
    }
}
