package me.enderlight3336.ancientcraft.util;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandUtil {
    public static boolean requirePlayer(CommandSender sender) {
        if (isPlayer(sender)) {
            return true;
        } else {
            sender.sendMessage("此命令只允许玩家执行");
            return false;
        }
    }

    public static boolean isPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    public static void sendHelp(CommandSender sender) {
    }
}