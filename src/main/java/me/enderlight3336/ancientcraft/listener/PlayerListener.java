package me.enderlight3336.ancientcraft.listener;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.brain.BrainManager;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemEventable;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.type.*;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.util.AsyncLoreBuilder;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.io.FileInputStream;
import java.util.UUID;

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
            Block block = event.getBlock();
            if (instance instanceof IPickaxe) {
                if (!block.hasMetadata("ac_place")) {
                    int exp = ConfigInstance.getPickaxeExp(block.getType());
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
            } else if (instance instanceof IFarmTool) {
                if (block.getBlockData() instanceof Ageable ageable) {
                    if (ageable.getAge() == ageable.getMaximumAge()) {
                        int exp = ConfigInstance.getHoeExp(block.getType());
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
                } else if (block.getType() == Material.MELON || block.getType() == Material.PUMPKIN) {
                    //todo
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

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (AncientCraft.isReload())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, "插件AncientCraft正在重载中");
        else if (event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            BrainManager.load(event.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        BrainManager.unload(event.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent event) {
        BrainManager.unload(event.getPlayer().getUniqueId());
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onCraft(CraftItemEvent event) {
        ItemInstance instance = ItemManager.getById(event.getInventory().getResult().getItemMeta().getPersistentDataContainer().get(KeyManager.getPreviewItemKey(), PersistentDataType.STRING));
        if (instance != null)
            event.getInventory().setResult(instance.createItem());
    }

    @EventHandler(ignoreCancelled = true)
    public void onFish(PlayerFishEvent event) {
        if (event.getState() == PlayerFishEvent.State.REEL_IN) {
            ItemMeta meta;
            if ((meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta()) != null) {
                if ("climber_hook".equals(meta.getPersistentDataContainer().get(KeyManager.getIdKey(), PersistentDataType.STRING))) {
                    if (event.getHook().getLocation().distance(event.getPlayer().getLocation()) >= 1) {
                        event.getHook().setHookedEntity(event.getPlayer());
                        event.getHook().pullHookedEntity();
                    }
                }
            }
        }
    }
}
