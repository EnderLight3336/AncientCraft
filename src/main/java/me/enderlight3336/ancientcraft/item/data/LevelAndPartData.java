package me.enderlight3336.ancientcraft.item.data;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.PartEventAcceptor;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Map;

public interface LevelAndPartData extends ItemData {

    List<String> rebuildLore(List<String> origin);

    List<String> loreChangeOnLevel(List<String> origin);

    List<String> loreChangeOnExp(List<String> origin);

    List<String> loreChangeOnPart(List<String> origin);

    int getFreeSlot();

    void setExp(int i);

    void setLevel(int i);

    /**
     * @param i how many exp will add to this item
     * @return -1 means not level up, other means level up and return targetLevel
     */
    int addExp(int i);

    int getExp();

    int getLevel();

    void addPart(String id);

    int removePart(String id);

    int getPartLevel(String id);

    void acceptEvent(Event event, ItemInstance itemInMainHand);

    void registerEventPart(Map<String, PartEventAcceptor<?>> map);
}
