package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class BasePart extends ItemInstance {
    protected final String partName;

    public BasePart(JSONObject json) {
        super(json);

        partName = json.getString("partName");
    }

    public abstract void apply(ItemStack target, int currentPartLevel);

    public abstract boolean canApply(int current);

    public <T extends LevelAndPartData> boolean safeApply(@NotNull T data, ItemStack target) {
        int currentLevel = data.getPartLevel(getId());
        if (canApply(currentLevel)) {
            data.addPart(getId());
            apply(target, currentLevel);
            return true;
        }
        return false;
    }

    public String getPartName() {
        return this.partName;
    }
}
