package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import me.enderlight3336.ancientcraft.loot.CustomLootTableManager;
import me.enderlight3336.ancientcraft.loot.CustomMobLootTable;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.Lootable;

public final class EntityListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onItemDespawn(ItemDespawnEvent event) {
        ItemStack item = event.getEntity().getItemStack();
        int dataId = ItemUtil.getDataId(item);
        if (dataId != -1) {
            ((ItemDatable<?>) ItemManager.getItemInstance(item)).getDataList().remove(dataId);
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity instanceof Lootable) {
            CustomMobLootTable lootTable = CustomLootTableManager.getLootTable(entity.getType());
            if (lootTable != null) {
                Player killer = entity.getKiller();
                event.getDrops().addAll(lootTable.populateLoot(killer == null ? 0 : killer.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING)));
            }
        }
    }
}
