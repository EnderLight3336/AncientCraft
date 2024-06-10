package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class MultipleBlock<T extends MultipleBlock.ICheckResult> {
    final Material core;

    public MultipleBlock(Material material) {
        this.core = material;
    }

    public abstract void execute(PlayerInteractEvent event, T result);

    public abstract T check(Block clicked);

    public final Material getCore() {
        return core;
    }

    public static class CheckResult implements ICheckResult {
        final boolean value;

        public CheckResult(boolean b) {
            value = b;
        }

        @Override
        public final boolean getValue() {
            return value;
        }
    }

    public interface ICheckResult {
        boolean getValue();
    }
}