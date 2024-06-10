package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.item.data.ItemData;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.function.Function;

public final class DataList<T extends ItemData> {
    private final ArrayDeque<Integer> freeIndex = new ArrayDeque<>();
    private final ArrayList<T> data;

    public DataList(@NotNull File fold, Function<JSONObject, T> function) {
        if (fold.exists()) {
            File[] files = fold.listFiles();
            if (files != null) {
                data = new ArrayList<>(files.length + 10);
                for (File file : files) {
                    if (file.getName().chars().allMatch(Character::isDigit)) {
                        int index = Integer.parseInt(file.getName());
                        while (data.size() < index) {
                            freeIndex.add(data.size());
                            data.add(null);
                        }
                        data.add(function.apply(FileUtil.getJSONByFile(file)));
                    }
                }
            } else {
                data = new ArrayList<>();
            }
        } else {
            data = new ArrayList<>();
            fold.mkdirs();
        }
    }

    public void remove(int index) {
        freeIndex.add(index);
        data.remove(index);
    }

    /**
     * @param v data
     * @return the index of the data
     */
    public int put(T v) {
        Integer i = freeIndex.poll();
        if (i != null) {
            data.set(i, v);
            return i;
        } else {
            data.add(v);
            return data.size() - 1;
        }
    }

    public T get(int index) {
        return data.get(index);
    }
}
