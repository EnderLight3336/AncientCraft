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

public final class Brain extends ACInventoryHolderImpl {
    public static final IDyncmicItemFactory BRAIN_ITEM = new IDyncmicItemFactory() {
        ItemStack item1 = ItemUtil.createHead("", "", "");
        public ItemStack get(int i) {
            return switch(i) {
                case 1 -> item1
                default -> null
            };
        }
    };
    public static final ItemStack SACK_ITEM = ItemUtil.createHead("");
    public static final ItemStack QUIVER_ITEM = ItemUtil.createHead()
    private int level;
    private final Sack sack;

    public Brain(UUID uuid) {
        super(54, new int[]{12, 37}, event -> {
            event.getWhoClicked().getInventory().getStorageContents();
            return false;
        }, event -> {
            new BukkitRunnable() {
                @Override
                public void run() {}
            }.runTaskLater(AncientCraft.getInstance(), 1L);
            return false;
        });

        File brainFile = new File(FileUtil.PLAYER_DATA_FOLDER, uuid.toString());
        File sackFile = new File(FileUtil.SACK_DATA_FOLDER, uuid.toString());
        if (brainFile.exists()) {
            JSONObject json = FileUtil.getJSONByFile(brainFile);
            this.level = json.getIntValue("level", 0);
        } else {
            level = 0;
        }
        this.sack = sackFile.exists() ? new Sack(FileUtil.getJSONByFile(sackFile)) : new Sack();
        inventory.setItem(37, SACK_ITEM);
        inventory.setItem(12, BRAIN_ITEM.get(level));
    }

    public int getLevel() {
        return this.level;
    }
    public void levelUp() {
        inventory.setItem(12, BRAIN_ITEM.get(
            ++this.level));
    }

    public Sack getSack() {
        return sack;
    }
}
