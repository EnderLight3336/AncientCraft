package me.enderlight3336.ancientcraft.item.base;

import me.enderlight3336.ancientcraft.item.part.PartEnum;
import org.bukkit.inventory.Inventory;

import java.util.Map;

public interface Partable {
    Map<PartEnum, Integer> getParts();
    Inventory getPartMenu();
    int getMaxPartSlot();
    /**
      @return how many parts will be given to player
     */
    int removePart(PartEnum part);
}
