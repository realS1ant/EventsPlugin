package club.s1ant.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Event {

    private EventsPlugin main = EventsPlugin.getInstance();
    private static Event instance;
    private boolean started = false;
    private ArrayList<Player> players =  new ArrayList<Player>();
    private int startCount;

    public boolean isInEvent(Player p){
        if(!isStarted()) return false;
        return players.contains(p);
    }
    public void addPlayer(Player p){
        if(!isStarted()) return;
        players.add(p);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void removePlayer(Player p){
        if(!isStarted()) return;
        players.remove(p);
        if(players.size() <= 1){
            if(players.size() < 1){
                winMessage();
            }else {
                winMessage(players.get(0));
                endEvent();
            }
        }
    }
    public void clearList(){
        players.clear();
    }

    public int getPlayerCount(){
        if(!isStarted()) return 0;
        return players.size();
    }

    public boolean isStarted(){
        return started;
    }

    public int getStartCount() { return startCount; }

    public void startEvent() {
        this.started = true;
        for(Player p : Bukkit.getOnlinePlayers()){
            addPlayer(p);
        }
        this.startCount = players.size();
    }

    public void endEvent(){
        this.started = false;
        clearList();
        startCount = 0;
    }

    public static Event getInstance(){
        if(instance == null){
            instance = new Event();
        }
        return instance;
    }

    private void winMessage(Player winner){
        Bukkit.broadcastMessage(main.getColorOne() + "------------EVENT ENDED------------");
        Bukkit.broadcastMessage(main.getColorOne() + "Winner: " + main.getColorTwo() + winner.getName());
        Bukkit.broadcastMessage(main.getColorOne() + "Congratulations " + winner.getName() + " and thank you for" +
                " playing everyone!");
    }
    private void winMessage(){
        Bukkit.broadcastMessage(main.getColorOne() + "------------EVENT ENDED------------");
        Bukkit.broadcastMessage(main.getColorOne() + "No one won! :(");
        Bukkit.broadcastMessage(main.getColorOne() + "Thank you for playing everyone!");
    }
}
