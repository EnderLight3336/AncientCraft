package me.enderlight3336.ancientcraft.item.consumer;

public final class Quiver implements ACInventoryHolder {
    private final List arrows;
    public Quiver() {
    }
    public Quiver(JSONObject json) {
        arrowMap = new 
    }
    public static final class Entry{
        int amount;
        final ItemStack arrow;
        public Entry(int amount, ItemStack item) {
            this.amount = amount;
            this.arrow = item;
        }
    }
}