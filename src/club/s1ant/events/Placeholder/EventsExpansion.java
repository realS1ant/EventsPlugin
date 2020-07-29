package club.s1ant.events.Placeholder;

import club.s1ant.events.Event;
import club.s1ant.events.EventsPlugin;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class EventsExpansion extends PlaceholderExpansion {

    private EventsPlugin eventsPlugin;
    private Event eventClass = Event.getInstance();

    public EventsExpansion(EventsPlugin pl){
        this.eventsPlugin = pl;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public String getIdentifier() {
        return "events";
    }

    @Override
    public String getAuthor() {
        return "S1ant";
    }

    @Override
    public String getVersion() {
        return eventsPlugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){
        // %eventplugin_playercount%
        if(identifier.equals("playercount")){
            return String.valueOf(eventClass.getPlayerCount());
        }
        if(identifier.equals("eventstarted")){
            return (eventClass.isStarted() ? "Yes" : "No");
        }
        if(identifier.equals("inevent")){
            if(player == null){
                return null;
            }
            return (eventClass.isInEvent(player) ? "Yes" : "No");
        }
        return null;
    }
}
