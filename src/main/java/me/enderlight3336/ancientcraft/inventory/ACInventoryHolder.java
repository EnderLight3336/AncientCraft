package me.enderlight3336.ancientcraft.inventory;

public final class ACInventoryHolder implements InventoryHolder {
    @Override
    public Inventory getInventory() {
        return InventoryInstance.getNoMean();
    }
}