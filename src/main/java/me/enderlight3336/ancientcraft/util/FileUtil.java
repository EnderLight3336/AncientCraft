package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class FileUtil {
    static final File fold;

    static {
        fold = AncientCraft.getInstance().getDataFolder();
    }

    public static void init() {
        try {
            if (!fold.exists()) {
                fold.mkdirs();
            }
            File item = new File(fold + "/meta");
            item.mkdirs();
            writeFromJar(fold, "/config.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFromJar(File root, String fileName) throws IOException {
        File target = new File(root + fileName);
        target.createNewFile();
        write(target, AncientCraft.class.getResourceAsStream(fileName));
    }

    public static void write(File f, String s) throws Exception {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(s);
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
            return JSON.parseObject(new String(input.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
