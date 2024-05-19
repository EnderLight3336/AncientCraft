package me.enderlight3336.ancientcraft.util;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSON;
import me.enderlight3336.ancientcraft.AncientCraft;

import java.io.*;

public class FileUtil {
    static final File fold;
    static {
        fold = AncientCraft.getInstance().getDataFolder();
    }
    public static void init(){
        try {
            if(!fold.exists()) {
                fold.mkdirs();
            }
            File item = new File(fold + "/item");
            item.mkdirs();
            writeFromJar(fold, "/config.json");
            writeFromJar(fold, "/item/DragonSword.json");
            writeFromJar(fold, "/item/HeavySword.json");
            writeFromJar(fold, "/item/NanometerSword.json");
            writeFromJar(fold, "/item/ShortSword.json");
            writeFromJar(fold, "/item/SwordOfHeaven.json");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void writeFromJar(File root, String fileName) throws IOException {
        File target = new File(root + fileName);
        target.createNewFile();
        write(target, AncientCraft.class.getResourceAsStream(fileName));
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
    public static void write(File f, InputStream input){
        try (FileOutputStream output = new FileOutputStream(f)) {
            output.write(input.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rewriteJson() {}
    public static JSONObject getJSON(String fileName){
        try {
            return JSON.parseObject(new FileReader(fold + "/" + fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
