package me.enderlight3336.ancientcraft.item.consumer;

public final class CustomItemConsumer implements ItemConsumer {
    int amount;
    final Predicate<ItemStack> func;
    public CustomItemConsumer(int amount, Predicate<ItemStack> func) {
        this.amount = amount;
        this.func = func;
    }
    public void accept(ItemStack item) {
        if (amount && func.test(item)) {}
    }
}