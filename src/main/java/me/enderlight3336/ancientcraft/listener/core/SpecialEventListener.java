package me.enderlight3336.ancientcraft.listener.core;

import me.enderlight3336.ancientcraft.AncientCraft;
import org.bukkit.event.*;
import org.bukkit.plugin.TimedRegisteredListener;
import org.jetbrains.annotations.NotNull;

public abstract class SpecialEventListener<T extends Event> implements Listener {
    public SpecialEventListener(Class<T> eventClass) {
        this(eventClass, EventPriority.NORMAL, false);
    }
    public SpecialEventListener(Class<T> eventClass, boolean ignoreCancelled) {
        this(eventClass, EventPriority.NORMAL, ignoreCancelled);
    }
    public SpecialEventListener(Class<T> eventClass, EventPriority priority) {
        this(eventClass, priority, false);
    }
    public SpecialEventListener(Class<T> eventClass, @NotNull EventPriority eventPriority, boolean ignoreCancelled) {
        try {
            ((HandlerList) eventClass.getMethod("getHandlerList").invoke(null)).register(
                    new TimedRegisteredListener(this, (listener, event) -> {
                        try {
                            ((SpecialEventListener) listener).accept(event);
                        } catch (Exception e) {
                            throw new EventException(e);
                        }
                    }, eventPriority, AncientCraft.getInstance(), ignoreCancelled)
            );
        } catch (Exception e) {
            throw new RuntimeException("ERROR! Register event failed!\n TargetEvent: " + eventClass.getName(), e);
        }
    }
    public abstract void accept(T event);
}
