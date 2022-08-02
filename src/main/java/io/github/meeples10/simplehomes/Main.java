package io.github.meeples10.simplehomes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;

public class Main extends JavaPlugin implements Listener {

    public static final String NAME = "SimpleHomes";
    public static final Map<UUID, List<Home>> HOMES = new HashMap<>();
    private static File df, cfg, homesDir;
    static String playersOnlyMessage;
    static String noPermissionMessage;
    static String homeSetMessage;
    static String homeExistsMessage;
    static String homeDeletedMessage;
    static String homeNotFoundMessage;
    static String noHomesMessage;
    static String teleportMessage;
    public static boolean playSound;
    public static Sound teleportSound;
    public static boolean resetVelocity;

    @Override
    public void onEnable() {
        df = Bukkit.getServer().getPluginManager().getPlugin(NAME).getDataFolder();
        cfg = new File(df, "config.yml");
        homesDir = new File(df, "homes");
        this.getCommand("sethome").setExecutor(new CommandSetHome());
        this.getCommand("home").setExecutor(new CommandHome());
        this.getCommand("homes").setExecutor(new CommandHomes());
        this.getCommand("delhome").setExecutor(new CommandDelHome());
        Bukkit.getPluginManager().registerEvents(this, Bukkit.getPluginManager().getPlugin(NAME));
        loadConfig();
    }

    @Override
    public void onDisable() {
        for(UUID uuid : HOMES.keySet()) {
            try {
                saveHomes(uuid);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean loadConfig() {
        try {
            if(!df.exists()) {
                df.mkdirs();
            }
            if(!cfg.exists()) {
                Bukkit.getPluginManager().getPlugin(NAME).saveDefaultConfig();
            }
            if(!homesDir.exists()) {
                homesDir.mkdir();
            }
            FileConfiguration c = YamlConfiguration.loadConfiguration(cfg);
            playSound = c.getBoolean("play-sound", true);
            teleportSound = Sound.sound(Key.key(c.getString("teleport-sound")), Sound.Source.PLAYER, 1f, 1f);
            resetVelocity = c.getBoolean("reset-velocity", false);
            ConfigurationSection messages = c.getConfigurationSection("messages");
            playersOnlyMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("players-only", "This command can only be used by players."));
            noPermissionMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("no-permission", "You do not have permission to use this command."));
            homeSetMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("home-set", "Home set: %s"));
            homeExistsMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("home-exists", "Home already exists: %s"));
            homeDeletedMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("home-deleted", "Home deleted: %s"));
            homeNotFoundMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("home-not-found", "Home not found."));
            noHomesMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("no-homes", "You have no homes."));
            teleportMessage = ChatColor.translateAlternateColorCodes('&',
                    messages.getString("teleport", "Teleported to %s"));
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        List<Home> list = new ArrayList<>();
        File f = new File(homesDir, e.getPlayer().getUniqueId().toString() + ".yml");
        if(!f.exists()) {
            HOMES.put(e.getPlayer().getUniqueId(), list);
            return;
        }
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        for(String key : c.getKeys(false)) {
            list.add(Home.load(c.getConfigurationSection(key)));
        }
        HOMES.put(e.getPlayer().getUniqueId(), list);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        try {
            saveHomes(e.getPlayer().getUniqueId());
        } catch(IOException e1) {
            e1.printStackTrace();
        }
        HOMES.remove(e.getPlayer().getUniqueId());
    }

    private static void saveHomes(UUID uuid) throws IOException {
        File f = new File(homesDir, uuid.toString() + ".yml");
        FileConfiguration c = YamlConfiguration.loadConfiguration(f);
        for(Home home : HOMES.get(uuid)) {
            home.save(c);
        }
        c.save(f);
    }

    public static Home getHome(UUID uuid, String name) {
        for(Home home : HOMES.get(uuid)) {
            if(home.name.equalsIgnoreCase(name)) return home;
        }
        return null;
    }
}
