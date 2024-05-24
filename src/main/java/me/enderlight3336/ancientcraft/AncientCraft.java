package me.enderlight3336.ancientcraft;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import me.enderlight3336.ancientcraft.item.Util;
import me.enderlight3336.ancientcraft.item.instance.Items;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.L18N;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;

public final class AncientCraft extends JavaPlugin {
    private static AncientCraft instance;
    private static String version;
    @Override
    public void onEnable() {
        instance = this;
        FastJsonConfig fj = new FastJsonConfig();
        fj.setSerializerFeatures(SerializerFeature.PrettyFormat);
        try {
            FileUtil.init();
            ConfigInstance.init();
            L18N.init();
            Util.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        version = YamlConfiguration.loadConfiguration(new InputStreamReader(instance.getResource("plugin.yml"))).getString("version");
        getLogger().info("========AncientCraft========");
        getLogger().info("  version: " + version);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "version" -> sender.sendMessage("========AncientCraft========", "    version: " + AncientCraft.getVersion());
                case "updateitem" -> {}
                case "give" -> {
                    if(sender instanceof Player) {
                        ((Player) sender).getInventory().addItem(Util.createItem(Items.valueOf(args[1]).getInstance()));
                    }
                }
                case "dummy" -> {
                    if(!CommandUtil.isPlayer(sender)) {
                        return true;
                    }
                    ((Player) sender).getWorld().spawnEntity(((Player) sender).getLocation(), EntityType)
                }
                default -> {}
            }
        } else {}
        return true;
    }

    public static AncientCraft getInstance() {
        return instance;
    }
    public static String getVersion() {
        return version;
    }
}
