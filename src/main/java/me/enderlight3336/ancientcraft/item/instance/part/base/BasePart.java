package me.enderlight3336.ancientcraft.item.instance.part.base;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.LevelAndPartData;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class BasePart extends ItemInstance {
    protected final String partName;
    protected final int costSlot;
    protected final String[] applyType;

    public BasePart(JSONObject json) {
        super(json);

        partName = json.getString("partName");
        costSlot = json.getIntValue("costSlot", 1);
        applyType = json.getJSONArray("applyType").toArray(String.class);
    }

    /**
     * @throws IndexOutOfBoundsException If you don't check part's max level
     */
    protected abstract void unsafeApply(ItemStack target, int currentPartLevel);

    public abstract boolean isMaxLevel(int partLevel);

    public <T extends LevelAndPartData> ApplyResult apply(@NotNull T data, ItemDatable<?> instance, ItemStack target) {
        int currentLevel = data.getPartLevel(getId());
        if (isMaxLevel(currentLevel)) {
            return ApplyResult.PART_LEVEL_MAXED;
        } else {
            boolean not_suit = true;
            for (String type : applyType) {
                if (instance.checkType(type)) {
                    not_suit = false;
                    break;
                }
            }
            if (not_suit) {
                return ApplyResult.NOT_SUIT;
            } else if (data.getFreeSlot() < costSlot) {
                return ApplyResult.PART_SLOT_RAN_OUT;
            } else {
                data.addPart(getId());
                unsafeApply(target, currentLevel);
                return ApplyResult.SUCCESS;
            }
        }
    }

    public String getPartName() {
        return this.partName;
    }

    public int getCostSlot() {
        return costSlot;
    }

    public enum ApplyResult {
        SUCCESS("成功添加模块"),
        NOT_SUIT("该模块不适用于目标物品"),
        PART_SLOT_RAN_OUT("目标物品模块槽位已用尽"),
        PART_LEVEL_MAXED("目标物品的该模块等级已满"),
        UNKNOWN("未知错误");

        ApplyResult(String message) {
            this.message = message;
        }

        private final String message;

        public String getMessage() {
            return message;
        }
    }
}
