package me.enderlight3336.ancientcraft.loot.calculate;

import me.enderlight3336.ancientcraft.loot.CustomLootTableManager;

public final class CommonCalc implements AmountCalculator {
    private final int amount;
    private final float chance;
    private final float chancePerLooting;
    private final float multiple;

    public CommonCalc(int amount, float chance, float chancePerLooting, float multiple) {
        if (chance > 1 || chance < 0)
            throw new IllegalArgumentException("Chance must be [0 - 1]");
        this.amount = amount;
        this.chance = chance;
        this.chancePerLooting = chancePerLooting;
        this.multiple = multiple;
    }

    @Override
    public int calculate(int lootingLevel) {
        return (chance + chancePerLooting * lootingLevel) > CustomLootTableManager.RANDOM.nextFloat() ?
                amount + (int) (lootingLevel * multiple) : 0;
    }
}
