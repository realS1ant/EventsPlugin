package club.s1ant.events;

import club.s1ant.events.Commands.EventCMD;
import club.s1ant.events.Listeners.Listeners;
import club.s1ant.events.Placeholder.EventsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsPlugin extends JavaPlugin {

    private static EventsPlugin instance;
    private Location jailLocation;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        instance = this;
        getCommand("event").setExecutor(new EventCMD());
        getServer().getPluginManager().registerEvents(new Listeners(), this);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new EventsExpansion(this).register();
        } else {
            getLogger().severe("PlaceholderAPI not found!");
        }

    }
    public String getPrefix() { return ChatColor.translateAlternateColorCodes('&', getConfig()
                .getString("prefix") + " " + getConfig().getString("primarycolor")); }
    public String getColorOne() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("primarycolor"));
    }
    public String getColorTwo() {
        return ChatColor.translateAlternateColorCodes('&', getConfig().getString("secondarycolor"));
    }
    public Location getJailLocation(){
        if(jailLocation == null){
            reloadJailLoacation();
        }
        return jailLocation;
    }
    public void reloadJailLoacation(){
        reloadConfig();
        this.jailLocation = new Location(getServer().getWorld("world"), getConfig().getDouble("jail.x"), getConfig().getDouble("jail.y"), getConfig().getDouble("jail.z"));
    }
    public static EventsPlugin getInstance(){
        return instance;
    }
}
