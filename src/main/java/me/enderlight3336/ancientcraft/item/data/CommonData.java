package me.enderlight3336.ancientcraft.item.data;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.part.PartEventAcceptor;
import me.enderlight3336.ancientcraft.item.instance.part.base.AbilityPart;
import me.enderlight3336.ancientcraft.item.instance.part.base.BasePart;
import me.enderlight3336.ancientcraft.item.instance.sword.ISword;
import me.enderlight3336.ancientcraft.listener.acceptor.EventAcceptor;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommonData implements ItemData, LevelAndPartData {
    protected int exp;
    protected int level;
    protected final List<String> lore = new ArrayList<>();
    protected final Map<String, Integer> parts = new HashMap<>();
    protected final Map<String, List<PartEventAcceptor<?>>> eventPart = new HashMap<>();

    {
        lore.add("模块:");
        lore.add(null);
        lore.add("==========");
        lore.add(null);
        lore.add(null);
    }

    public CommonData() {
        exp = 0;
        level = 0;
    }

    @SuppressWarnings("unchecked")
    public CommonData(@NotNull JSONObject json) {
        exp = json.getIntValue("exp");
        level = json.getIntValue("level");
        json.getJSONObject("parts").forEach((s, o) -> parts.put(s, (Integer) o));
        parts.forEach((s, integer) -> {
            ItemInstance instance = ItemManager.getById(s);
            if(instance != null) {
                if(instance instanceof AbilityPart<?>) {
                    registerEventPart((AbilityPart<?>) instance);
                }
            }
        });
    }

    @Override
    public void registerEventPart(AbilityPart<?> part) {
        for (String s : part.getListenedEventNames()) {
            if(eventPart.containsKey(s)) {
                List<PartEventAcceptor<?>> list = eventPart.get(s);
                if(!list.contains(part)) {
                    list.add(part);
                }
            }
        }
    }

    @Override
    public void acceptEvent(Event event, ItemInstance itemInMainHand) {
        List<PartEventAcceptor<?>> list = eventPart.get(event.getEventName());
        if(list != null) {
            list.forEach(eventAcceptor -> eventAcceptor.accept(event, parts.get(eventAcceptor.getId())));
        }
    }

    /**
     * Only be called on an item creation
     */
    @Override
    public List<String> buildLore() {
        List<String> l = new ArrayList<>(lore);
        l.set(1, "  无");
        l.set(3, "Level: " + level);
        l.set(4, "Exp: " + exp + " / " + ConfigInstance.getNeedExp(level));
        return l;
    }

    @Override
    public List<String> rebuildLore(List<String> origin) {//todo
        return null;
    }

    @Override
    public List<String> loreChangeOnLevel(List<String> origin) {
        int startIndex = origin.size() - 5;
        this.loreChangeOnExp(origin);
        origin.set(startIndex + 3, "Level: " + level);
        return origin;
    }

    @Override
    public List<String> loreChangeOnExp(List<String> origin) {
        int startIndex = origin.size() - 5;
        origin.set(startIndex + 4, "Exp:" + exp + " / " + ConfigInstance.getNeedExp(level));
        return origin;
    }

    @Override
    public List<String> loreChangeOnPart(List<String> origin) {
        int startIndex = origin.size() - 5;
        if (parts.size() == 0) {
            origin.set(startIndex + 1, "  无");
        } else {
            StringBuilder sb = new StringBuilder();
            parts.forEach((s, integer) -> sb.append(((BasePart) ItemManager.getById(s)).getPartName()).append(integer).append(", "));
            origin.set(startIndex + 1, sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1).toString());
        }
        return origin;
    }

    @Override
    public void setExp(int i) {
        this.exp = i;
    }

    @Override
    public void setLevel(int i) {
        this.level = i;
    }

    /**
     * @param i how many exp will add to this item
     * @return true if level up, false means not level up
     */
    @Override
    public boolean addExp(int i) {
        int expCap = ConfigInstance.getNeedExp(level);
        this.exp = this.exp + i;
        if (this.exp >= expCap) {
            this.exp = this.exp - expCap;
            this.level++;
            return true;
        }
        return false;
    }

    @Override
    public int getExp() {
        return exp;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void addPart(String id) {
        parts.compute(id, (s, integer) -> integer != null ? integer + 1 : 1);
    }

    @Override
    public int removePart(String id) {
        return parts.remove(id);
    }

    /**
     * @return the Part's level, 0 means not exist
     */
    @Override
    public int getPartLevel(String id) {
        return parts.getOrDefault(id, 0);
    }


    @Override
    public String toJsonString() {
        StringBuilder sb = new StringBuilder("{\"exp\":" + exp + ",\"level\":" + level + ",\"parts\":{");
        parts.forEach((s, integer) -> sb.append("\"").append(s).append("\":").append(integer));
        return sb.append("}}").toString();
    }
}
