package me.mynelife.timelapsebuilder.listener;

import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class SetBlockListener implements Listener {
    
    private TimelapseBuilder plugin;
    
    public SetBlockListener(TimelapseBuilder plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onSetBlock(BlockPlaceEvent e) {
        Location loc = e.getBlockPlaced().getLocation();
        e.getPlayer().sendMessage(loc.toString());
    }
}
