package me.enderlight3336.ancientcraft.item.ref;

import me.enderlight3336.ancientcraft.item.base.AttributeModifier;
import me.enderlight3336.ancientcraft.item.base.AdvancedTool;
import me.enderlight3336.ancientcraft.item.base.ItemRef;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StarRef {
    final List<ItemRef[]> starItem;
    final List<AttributeModifier[]> modifier;
    public StarRef() {
        starItem = new ArrayList<>();
        modifier = new ArrayList<>();
    }
    public final boolean addStar(@NotNull Player player, @NotNull AdvancedTool item) {
        ItemRef[] require = starItem.get(item.getStar());
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
    }

    public List<ItemRef[]> getStarItem() {
        return starItem;
    }

    public List<AttributeModifier[]> getModifier() {
        return modifier;
    }
}
