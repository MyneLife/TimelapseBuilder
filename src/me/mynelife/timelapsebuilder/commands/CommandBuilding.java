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
    
    static int countReset;
    static int countBuild;
    static int blockcounter;
    
    @Override
    public void onCommand(Player p, String[] args) {
        
        //Permission
        if(!(p.hasPermission("tlb.building.start")) || !(p.hasPermission("tlb.building.stop")) || !(p.hasPermission("tlb.building.pause")) || !(p.hasPermission("tlb.building.continue"))) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
            return;
        }
        
        if(args.length >= 1 && args[0].equalsIgnoreCase("stop")) {
            Bukkit.getScheduler().cancelTask(countBuild);
            
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("pause")) {
            
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("continue")) {
            
            
        //Start building    
        } else if(args.length >= 2 && args[0].equalsIgnoreCase("start")) {
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
            blockcounter--;
            countBuild = Bukkit.getScheduler().scheduleSyncRepeatingTask(TimelapseBuilder.getProvidingPlugin(getClass()), new Runnable() {                                        

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

                        if(mode.equalsIgnoreCase("set")) {
                            world.getBlockAt(x, y, z).setType(Material.getMaterial(material));
                            world.getBlockAt(x, y, z).setData(id);
                        } else if(mode.equalsIgnoreCase("remove")) {
                            world.getBlockAt(x, y, z).setType(Material.AIR);
                        }
                        blockcounter--;
                    } else {
                        Bukkit.getScheduler().cancelTask(countBuild);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &2" + name + "&6 built successfully!"));
                    }
                }
            }, 0L, TimelapseBuilder.getBuildBuildingsTickrate());

            
        //Reset building    
        } else if(args.length >= 2 && args[0].equalsIgnoreCase("reset")) {
            String name = args[1];
            File file = new File("plugins//TimelapseBuilder//" + name + ".yml");
            if(!file.exists()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &4" + name + "&6 doesn't exists!"));
                return;
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
                }, 0L, TimelapseBuilder.getResetBuildingsTickrate());
            }
        }
    }
}
