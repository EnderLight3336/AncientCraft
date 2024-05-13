package me.enderlight3336.ancientcraft;

import me.enderlight3336.ancientcraft.item.Util;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemNBTUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class AncientCraft extends JavaPlugin {
    private static AncientCraft instance;

    @Override
    public void onEnable() {
        instance = this;
        try {
            FileUtil.init();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        ConfigInstance.init();
        L18N.init();
        Util.init();
        ItemNBTUtil.init();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
    public static AncientCraft getInstance() {
        return instance;
    }
}
