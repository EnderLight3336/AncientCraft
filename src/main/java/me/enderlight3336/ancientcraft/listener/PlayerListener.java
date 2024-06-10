package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.type.ItemLevelAndPartble;
import me.enderlight3336.ancientcraft.item.instance.type.ItemSword;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.util.AsyncLoreBuilder;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public final class PlayerListener implements Listener {

    /**
     * MultipleBlock
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == RIGHT_CLICK_BLOCK) {
            Block clicked = event.getClickedBlock();
            if (clicked != null) {
                if (MultipleBlockManager.onInteract(event)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /**
     * Item modifies damage
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta() != null) {
                ItemInstance instance = ItemManager.getItemInstance(item);
                if (instance != null) {
                    if (instance instanceof CustomDamageHandler) {
                        ((CustomDamageHandler) instance).execute(event);
                    }
                    if (instance instanceof ItemLevelAndPartble<?>) {
                        ((ItemLevelAndPartble<?>) instance).getData(item).acceptEvent(event, instance);
                    }
                }
            }
        }
    }

    /**
     * Sword exp listener
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player player) {//is player
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.hasItemMeta()) {
                ItemInstance instance = ItemManager.getItemInstance(item);
                if (instance instanceof ItemSword && instance instanceof ItemLevelAndPartble<?>) {//hold item can have level
                    ((ItemLevelAndPartble<?>) instance).modifyItemData(item, data -> {
                        int ret = data.addExp(ConfigInstance.getExp(event.getEntityType().getKey().getKey()));
                        if (ret == -1) {
                            AsyncLoreBuilder.addExpBuildTask(item, data);// only add exp
                        } else {
                            player.sendMessage("你的物品已升级至Lv" + ret);//level up
                            AsyncLoreBuilder.addLevelBuildTask(item, data);
                        }
                    });
                }
            }
        }
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (AncientCraft.isReload())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "插件AncientCraft正在重载中");
    }
}
