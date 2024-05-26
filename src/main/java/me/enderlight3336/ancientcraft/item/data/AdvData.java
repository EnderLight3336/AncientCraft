package me.enderlight3336.ancientcraft.item.data;

import org.bukkit.inventory.ItemStack;

public class AdvData extends LevelData implements StarData {
    int star;

    public AdvData(ItemStack item) {
        super(item);
    }

    @Override
    public void addStar() {
        star++;
    }

    @Override
    public void setStar(int i) {
        this.star = i;
    }

    @Override
    public int getStar() {
        return star;
    }
}
