package me.enderlight3336.ancientcraft.item.sword;

import me.enderlight3336.ancientcraft.item.base.AdvancedTool;
import me.enderlight3336.ancientcraft.item.base.ItemRef;
import org.bukkit.Material;

import java.util.List;

public class Sword extends AdvancedTool {
    public Sword(Material material, String name, String id, List<ItemRef[]> list, String... lore) {
        super(material, name, id, list, lore);
    }

    @Override
    public void setStar(int target) {

    }
}
