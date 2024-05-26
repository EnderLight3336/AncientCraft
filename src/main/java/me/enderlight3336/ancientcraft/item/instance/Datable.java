package me.enderlight3336.ancientcraft.item.instance;

import me.enderlight3336.ancientcraft.item.data.ItemData;

import java.util.List;

public interface Datable<T extends ItemData> {
    List<T> getRefs();
}
