package me.enderlight3336.ancientcraft.multiple.impl;

import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.base.BasePart;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import me.enderlight3336.ancientcraft.multiple.MultipleBlock;
import me.enderlight3336.ancientcraft.util.AsyncLoreBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public final class MultipleUpgradeBlock extends MultipleBlock<MultipleBlock.CheckResult> {
    public MultipleUpgradeBlock() {
        super(Material.ANVIL);
    }

    @Override
    public void execute(PlayerInteractEvent event, MultipleBlock.CheckResult ignore) {
        Dropper dropper = (Dropper) event.getClickedBlock().getRelative(BlockFace.DOWN).getState();
        ItemStack partItem = null;
        BasePart part = null;
        ItemStack itemWillBeApply = null;
        ItemDatable<?> targetItemInstance = null;

        for (ItemStack item : dropper.getInventory().getStorageContents()) {
            if (item != null) {
                if (item.getItemMeta() != null) {
                    ItemInstance instance = ItemManager.getItemInstance(item);
                    if (instance != null) {
                        if (instance instanceof BasePart &&
                                part == null) {
                            //find part that need apply
                            part = (BasePart) instance;
                            partItem = item;
                        } else if (instance instanceof ItemDatable<?> &&
                                targetItemInstance == null) {
                            //find item that will be applied
                            targetItemInstance = (ItemDatable<?>) instance;
                            itemWillBeApply = item;
                        }
                    }
                }
            }
        }
        if (targetItemInstance != null && part != null) {
            ItemStack finalItemWillBeApply = itemWillBeApply;
            BasePart finalPart = part;
            ItemStack finalPartItem = partItem;
            ItemDatable<?> finalTargetItemInstance = targetItemInstance;

            targetItemInstance.modifyItemData(itemWillBeApply, data -> {
                BasePart.ApplyResult result = finalPart.apply((LevelAndPartData) data, finalTargetItemInstance, finalItemWillBeApply);
                if (result == BasePart.ApplyResult.SUCCESS) {
                    finalPartItem.setAmount(finalPartItem.getAmount() - 1);
                    AsyncLoreBuilder.addPartBuildTask(finalItemWillBeApply, (LevelAndPartData) data);
                }
                event.getPlayer().sendMessage(result.getMessage());
            });
        } else {
            event.getPlayer().sendMessage("操作失败!");
        }
    }

    @Override
    public CheckResult check(Block clicked) {
        return new CheckResult(clicked.getRelative(BlockFace.DOWN).getType() == Material.DROPPER);
    }
}
