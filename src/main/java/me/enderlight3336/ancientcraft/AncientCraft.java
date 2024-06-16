package me.enderlight3336.ancientcraft;

import me.enderlight3336.ancientcraft.command.CommandManager;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.listener.DummyListener;
import me.enderlight3336.ancientcraft.listener.EntityListener;
import me.enderlight3336.ancientcraft.listener.ItemProtectListener;
import me.enderlight3336.ancientcraft.listener.PlayerListener;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.recipe.RecipeManager;
import me.enderlight3336.ancientcraft.util.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class AncientCraft extends JavaPlugin {
    private static AncientCraft instance;
    private static boolean reload = false;

    public static AncientCraft getInstance() {
        return instance;
    }

    public static String getVersion() {
        return instance.getDescription().getVersion();
    }

    @Override
    public void onLoad() {
        super.onLoad();

        instance = this;
    }

    @Override
    public void onEnable() {
        FileUtil.init();
        //L18N.init();
        ItemManager.init();
        MultipleBlockManager.init();
        RecipeManager.recipeManager.init();

        getServer().getPluginManager().registerEvents(new DummyListener(), instance);
        getServer().getPluginManager().registerEvents(new EntityListener(), instance);
        getServer().getPluginManager().registerEvents(new ItemProtectListener(), instance);
        getServer().getPluginManager().registerEvents(new PlayerListener(), instance);

        new AsyncDataSaver().runTaskTimerAsynchronously(instance, 6000L, 6000L);
        new AsyncLoreBuilder().runTaskTimerAsynchronously(instance, 10L, 10L);
        new SyncBlockChangeTask().runTaskTimer(instance, 2L, 2L);

        getLogger().info("================AncientCraft================");
        getLogger().info("       version: " + getVersion());
        getLogger().info("**************Load Successfully*************");
    }

    @Override
    public void onDisable() {
        AsyncDataSaver.execute();
        AsyncLoreBuilder.execute();

        instance = null;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 0 || args.length == 1) {
            return List.of("version", "give", "dummy", "id");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("give")) {
                return new ArrayList<>(ItemManager.getRegisteredItem().keySet());
            }
        }//todo
        return null;
    }

    public static boolean isReload() {
        return reload;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String @NotNull [] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case "version" ->
                        sender.sendMessage("================AncientCraft================", "    version: " + AncientCraft.getVersion());
                case "give" -> {
                    if (sender instanceof Player) {
                        if (args.length == 1) {
                            sender.sendMessage("无效的物品id");
                            break;
                        }
                        String str = args[1].toLowerCase();
                        if (ItemManager.checkId(str)) {
                            int amount = 1;
                            if (args.length > 2) {
                                try {
                                    amount = Integer.parseInt(args[2]);
                                } catch (NumberFormatException e) {
                                    sender.sendMessage("无效的数字");
                                    break;
                                }
                            }
                            ((Player) sender).getInventory().addItem(args.length > 2 ?
                                    ItemManager.createItem(str, amount) :
                                    ItemManager.createItem(str));
                            sender.sendMessage("成功获取 " + amount + " * " + str);
                        } else {
                            sender.sendMessage("无效的物品id");
                        }
                    }
                }
                case "dummy" -> {
                    if (!CommandManager.requirePlayer(sender)) {
                        break;
                    }
                    Class<? extends Entity> c;
                    if (args.length == 2) {
                        if (args[1].equalsIgnoreCase("remove")) {
                            RayTraceResult result = ((Player) sender).getWorld().rayTraceEntities(((Player) sender).getLocation(), ((Player) sender).getLocation().getDirection(), 5);
                            Entity e;
                            if (result != null && (e = result.getHitEntity()) != null) {
                                if (ItemUtil.hasId(e)) {
                                    e.remove();
                                } else {
                                    sender.sendMessage("这不是Dummy");
                                }
                            } else {
                                sender.sendMessage("找不到你面对的实体");
                            }
                            break;
                        }
                        try {
                            c = EntityType.valueOf(args[1].toUpperCase()).getEntityClass();
                        } catch (Exception e) {
                            sender.sendMessage("failed!");
                            break;
                        }
                    } else {
                        c = EntityType.ZOMBIE.getEntityClass();
                    }
                    ((Player) sender).getWorld().spawn(
                            ((Player) sender).getLocation(),
                            c,
                            false,
                            entity -> {
                                LivingEntity e = (LivingEntity) entity;
                                e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(5000);
                                entity.getPersistentDataContainer().set(KeyManager.getIdKey(), PersistentDataType.STRING, "dummy");
                                ((LivingEntity) entity).setAI(false);
                            });
                }
                case "id" -> {
                    if (!CommandManager.requirePlayer(sender)) {
                        break;
                    }
                    String id = ItemUtil.getId(((Player) sender).getInventory().getItemInMainHand().getItemMeta());
                    sender.sendMessage(id == null ? "你手持的不是AncientCraft的物品 !" : "id: " + id);
                }
                case "reload" -> {
                    reload = true;
                    Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer("插件AncientCraft正在重载中"));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            AsyncDataSaver.execute();
                            AsyncLoreBuilder.execute();

                            Map<String, List<NamespacedKey>> map = RecipeManager.getRecipeKeyMap();
                            map.forEach((str, list) -> list.forEach(Bukkit::removeRecipe));
                            map.clear();

                            RecipeManager.recipeManager = new RecipeManager();
                            ConfigInstance.getSwordExpMap().clear();
                            FileUtil.init();
                            ItemManager.reload();
                            MultipleBlockManager.reload();
                            RecipeManager.recipeManager.init();
                            RecipeManager.recipeManager = null;
                            reload = false;
                            getLogger().info("================AncientCraft================");
                            getLogger().info("        version: " + getVersion());
                            getLogger().info("*************Reload Successfully************");
                        }
                    }.runTaskAsynchronously(instance);
                }
                default -> CommandManager.sendHelp(sender);
            }
        } else {
            CommandManager.sendHelp(sender);
        }
        return true;
    }
}
