package club.s1ant.events.Commands;

import club.s1ant.events.Event;
import club.s1ant.events.EventsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCMD implements CommandExecutor {

    private EventsPlugin main = EventsPlugin.getInstance();
    private Event eventClass = Event.getInstance();
    String prefix = main.getPrefix();
    String color1 = main.getColorOne();
    String color2 = main.getColorTwo();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(!sender.hasPermission(main.getConfig().getString("permission"))){
            sender.sendMessage(prefix + ChatColor.RED + "No permission!");
            return false;
        }

        if(args.length == 0){
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',color1 + ChatColor.BOLD + "Events " + ChatColor.GRAY + "(1/1)\n" +
                    color1 + "/event start &8- " + color2 + "Start up the event!\n" +
                    color1 + "/event count &8- " + color2 + "Get the number of people in the event!\n" +
                    color1 + "/event revive <player> &8- " + color2 + "Tells you if player is in the event.\n" +
                    color1 + "/event isalive <player> &8- " + color2 + "Tells you if player is in the event.\n" +
                    color1 + "/event exclude <player> &8- " + color2 + "Remove a player from the event.\n" +
                    color1 + "/event setjail &8- " + color2 + "Sets the location for the jail where the player's respawn."
            ));
            return false;
        }

        switch (args[0]) {
            case "start":
                eventClass.startEvent();
                Bukkit.broadcastMessage(prefix + "The event has begun!");
                sender.sendMessage(prefix + "Adding everyone in the server.");
                return false;
            case "count":
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                sender.sendMessage(prefix + "Event count: " + color2 + eventClass.getPlayerCount());
                return false;
            case "revive":
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                if(args.length < 2){
                    sender.sendMessage(prefix + ChatColor.RED + "Please specify a player!");
                    return false;
                }
                Player player = Bukkit.getPlayer(args[1]);
                if(player == null){
                    sender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return false;
                }
                if(eventClass.isInEvent(player)){
                    sender.sendMessage(prefix + ChatColor.RED + "That player is still in the event!");
                    return false;
                }
                eventClass.addPlayer(player);
                sender.sendMessage(prefix + color2 + player.getName() + color1 + " has been revived.");
                player.sendMessage(prefix + color1 + ChatColor.translateAlternateColorCodes('&', main.getConfig().getString("revived")));
                return false;
            case "isalive":
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                if(args.length < 2){
                    sender.sendMessage(prefix + ChatColor.RED + "Please specify a player!");
                    return false;
                }
                Player p = Bukkit.getPlayer(args[1]);
                if(p == null){
                    sender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return false;
                }
                sender.sendMessage(prefix + color2 + p.getName() + color1 + " is " + (eventClass.isInEvent(p) ? ChatColor.GREEN + "ALIVE" : ChatColor.RED + "DEAD"));
                return false;
            case "exclude":
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                if(args.length < 2){
                    sender.sendMessage(prefix + ChatColor.RED + "Please specify a player!");
                    return false;
                }
                Player pl = Bukkit.getPlayer(args[1]);
                if(pl == null){
                    sender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return false;
                }
                if(!eventClass.isInEvent(pl)){
                    sender.sendMessage(prefix + ChatColor.RED + "That player is not in the event!");
                }
                eventClass.removePlayer(pl);
                sender.sendMessage(prefix + color2 + pl.getName() + color1 + " has been excluded from the event!");
                pl.sendMessage(prefix + color1 + "You've been excluded from the event.");
                return false;
            case "setjail":
                if(!(sender instanceof Player)){
                    sender.sendMessage(prefix + ChatColor.RED + "Sorry! This is a player-only command!");
                    return false;
                }
                Location loc = ((Player) sender).getLocation();
                main.getConfig().set("jail.x", loc.getX());
                main.getConfig().set("jail.y", loc.getY());
                main.getConfig().set("jail.z", loc.getZ());
                main.getConfig().set("jail.world", loc.getWorld());
                main.saveConfig();
                main.reloadJailLoacation();
                sender.sendMessage(prefix + "Successfully updated jail location to: (" + color2 + loc.getX() + ","
                        + loc.getZ() + color1 + ")");
                return false;
            case "tpalive":
                if(!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "Sorry, you can't do that!");
                    return false;
                }
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                sender.sendMessage(prefix + "Teleporting all alive players.");
                Location l = ((Player) sender).getLocation();
                for(Player t : eventClass.getPlayers()){
                    t.teleport(l);
                }
                return false;
            case "tpdead":
                if(!eventClass.isStarted()){
                    sender.sendMessage(prefix + ChatColor.RED + "The event hasn't started!");
                    return false;
                }
                if(!(sender instanceof Player)) {
                    sender.sendMessage(prefix + "Sorry, you can't do that!");
                    return false;
                }
                sender.sendMessage(prefix + "Teleporting all dead players.");
                Location location = ((Player) sender).getLocation();
                for(Player t : main.getServer().getOnlinePlayers()){
                    if(!eventClass.getPlayers().contains(t)) t.teleport(location);
                }
                return false;
            default:
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&',color1 + ChatColor.BOLD + "Events " + ChatColor.GRAY + "(1/1)\n" +
                        color1 + "/event start &8- " + color2 + "Start up the event!\n" +
                        color1 + "/event count &8- " + color2 + "Get the number of people in the event!\n" +
                        color1 + "/event revive <player> &8- " + color2 + "Tells you if player is in the event.\n" +
                        color1 + "/event isalive <player> &8- " + color2 + "Tells you if player is in the event.\n" +
                        color1 + "/event exclude <player> &8- " + color2 + "Remove a player from the event.\n" +
                        color1 + "/event setjail &8- " + color2 + "Sets the location for the jail where the player's respawn.\n" +
                        color1 + "Plugin created by "+color2+"S1ant"+color1+"."
                ));
                return false;
        }
    }
}
