package me.enderlight3336.ancientcraft.brain;

import com.alibaba.fastjson2.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BrainManager {
    private static final Map<UUID, Brain> brainMap = new HashMap<>();
    public static void load(UUID uuid) {
        brainMap.put(uuid, new Brain(uuid));
    }
    public static void unload(UUID uuid) {
        Brain brain = brainMap.get(uuid);
        if (brain == null)
            throw new NullPointerException("Cannot get brain instance by UUID:" + uuid.toString());
    }
    public static Brain getBrain(UUID uuid) {
        return brainMap.get(uuid);
    }
}
