package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

public class DamageListener implements Listener {
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerCauseDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (ItemManager.isACItem(item)) {/**
             ItemRef data = ItemManager.getRef(item);
             if(data != null) {
             if(data instanceof CustomDamageHandler handler) {
             handler.accept(event);
             }
             }*/
                if (ItemManager.getItemInstance(item) instanceof CustomDamageHandler handler) {
                    handler.accept(event);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerReceiveDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
        }
    }
}