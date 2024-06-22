package me.enderlight3336.ancientcraft.item;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.hoe.BaseHoe;
import me.enderlight3336.ancientcraft.item.instance.misc.ClimberHook;
import me.enderlight3336.ancientcraft.item.instance.misc.MelonKnife;
import me.enderlight3336.ancientcraft.item.instance.part.PartFarmhand;
import me.enderlight3336.ancientcraft.item.instance.part.PartSuckBlood;
import me.enderlight3336.ancientcraft.item.instance.part.base.AttributePart;
import me.enderlight3336.ancientcraft.item.instance.part.base.EnchantmentPart;
import me.enderlight3336.ancientcraft.item.instance.sword.DragonSword;
import me.enderlight3336.ancientcraft.item.instance.sword.HeavySword;
import me.enderlight3336.ancientcraft.item.instance.sword.LongSword;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import me.enderlight3336.ancientcraft.util.AsyncDataSaver;
import me.enderlight3336.ancientcraft.util.FileUtil;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class ItemManager {
    private static final Map<String, ItemInstance> registeredItem = new HashMap<>();

    public static ItemStack createItem(String id) {
        return registeredItem.get(id).createItem();
    }

    public static ItemStack createItem(String id, int amount) {
        return registeredItem.get(id).createItem(amount);
    }

    public static boolean isACItem(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().has(KeyManager.getIdKey());
    }

    public static void regItem(ItemInstance item) {
        String id = item.getId();
        if (registeredItem.containsKey(id))
            AncientCraft.getInstance().getLogger().warning("Find repetitive id! Skip register it.Id: " + id);
        else
            registeredItem.put(id, item);
    }

    public static boolean checkId(String id) {
        return registeredItem.containsKey(id);
    }

    public static ItemInstance getById(@Nullable String id) {
        if (id == null)
            return null;
        return registeredItem.get(id);
    }

    @Nullable
    public static ItemInstance getItemInstance(ItemStack item) {
        if (item.hasItemMeta())
            return registeredItem.get(item.getItemMeta().getPersistentDataContainer().get(KeyManager.getIdKey(), PersistentDataType.STRING));
        return null;
    }

    public static void modifyItemData(ItemStack target, ItemDatable<?> itemInstance, Consumer<ItemData> consumer) {
        int index = ItemUtil.getDataId(target);
        ItemData data1 = itemInstance.getDataList().get(index);
        consumer.accept(data1);
        AsyncDataSaver.put(itemInstance.getId(), index, data1);
    }

    public static Map<String, ItemInstance> getRegisteredItem() {
        return registeredItem;
    }

    public static void init() {
        new ItemInstance(FileUtil.getJSON("/meta/ItemCorruptedHeart.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemDragonBlood.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemHoaryBone.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemRefinedCopper.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemRefinedDiamond.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemRefinedGold.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemRefinedIron.json"));
        new ItemInstance(FileUtil.getJSON("/meta/ItemSpicyStrip.json"));
        new ClimberHook(FileUtil.getJSON("/meta/ItemClimberHook.json"));
        new MelonKnife(FileUtil.getJSON("/meta/ItemMelonKnife.json"));
        //part must load before other items
        //Because dataItem may try to find some part's instance
        new PartSuckBlood(FileUtil.getJSON("/meta/PartAbSuckBlood.json"));
        new PartFarmhand(FileUtil.getJSON("/meta/PartAbFarmhand.json"));
        new AttributePart(FileUtil.getJSON("/meta/PartAtHealth.json"));
        new AttributePart(FileUtil.getJSON("/meta/PartAtJump.json"));
        new AttributePart(FileUtil.getJSON("/meta/PartAtPlayerblockrange.json"));
        new AttributePart(FileUtil.getJSON("/meta/PartAtSavefall.json"));
        new AttributePart(FileUtil.getJSON("/meta/PartAtToughness.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnEfficiency.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnFortune.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnLooting.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnProtection.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnRespiration.json"));
        new EnchantmentPart(FileUtil.getJSON("/meta/PartEnSharpness.json"));
        //then load other items
        new BaseHoe(FileUtil.getJSON("/meta/HoeBaseHoe.json"));
        new DragonSword(FileUtil.getJSON("/meta/SwordDragonSword.json"));
        new HeavySword(FileUtil.getJSON("/meta/SwordHeavySword.json"));
        new LongSword(FileUtil.getJSON("/meta/SwordLongSword.json"));
    }

    public static void reload() {
        registeredItem.clear();
        init();
    }
}
