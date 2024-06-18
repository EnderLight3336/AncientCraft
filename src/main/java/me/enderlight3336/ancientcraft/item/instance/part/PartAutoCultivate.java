package me.enderlight3336.ancientcraft.item.instance.part;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import me.enderlight3336.ancientcraft.util.SyncBlockChangeTask;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.Map;

public final class PartAutoCultivate extends AbilityPart {
    private final Map<String, PartEventAcceptor<?>> handlers = Map.of(BlockBreakEvent.class.getSimpleName(), new PartEventAcceptor<BlockBreakEvent>() {
        @Override
        public void execute(BlockBreakEvent event, int partLevel) {
            if (event.getBlock().getBlockData() instanceof Ageable) {
                SyncBlockChangeTask.put(event.getBlock(), event.getBlock().getType());
            }//todo :need check
        }

        @Override
        public String getId() {
            return id;
        }
    });
    public PartAutoCultivate(JSONObject object) {
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
}
