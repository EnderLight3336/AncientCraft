package me.enderlight3336.ancientcraft.inventory;

public class ACInventoryHolder implements InventoryHolder {
    public ACInventoryHolder() {}
    @Override
    public Inventory getInventory() {
        return InventoryInstance.getNoMean();
    }
    public boolean click(int target) {
        return false;
    }
}