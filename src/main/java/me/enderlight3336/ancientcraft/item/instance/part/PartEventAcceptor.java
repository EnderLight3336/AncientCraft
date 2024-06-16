package me.enderlight3336.ancientcraft.item.instance.part;

import org.bukkit.event.Event;

public interface PartEventAcceptor<T extends Event> {
    /**
     * Shouldn't call this method direct, call {@link PartEventAcceptor#accept(Event, int)} instantly
     */
    void execute(T event, int partLevel);

    /**
     * @throws ClassCastException May throw if you don't check whether event can be cast
     */
    @SuppressWarnings("unchecked")
    default void accept(Event event, int partLevel) {
        execute((T) event, partLevel);
    }

    String getId();
}
