package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.calculate.Calculator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Map;

public class DamageListener implements Listener {
    private static Map<String, Calculator> idCalculator;
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerCauseDamage(EntityDamageEvent event) {
        if(event.getDamageSource().getCausingEntity() instanceof Player) {
            Player player = ((Player) event.getDamageSource().getCausingEntity());
        }
    }
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerReceiveDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player) {
            Player player = ((Player) event.getEntity());
        }
    }
}