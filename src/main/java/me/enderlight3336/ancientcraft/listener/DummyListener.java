package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public final class DummyListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
    public void onEntityDamage(EntityDamageEvent event) {
        if (ItemUtil.hasId(event.getEntity())) {
            event.setCancelled(true);
            if (event.getDamageSource().getCausingEntity() instanceof Player) {
                event.getDamageSource().getCausingEntity().sendMessage("Damage: " + event.getDamage(),
                        "Distance: " + event.getEntity().getLocation().distance(event.getDamageSource().getCausingEntity().getLocation()));
            }
        }
    }
}
