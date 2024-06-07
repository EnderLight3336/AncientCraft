package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.Datable;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.sword.ISword;
import me.enderlight3336.ancientcraft.listener.acceptor.CustomDamageHandler;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.util.LoreBuildService;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public final class PlayerListener implements Listener {

    /**
     * MultipleBlock Listener
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
     * Item modifies damage Listener
     */
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemInstance instance = ItemManager.getItemInstance(item);
            if(instance != null) {
                if (instance instanceof CustomDamageHandler) {
                    ((CustomDamageHandler) instance).execute(event);
                }
                if (instance instanceof Datable) {
                    ItemData data = ((Datable<?>) instance).getData(item);
                    if (data instanceof LevelAndPartData)
                        ((LevelAndPartData) data).acceptEvent(event, instance);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onFinalDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemInstance instance = ItemManager.getItemInstance(item);
            if (instance instanceof ISword & instance instanceof Datable<?>)
                ((Datable<?>) instance).modifyItemData(item, data -> {
                    if (data instanceof CommonData) {
                        if (((CommonData) data).addExp(1)) {
                            player.sendMessage("你的物品已升级");
                            LoreBuildService.addLevelBuildTask(item, (LevelAndPartData) data);
                        } else {
                            LoreBuildService.addExpBuildTask(item, (LevelAndPartData) data);
                        }
                    }
                });
        }
    }
}
