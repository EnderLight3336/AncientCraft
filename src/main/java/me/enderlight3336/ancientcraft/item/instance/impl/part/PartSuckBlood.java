package me.enderlight3336.ancientcraft.item.instance.impl.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.AbilityPart;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class PartSuckBlood extends AbilityPart<EntityDamageEvent> {
    private final double ratio;
    private final double max;
    public PartSuckBlood(JSONObject json) {
        super(json);

        ratio = json.getDouble("ratio");
        max = json.getDouble("max");
    }

    /**
     * accept event
     * @param event this event has been authed that damage is caused by Player
     */
    @Override
    public void accept(EntityDamageEvent event) {
        Player player = (Player) event.getDamageSource().getCausingEntity();
        double re = event.getDamage() * ratio;
        if(re > max) {
            re = max;
        }
        player.setHealth(Math.min(player.getHealth() + re,
                player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()
        ));
    }
}
