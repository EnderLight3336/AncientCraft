package me.enderlight3336.ancientcraft.item.consumer;

public abstract class AbstractItemConsumer implements ItemConsumer {
    int amount;
    @Override
    public final boolean isComplete() {
        return amount == 0;
    }
    public final void handle(ItemStack item) {}
}