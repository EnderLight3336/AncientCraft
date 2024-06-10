package me.enderlight3336.ancientcraft.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SubCommand {
    protected final String name;

    public SubCommand(String name) {
        this.name = name;
    }

    abstract void execute(CommandSender sender, String[] args);

    @Nullable
    public List<String> getTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public String getName() {
        return name;
    }
}
