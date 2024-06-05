package me.enderlight3336.ancientcraft.listener;

import org.bukkit.event.Listener;

public final class PlayerListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onInteract(PlayerInteractEvent event) {}
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemInstance instance = ItemManager.getItemInstance(item);
            if (instance instanceof CustomDamageHandler) {
                ((CustomDamageHandler) instance).accept(event);
            }
            if (instance instanceof Datable) {
                ItemData data = ((Datable) instance).getData(item)
            }
        }
    }

}
