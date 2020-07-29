package club.s1ant.events.Listeners;

import club.s1ant.events.Event;
import club.s1ant.events.EventsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Listeners implements Listener {
    private Event eventClass = Event.getInstance();
    private EventsPlugin main = EventsPlugin.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(!eventClass.isStarted()) return;
        eventClass.addPlayer(e.getPlayer());
        Bukkit.broadcastMessage(EventsPlugin.getInstance().getPrefix() + "Player Added: " +
                EventsPlugin.getInstance().getColorTwo() + e.getPlayer().getName());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        if(!eventClass.isStarted()) return;
        eventClass.removePlayer(e.getPlayer());
    }
    @EventHandler
    public void onLeave(PlayerKickEvent e){
        if(!eventClass.isStarted()) return;
        eventClass.removePlayer(e.getPlayer());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        if(!eventClass.isStarted()) return;
        eventClass.removePlayer(e.getEntity());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(final PlayerRespawnEvent e){
        e.setRespawnLocation(main.getJailLocation());
        Bukkit.getScheduler().runTaskLater(main, new Runnable() {
            public void run() {
                e.getPlayer().teleport(main.getJailLocation());
            }
        }, 5);
    }
}
