package me.enderlight3336.ancientcraft.brain;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.inventory.ACInventoryHolderImpl;
import me.enderlight3336.ancientcraft.item.IDynamicItemFactory;
import me.enderlight3336.ancientcraft.metadata.Sack;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Brain extends ACInventoryHolderImpl {
    public static final IDynamicItemFactory BRAIN_ITEM = new IDynamicItemFactory() {
        final ItemStack[] item = new ItemStack[]{
                ItemUtil.createItem(Material.GOLD_NUGGET, "萎缩的大脑", false, "僵尸看了都得给你留个大脑"),
                ItemUtil.createItem(Material.GOLD_INGOT, "迟钝的大脑", false, "蜗牛的反应速度都比你快"),
                ItemUtil.createItem(Material.DIAMOND, "普通的大脑", false, "普普通通的大脑，至少僵尸不会嫌弃了"),
                ItemUtil.createItem(Material.EMERALD, "聪慧的大脑", false, "你的大脑打败了99%的人 (也许?)"),
                ItemUtil.createHead("", "聪明绝顶的大脑", "反应速度堪比量子计算机"),
                ItemUtil.createHead("", "超级大脑", "反应速度堪比光子计算机")
        };
        public ItemStack get(int i) {
            return item[i];
        }
    };
    public static final ItemStack SACK_ITEM = ItemUtil.createHead("", "麻袋", "可以装很多原版物品", "左键打开", "右键一键收取玩家背包内物品");
    public static final IDynamicItemFactory QUIVER_ITEM = new IDynamicItemFactory() {
        final ItemStack item = ItemUtil.createHead("", "箭袋");
        final List<String> lore = new ArrayList<>(3) {{add("可以装很多箭");add("");}};
        @Override
        public ItemStack get(int i) {
            ItemStack ret = new ItemStack(item);
            ItemMeta im = ret.getItemMeta();
            im.setLore(new ArrayList<>(lore){{set(3, "当前有" + i + "根箭");}});
            ret.setItemMeta(im);
            return ret;
        }
    };
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
