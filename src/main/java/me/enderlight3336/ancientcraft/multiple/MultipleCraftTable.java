package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Dropper;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class MultipleCraftTable extends MultipleBlock {
    public MultipleCraftTable() {
        super(Material.CRAFTING_TABLE);
    }

    @Override
    public void execute(PlayerInteractEvent event) {
        Inventory inv = ((Dropper) event.getClickedBlock().getRelative(BlockFace.DOWN).getState()).getInventory();
        event.getPlayer().sendMessage("success At: " + this.getClass().getName());//todo
    }

    @Override
    public boolean check(Block clicked) {
        return clicked.getRelative(BlockFace.DOWN).getType() == Material.DROPPER;
    }
}
