package me.enderlight3336.ancientcraft.listener;

import org.bukkit.event.Listener;

public final class InventoryListener implements Listener {/**
 @EventHandler(ignoreCancelled = true)
 public void onClose(InventoryCloseEvent event) {
 if(event.getView().getTopInventory().getHolder() instanceof ACInventoryHolder) {
 ((ACInventoryHolder) event.getView().getTopInventory().getHolder()).returnAllItem(event.getViewers().get(0));
 }
 }
 @EventHandler(ignoreCancelled = true)
 public void onClick(InventoryClickEvent event) {
 if(event.getView().getTopInventory().getHolder() instanceof ACInventoryHolder) {
 if() {
 ((Player) event.getViewers().get(0)).updateInventory();
 }
 if (event.getCurrentItem() != null) {}
 }
 }
 @EventHandler(ignoreCancelled = true)
 public void onDrag(InventoryDragEvent event) {

 ((Player) event.getViewers().get(0)).updateInventory();
 }
 public void handle(@NotNull ItemStack meta, ACInventoryHolder holder) {

 }*/
}