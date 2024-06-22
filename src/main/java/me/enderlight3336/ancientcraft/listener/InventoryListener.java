package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.inventory.ACInventoryHolder;
import me.enderlight3336.ancientcraft.inventory.StoreItemable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;

public final class InventoryListener implements Listener {
    @EventHandler(ignoreCancelled = true)
    public void onClose(InventoryCloseEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof StoreItemable holder) {
            holder.returnAllItem((Player) event.getPlayer());
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onClick(InventoryClickEvent event) {
        if (event.getView().getTopInventory().getHolder() instanceof ACInventoryHolder holder)
            event.setCancelled(holder.click(event.getSlot(), event));
    }

    @EventHandler(ignoreCancelled = true)
    public void onDrag(InventoryDragEvent event) {
    }
}