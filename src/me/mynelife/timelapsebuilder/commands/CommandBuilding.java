package me.mynelife.timelapsebuilder.commands;

import java.io.File;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

@CommandInfo(description = "Start/stop/pause/continue a building of a Timelapse-Build", usage = "<start | stop | pause | continue> <name>", aliases = {"building", "build"})
public class CommandBuilding extends GameCommand {
    
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
                int blockcounter = cfg.getInt("0");
                for(int i = blockcounter; i > 1; i--) {
                    String blockdata = cfg.getString(String.valueOf(blockcounter));
                    System.out.println(blockdata);
                    blockdata = blockdata.replace("[", "");
                    blockdata = blockdata.replace("]", "");
                    String[] data = blockdata.split(",");
                    
                    int x = Integer.valueOf(data[1]);
                    int y = Integer.valueOf(data[2]);
                    int z = Integer.valueOf(data[3]);
                    
                }
            }
        }
    }
}
