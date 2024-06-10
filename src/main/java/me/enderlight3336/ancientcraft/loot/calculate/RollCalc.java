package me.enderlight3336.ancientcraft.loot.calculate;

import me.enderlight3336.ancientcraft.loot.CustomLootTableManager;

public final class RollCalc implements AmountCalculator {
    private final Pack[] pack;

    public RollCalc(float[] fa, int[] ia) {
        if (fa.length != ia.length)
            throw new IllegalArgumentException("Unexpected arguments! Require two arrays' length equal, but length is " + fa.length + " and " + ia.length);
        pack = new Pack[fa.length];
        for (int i = 0; i < fa.length - 1; i++)
            pack[i] = new Pack(fa[i], ia[i]);
    }

    @Override
    public int calculate(int lootingLevel) {
        float f = CustomLootTableManager.RANDOM.nextFloat() - pack[0].f;
        int index = 0;
        while (f > 0)
            f = f - pack[index++].f;
        return pack[index].amount;
    }

    public static final class Pack {
        final float f;
        final int amount;

        public Pack(float f1, int amount) {
            this.f = f1;
            this.amount = amount;
        }
    }
}
