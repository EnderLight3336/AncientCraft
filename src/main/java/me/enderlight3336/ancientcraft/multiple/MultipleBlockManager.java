package me.enderlight3336.ancientcraft.multiple;

import me.enderlight3336.ancientcraft.listener.core.SpecialEventListener;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public final class MultipleBlockManager {
    private static final Map<Material, List<MultipleBlock>> mb = new HashMap<>();

    public static void init() {
        new SpecialEventListener<>(PlayerInteractEvent.class, true) {
            @Override
            public void accept(PlayerInteractEvent event) {
                if (event.getAction() == RIGHT_CLICK_BLOCK) {
                    Block clicked = event.getClickedBlock();
                    if (clicked != null) {
                        List<MultipleBlock> list = mb.get(clicked.getType());
                        if (list != null) {
                            for (MultipleBlock multipleBlock : list) {
                                if (multipleBlock.check(clicked)) {
                                    event.setCancelled(true);
                                    multipleBlock.execute(event);
                                }
                            }
                        }
                    }
                }
            }
        };

        reg(new MultipleCraftTable());
    }

    public static void reg(MultipleBlock b) {
        Material material = b.getCore();
        if (mb.containsKey(material)) {
            mb.get(material).add(b);
        } else {
            mb.put(material, new ArrayList<>());
            mb.get(material).add(b);
        }
    }
}
