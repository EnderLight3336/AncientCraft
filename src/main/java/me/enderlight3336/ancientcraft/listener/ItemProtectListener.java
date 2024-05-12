package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.base.ACItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;

public class ItemProtectListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        for(ItemStack item : event.getInventory().getContents()) {
            if(item instanceof ACItemStack) {
                event.setResult(null);
                return;
            }
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if(event.getItem() instanceof ACItemStack)
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for(ItemStack item : event.getInventory().getMatrix()) {
            if(item instanceof ACItemStack) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }
}
