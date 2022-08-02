package io.github.meeples10.simplehomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSetHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("simplehomes.sethome")) {
                Player p = (Player) sender;
                String name = "home";
                if(args.length > 0) {
                    name = args[0];
                }
                name = name.replaceAll("[^A-Za-z0-9_\\-]", "_");
                if(Main.getHome(p.getUniqueId(), name) != null) {
                    sender.sendMessage(String.format(Main.homeExistsMessage, name));
                } else {
                    Main.HOMES.get(p.getUniqueId()).add(new Home(name, p.getLocation()));
                    sender.sendMessage(String.format(Main.homeSetMessage, name));
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
