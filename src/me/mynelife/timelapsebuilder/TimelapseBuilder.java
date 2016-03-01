package me.mynelife.timelapsebuilder;

import me.mynelife.timelapsebuilder.listener.SetBlockListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TimelapseBuilder extends JavaPlugin implements Listener {
    
    private static boolean enabled;
    private String activeBlockManager;
    
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        defaultConfiguration();
        loadConfiguration();
        if(enabled) {
            System.out.println("[Timelapse Builder] TLB is enabled! Loading options...");
            registerListener();
            registerCommands();
            activeBlockManager = "";
        } else {
            System.out.println("[Timelapse Builder] TLB is disabled in config.yml!");
            System.out.println("[Timelapse Builder] TLB is now disabled!");
            this.getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    @Override
    public void onDisable() {
        System.out.println("[Timelapse Builder] TLB is now disabled!");
    }
    
    public void registerListener() {
        new SetBlockListener(this);
    }
    
    public void registerCommands() {
        //getCommand("tlb").setExecutor(new CommandManager());
    }
    
    public void defaultConfiguration() {
        // General
        getConfig().addDefault("Config.enabled", true);
        
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    
    public void loadConfiguration() {
        // General
        enabled = getConfig().getBoolean("Config.enabled");
    } 
    
    public String getActiveBlockManager() {
        return activeBlockManager;
    }
}
