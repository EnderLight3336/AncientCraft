package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class MultipleBlock {
    final Material core;

    public MultipleBlock(Material material) {
        this.core = material;
    }

    public abstract void execute(PlayerInteractEvent event);

    public abstract boolean check(Block clicked);

    public final Material getCore() {
        return core;
    }
}