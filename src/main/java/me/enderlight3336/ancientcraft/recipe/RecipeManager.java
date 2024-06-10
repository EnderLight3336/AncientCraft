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
                    NamespacedKey key = KeyManager.genRecipeKey("shapedCraft", index, entry.instance.getId());
                    keyList.add(key);
                    ShapedRecipe sr = new ShapedRecipe(key,
                            entry.instance.createItem(recipeDesc.getIntValue("resultAmount")))
                                .shape(recipeDesc.getJSONArray("sharp").toArray(String.class));
                    int index1 = 1;
                    if (recipeDesc.containKey("a")) sr.setIngredient('a', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("a"))));
                    if (recipeDesc.containKey("b")) sr.setIngredient('b', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("b"))));
                    if (recipeDesc.containKey("c")) sr.setIngredient('c', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("c"))));
                    if (recipeDesc.containKey("d")) sr.setIngredient('d', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("d"))));
                    if (recipeDesc.containKey("e")) sr.setIngredient('e', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("e"))));
                    if (recipeDesc.containKey("f")) sr.setIngredient('f', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("f"))));
                    if (recipeDesc.containKey("g")) sr.setIngredient('g', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("g"))));
                    if (recipeDesc.containKey("h")) sr.setIngredient('h', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("h"))));
                    if (recipeDesc.containKey("i")) sr.setIngredient('i', new RecipeChoice.ExtraChoice(handleItems(recipeDesc.getString("i"))));
                    Bukkit.addRecipe(sr);
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
    public static ItemStack handleItems(JSONArray array) {
        ItemStack[] ret = new ItemStack[array.size()];
        int index = 0;
        while (index < array.size()) {
            ret[index] = handleItem(array.get(index));
            index++;
        }
        return ret;
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
