package me.mynelife.timelapsebuilder.commands;

import java.io.File;
import me.mynelife.timelapsebuilder.TimelapseBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.World;

@CommandInfo(description = "Start/stop/pause/continue/reset a building of a Timelapse-Build", usage = "<start | stop | pause | continue | reset> <name>", aliases = {"building", "build"})
public class CommandBuilding extends GameCommand {
    
    //Counter for Bukkit-Scheduler on reset command
    static int countReset;
    //Counter for Bukkit-Scheduler on build command
    static int countBuild;
    //Counter from name.yml-file to count blockindex
    static int blockcounter;
    //Counter for building-process  
    static int counter;
    //Is the build-process running or on pause?
    static boolean buildingOnPause;
    
    @Override
    public void onCommand(Player p, String[] args) {        
        
        //Stop building-process
        if(args.length >= 1 && args[0].equalsIgnoreCase("stop")) {
            if(!p.hasPermission("tlb.building.stop")) {  
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            Bukkit.getScheduler().cancelTask(countBuild);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have stopped the build-process!"));                
            
            
        //Pause building-process
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("pause")) {
            if(!p.hasPermission("tlb.building.pause")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            buildingOnPause = true;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have paused the build-process!"));                                     
            
            
        //Continue building-process    
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("continue")) {
            if(!p.hasPermission("tlb.building.continue")) {  
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            buildingOnPause = false;
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The build-process will be continued!"));
            
            
        //Start building    
        } else if(args.length >= 2 && args[0].equalsIgnoreCase("start")) {
            if(!p.hasPermission("tlb.building.start")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(!file.exists()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &4" + name + "&6 doesn't exists!"));
                return;
            } 
            YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            String worldname = cfg.getString("world");
            if(!p.getWorld().getName().equals(worldname)) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You have to go into the world called &2" + worldname + "&6 to build this building!"));
                return;
            }
            blockcounter = cfg.getInt("0");
            counter = 1;
            buildingOnPause= false;
            countBuild = Bukkit.getScheduler().scheduleSyncRepeatingTask(TimelapseBuilder.getProvidingPlugin(getClass()), new Runnable() {                                        

                @Override
                public void run() {
                    if(!buildingOnPause) {
                        if(counter < blockcounter) {
                            String blockdata = cfg.getString(String.valueOf(counter));
                            blockdata = blockdata.replace("[", "");
                            blockdata = blockdata.replace("]", "");
                            String[] data = blockdata.split(",");
                            //Set or Remove?
                            String mode = data[0];
                            //Coordinates                   
                            int x = Integer.valueOf(data[1]);
                            int y = Integer.valueOf(data[2]);
                            int z = Integer.valueOf(data[3]);                            
                            //World
                            String worldname = cfg.getString("world");
                            World world = p.getServer().getWorld(worldname);
                            //Data & Material
                            byte id = Byte.valueOf(data[4]);
                            String material = data[5];

                            if(mode.equalsIgnoreCase("set")) {
                                world.getBlockAt(x, y, z).setType(Material.getMaterial(material));
                                world.getBlockAt(x, y, z).setData(id);
                            } else if(mode.equalsIgnoreCase("remove")) {
                                world.getBlockAt(x, y, z).setType(Material.AIR);
                            }
                            counter++;
                        } else {
                            Bukkit.getScheduler().cancelTask(countBuild);
                            counter = 1;
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &2" + name + "&6 built successfully!"));
                        }
                    }
                }
            }, 0L, TimelapseBuilder.buildBuildingsTickrate);

            
        //Reset building    
        } else if(args.length >= 2 && args[0].equalsIgnoreCase("reset")) {
            if(!p.hasPermission("tlb.building.reset")) { 
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
                return;
            }
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(!file.exists()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &4" + name + "&6 doesn't exists!"));                
            } else {
                YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                blockcounter = cfg.getInt("0");
                blockcounter--;
                countReset = Bukkit.getScheduler().scheduleSyncRepeatingTask(TimelapseBuilder.getProvidingPlugin(getClass()), new Runnable() {                                        

                    @Override
                    public void run() {
                        if(blockcounter >= 1) {
                            String blockdata = cfg.getString(String.valueOf(blockcounter));
                            blockdata = blockdata.replace("[", "");
                            blockdata = blockdata.replace("]", "");
                            String[] data = blockdata.split(",");
                            //Set or Remove?
                            String mode = data[0];
                            //Coordinates                   
                            int x = Integer.valueOf(data[1]);
                            int y = Integer.valueOf(data[2]);
                            int z = Integer.valueOf(data[3]);                            
                            //World
                            String worldname = cfg.getString("world");
                            World world = p.getServer().getWorld(worldname);
                            //Data & Material
                            byte id = Byte.valueOf(data[4]);
                            String material = data[5];

                            if(mode.equalsIgnoreCase("remove")) {
                                world.getBlockAt(x, y, z).setType(Material.getMaterial(material));
                                world.getBlockAt(x, y, z).setData(id);
                            } else if(mode.equalsIgnoreCase("set")) {
                                world.getBlockAt(x, y, z).setType(Material.AIR);
                            }
                            blockcounter--;
                        } else {
                            Bukkit.getScheduler().cancelTask(countReset);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &2" + name + "&6 reseted successfully!"));
                        }
                    }
                }, 0L, TimelapseBuilder.resetBuildingsTickrate);
            }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] Please use &2/tlb build &6to see all vaild commands!"));
        }
    }
}
