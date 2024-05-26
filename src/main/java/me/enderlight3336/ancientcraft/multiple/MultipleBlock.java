package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class MultipleBlock {
    public MultipleBlock(Material material) {
        MultipleBlockManager.reg(this, material);
    }

    public abstract boolean execute(PlayerInteractEvent event);
}