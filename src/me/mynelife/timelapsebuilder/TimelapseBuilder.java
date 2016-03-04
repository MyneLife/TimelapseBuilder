package me.mynelife.timelapsebuilder;

import java.io.File;
import me.mynelife.timelapsebuilder.commands.CommandManager;
import me.mynelife.timelapsebuilder.listener.BreakBlockListener;
import me.mynelife.timelapsebuilder.listener.SetBlockListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class TimelapseBuilder extends JavaPlugin implements Listener {
    
    private static boolean enabled;
    private static int resetBuildingsBPS;
    private static int buildBuildingsBPS;
    public static long resetBuildingsTickrate;
    public static long buildBuildingsTickrate;
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
            resetBuildingsTickrate = setResetBuildingsTickrate();
            buildBuildingsTickrate = setBuildBuildingsTickrate();
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
        getConfig().addDefault("BlocksPerSecond.resetBuildings", 10);
        getConfig().addDefault("BlocksPerSecond.buildBuildings", 4);
        
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
    
    public void loadConfiguration() {
        // General
        enabled = getConfig().getBoolean("Config.enabled");
        
        //Building-Speeds
        resetBuildingsBPS = getConfig().getInt("BlocksPerSecond.resetBuildings");
        buildBuildingsBPS = getConfig().getInt("BlocksPerSecond.buildBuildings");
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
    
    public static long setResetBuildingsTickrate() {
        double resetTickrate = 20.0 / (double) resetBuildingsBPS;
        return (long) resetTickrate;
    }
    
    public static long setBuildBuildingsTickrate() {
        double buildTickrate = 20.0 / (double) buildBuildingsBPS;
        return (long) buildTickrate;
    }    
}
