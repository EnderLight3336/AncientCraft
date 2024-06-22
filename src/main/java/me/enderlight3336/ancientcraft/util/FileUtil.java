package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;

import java.io.*;
import java.nio.charset.StandardCharsets;

public final class FileUtil {
    static final File fold = AncientCraft.getInstance().getDataFolder();
    public static final File PLAYER_DATA_FOLDER = new File(fold, "data/player");

    public static final File SACK_DATA_FOLDER = new File(fold, "data/sack");
    public static void init() {
        File item = new File(fold + "/meta");
        File config = new File(fold, "config.json");
        if (!fold.exists()) {
            fold.mkdirs();
        }

        item.mkdirs();

        if (config.exists()) {
            JSONObject ne = JSON.parseObject(AncientCraft.class.getResourceAsStream("/config.json"), StandardCharsets.UTF_8);
            try {
                ne.putAll(JSON.parseObject(new FileInputStream(config), StandardCharsets.UTF_8));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            ConfigInstance.init(ne);
            write(config, ne.toString());
        } else {
            writeFromJar(fold, "/config.json");
            ConfigInstance.init();
        }
    }

    public static void writeFromJar(File root, String fileName) {
        File target = new File(root + fileName);
        try {
            target.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        write(target, AncientCraft.class.getResourceAsStream(fileName));
    }

    public static void write(File f, String s) {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void write(File f, InputStream input) {
        try (FileOutputStream output = new FileOutputStream(f)) {
            input.transferTo(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static JSONObject getJSONByFile(File f) {
        try (FileInputStream input = new FileInputStream(f)) {
            return JSON.parseObject(input, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getItemDataFolder(String id) {
        return new File(fold, "data/item/" + id);
    }

    /**
     * if file not exists, it will create it
     *
     * @param name should be like this format :   /xxx/xx
     */
    public static JSONObject getJSON(String name) {
        File f = new File(fold, name);
        if (!f.exists()) {
            write(f, AncientCraft.class.getResourceAsStream(name));
        }
        return getJSONByFile(f);
    }
}
