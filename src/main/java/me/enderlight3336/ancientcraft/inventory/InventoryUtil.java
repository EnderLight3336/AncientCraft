package me.enderlight3336.ancientcraft.inventory;

public final class InventoryUtil {
    private static final NamespacedKey actionId =
            new NamespacedKey(AncientCraft.getInstance(), "ac_invact");
    public static void setId(byte id, ItemMeta im) {
        im.getPersistentDataContainer().set(actionId, PersistentDataType.BYTE, id);
    }
    public static byte getId(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer.get(actionId, PersistentDataType.BYTE);
    }
}