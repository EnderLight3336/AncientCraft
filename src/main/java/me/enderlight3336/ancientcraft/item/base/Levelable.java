package me.enderlight3336.ancientcraft.item.base;

public interface Levelable {
    int getLevel();
    void addExp(int exp);
    void setLevel(int currentLevel);
    void levelUp(int targetLevel);
}
