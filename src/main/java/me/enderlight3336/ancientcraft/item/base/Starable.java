package me.enderlight3336.ancientcraft.item.base;

import org.bukkit.entity.Player;

public interface Starable {
    boolean addStar(Player p);
    void setStar(int target);
    int getStar();
}
