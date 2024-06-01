package me.enderlight3336.ancientcraft;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.listener.DamageListener;
import me.enderlight3336.ancientcraft.listener.DummyListener;
import me.enderlight3336.ancientcraft.listener.ItemProtectListener;
import me.enderlight3336.ancientcraft.multiple.MultipleBlockManager;
import me.enderlight3336.ancientcraft.util.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.RayTraceResult;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;

public final class AncientCraft extends JavaPlugin {
    private static AncientCraft instance;
    private static String version;

    @Override
    public void onEnable() {
        instance = this;
        version = YamlConfiguration.loadConfiguration(new InputStreamReader(instance.getResource("plugin.yml"))).getString("version");
        try {
            FileUtil.init();
            ConfigInstance.init();
            L18N.init();
            ItemManager.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        MultipleBlockManager.init();

        getServer().getPluginManager().registerEvents(new DamageListener(), instance);
        getServer().getPluginManager().registerEvents(new DummyListener(), instance);
        getServer().getPluginManager().registerEvents(new ItemProtectListener(), instance);

        getLogger().info("========AncientCraft========");
        getLogger().info("  version: " + version);
    }

    @Override
    public void onDisable() {
        instance = null;
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
                        String str = args[1].toUpperCase();
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
                            ((Player) sender).getInventory().addItem(ItemManager.createItem(str, amount));
                            sender.sendMessage("成功获取 " + amount + " * " + str);
                        } else {
                            sender.sendMessage("无效的物品id");
                        }
                    }
                }
                case "dummy" -> {
                    if (!CommandUtil.requirePlayer(sender)) {
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
                    ((LivingEntity) ((Player) sender).getWorld().spawn(((Player) sender).getLocation(), c, false, entity -> {
                        LivingEntity e = (LivingEntity) entity;
                        e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(5000);
                        ItemUtil.setId(entity, "dummy");
                    })).setAI(false);
                }
                case "id" -> {
                    if (!CommandUtil.requirePlayer(sender)) {
                        break;
                    }
                    String id = ItemUtil.getId(((Player) sender).getInventory().getItemInMainHand().getItemMeta());
                    sender.sendMessage(id == null ? "你手持的不是AncientCraft的物品 !" : "id: " + id);
                }
                default -> CommandUtil.sendHelp(sender);
            }
        } else {
            CommandUtil.sendHelp(sender);
        }
        return true;
    }

    public static AncientCraft getInstance() {
        return instance;
    }

    public static String getVersion() {
        return version;
    }
}
