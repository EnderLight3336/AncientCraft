package me.enderlight3336.ancientcraft.listener;

import org.bukkit.event.Listener;

public final class ItemProtectListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        for(ItemStack item : event.getInventory().getContents()) {
            if(Util.isACItem(item)) {
                event.setResult(null);
                return;
            }
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if(Util.isACItem(event.getItem()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for(ItemStack item : event.getInventory().getMatrix()) {
            if(Util.isACItem(item)) {
                event.getInventory().setResult(null);
                return;
            }
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        event.getInventory()//todo
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPrepareGrindstone(PrepareGrindstoneEvent event) {}
}
