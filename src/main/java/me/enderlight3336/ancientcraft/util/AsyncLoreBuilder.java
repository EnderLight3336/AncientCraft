package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AsyncLoreBuilder extends BukkitRunnable {
    private static Map<ItemStack, ItemData> re_build = new HashMap<>();
    private static Map<ItemStack, ItemData> exp_build = new HashMap<>();
    private static Map<ItemStack, ItemData> level_build = new HashMap<>();
    private static Map<ItemStack, ItemData> part_build = new HashMap<>();

    @Override
    public void run() {
        execute();
    }

    public static void execute() {
        Set<ItemStack> re;
        if (re_build.size() != 0) {
            re = re_build.keySet();
            Map<ItemStack, ItemData> copy = new HashMap<>(re_build);
            re_build = new HashMap<>();
            copy.forEach((item, data) -> {
                ItemMeta im = item.getItemMeta();
                im.setLore(data.rebuildLore(ItemManager.getItemInstance(item).getLore()));
                item.setItemMeta(im);
            });
        } else {
            re = null;
        }
        Set<ItemStack> level;
        if (level_build.size() != 0) {
            Map<ItemStack, ItemData> copy = new HashMap<>(level_build);
            level_build = new HashMap<>();
            if (re != null) {
                re.forEach(copy::remove);
            }
            level = copy.keySet();
            copy.forEach((item, data) -> {
                ItemMeta im = item.getItemMeta();
                im.setLore(((LevelAndPartData) data).loreChangeOnLevel(im.getLore()));
                item.setItemMeta(im);
            });
        } else {
            level = null;
        }
        if (exp_build.size() != 0) {
            Map<ItemStack, ItemData> copy = new HashMap<>(exp_build);
            exp_build = new HashMap<>();
            if (re != null) {
                re.forEach(copy::remove);
            }
            if (level != null) {
                level.forEach(copy::remove);
            }
            copy.forEach((item, data) -> {
                ItemMeta im = item.getItemMeta();
                im.setLore(((LevelAndPartData) data).loreChangeOnExp(im.getLore()));
                item.setItemMeta(im);
            });
        }
        if (part_build.size() != 0) {
            Map<ItemStack, ItemData> copy = new HashMap<>(part_build);
            part_build = new HashMap<>();
            if (re != null) {
                re.forEach(copy::remove);
            }
            copy.forEach((item, data) -> {
                ItemMeta im = item.getItemMeta();
                im.setLore(((LevelAndPartData) data).loreChangeOnPart(im.getLore()));
                item.setItemMeta(im);
            });
        }
    }

    public static void addReBuildTask(ItemStack item, ItemData data) {
        if (!re_build.containsKey(item))
            re_build.put(item, data);
    }

    public static void addExpBuildTask(ItemStack item, LevelAndPartData data) {
        if (!exp_build.containsKey(item))
            exp_build.put(item, data);
    }

    public static void addLevelBuildTask(ItemStack item, LevelAndPartData data) {
        if (!level_build.containsKey(item))
            level_build.put(item, data);
    }

    public static void addPartBuildTask(ItemStack item, LevelAndPartData data) {
        if (!part_build.containsKey(item))
            part_build.put(item, data);
    }
}
