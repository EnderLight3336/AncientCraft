package me.enderlight3336.ancientcraft.item.data;

import me.enderlight3336.ancientcraft.item.instance.Starable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface StarData {
    default void addStar(@NotNull Player player, Starable instance) {
        if (instance.prepareAddStar(player, getStar())) {
            player.sendMessage("打星成功");
            addStar();
        } else {
            player.sendMessage("打星失败");
        }
    }

    void addStar();

    void setStar(int i);

    int getStar();
}
