package me.enderlight3336.ancientcraft.item.instance.sword;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.AdvData;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.Levelable;
import me.enderlight3336.ancientcraft.item.instance.Starable;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class HeavySword extends DataItem implements CustomDamageHandler, Starable, Levelable {
    public HeavySword(JSONObject json) {
        super(json);

        set(json);
    }

    @Override
    public @NotNull ItemData genData(ItemStack item) {
        return new AdvData(item);
    }

    @Override
    public void accept(EntityDamageByEntityEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK) {
            event.setDamage(event.getDamage() * 0.5);
        } else if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
            event.setDamage(event.getDamage() * 2);
        }
    }
}
