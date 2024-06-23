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
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

public class ItemInstance {
    protected final String id;
    protected final String name;
    protected final Material material;
    protected final List<String> lore = new ArrayList<>();
    protected final ItemStack originItem;
    public static final UUID HEAD_UUID = UUID.fromString("570995BA-8733-604D-1A6D-0BDB1B6366B0");

    public ItemInstance(JSONObject itemJSON) {
        if (itemJSON.containsKey("food") && itemJSON.containsKey("potionInfo"))
            throw new IllegalStateException("Invalid json! An item cannot be a food while have potionInfo" + itemJSON);

        if (ConfigInstance.isBeta()) {
            this.id = "beta_" + itemJSON.getString("id");
        } else {
            this.id = itemJSON.getString("id");
        }
        this.name = itemJSON.getString("name");
        this.material = Material.valueOf(itemJSON.getString("material").toUpperCase(Locale.ROOT));
        this.lore.addAll(itemJSON.getJSONArray("lore").toJavaList(String.class));

        originItem = new ItemStack(this.material);
        ItemMeta im = originItem.getItemMeta();
        im.setItemName(this.name);
        im.getPersistentDataContainer().set(KeyManager.getIdKey(), PersistentDataType.STRING, id);
        im.setLore(this.lore);

        if (material == Material.PLAYER_HEAD && itemJSON.containsKey("texture")) {
            PlayerProfile profile = Bukkit.createPlayerProfile(HEAD_UUID);
            PlayerTextures textures = profile.getTextures();
            try {
                textures.setSkin(new URI("http://textures.minecraft.net/texture/" + itemJSON.getString("texture")).toURL());
            } catch (URISyntaxException | MalformedURLException e) {
                throw new RuntimeException(e);
            }
            ((SkullMeta) im).setOwnerProfile(profile);
        }

        if (itemJSON.containsKey("baseAttributes")) {
            JSONObject attributeJSON = itemJSON.getJSONObject("baseAttributes");
            attributeJSON.keySet().forEach(s -> {
                Attribute a = Attribute.valueOf(s.toLowerCase(Locale.ROOT));
                im.addAttributeModifier(a,
                        new AttributeModifier("ancientcraft", ItemUtil.handleAttribute(a, this.material, attributeJSON.getDoubleValue(s)), AttributeModifier.Operation.ADD_NUMBER));
            });
        }

        if (im instanceof Damageable && itemJSON.containsKey("durability")) {
            int damage = itemJSON.getIntValue("durability");
            ((Damageable) im).setMaxDamage(damage);
        }

        if (itemJSON.containsKey("baseEnchantments")) {
            JSONObject enchantmentJSON = itemJSON.getJSONObject("baseEnchantments");
            if (enchantmentJSON.size() == 1 && enchantmentJSON.containsKey("unbreaking")) {
                im.addEnchant(Enchantment.UNBREAKING, 1, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            } else {
                enchantmentJSON.forEach((s, o) -> im.addEnchant(
                        Registry.ENCHANTMENT.get(NamespacedKey.minecraft(s.toLowerCase(Locale.ROOT))), Integer.parseInt(o.toString()), true));
            }
        }

        if (itemJSON.containsKey("potionInfo")) {
            JSONObject potion = itemJSON.getJSONObject("potionInfo");
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

        if (itemJSON.containsKey("food")) {
            FoodComponent foodComponent = im.getFood();
            JSONObject foodInfo = itemJSON.getJSONObject("food");
            foodComponent.setCanAlwaysEat(foodInfo.getBooleanValue("canAlwaysEat"));
            foodComponent.setEatSeconds(foodInfo.getFloatValue("eatSeconds"));
            foodComponent.setNutrition(foodInfo.getIntValue("nutrition"));
            foodComponent.setSaturation(foodInfo.getFloatValue("saturation"));
            if (foodInfo.containsKey("effect")) {
                JSONArray effect = foodInfo.getJSONArray("effect");
                for (int index = 0; index < effect.size(); index++) {
                    JSONObject e = effect.getJSONObject(index);
                    foodComponent.addEffect(new PotionEffect(Registry.EFFECT.get(NamespacedKey.minecraft(e.getString("type").toLowerCase(Locale.ROOT))), e.getIntValue("time") * 20, e.getIntValue("level")),
                            e.getFloatValue("chance"));
                }
            }
            im.setFood(foodComponent);
        } else if (im.hasFood()) {
            im.setFood(null);
        }

        if (itemJSON.containsKey("toolRule")) {
            ToolComponent toolComponent = im.getTool();
            JSONArray toolRule = itemJSON.getJSONArray("toolRule");
            for (int index = 0; index < toolRule.size(); index++) {
                JSONObject rule = toolRule.getJSONObject(index);
                JSONArray blockStrArray = rule.getJSONArray("blocks");
                List<Material> blockList = new ArrayList<>(blockStrArray.size());
                for (int index1 = 0; index1 < blockStrArray.size(); index1++)
                    blockList.add(Material.valueOf(blockStrArray.getString(index1).toUpperCase()));
                toolComponent.addRule(blockList, rule.getFloat("speed"), rule.getBoolean("correctForDrops"));
            }
            im.setTool(toolComponent);
        }

        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        this.originItem.setItemMeta(im);

        if (itemJSON.containsKey("recipes")) {
            RecipeManager.recipeManager.put(itemJSON.getJSONObject("recipes"), this);
        }

        if (itemJSON.containsKey("looting")) {
            CustomLootTableManager.put(this, itemJSON.getJSONArray("looting"));
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

    @Override
    public final int hashCode() {
        return id.hashCode();
    }

    @Override
    public final boolean equals(Object obj) {
        return (obj instanceof ItemInstance) && ((ItemInstance) obj).id.equals(this.id);
    }
}
