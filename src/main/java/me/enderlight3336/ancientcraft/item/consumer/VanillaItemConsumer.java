package me.enderlight3336.ancientcraft.item.consumer;

public class VanillaItemConsumer implements ItemConsumer {
    protected int amount;
    protected final Material material;
    public VanillaItemConsumer(int amount, Material material) {
        this.amount = amount;
        this.material = material;
    }
    @Override
    public void accept(ItemStack item) {
        if (amount != 0) {}
    }
    @Override
    public boolean isComplete() {
        return amount == 0;
    }
    @Override
    public VanillaItemConsumer clone() {
        return new VanillaItemConsumer(amount, material);
    }
}