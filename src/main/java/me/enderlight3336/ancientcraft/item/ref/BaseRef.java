package me.enderlight3336.ancientcraft.item.ref;

import java.util.ArrayList;
import java.util.List;

public class BaseRef {
    final List<Integer> requireExp;

    public BaseRef() {
        requireExp = new ArrayList<>();
    }

    public int handleLevelExp(int currentLevel, int calculatedExp) {
        int nextExp = requireExp.get(currentLevel - 1);
        if(nextExp < calculatedExp)
            return calculatedExp - nextExp;
        return -1;
    }

    public List<Integer> getRequireExp() {
        return requireExp;
    }
}
