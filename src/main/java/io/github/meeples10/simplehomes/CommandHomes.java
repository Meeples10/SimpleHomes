package io.github.meeples10.simplehomes;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandHomes implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("simplehomes.homes")) {
                Player p = (Player) sender;
                List<Home> homes = Main.HOMES.get(p.getUniqueId());
                if(homes.size() == 0) {
                    sender.sendMessage(Main.noHomesMessage);
                    return true;
                }
                StringBuilder sb = new StringBuilder();
                int i = 0;
                for(Home home : homes) {
                    sb.append(ChatColor.DARK_AQUA);
                    sb.append(home.name);
                    if(i < homes.size() - 1) {
                        sb.append(ChatColor.GRAY);
                        sb.append(", ");
                    }
                    i++;
                }
                sender.sendMessage(sb.toString());
            } else {
                sender.sendMessage(Main.noPermissionMessage);
            }
        } else {
            sender.sendMessage(Main.playersOnlyMessage);
        }
        return true;
    }
}
