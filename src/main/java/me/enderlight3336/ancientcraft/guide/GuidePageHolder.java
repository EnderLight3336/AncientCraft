package me.enderlight3336.ancientcraft.guide;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.inventory.ACInventoryHolder;
import me.enderlight3336.ancientcraft.inventory.ACInventoryHolderImpl;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public class GuidePageHolder implements ACInventoryHolder {
    protected GuidePageHolder[] subPage = new GuidePageHolder[54];
    public GuidePageHolder(ItemStack[] item) {

    }

    @Override
    public boolean click(int slot, InventoryClickEvent event) {
        if (slot > 8 && slot < 45) {
            final GuidePageHolder holder = subPage[slot - 9];
            if (holder != null) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        event.getWhoClicked().openInventory(holder.getInventory());
                    }
                }.runTaskLater(AncientCraft.getInstance(), 1L);
            }
        } else if (slot == 47) {
            event.getWhoClicked().sendMessage("debug: back");
        } else if (slot == 52) {
            event.getWhoClicked().sendMessage("debug: ahead");
        }
        return false;
    }

    @NotNull
    @Override
    public Inventory getInventory() {
        return null;
    }
}
