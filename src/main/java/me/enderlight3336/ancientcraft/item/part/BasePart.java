package me.enderlight3336.ancientcraft.item.part;

import me.enderlight3336.ancientcraft.item.base.ACItemStack;
import me.enderlight3336.ancientcraft.item.base.AdvancedTool;
import me.enderlight3336.ancientcraft.item.base.CommonTool;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;

public abstract class BasePart extends ACItemStack{
    public BasePart(String id, String name, Material material,int amount, String... lore) {
        super(id, name, material, amount, lore);
    }
    public void apply(CommonTool tool, int targetLevel) {
        tool.addAttribute(getAttribute(), getAmount(targetLevel));
    }
    public abstract boolean canApply(CommonTool tool);
    public Attribute getAttribute() {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }
    public int getAmount(int targetLevel) {
        throw new RuntimeException("This method is designed for REWRITE ONLY! Please rewrite this method or Method apply");
    }
}
