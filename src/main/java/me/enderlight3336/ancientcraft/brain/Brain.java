package me.enderlight3336.ancientcraft.brain;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.inventory.ACInventoryHolderImpl;
import me.enderlight3336.ancientcraft.metadata.Sack;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public final class Brain {
    public static final ItemStack BRAIN_ITEM = ItemUtil.createHead("");
    public static final ItemStack SACK_ITEM = ItemUtil.createHead("");
    private int level;
    private final Sack sack;
    private final ACInventoryHolderImpl ui = new ACInventoryHolderImpl(new ItemStack[]{
            null, null, null, null, null, null, null, null, null,
            null, null, null, null, BRAIN_ITEM, null, null, null, null,
            null, null, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null,
            null, SACK_ITEM, null, null, null, null, null, null, null,
            null, null, null, null, null, null, null, null, null
    }, new int[]{13, 37}, (event -> {
        event.getWhoClicked().getInventory().getStorageContents()
        return false;
    }), event -> {
        new BukkitRunnable() {
            @Override
            public void run() {
                event.getWhoClicked().openInventory();
            }
        }.runTaskLater(AncientCraft.getInstance(), 1L);
        return false;
    });

    public Brain(UUID uuid) {
        File brainFile = new File(FileUtil.PLAYER_DATA_FOLDER, uuid.toString());
        File sackFile = new File(FileUtil.SACK_DATA_FOLDER, uuid.toString());
        if (brainFile.exists()) {
            JSONObject json = FileUtil.getJSONByFile(brainFile);
            this.level = json.getIntValue("level", 0);
        } else {
            level = 0;
        }
        this.sack = sackFile.exists() ? new Sack(FileUtil.getJSONByFile(sackFile)) : new Sack();
    }

    public int getLevel() {
        return this.level;
    }
    public void levelUp() {
        this.level++;
    }

    public Sack getSack() {
        return sack;
    }
}
