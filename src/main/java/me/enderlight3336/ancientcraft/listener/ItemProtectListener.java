package me.enderlight3336.ancientcraft.listener;

import org.bukkit.event.Listener;

public class ItemProtectListener implements Listener {/**
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        for(ItemStack item : event.getInventory().getContents()) {
            if(item instanceof ItemInstance) {
                event.setResult(null);
                return;
            }
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if(event.getItem() instanceof ItemInstance)
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for(ItemStack item : event.getInventory().getMatrix()) {
            if(item instanceof ItemInstance) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }*/
}
