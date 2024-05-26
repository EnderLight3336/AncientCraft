package me.enderlight3336.ancientcraft.listener.acceptor;

import org.bukkit.event.Event;

public interface EventAcceptor<T extends Event> {
    void accept(T event);
}
