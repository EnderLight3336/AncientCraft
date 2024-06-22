package me.enderlight3336.ancientcraft.inventory;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public interface ACInventoryHolder extends InventoryHolder {
    boolean click(int slot, InventoryClickEvent event);
}
