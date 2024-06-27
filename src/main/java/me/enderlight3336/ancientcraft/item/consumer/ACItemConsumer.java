package me.enderlight3336.ancientcraft.item.consumer;

public class ACItemConsumer implements ItemConsumer {
    int amount;
    final String id;
    public ACItemConsumer(String id, int amount) {
        this.id = id;
        this.amount = amount;
    }
    @Override
    public void accept(ItemStack item) {
        if (amount != 0) {
            ItemInstance instance = ItemManager.getItemInstance(item);
            if (instance != null &&
                instance.getId().equals(id)) {
                    int a = item.getAmount();
                    if (a >= amount) {
                        item.setAmount(a - amount);
                        amount = 0;
                    } else {
                        item.setAmount(0);
                        amount -= a;
                    }
                }
        }
    }
    @Override
    public boolean isComplete() {
        return amount == 0;
    }
    @Override
    public ACItemConsumer clone() {
        return new ACItemConsumer(id, amount);
    }
}