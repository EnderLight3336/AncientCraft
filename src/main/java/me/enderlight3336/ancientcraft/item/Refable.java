package me.enderlight3336.ancientcraft.item;

public interface Refable <T extends ItemRef> {
    T getRef(int index);
    List<T> getRefs();
}