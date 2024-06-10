package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public final class PartSuckBlood extends AbilityPart<EntityDamageByEntityEvent> {
    private final Double[] value;
    private final double max;

    public PartSuckBlood(JSONObject json) {
        super(json);

        value = json.getJSONArray("value").toArray(Double.class);
        max = json.getDouble("max");
    }

    @Override
    protected void unsafeApply(ItemStack target, int currentPartLevel) {

    }

    @Override
    public boolean isMaxLevel(int partLevel) {
        return value.length == partLevel || value.length < partLevel;
    }

    @Override
    public void execute(EntityDamageByEntityEvent event, int partLevel) {
        if (event.getDamager() instanceof Player player) {
            double re = event.getDamage() * value[partLevel - 1];
            if (re > max) {
                re = max;
            }
            player.setHealth(Math.min(player.getHealth() + re,
                    player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
            ));
            player.sendMessage("!Test only , means you successfully suckblood");
        }
    }

    @Override
    public String[] getListenedEventNames() {
        return new String[]{EntityDamageByEntityEvent.class.getSimpleName()};
    }
}
