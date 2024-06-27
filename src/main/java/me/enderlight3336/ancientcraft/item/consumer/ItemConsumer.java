package me.enderlight3336.ancientcraft.item.consumer;

public interface ItemConsumer extends Cloneable {
    void accept(ItemStack item);
    boolean isComplete();
}