package me.enderlight3336.ancientcraft.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface StoreItemable {
    void returnAllItem(Player player);
    ItemStack[] getContents();
}