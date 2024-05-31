package me.enderlight3336.ancientcraft.multiple;

import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MultipleBlockManager {
    private static final Map<Material, List<MultipleBlock>> mb = new HashMap<>();

    public static void reg(MultipleBlock b, Material material) {
        if (mb.containsKey(material)) {
            mb.get(material).add(b);
        } else {
            mb.put(material, new ArrayList<>());
            mb.get(material).add(b);
        }
    }

    public static void accept(@NotNull PlayerInteractEvent event) {
        if(!event.isCancelled()) {
            if(event.getAction() == RIGHT_CLICK_BLOCK)
        }
        List<MultipleBlock> m = mb.get((event.getClickedBlock()).getType());
        if (m != null) {
            event.setCancelled(true);
            for (MultipleBlock block : m) {
                if (block.execute(event))
                    break;
            }
        }
    }
}
