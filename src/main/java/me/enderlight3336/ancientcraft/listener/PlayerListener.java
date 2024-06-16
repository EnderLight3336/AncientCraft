package me.enderlight3336.ancientcraft.listener;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemEventable;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.type.*;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.util.AsyncLoreBuilder;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.util.concurrent.Callable;

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
            ItemInstance instance = ItemManager.getItemInstance(item);
            if (instance instanceof ItemEventable)
                ((ItemEventable) instance).on(event);
            if (instance instanceof ItemLevelAndPartable<?>) {
                ((ItemLevelAndPartable<?>) instance).getData(item).acceptEvent(event, instance);
            }
        }
    }

    /**
     * Sword exp listener
     */
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntity().getKiller() instanceof Player player) {
            ItemStack item = player.getInventory().getItemInMainHand();
            ItemInstance instance = ItemManager.getItemInstance(item);
            if (instance instanceof ItemLevelAndPartable<?>) {
                if (instance instanceof ISword) {//hold item can have level
                    ((ItemLevelAndPartable<?>) instance).modifyItemData(item, data -> {
                        int ret = data.addExp(ConfigInstance.getSwordExp(event.getEntityType().getKey().getKey()));
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
    public void onBlockBreak(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemInstance instance = ItemManager.getItemInstance(item);
        if (instance instanceof ItemEventable)
            ((ItemEventable) instance).on(event);
        if (instance instanceof ItemLevelAndPartable<?>)
            ((ItemLevelAndPartable<?>) instance).getData(item).acceptEvent(event, instance);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBlockBreakFinally(BlockBreakEvent event) {
        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        ItemInstance instance = ItemManager.getItemInstance(item);
        if (instance instanceof ItemLevelAndPartable<?>) {
            if (instance instanceof IPickaxe) {
                if (!event.getBlock().hasMetadata("ac_place")) {
                    int exp = ConfigInstance.getPickaxeExp(event.getBlock().getType());
                    if (exp == 0)
                        return;
                    ((ItemLevelAndPartable<?>) instance).modifyItemData(item, data -> {
                        int ret = data.addExp(exp);
                        if (ret == -1) {
                            AsyncLoreBuilder.addExpBuildTask(item, data);// only add exp
                        } else {
                            event.getPlayer().sendMessage("你的物品已升级至Lv" + ret);//level up
                            AsyncLoreBuilder.addLevelBuildTask(item, data);
                        }
                    });
                }
            } else if (instance instanceof IHoe) {
                if (event.getBlock().getBlockData() instanceof Ageable ageable) {
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        int exp = ConfigInstance.getHoeExp(event.getBlock().getType());
                        if (exp == 0)
                            return;
                        ((ItemLevelAndPartable<?>) instance).modifyItemData(item, data -> {
                            int ret = data.addExp(exp);
                            if (ret == -1) {
                                AsyncLoreBuilder.addExpBuildTask(item, data);// only add exp
                            } else {
                                event.getPlayer().sendMessage("你的物品已升级至Lv" + ret);//level up
                                AsyncLoreBuilder.addLevelBuildTask(item, data);
                            }
                        });
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event) {
        if (ConfigInstance.isPickaxeExpTarget(event.getBlockPlaced().getType())) {
            event.getBlockPlaced().setMetadata("ac_place", new LazyMetadataValue(AncientCraft.getInstance(), () -> null));
        }
    }

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (AncientCraft.isReload())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "插件AncientCraft正在重载中");
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        ItemInstance instance = ItemManager.getById(event.getInventory().getResult().getItemMeta().getPersistentDataContainer().get(KeyManager.getPreviewItemKey(), PersistentDataType.STRING));
        if (instance instanceof ItemDatable<?>)
            event.getInventory().setResult(instance.createItem());
    }
}
