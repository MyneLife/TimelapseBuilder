package me.mynelife.timelapsebuilder.listener;

import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BreakBlockListener implements Listener {
    
    public TimelapseBuilder plugin;
    
    public BreakBlockListener(TimelapseBuilder plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
    
    @EventHandler
    public void onSetBlock(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(plugin.getActiveBlockManager() != null && plugin.getActiveBlockManager().getName() != "") {        
            plugin.getActiveBlockManager().removeBlock(block);
        }
    }
}
