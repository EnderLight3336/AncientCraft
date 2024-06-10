package me.enderlight3336.ancientcraft.recipe;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.*;

public final class RecipeManager {
    private final List<Entry> unhandled = new ArrayList<>();
    private static final Map<String, List<NamespacedKey>> recipeKey = new HashMap<>();
    public void init() {
        for (Entry entry : unhandled) {
            JSONObject recipes = entry.json;
            List<NamespacedKey> keyList = recipeKey.computeIfAbsent(entry.instance.getId(), k -> new ArrayList<>());
            if (recipes.containsKey("furnace")) {
                JSONArray array = recipes.getJSONArray("furnace");
                int index = 0;
                while (index < array.size()) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    index++;
                    NamespacedKey key = KeyManager.genRecipeKey("furnace", index, entry.instance.getId());
                    keyList.add(key);
                    Bukkit.addRecipe(new FurnaceRecipe(key,
                            entry.instance.createItem(recipeDesc.getIntValue("resultAmount")),
                            Material.valueOf(recipeDesc.getString("input").toUpperCase(Locale.ROOT)),
                            recipeDesc.getFloatValue("exp"),
                            recipeDesc.getIntValue("cookingTime")
                    ));
                }
            } else if (recipes.containsKey("shapedCraft")) {
                JSONArray array = recipes.getJSONArray("shapedCraft");
                int index = 0;
                while (index < array.size()) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    index++;
                    JSONArray recipe = recipeDesc.getJSONArray("recipe");
                    if (recipe.size() == 9) {
                        NamespacedKey key = KeyManager.genRecipeKey("shapedCraft", index, entry.instance.getId());
                        keyList.add(key);
                        Bukkit.addRecipe(new ShapedRecipe(key,
                                entry.instance.createItem(recipeDesc.getIntValue("resultAmount"))).shape("a", "b", "c")
                                .setIngredient('a', new RecipeChoice.ExactChoice(handleItem(recipe.getString(0)), handleItem(recipe.getString(1)), handleItem(recipe.getString(2))))
                                .setIngredient('b', new RecipeChoice.ExactChoice(handleItem(recipe.getString(3)), handleItem(recipe.getString(4)), handleItem(recipe.getString(5))))
                                .setIngredient('c', new RecipeChoice.ExactChoice(handleItem(recipe.getString(6)), handleItem(recipe.getString(7)), handleItem(recipe.getString(8))))
                        );
                    } else {
                        AncientCraft.getInstance().getLogger().warning("Find unavailable recipe, skip it!" + recipeDesc);
                    }
                }
            } else if (recipes.containsKey("shapelessCraft")) {
                JSONArray array = recipes.getJSONArray("shapedCraft");
                int index = 0;
                while (index < array.size()) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    index++;
                    JSONArray recipe = recipeDesc.getJSONArray("recipe");
                    if(recipe.size() < 10 && recipe.size() != 0) {
                        NamespacedKey key = KeyManager.genRecipeKey("shapelessCraft", index, entry.instance.getId());
                        keyList.add(key);
                        ShapelessRecipe sr = new ShapelessRecipe(key, entry.instance.createItem(recipeDesc.getIntValue("resultAmount")));
                        recipe.forEach(o -> sr.addIngredient(new RecipeChoice.ExactChoice(handleItem((String) o))));
                        Bukkit.addRecipe(sr);
                    }
                }
            }
        }
    }
    public void put(JSONObject jsonObject, ItemInstance instance) {
        unhandled.add(new Entry(instance, jsonObject));
    }
    public static ItemStack handleItem(String str) {
        return str.startsWith("ac:") ? ItemManager.getById(str.substring(3)).createItem() :
                str.startsWith("ancientcraft:") ? ItemManager.getById(str.substring(13)).createItem() :
                        new ItemStack(Material.valueOf(str.toUpperCase(Locale.ROOT)));
    }
    public static RecipeManager recipeManager = new RecipeManager();
    public static final class Entry {
        final ItemInstance instance;
        final JSONObject json;
        public Entry(ItemInstance instance, JSONObject json) {
            this.instance = instance;
            this.json = json;
        }
    }
}
