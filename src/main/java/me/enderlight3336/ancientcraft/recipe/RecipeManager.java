package me.enderlight3336.ancientcraft.recipe;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.ItemManager;
import me.enderlight3336.ancientcraft.item.instance.ItemInstance;
import me.enderlight3336.ancientcraft.item.instance.type.ItemDatable;
import me.enderlight3336.ancientcraft.util.KeyManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.*;

public final class RecipeManager {
    private final List<Entry> unhandled = new ArrayList<>();
    private static final Map<String, List<NamespacedKey>> recipeKeyMap = new HashMap<>();
    public static RecipeManager recipeManager = new RecipeManager();

    public void init() {
        for (Entry entry : unhandled) {
            JSONObject recipeJSON = entry.json;
            ItemInstance instance = entry.instance;
            List<NamespacedKey> keyList = recipeKeyMap.computeIfAbsent(instance.getId(), k -> new ArrayList<>());
            if (recipeJSON.containsKey("furnace")) {
                JSONArray array = recipeJSON.getJSONArray("furnace");
                int index = 0;
                while (index < array.size()) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    index++;
                    NamespacedKey key = KeyManager.genRecipeKey("furnace", index, instance.getId());
                    keyList.add(key);
                    Bukkit.addRecipe(new FurnaceRecipe(key,
                            instance instanceof ItemDatable<?> ?
                                    ((ItemDatable<?>) instance).getPreviewItem() :
                                    instance.createItem(recipeDesc.getIntValue("resultAmount")),
                            Material.valueOf(recipeDesc.getString("input").toUpperCase(Locale.ROOT)),
                            recipeDesc.getFloatValue("exp"),
                            recipeDesc.getIntValue("cookingTime")
                    ));
                }
            } else if (recipeJSON.containsKey("shapedCraft")) {
                JSONArray array = recipeJSON.getJSONArray("shapedCraft");
                int index = 0;
                while (index < array.size()) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    index++;
                    NamespacedKey key = KeyManager.genRecipeKey("shapedCraft", index, instance.getId());
                    keyList.add(key);
                    ShapedRecipe sr = new ShapedRecipe(key,
                            instance instanceof ItemDatable ?
                                    ((ItemDatable<?>) instance).getPreviewItem() :
                                    instance.createItem(recipeDesc.getIntValue("resultAmount")))
                            .shape(recipeDesc.getJSONArray("sharp").toArray(String.class));
                    if (recipeDesc.containsKey("a"))
                        sr.setIngredient('a', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("a"))));
                    if (recipeDesc.containsKey("b"))
                        sr.setIngredient('b', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("b"))));
                    if (recipeDesc.containsKey("c"))
                        sr.setIngredient('c', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("c"))));
                    if (recipeDesc.containsKey("d"))
                        sr.setIngredient('d', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("d"))));
                    if (recipeDesc.containsKey("e"))
                        sr.setIngredient('e', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("e"))));
                    if (recipeDesc.containsKey("f"))
                        sr.setIngredient('f', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("f"))));
                    if (recipeDesc.containsKey("g"))
                        sr.setIngredient('g', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("g"))));
                    if (recipeDesc.containsKey("h"))
                        sr.setIngredient('h', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("h"))));
                    if (recipeDesc.containsKey("i"))
                        sr.setIngredient('i', new RecipeChoice.ExactChoice(handleItems(recipeDesc.getJSONArray("i"))));
                    Bukkit.addRecipe(sr);
                }
            } else if (recipeJSON.containsKey("shapelessCraft")) {
                JSONArray array = recipeJSON.getJSONArray("shapelessCraft");
                for (int index = 0; index < array.size(); index++) {
                    JSONObject recipeDesc = array.getJSONObject(index);
                    JSONArray recipe = recipeDesc.getJSONArray("recipe");
                    if (recipe.size() < 10 && recipe.size() != 0) {
                        NamespacedKey key = KeyManager.genRecipeKey("shapelessCraft", index, instance.getId());
                        keyList.add(key);
                        ShapelessRecipe sr = new ShapelessRecipe(key, instance instanceof ItemDatable ?
                                ((ItemDatable<?>) instance).getPreviewItem() :
                                instance.createItem(recipeDesc.getIntValue("resultAmount")));
                        recipe.forEach(o -> sr.addIngredient(new RecipeChoice.ExactChoice(handleItems(o))));
                        Bukkit.addRecipe(sr);
                    } else AncientCraft.getInstance().getLogger().severe("Invalid file ! \n" + recipe);
                }
            }
        }

        recipeManager = null;
    }

    public void put(JSONObject jsonObject, ItemInstance instance) {
        unhandled.add(new Entry(instance, jsonObject));
    }

    public static List<ItemStack> handleItems(Object obj) {
        if (obj instanceof JSONArray array) {
            List<ItemStack> ret = new ArrayList<>(array.size());
            for (int index = 0; index < array.size(); index++)
                ret.add(handleItem(array.getString(index)));
            return ret;
        } else if (obj instanceof String str) {
            return List.of(handleItem(str));
        } else throw new IllegalArgumentException("Invalid arg! \n" + obj.toString());
    }

    public static ItemStack handleItem(String str) {
        return str.startsWith("ac:") ? ItemManager.getById(str.substring(3)).createItem() :
                str.startsWith("ancientcraft:") ? ItemManager.getById(str.substring(13)).createItem() :
                        new ItemStack(Material.valueOf(str.toUpperCase(Locale.ROOT)));
    }

    public static Map<String, List<NamespacedKey>> getRecipeKeyMap() {
        return recipeKeyMap;
    }

    public static final class Entry {
        final ItemInstance instance;
        final JSONObject json;

        public Entry(ItemInstance instance, JSONObject json) {
            this.instance = instance;
            this.json = json;
        }
    }
}
