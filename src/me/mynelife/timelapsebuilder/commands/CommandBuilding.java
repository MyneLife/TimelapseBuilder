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
    
    static int count;
    static int blockcounter;
    
    @Override
    public void onCommand(Player p, String[] args) {
        
        //Permission
        if(!(p.hasPermission("tlb.building.start")) || !(p.hasPermission("tlb.building.stop")) || !(p.hasPermission("tlb.building.pause")) || !(p.hasPermission("tlb.building.continue"))) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] You don't have &4permissions &6to use this command!"));
            return;
        }
        
        if(args.length >= 1 && args[0].equalsIgnoreCase("stop")) {
            return;
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("pause")) {
            return;
        } else if(args.length >= 1 && args[0].equalsIgnoreCase("continue")) {
            return;
        } else if(args.length >= 2 && args[0].equalsIgnoreCase("start")) {
            String name = args[1];
            return;
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
                count = Bukkit.getScheduler().scheduleSyncRepeatingTask(TimelapseBuilder.getProvidingPlugin(getClass()), new Runnable() {                                        

                    @Override
                    public void run() {
                        if(blockcounter >= 1) {
                            System.out.println("blockcounter: " + String.valueOf(blockcounter));
                            String blockdata = cfg.getString(String.valueOf(blockcounter));
                            System.out.println("BlockDaten: " + blockdata);
                            System.out.println("Schleife");
                            blockdata = blockdata.replace("[", "");
                            blockdata = blockdata.replace("]", "");
                            System.out.println("BlockDaten später: " + blockdata);
                            String[] data = blockdata.split(",");
                            System.out.println(data);
                            //Coordinates                   
                            int x = Integer.valueOf(data[1]);
                            int y = Integer.valueOf(data[2]);
                            int z = Integer.valueOf(data[3]);
                            //Set or Remove?
                            String mode = data[0];
                            //World
                            String worldname = cfg.getString("world");
                            World world = p.getServer().getWorld(worldname);
                            System.out.println("Welt: ");
                            System.out.println(world);
                            //Data & Material
                            byte id = Byte.valueOf(data[4]);
                            System.out.println("Byte: " + String.valueOf(id));
                            String material = data[5];
                            System.out.println("Material: " + material);

                            if(mode.equalsIgnoreCase("remove")) {
                                System.out.println("Remove");
                                world.getBlockAt(x, y, z).setType(Material.getMaterial(material));
                                world.getBlockAt(x, y, z).setData(id);
                            } else if(mode.equalsIgnoreCase("set")) {
                                System.out.println("Set");
                                world.getBlockAt(x, y, z).setType(Material.AIR);
                            }
                            blockcounter--;
                        } else {
                            Bukkit.getScheduler().cancelTask(count);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6[Timelapse Builder] The building called &2" + name + "&6 reseted successfully!"));
                        }
                    }
                }, 0L, Long.valueOf(TimelapseBuilder.getResetBuildingsTickrate()));
                return;
            }
        }
    }
}
