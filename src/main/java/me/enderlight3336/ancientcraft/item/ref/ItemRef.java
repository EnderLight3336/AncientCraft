package me.enderlight3336.ancientcraft.item.ref;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.PartEnum;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemRef {
    public static ItemInstance instance;
    public List<PartEnum> parts;
    public int star = 0;
    public ItemRef() {}
    public ItemInstance getInstance() {
        return instance;
    }
    public ItemStack getItemStack() {
        return null;//todo
    }
    /**
    public final boolean addStar(@NotNull Player player) {
        ItemRef[] require = starItem.get(star);
        ItemStack[] inv = player.getInventory().getStorageContents();
        for (ItemRef itemRef : require) {
            int amount = itemRef.getAmount();
            for(int i = 1; i <= inv.length; i = i + 1) {
                ItemStack item1 = inv[i];
                if(item1.getType() == itemRef.getMaterial()) {
                    int a = item1.getAmount();
                    if(a > amount) {
                        item1.setAmount(a - amount);
                    } else if (a == amount) {
                        inv[i] = null;
                    } else {
                        inv[i] = null;
                        amount = amount - a;
                    }
                }
            }
            if(amount > 0) {
                return false;
            }
        }
        player.getInventory().setStorageContents(inv);
        item.setStar((short) (item.getStar() + 1));
        return true;
    }*/
}
