package me.enderlight3336.ancientcraft.item.instance.misc;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.CommonData;
import me.enderlight3336.ancientcraft.item.instance.DataItem;
import me.enderlight3336.ancientcraft.item.instance.ItemEventable;
import me.enderlight3336.ancientcraft.item.instance.type.IFarmTool;
import me.enderlight3336.ancientcraft.item.instance.type.ISword;
import me.enderlight3336.ancientcraft.item.instance.type.ItemLevelAndPartable;
import me.enderlight3336.ancientcraft.util.AsyncDataSaver.Entry;
import org.bukkit.Bukkit;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.Lootable;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Random;

public class MelonKnife extends DataItem<CommonData> implements ISword, IFarmTool, ItemLevelAndPartable<CommonData>, ItemEventable {
    public MelonKnife(JSONObject json) {
        super(json);
    }

    @Override
    public Entry genNewData(ItemStack item) {
        CommonData data1 = new CommonData();
        return new Entry(data.put(data1), data1);
    }

    @Override
    public @NotNull CommonData readData(JSONObject json) {
        return new CommonData(json);
    }

    @Override
    public void on(Event event1) {
        if (event1 instanceof EntityDamageByEntityEvent event) {
            EntityDeathEvent entityDeathEvent = new EntityDeathEvent((LivingEntity) event.getEntity(),
                    DamageSource.builder(DamageType.PLAYER_ATTACK).build(),
                    (List<ItemStack>) ((Lootable) event.getEntity()).getLootTable().populateLoot(new Random(),
                            new LootContext.Builder(event.getEntity().getLocation()).killer((HumanEntity) event.getDamager()).lootedEntity(event.getEntity()).lootingModifier(((HumanEntity) event.getDamager()).getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOTING)).build()));
            Bukkit.getPluginManager().callEvent(entityDeathEvent);
            if (!event.isCancelled()) {
                event.getDamager().sendMessage("test only!");
            }
        }
    }
}
