package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public class MultipleCraftTable extends MultipleBlock {
    public MultipleCraftTable(Material material) {
        super(material);
    }

    @Override
    public boolean execute(PlayerInteractEvent event) {
        return false;
    }
}
