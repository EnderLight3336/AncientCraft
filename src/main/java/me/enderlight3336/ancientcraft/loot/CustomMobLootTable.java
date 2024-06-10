package me.enderlight3336.ancientcraft.loot;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.loot.calculate.AmountCalculator;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomMobLootTable {
    protected final Map<ItemInstance, AmountCalculator> lootTable = new HashMap<>();

    @NotNull
    public List<ItemStack> populateLoot(int lootingLevel) {
        List<ItemStack> loot = new ArrayList<>();
        lootTable.forEach((instance, calculator) -> loot.add(instance.createItem(calculator.calculate(lootingLevel))));
        return loot;
    }

    public void put(ItemInstance instance, AmountCalculator calculator) {
        lootTable.put(instance, calculator);
    }
}
