package me.enderlight3336.ancientcraft.inventory;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ACInventoryHolderImpl implements ACInventoryHolder {
    protected final Int2ObjectMap<Predicate<InventoryClickEvent>> handlerMap;
    protected final Inventory inventory;
    @SafeVarargs
    public ACInventoryHolderImpl(@Nullable ItemStack[] items, int[] boundSlots, @NotNull Predicate<InventoryClickEvent>... handlers) {
        this(items.length, boundSlots, handlers);
        inventory.setStorageContents(items);
    }
    @SafeVarargs
    public ACInventoryHolderImpl(int size, int[] boundSlots, @NotNull Predicate<InventoryClickEvent>... handlers) {
        int length = handlers.length;
        if (length != boundSlots.length)
            throw new IllegalArgumentException();
        handlerMap = new Int2ObjectArrayMap<>(length);
        for (int index = 0; index < length; index++)
            handlerMap.put(boundSlots[index], handlers[index]);
        inventory = Bukkit.createInventory(this, size);
    }
    @NotNull
    @Override
    public Inventory getInventory() {
        return inventory;
    }
    @Override
    public boolean click(int slot, InventoryClickEvent event) {
        Predicate<InventoryClickEvent> handler = handlerMap.get(slot);
        return handler != null && handler.test(event);
    }
}