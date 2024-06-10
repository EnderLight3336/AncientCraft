package me.enderlight3336.ancientcraft.item.data;

import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import org.bukkit.event.Event;

import java.util.List;

public interface LevelAndPartData extends ItemData {

    List<String> rebuildLore(List<String> origin);

    List<String> loreChangeOnLevel(List<String> origin);

    List<String> loreChangeOnExp(List<String> origin);

    List<String> loreChangeOnPart(List<String> origin);

    int getFreeSlot();

    void setExp(int i);

    void setLevel(int i);

    int addExp(int i);

    int getExp();

    int getLevel();

    void addPart(String id);

    int removePart(String id);

    int getPartLevel(String id);

    void acceptEvent(Event event, ItemInstance itemInMainHand);

    void registerEventPart(AbilityPart<?> part);
}
