package me.enderlight3336.ancientcraft.item.instance;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.loot.CustomLootTableManager;
import me.enderlight3336.ancientcraft.recipe.RecipeManager;
import me.enderlight3336.ancientcraft.util.ConfigInstance;
import me.enderlight3336.ancientcraft.util.ItemUtil;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ItemInstance {
    protected final String id;
    protected final String name;
    protected final Material material;
    protected final List<String> lore = new ArrayList<>();
    protected final ItemStack originItem;

    public ItemInstance(String id, Material material, String name, List<String> lore) {
        if (ConfigInstance.isBeta()) {
            this.id = "beta_" + id;
        } else {
            this.id = id;
        }
        this.name = name;
        this.material = material;

        this.lore.addAll(lore);
        this.originItem = new ItemStack(material);

        ItemMeta im = originItem.getItemMeta();
        im.setDisplayName(name);
        im.setLore(this.lore);

        originItem.setItemMeta(im);
    }

    public ItemInstance(JSONObject itemInfo) {
        if (ConfigInstance.isBeta()) {
            this.id = "beta_" + itemInfo.getString("id");
        } else {
            this.id = itemInfo.getString("id");
        }
        this.name = itemInfo.getString("name");
        this.material = Material.valueOf(itemInfo.getString("material").toUpperCase(Locale.ROOT));
        this.lore.addAll(itemInfo.getJSONArray("lore").toJavaList(String.class));

        originItem = new ItemStack(this.material);
        ItemMeta im = originItem.getItemMeta();
        im.setDisplayName(this.name);
        ItemUtil.setId(im, id);
        im.setLore(this.lore);

        if (itemInfo.containsKey("baseAttributes")) {
            JSONObject attributes = itemInfo.getJSONObject("baseAttributes");
            attributes.forEach((str, o) -> im.addAttributeModifier(Attribute.valueOf(str.toUpperCase()),
                    new AttributeModifier("ancientcraft", Double.parseDouble(o.toString()), AttributeModifier.Operation.ADD_NUMBER)));
        }

        if (im instanceof Damageable && itemInfo.containsKey("durability")) {
            int damage = itemInfo.getIntValue("durability");
            ((Damageable) im).setMaxDamage(damage);
        }

        if (itemInfo.containsKey("baseEnchantments")) {
            JSONObject enchantments = itemInfo.getJSONObject("baseEnchantments");
            if (enchantments.size() == 1 && enchantments.containsKey("unbreaking")) {
                im.addEnchant(Enchantment.UNBREAKING, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                enchantments.forEach((s, o) -> im.addEnchant(
                        Registry.ENCHANTMENT.get(NamespacedKey.minecraft(s.toLowerCase(Locale.ROOT))), Integer.parseInt(o.toString()), true));
            }
        }

        if (itemInfo.containsKey("potionInfo")) {
            JSONObject potion = itemInfo.getJSONObject("potionInfo");
            PotionMeta pm = (PotionMeta) im;
            pm.setBasePotionType(PotionType.valueOf(potion.getString("baseType").toUpperCase()));
            JSONObject color = potion.getJSONObject("color");
            pm.setColor(Color.fromRGB(color.getIntValue("red"), color.getIntValue("green"), color.getIntValue("blue")));
            JSONArray effect = potion.getJSONArray("effect");
            int index = 0;
            while (index < effect.size()) {
                JSONObject jsonObject = effect.getJSONObject(index);
                index++;
                pm.addCustomEffect(new PotionEffect(Registry.EFFECT.get(NamespacedKey.minecraft(jsonObject.getString("type"))),
                        jsonObject.getIntValue("time") * 20, jsonObject.getIntValue("level")), true);
            }
        }

        if (itemInfo.containsKey("foodInfo")) {
            FoodComponent food = im.getFood();
            JSONObject foodInfo = itemInfo.getJSONObject("foodInfo");
            food.setCanAlwaysEat(foodInfo.getBooleanValue("canAlwaysEat"));
            food.setEatSeconds(foodInfo.getFloatValue("eatSeconds"));
            food.setNutrition(foodInfo.getIntValue("nutrition"));
            food.setSaturation(foodInfo.getFloatValue("saturation"));
            if (foodInfo.containsKey("effect")) {
                JSONArray effect = foodInfo.getJSONArray("effect");
                int index = 0;
                while (index < effect.size()) {
                    JSONObject e = effect.getJSONObject(index);
                    index++;
                    foodInfo.addEffect()
                }
            }
        }

        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.originItem.setItemMeta(im);

        if (itemInfo.containsKey("recipes")) {
            RecipeManager.recipeManager.put(itemInfo.getJSONObject("recipes"), this);
        }

        if (itemInfo.containsKey("looting")) {
            CustomLootTableManager.put(this, itemInfo.getJSONArray("looting"));
        }

        ItemManager.regItem(this);
    }


    /**
     * called when prepare create {@link ItemStack}
     *
     * @return a copy of {@link ItemInstance#originItem}
     * @see #createItem()
     */
    public ItemStack createItem() {
        return originItem.clone();
    }

    /**
     * called when prepare create {@link ItemStack}
     *
     * @return a copy of {@link ItemInstance#originItem}
     * @throws RuntimeException if extends {@link DataItem}
     * @see ItemManager#createItem(String, int)
     * @see DataItem#createItem(int)
     */
    public ItemStack createItem(int amount) {
        ItemStack item = originItem.clone();
        item.setAmount(amount);
        return item;
    }

    public final String getId() {
        return id;
    }

    public final String getName() {
        return name;
    }

    public Material getMaterial() {
        return material;
    }

    public List<String> getLore() {
        return lore;
    }

    public boolean checkType(String typeName) {
        return typeName.equals("item");
    }
}
