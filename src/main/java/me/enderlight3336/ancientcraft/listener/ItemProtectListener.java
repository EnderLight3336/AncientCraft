package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.base.BasePart;
import me.enderlight3336.ancientcraft.item.instance.type.ItemLevelAndPartble;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockCookEvent;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;

public final class ItemProtectListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPrepareAnvil(PrepareAnvilEvent event) {
        ItemStack[] inv = event.getInventory().getContents();
        ItemInstance i1 = ItemManager.getItemInstance(inv[0]);
        ItemInstance i2 = ItemManager.getItemInstance(inv[1]);
        if(i1 != null) {
            if (i1 instanceof ItemLevelAndPartble<?> && i2 instanceof BasePart) {
                //
            } else {
                event.setResult(null);
            }
        } else {
            if (i2 != null) {
                event.setResult(null);
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
                    if (event.isRepair()) {
                        event.getInventory().setResult(null);
                    } else if (event.getRecipe() instanceof CraftingRecipe recipe) {
                        if (!recipe.getKey().getNamespace().equals("ancientcraft"))
                            event.getInventory().setResult(null);
                    }
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
