package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PartFarmhand extends AbilityPart {
    private final Map<String, PartEventAcceptor<?>> handlers = Map.of(BlockBreakEvent.class.getSimpleName(), new PartEventAcceptor<BlockBreakEvent>() {
        private static final Map<UUID, Location> map = new HashMap<>();

        @Override
        public void execute(BlockBreakEvent event, int partLevel) {
            Block block = event.getBlock();
            Player player = event.getPlayer();
            if (!player.isSneaking() &&
                    block.getBlockData() instanceof Ageable &&
                    !block.getLocation().equals(map.get(player.getUniqueId()))) {
                handle(block, player, BlockFace.NORTH);
                handle(block, player, BlockFace.SOUTH);
                handle(block, player, BlockFace.EAST);
                handle(block, player, BlockFace.WEST);
                handle(block, player, BlockFace.NORTH_EAST);
                handle(block, player, BlockFace.NORTH_WEST);
                handle(block, player, BlockFace.SOUTH_EAST);
                handle(block, player, BlockFace.SOUTH_WEST);
            }
        }

        private static void handle(Block block, Player player, BlockFace face) {
            if (block.getRelative(face).getBlockData() instanceof Ageable age &&
                    age.getAge() == age.getMaximumAge()) {
                map.put(player.getUniqueId(), block.getRelative(face).getLocation());
                player.breakBlock(block.getRelative(face));
                map.remove(player.getUniqueId());
            }
        }

        @Override
        public String getId() {
            return id;
        }
    });

    public PartFarmhand(JSONObject object) {
        super(object);
    }

    @Override
    public Map<String, PartEventAcceptor<?>> getEventHandlers() {
        return handlers;
    }

    @Override
    public boolean isMaxLevel(int partLevel) {
        return partLevel == 1;
    }

    public static final class SpecialBlockBreakEvent extends BlockBreakEvent {
        public SpecialBlockBreakEvent(@NotNull Block theBlock, @NotNull Player player) {
            super(theBlock, player);
        }
    }
}
