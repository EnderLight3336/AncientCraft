package me.enderlight3336.ancientcraft.listener;

public final class InventoryListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent event) {}
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {

        event.getViewers().get(0).updateInventory();
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onDrag(InventoryDragEvent event) {

        event.getViewers().get(0).updateInventory();
    }
}