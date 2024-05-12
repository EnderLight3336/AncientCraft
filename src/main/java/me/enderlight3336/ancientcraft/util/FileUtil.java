package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import me.enderlight3336.ancientcraft.AncientCraft;

import java.io.*;

public class FileUtil {
    static final File fold;
    static {
        fold = AncientCraft.getInstance().getDataFolder();
    }
    public static void init() throws Exception {
        if(!fold.exists()) {
            fold.mkdirs();
            File f = new File(fold + "/config.json");
            f.createNewFile();
            write(f, AncientCraft.getInstance().getResource("config.json"));
        }
        File item = new File(fold + "/item");
        item.mkdirs();
        writeItemData("sword.json");
    }
    public static void writeItemData(String fileName) throws Exception {
        File target = new File(fold + "/" + fileName);
        target.createNewFile();
        write(target, AncientCraft.getInstance().getResource(fileName));
    }
    public static void write(File f, String s) throws Exception {
        try (FileWriter fw = new FileWriter(f)) {
            fw.write(s);
        }
    }
    public static void write(File f, InputStream input) throws Exception {
        try (FileOutputStream output = new FileOutputStream(f)) {
            output.write(input.readAllBytes());
        }
    }
    public static void rewriteJson() {}
    public static void rewriteYaml() {}
}
