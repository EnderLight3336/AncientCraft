package me.enderlight3336.ancientcraft.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SyncBlockChangeTask extends BukkitRunnable {
    private static List<Entry> list = new ArrayList<>();
    public static void put(Block block, Material material) {
        list.add(new Entry(block, material));
    }
    public static void execute() {
        List<Entry> copy = list;
        list = new ArrayList<>();
        copy.forEach(entry -> entry.block.setType(entry.material));
    }

    @Override
    public void run() {
        execute();
    }

    public final static class Entry {
        final Block block;
        final Material material;
        public Entry(Block block, Material material) {
            this.block = block;
            this.material = material;
        }
    }
}
