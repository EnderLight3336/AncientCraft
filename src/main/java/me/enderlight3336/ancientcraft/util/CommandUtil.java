package me.enderlight3336.ancientcraft.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandUtil {
    public static boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }
}