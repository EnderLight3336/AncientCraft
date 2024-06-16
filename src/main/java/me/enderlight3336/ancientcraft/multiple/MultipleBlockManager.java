package me.enderlight3336.ancientcraft.multiple;

import me.enderlight3336.ancientcraft.multiple.impl.MultipleCraftTable;
import me.enderlight3336.ancientcraft.multiple.impl.MultipleUpgradeBlock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MultipleBlockManager {
    private static final Map<Material, List<MultipleBlock<?>>> mb = new HashMap<>();

    public static void init() {
        reg(new MultipleCraftTable());
        reg(new MultipleUpgradeBlock());
    }

    public static void reg(MultipleBlock<?> b) {
        Material material = b.getCore();
        if (mb.containsKey(material)) {
            mb.get(material).add(b);
        } else {
            List<MultipleBlock<?>> list = new ArrayList<>();
            list.add(b);
            mb.put(material, list);
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean onInteract(PlayerInteractEvent event) {
        Block clicked = event.getClickedBlock();
        List<MultipleBlock<?>> list = mb.get(clicked.getType());
        if (list != null) {
            for (MultipleBlock multipleBlock : list) {
                MultipleBlock.ICheckResult result = multipleBlock.check(clicked);
                if (result.getValue()) {
                    multipleBlock.execute(event, result);
                    return true;
                }
            }
        }
        return false;
    }

    public static void reload() {
        mb.clear();
        init();
    }
}
