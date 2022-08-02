package io.github.meeples10.simplehomes;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.kyori.adventure.sound.Sound;

public class CommandHome implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            if(sender.hasPermission("simplehomes.home")) {
                Player p = (Player) sender;
                String name = "home";
                if(args.length > 0) {
                    name = args[0];
                }
                name = name.replaceAll("[^A-Za-z0-9_\\-]", "_");
                Home home = Main.getHome(p.getUniqueId(), name);
                if(home != null) {
                    if(Main.resetVelocity) {
                        p.setVelocity(new Vector(0, 0, 0));
                        p.setFallDistance(0.0f);
                    }
                    p.teleport(home.location);
                    if(Main.playSound) {
                        p.playSound(Main.teleportSound, Sound.Emitter.self());
                    }
                    sender.sendMessage(String.format(Main.teleportMessage, name));
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
