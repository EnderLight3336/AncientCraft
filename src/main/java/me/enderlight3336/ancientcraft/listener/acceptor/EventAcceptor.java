package me.enderlight3336.ancientcraft.listener.acceptor;

import org.bukkit.event.Event;

public interface EventAcceptor<T extends Event> {
    /**
     * Shouldn't call this method direct, call {@link EventAcceptor#accept(Event)} instantly
     */
    void execute(T event);

    /**
     * @throws ClassCastException May throw if you don't check whether event can be cast
     */
    @SuppressWarnings("unchecked")
    default void accept(Event event) {
        execute((T) event);
    }
}
