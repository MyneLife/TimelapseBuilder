package me.mynelife.timelapsebuilder;

import java.io.File;
import me.mynelife.timelapsebuilder.commands.CommandManager;
import me.mynelife.timelapsebuilder.listener.BreakBlockListener;
import me.mynelife.timelapsebuilder.listener.SetBlockListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TimelapseBuilder extends JavaPlugin implements Listener {
    
    private static boolean enabled;
    private static int resetBuildingsTickrate;
    private static int buildBuildingsTickrate;
    private static BlockManager activeBlockManager;
    private final File folder = new File("plugins//TimelapseBuilder");
    
    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        defaultConfiguration();
        loadConfiguration();
        if(enabled) {
            System.out.println("[Timelapse Builder] TLB is enabled! Loading options...");
            registerListener();
            registerCommands();
            activeBlockManager = null;
            if(!folder.exists()) {
                folder.mkdir();
            }   
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
        new BreakBlockListener(this);
    }
    
    public void registerCommands() {
        getCommand("tlb").setExecutor(new CommandManager());
    }
    
    public void defaultConfiguration() {
        // General
        getConfig().addDefault("Config.enabled", true);
        
        //BuildingSpeeds
        getConfig().addDefault("Rates.resetBuildingsTicks", 4);
        getConfig().addDefault("Rates.buildBuildingsTicks", 4);
        
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    
    public void loadConfiguration() {
        // General
        enabled = getConfig().getBoolean("Config.enabled");
        
        //Building-Speeds
        resetBuildingsTickrate = getConfig().getInt("Rates.resetBuildingsTicks");
        resetBuildingsTickrate = getConfig().getInt("Rates.buildBuildingsTicks");
    } 
    
    public static BlockManager getActiveBlockManager() {
        return activeBlockManager;
    }
    
    public static void setActiveBlockManager(String name, String worldname) {
        activeBlockManager = new BlockManager(name, worldname);
    }
    
    public static void resetActiveBlockManager() {
        activeBlockManager = null;
    }
    
    public static int getResetBuildingsTickrate() {
        return resetBuildingsTickrate;
    }
    
    public static int getBuildBuildingsTickrate() {
        return buildBuildingsTickrate;
    }    
}
