package me.enderlight3336.ancientcraft.item.base;

import me.enderlight3336.ancientcraft.item.ref.StarRef;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AdvancedTool extends CommonTool implements Starable {
    static final StarRef commonRef = new StarRef();
    int star;
    final List<ItemRef[]> starItem;

    public AdvancedTool(Material material, String name, String id, List<ItemRef[]> list, String... lore) {
        this(material, name, id, 0, list,  lore);
    }
    public AdvancedTool(Material material, String name, String id, int star, List<ItemRef[]> list, String... lore) {
        super(id, name, material, lore);

        this.star = star;
        this.starItem = list;
    }
    @Override
    public boolean addStar(@NotNull Player player) {
        return commonRef.addStar(player, this);
    }

    @Override
    public final int getStar() {
        return star;
    }

}
