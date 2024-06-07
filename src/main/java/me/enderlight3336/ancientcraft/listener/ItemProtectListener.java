package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

public final class ItemProtectListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        for (ItemStack item : event.getInventory().getContents()) {
            if (item != null) {
                if (ItemManager.isACItem(item)) {
                    event.setResult(null);
                    return;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareItemEnchant(PrepareItemEnchantEvent event) {
        if (ItemManager.isACItem(event.getItem()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareItemCraft(PrepareItemCraftEvent event) {
        for (ItemStack item : event.getInventory().getMatrix()) {
            if (item != null) {
                if (ItemManager.isACItem(item)) {
                    event.getInventory().setResult(null);
                    return;
                }
            }
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareSmithing(PrepareSmithingEvent event) {
        ItemStack[] items = event.getInventory().getStorageContents();
        if ((items[0] != null && ItemManager.isACItem(items[0])) ||
                (items[1] != null && ItemManager.isACItem(items[1])) ||
                (items[2] != null && ItemManager.isACItem(items[2]))) {
            event.setResult(null);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareGrindstone(PrepareGrindstoneEvent event) {
        ItemStack[] items = event.getInventory().getStorageContents();
        if ((items[0] != null && ItemManager.isACItem(items[0])) ||
                (items[1] != null && ItemManager.isACItem(items[1]))) {
            event.setResult(null);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBrewingStandFuel(BrewingStandFuelEvent event) {
        if (ItemManager.isACItem(event.getFuel()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onFurnaceStartSmelt(FurnaceStartSmeltEvent event) {
        if (ItemManager.isACItem(event.getSource()))
            event.setTotalCookTime(Integer.MAX_VALUE);//todo: may change
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onFurnaceBurn(FurnaceBurnEvent event) {
        if (ItemManager.isACItem(event.getFuel()))
            event.setCancelled(true);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onBlockCook(BlockCookEvent event) {
        if (ItemManager.isACItem(event.getSource()))
            event.setCancelled(true);
    }
}
