package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.Datable;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;

public final class EntityListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemDespawn(ItemDespawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        int dataId = ItemUtil.getDataId(item);
        if (dataId != -1) {
            ((Datable<?>) ItemManager.getItemInstance(item)).getDataList().remove(dataId);
        }
    }
}
