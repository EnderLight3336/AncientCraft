package me.enderlight3336.ancientcraft.command;

import me.enderlight3336.ancientcraft.AncientCraft;
import me.enderlight3336.ancientcraft.item.Util;
import me.enderlight3336.ancientcraft.item.instance.Items;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MainCommand extends Command{
    public MainCommand() {
        super("ancientcraft");
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (strings.length > 0) {
            switch (strings[0]) {
                case "version" -> commandSender.sendMessage("========AncientCraft========", "    version: " + AncientCraft.getVersion());
                case "updateitem" -> {}
                case "give" -> {
                    if(commandSender instanceof Player) {
                        ((Player) commandSender).getInventory().addItem(Util.createItem(Items.valueOf(strings[1]).getInstance()));
                    }
                }
            }
        } else {}
        return true;
    }

}