package me.mynelife.timelapsebuilder.listener;

import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockPlaceEvent;

public class SetBlockListener implements Listener {
    
    public TimelapseBuilder plugin;
    
    public SetBlockListener(TimelapseBuilder plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onSetBlock(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if(plugin.getActiveBlockManager() != null && plugin.getActiveBlockManager().getName() != "") {        
            plugin.getActiveBlockManager().saveBlock(block);
        }
    }    
}
