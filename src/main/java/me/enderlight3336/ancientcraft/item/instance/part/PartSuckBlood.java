package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class PartSuckBlood extends AbilityPart<EntityDamageByEntityEvent> {
    private final Double[] value;
    private final double max;

    public PartSuckBlood(JSONObject json) {
        super(json);

        value = json.getJSONArray("value").toArray(Double.class);
        max = json.getDouble("max");
    }

    @Override
    public void apply(ItemStack target, int currentPartLevel) {

    }

    @Override
    public boolean canApply(int current) {
        return current < value.length;
    }

    @Override
    public void execute(EntityDamageByEntityEvent event, int partLevel) {
        if(event.getDamager() instanceof Player player) {
            double re = event.getDamage() * value[partLevel - 1];
            if (re > max) {
                re = max;
            }
            player.setHealth(Math.min(player.getHealth() + re,
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
            ));
        }
    }

    @Override
    public String[] getListenedEventNames() {
        return new String[]{};
    }
}
