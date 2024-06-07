package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.Levelable;
import me.enderlight3336.ancientcraft.item.instance.Starable;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import me.enderlight3336.ancientcraft.util.DataSaver;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LongSword extends DataItem<CommonData> implements CustomDamageHandler, Starable, Levelable, ISword {
    public LongSword(JSONObject json) {
        super(json);

        set(json);
    }

    @Override
    public DataSaver.Entry genNewData(ItemStack item) {
        CommonData data1 = new CommonData();
        return new DataSaver.Entry(data.put(data1), data1);
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }

    @Override
    public void execute(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            event.setDamage(event.getDamage() * 1.3);
        }
    }
}
