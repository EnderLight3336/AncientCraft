package me.enderlight3336.ancientcraft.item.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RequireItem {
    private final Material material;
    private final int amount;
    private final List<String> tags;

    public RequireItem(Material material, int amount, List<String> tags) {
        this.material = material;
        this.amount = amount;
        this.tags = tags;
    }

    public int getAmount() {
        return amount;
    }
    public int accept(ItemStack item, int a) {
        if(item.getType() == material) {
            if(tags.size() > 0) {
                for(String s : tags) {
                    //todo
                }
                if(item.getAmount() >= a) {
                    item.setAmount(item.getAmount() - a);
                    return 0;
                } else {
                    int i = item.getAmount();
                    item.setAmount(0);
                    return a - i;
                }
            }
        }
        return a;
    }
}
