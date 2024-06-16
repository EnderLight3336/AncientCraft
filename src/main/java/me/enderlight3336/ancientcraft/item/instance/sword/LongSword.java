package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.ItemEventable;
import me.enderlight3336.ancientcraft.item.instance.type.ItemLevelAndPartable;
import me.enderlight3336.ancientcraft.item.instance.type.ISword;
import me.enderlight3336.ancientcraft.util.AsyncDataSaver;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class LongSword extends DataItem<CommonData> implements ItemLevelAndPartable<CommonData>, ISword, ItemEventable {
    public LongSword(JSONObject json) {
        super(json);
    }

    @Override
    public AsyncDataSaver.Entry genNewData(ItemStack item) {
        CommonData data1 = new CommonData();
        return new AsyncDataSaver.Entry(data.put(data1), data1);
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }

    @Override
    public void on(Event event1) {
        if (event1 instanceof EntityDamageByEntityEvent event) {
            if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
                event.setDamage(event.getDamage() * 1.3);
            }
        }
    }

    @Override
    public boolean checkType(String typeName) {
        return typeName.equals("sword") || super.checkType(typeName);
    }
}
