package io.github.meeples10.simplehomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandDelHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("simplehomes.delhome")) {
                Player p = (Player) sender;
                String name = "home";
                if(args.length > 0) {
                    name = args[0];
                }
                name = name.replaceAll("[^A-Za-z0-9_\\-]", "_");
                Home home = Main.getHome(p.getUniqueId(), name);
                if(home != null) {
                    Main.HOMES.get(p.getUniqueId()).remove(home);
                    sender.sendMessage(String.format(Main.homeDeletedMessage, name));
                } else {
                    sender.sendMessage(Main.homeNotFoundMessage);
                }
            } else {
                sender.sendMessage(Main.noPermissionMessage);
            }
        } else {
            sender.sendMessage(Main.playersOnlyMessage);
        }
        return true;
    }
}
