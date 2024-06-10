package me.enderlight3336.ancientcraft.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandManager {
    private static final Map<String, SubCommand> commandMap = new HashMap<>();

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

    @Nullable
    public static List<String> getTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    public static void execute(CommandSender sender, String[] args) {
        if (args.length > 0) {
            commandMap.getOrDefault(args[0], nullCommand).execute(sender, args);
        }
    }

    public static void register(SubCommand command) {
        commandMap.put(command.name, command);
    }

    private static final SubCommand nullCommand = new SubCommand("") {
        @Override
        void execute(CommandSender sender, String[] args) {
            sender.sendMessage("找不到该命令");
        }
    };
}