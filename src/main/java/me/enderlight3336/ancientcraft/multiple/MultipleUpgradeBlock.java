package me.enderlight3336.ancientcraft.multiple;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.Datable;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.base.BasePart;
import me.enderlight3336.ancientcraft.util.LoreBuildService;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class MultipleUpgradeBlock<T extends ItemInstance & Datable<?>> extends MultipleBlock {
    public MultipleUpgradeBlock() {
        super(Material.ANVIL);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(PlayerInteractEvent event) {
        Dropper dropper = (Dropper) event.getClickedBlock().getRelative(BlockFace.DOWN).getState();
        BasePart part = null;
        T target = null;
        ItemStack partItem = null;
        ItemStack targetItem = null;
        for (ItemStack item : dropper.getInventory().getStorageContents()) {
            if (item != null) {
                ItemInstance instance1 = ItemManager.getItemInstance(item);
                if (instance1 != null) {
                    if (instance1 instanceof BasePart &&
                            part == null) {
                        part = (BasePart) instance1;
                        partItem = item;
                    } else if (instance1 instanceof Datable<?>) {
                        target = (T) instance1;
                        targetItem = item;
                    }
                }
            }
        }
        if (target != null && part != null) {
            ItemStack finalTargetItem = targetItem;
            BasePart finalPart = part;
            ItemStack finalPartItem = partItem;
            target.modifyItemData(targetItem, data -> {
                if (data instanceof LevelAndPartData
                        && finalPart.safeApply((LevelAndPartData) data, finalTargetItem)) {
                    finalPartItem.setAmount(finalPartItem.getAmount() - 1);
                    event.getPlayer().sendMessage("success!");
                    LoreBuildService.addPartBuildTask(finalTargetItem, (LevelAndPartData) data);
                } else {
                    event.getPlayer().sendMessage("无法添加模块");
                }
            });
        } else {
            event.getPlayer().sendMessage("failed!");
        }
    }

    @Override
    public boolean check(Block clicked) {
        return clicked.getRelative(BlockFace.DOWN).getType() == Material.DROPPER;
    }
}
