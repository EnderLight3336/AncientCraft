package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class MultipleCraftTable extends MultipleBlock {
    public MultipleCraftTable() {
        super(Material.);
    }

    @Override
    public boolean execute(PlayerInteractEvent event) {
        return false;
    }
}
