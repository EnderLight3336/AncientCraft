package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Map;

public final class PartSuckBlood extends AbilityPart {
    private final Double[] value;
    private final double max;
    private final Map<String, PartEventAcceptor<?>> handlers = Map.of(EntityDamageByEntityEvent.class.getSimpleName(), new PartEventAcceptor<EntityDamageByEntityEvent>() {
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
            }
        }

        @Override
        public String getId() {
            return id;
        }
    });

    public PartSuckBlood(JSONObject json) {
        super(json);

        value = json.getJSONArray("value").toArray(Double.class);
        max = json.getDouble("max");
    }

    @Override
    public Map<String, PartEventAcceptor<?>> getEventHandlers() {
        return handlers;
    }

    @Override
    public boolean isMaxLevel(int partLevel) {
        return value.length == partLevel || value.length < partLevel;
    }
}
