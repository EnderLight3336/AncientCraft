package me.enderlight3336.ancientcraft.util;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AsyncDataSaver extends BukkitRunnable {
    private static Map<String, List<Entry>> itemDataMap = new HashMap<>();

    @Override
    public void run() {
        execute();
    }

    public static void put(String id, int index, ItemData data) {
        itemDataMap.computeIfAbsent(id, k -> new ArrayList<>()).add(new Entry(index, data));
    }

    public static void putItemData(String id, Entry entry) {
        itemDataMap.computeIfAbsent(id, k -> new ArrayList<>()).add(entry);
    }

    public static void execute() {
        if (itemDataMap.size() == 0)
            return;
        Map<String, List<Entry>> todo = itemDataMap;
        itemDataMap = new HashMap<>();
        todo.forEach((str, list) -> {
            File root = FileUtil.getItemDataFolder(str);
            list.forEach(entry -> {
                try (FileWriter fw = new FileWriter(new File(root, String.valueOf(entry.index)))) {
                    fw.write(entry.data.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
        AncientCraft.getInstance().getLogger().info("DataSaver has finished saving data!");
    }

    public static final class Entry {
        private final int index;
        private final ItemData data;

        public Entry(int i, ItemData d) {
            this.index = i;
            this.data = d;
        }

        public int getIndex() {
            return index;
        }

        public ItemData getData() {
            return data;
        }
    }
}
