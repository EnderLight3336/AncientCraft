package me.enderlight3336.ancientcraft.multiple.impl;

import me.enderlight3336.ancientcraft.multiple.MultipleBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class MultipleCraftTable extends MultipleBlock<MultipleCraftTable.CheckResult> {
    public MultipleCraftTable() {
        super(Material.CRAFTING_TABLE);
    }

    @Override
    public void execute(PlayerInteractEvent event, CheckResult result) {
    }

    @Override
    public CheckResult check(Block clicked) {
        if (clicked.getRelative(BlockFace.DOWN).getType() == Material.DROPPER) {
            if (clicked.getRelative(BlockFace.NORTH).getType() == Material.CHEST) {
                return new CheckResult(((Chest) clicked.getRelative(BlockFace.NORTH).getState()).getInventory());
            } else if (clicked.getRelative(BlockFace.SOUTH).getType() == Material.CHEST) {
                return new CheckResult(((Chest) clicked.getRelative(BlockFace.SOUTH).getState()).getInventory());
            } else if (clicked.getRelative(BlockFace.WEST).getType() == Material.CHEST) {
                return new CheckResult(((Chest) clicked.getRelative(BlockFace.WEST).getState()).getInventory());
            } else if (clicked.getRelative(BlockFace.EAST).getType() == Material.CHEST) {
                return new CheckResult(((Chest) clicked.getRelative(BlockFace.EAST).getState()).getInventory());
            }
        }
        return new CheckResult();
    }

    public static class CheckResult extends MultipleBlock.CheckResult {
        final Inventory output;

        public CheckResult() {
            super(false);

            output = null;
        }

        public CheckResult(Inventory inv) {
            super(true);

            this.output = inv;
        }
    }
}
