package me.mynelife.timelapsebuilder.commands;

import org.bukkit.entity.Player;

public abstract class GameCommand {
    
    public abstract void onCommand(Player p, String[] args);
    
}
