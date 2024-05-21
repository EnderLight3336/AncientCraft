package me.enderlight3336.ancientcraft.listener;

public final class InventoryListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onClose(InventoryCloseEvent event) {
        if(event.getView().getTopInventory().getHolder() instanceof ACInventoryHolder) {
            ((ACInventoryHolder) event.getView().getTopInventory().getHolder()).returnAllItem(event.getViewers().get(0));
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onClick(InventoryClickEvent event) {
        if(event.getView().getTopInventory().getHolder() instanceof ACInventoryHolder) {
            if() {
                event.getViewers().get(0).updateInventory();
            }
            if (event.getCurrentItem() != null) 
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onDrag(InventoryDragEvent event) {

        event.getViewers().get(0).updateInventory();
    }
    public void handle(@NotNull ItemStack item, ACInventoryHolder holder) {
        holder.click(InventoryUtil.getId(item));
    }
}